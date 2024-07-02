package com.ngleanhvu.shopapp.service;

import com.ngleanhvu.shopapp.dto.OrderDetailDTO;
import com.ngleanhvu.shopapp.exception.DataNotFoundException;
import com.ngleanhvu.shopapp.response.OrderDetailResponse;

import java.util.List;

public interface IOrderDetailService {
    OrderDetailResponse createOrderDetail(OrderDetailDTO orderDetailDTO) throws DataNotFoundException;

    OrderDetailResponse getOrderDetail(Integer id) throws DataNotFoundException;

    OrderDetailResponse updateOrderDetail(Integer id, OrderDetailDTO orderDetailDTO) throws DataNotFoundException;

    List<OrderDetailResponse> getOrderDetails(Integer orderId);

    void deleteOrderDetail(Integer id);
}
