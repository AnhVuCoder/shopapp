package com.ngleanhvu.shopapp.service.impl;

import com.ngleanhvu.shopapp.constant.Constant;
import com.ngleanhvu.shopapp.dto.CartItemDTO;
import com.ngleanhvu.shopapp.dto.OrderDTO;
import com.ngleanhvu.shopapp.entity.*;
import com.ngleanhvu.shopapp.exception.DataNotFoundException;
import com.ngleanhvu.shopapp.repo.IOrderDetailRepo;
import com.ngleanhvu.shopapp.repo.IOrderRepo;
import com.ngleanhvu.shopapp.repo.IProductRepo;
import com.ngleanhvu.shopapp.repo.IUserRepo;
import com.ngleanhvu.shopapp.response.OrderResponse;
import com.ngleanhvu.shopapp.service.IOrderService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderService implements IOrderService {
    @Autowired
    private IOrderRepo iOrderRepo;
    @Autowired
    private IUserRepo iUserRepo;
    @Autowired
    private IProductRepo iProductRepo;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private IOrderDetailRepo iOrderDetailRepo;
    @Override
    @Transactional
    public OrderResponse createOrder(OrderDTO orderDTO) throws DataNotFoundException {
        User user = iUserRepo.findById(orderDTO.getUserId())
                .orElseThrow(() -> new DataNotFoundException(Constant.USER_NOT_FOUND));
        Order order = modelMapper.map(orderDTO, Order.class);
        order.setUser(user);
        order.setOrderDate(LocalDate.now());
        order.setStatus(OrderStatus.PENDING.name());
        LocalDate shippingDate = orderDTO.getShippingDate() == null ? LocalDate.now() : order.getShippingDate();
        // Kiem tra ngay giao hang co sau ngay hom nay khong
        if (shippingDate.isBefore(LocalDate.now())) {
            throw new DataNotFoundException("Date must be at least today");
        }
        order.setShippingDate(shippingDate);
        order.setActive(true);
        iOrderRepo.save(order);
        List<OrderDetail> orderDetails = new ArrayList<>();
        for(CartItemDTO item: orderDTO.getCartItemDTOs()){
            OrderDetail orderDetail = new OrderDetail();
            orderDetail.setOrder(order);
            int productId = item.getProductId();
            Product product = iProductRepo.findById(productId).orElseThrow(() -> new DataNotFoundException(Constant.PRODUCT_NOT_FOUND));
            orderDetail.setProduct(product);
            orderDetail.setPrice(product.getPrice());
            orderDetail.setNumberOfProducts(item.getQuantity());
            orderDetail.setTotalMoney(order.getTotalMoney());
            orderDetails.add(orderDetail);
        }
        iOrderDetailRepo.saveAll(orderDetails);
        return modelMapper.map(order, OrderResponse.class);
    }

    @Override
    public OrderResponse getOrder(Integer id) throws DataNotFoundException {
        Order order = iOrderRepo.findById(id)
                .orElseThrow(() -> new DataNotFoundException(Constant.ORDER_NOT_FOUND));
        OrderResponse orderResponse = modelMapper.map(order, OrderResponse.class);
        return orderResponse;
    }

    @Override
    @Transactional
    public OrderResponse updateOrder(Integer id, OrderDTO orderDTO) throws DataNotFoundException {
        Order order = iOrderRepo.findById(id)
                .orElseThrow(() -> new DataNotFoundException(Constant.ORDER_NOT_FOUND));
        User user = iUserRepo.findById(orderDTO.getUserId())
                .orElseThrow(() -> new DataNotFoundException(Constant.USER_NOT_FOUND));
        modelMapper.typeMap(OrderDTO.class, Order.class)
                .addMappings(mapper -> mapper.skip(Order::setId));
        modelMapper.map(orderDTO, order);
        iOrderRepo.save(order);
        return modelMapper.map(order, OrderResponse.class);
    }

    @Override
    @Transactional
    public void deleteOrder(Integer id) throws DataNotFoundException {
        Order order = iOrderRepo.findById(id)
                .orElseThrow(() -> new DataNotFoundException(Constant.ORDER_NOT_FOUND));
        if (order != null) {
            order.setActive(false);
            iOrderRepo.save(order);
        }
    }

    @Override
    public List<OrderResponse> findByUserId(Integer userId) throws DataNotFoundException {
        User user = iUserRepo.findById(userId)
                .orElseThrow(() -> new DataNotFoundException(Constant.USER_NOT_FOUND));
        return iOrderRepo.findByUserId(userId)
                .stream()
                .map(order -> modelMapper.map(order, OrderResponse.class))
                .collect(Collectors.toList());
    }
}
