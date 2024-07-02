package com.ngleanhvu.shopapp.controller;

import com.ngleanhvu.shopapp.dto.OrderDetailDTO;
import com.ngleanhvu.shopapp.exception.DataNotFoundException;
import com.ngleanhvu.shopapp.service.IOrderDetailService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("${api.prefix}/order_details")
@RequiredArgsConstructor
public class OrderDetailController {
    private final IOrderDetailService iOrderDetailService;

    @PostMapping
    public ResponseEntity<?> createOrderDetail(@Valid @RequestBody OrderDetailDTO orderDetailDTO) {
        try {
            return ResponseEntity.ok().body(iOrderDetailService.createOrderDetail(orderDetailDTO));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getOrderDetail(@Valid @PathVariable("id") Integer id) {
        try {
            return ResponseEntity.ok().body(iOrderDetailService.getOrderDetail(id));
        } catch (DataNotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/orders/{order_id}")
    public ResponseEntity<?> getOrderDetails(@Valid @PathVariable("order_id") Integer orderId) {
        return ResponseEntity.ok().body(iOrderDetailService.getOrderDetails(orderId));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateOrderDetail(@Valid @PathVariable("id") Integer id,
                                               @Valid @RequestBody OrderDetailDTO newOrderDetailData) {
        return ResponseEntity.ok().body("Update order detail with id: " + id);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteOrderDetail(@Valid @PathVariable("id") Integer id) {
        try {
            iOrderDetailService.deleteOrderDetail(id);
            return ResponseEntity.ok().body("Delete successfully");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
