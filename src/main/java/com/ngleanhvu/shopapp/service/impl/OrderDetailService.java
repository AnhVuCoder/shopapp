package com.ngleanhvu.shopapp.service.impl;

import com.ngleanhvu.shopapp.constant.Constant;
import com.ngleanhvu.shopapp.dto.OrderDetailDTO;
import com.ngleanhvu.shopapp.entity.Order;
import com.ngleanhvu.shopapp.entity.OrderDetail;
import com.ngleanhvu.shopapp.entity.Product;
import com.ngleanhvu.shopapp.exception.DataNotFoundException;
import com.ngleanhvu.shopapp.repo.IOrderDetailRepo;
import com.ngleanhvu.shopapp.repo.IOrderRepo;
import com.ngleanhvu.shopapp.repo.IProductRepo;
import com.ngleanhvu.shopapp.response.OrderDetailResponse;
import com.ngleanhvu.shopapp.service.IOrderDetailService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderDetailService implements IOrderDetailService {
    private final IOrderDetailRepo iOrderDetailRepo;
    private final IOrderRepo iOrderRepo;
    private final IProductRepo iProductRepo;
    private final ModelMapper modelMapper;

    @Override
    public OrderDetailResponse createOrderDetail(OrderDetailDTO orderDetailDTO) throws DataNotFoundException {
        Order order = iOrderRepo.findById(orderDetailDTO.getOrderId())
                .orElseThrow(() -> new DataNotFoundException(Constant.ORDER_NOT_FOUND));
        Product product = iProductRepo.findById(orderDetailDTO.getProductId())
                .orElseThrow(() -> new DataNotFoundException(Constant.PRODUCT_NOT_FOUND));
        OrderDetail orderDetail = modelMapper.map(orderDetailDTO, OrderDetail.class);
        iOrderDetailRepo.save(orderDetail);
        return modelMapper.map(orderDetail, OrderDetailResponse.class);
    }

    @Override
    public OrderDetailResponse getOrderDetail(Integer id) throws DataNotFoundException {
        OrderDetail orderDetail = iOrderDetailRepo.findById(id)
                .orElseThrow(() -> new DataNotFoundException(Constant.ORDER_NOT_FOUND));
        return modelMapper.map(orderDetail, OrderDetailResponse.class);
    }

    @Override
    public OrderDetailResponse updateOrderDetail(Integer id, OrderDetailDTO orderDetailDTO) throws DataNotFoundException {
        OrderDetail orderDetail = iOrderDetailRepo.findById(id)
                .orElseThrow(() -> new DataNotFoundException(Constant.ORDER_DETAIL_NOT_FOUND));
        Order order = iOrderRepo.findById(orderDetailDTO.getOrderId())
                .orElseThrow(() -> new DataNotFoundException(Constant.ORDER_NOT_FOUND));
        Product product = iProductRepo.findById(orderDetailDTO.getProductId())
                .orElseThrow(() -> new DataNotFoundException(Constant.PRODUCT_NOT_FOUND));
        modelMapper.typeMap(OrderDetailDTO.class, OrderDetail.class)
                .addMappings(mapper -> mapper.skip(OrderDetail::setId));
        modelMapper.map(orderDetailDTO, orderDetail);
        iOrderDetailRepo.save(orderDetail);
        return modelMapper.map(orderDetail, OrderDetailResponse.class);
    }

    @Override
    public List<OrderDetailResponse> getOrderDetails(Integer orderId) {
        return iOrderDetailRepo.findByOrderId(orderId)
                .stream()
                .map(orderDetail -> modelMapper.map(orderDetail, OrderDetailResponse.class))
                .collect(Collectors.toList());
    }

    @Override
    public void deleteOrderDetail(Integer id) {
        Optional<OrderDetail> optionalOrderDetail = iOrderDetailRepo.findById(id);
        optionalOrderDetail.ifPresent(iOrderDetailRepo::delete);
    }
}
