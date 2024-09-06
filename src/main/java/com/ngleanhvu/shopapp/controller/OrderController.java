package com.ngleanhvu.shopapp.controller;

import com.ngleanhvu.shopapp.dto.OrderDTO;
import com.ngleanhvu.shopapp.entity.Order;
import com.ngleanhvu.shopapp.response.OrderListResponse;
import com.ngleanhvu.shopapp.response.OrderResponse;
import com.ngleanhvu.shopapp.service.IOrderService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("${api.prefix}/orders")
public class OrderController {
    @Autowired
    private IOrderService iOrderService;

    @PostMapping
    public ResponseEntity<?> createOrder(@RequestBody @Valid OrderDTO orderDTO,
                                         BindingResult result) {
        try {
            if (result.hasErrors()) {
                List<String> messageError = result.getFieldErrors()
                        .stream()
                        .map(FieldError::getDefaultMessage)
                        .toList();
                return ResponseEntity.badRequest().body(messageError);
            }
            return ResponseEntity.ok().body(iOrderService.createOrder(orderDTO));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/users/{user_id}")
    public ResponseEntity<?> getOrders(@Valid @PathVariable("user_id") Integer userId) {
        try {
            return ResponseEntity.ok().body(iOrderService.findByUserId(userId));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getOrder(@Valid @PathVariable("id") Integer id) {
        try {
            return ResponseEntity.ok().body(iOrderService.getOrder(id));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateOrder(@Valid @PathVariable Integer id,
                                         @Valid @RequestBody OrderDTO orderDTO) {
        try {
            return ResponseEntity.ok().body(iOrderService.updateOrder(id, orderDTO));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteOrder(@Valid @PathVariable Integer id) {
        try {
            iOrderService.deleteOrder(id);
            return ResponseEntity.ok().body("Delete order success");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/get-orders-by-keyword")
    public ResponseEntity<OrderListResponse> getOrdersByKeyword(@RequestParam(defaultValue = "", required = false) String keyword,
                                                                @RequestParam(defaultValue = "0") int page,
                                                                @RequestParam(defaultValue = "10") int limit) {
        PageRequest pageable = PageRequest.of(Math.max(page - 1, 0), limit, Sort.by("id").ascending());
        Page<OrderResponse> orders = iOrderService.getOrdersByKeyword(keyword, pageable).map(OrderResponse::fromOrder);
        int totalPages = orders.getTotalPages();
        List<OrderResponse> orderResponseList = orders.getContent();
        return ResponseEntity.ok().body(OrderListResponse.builder()
                .orderResponseList(orderResponseList)
                .totalPages(totalPages)
                .build());
    }
}
