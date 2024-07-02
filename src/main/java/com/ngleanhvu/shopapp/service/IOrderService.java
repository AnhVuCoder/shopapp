package com.ngleanhvu.shopapp.service;

import com.ngleanhvu.shopapp.dto.OrderDTO;
import com.ngleanhvu.shopapp.exception.DataNotFoundException;
import com.ngleanhvu.shopapp.response.OrderResponse;

import java.util.List;

public interface IOrderService {
    OrderResponse createOrder(OrderDTO orderDTO) throws DataNotFoundException;

    OrderResponse getOrder(Integer id) throws DataNotFoundException;

    OrderResponse updateOrder(Integer id, OrderDTO orderDTO) throws DataNotFoundException;

    void deleteOrder(Integer id) throws DataNotFoundException;

    List<OrderResponse> findByUserId(Integer userId) throws DataNotFoundException;
}
