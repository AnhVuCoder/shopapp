package com.ngleanhvu.shopapp.repo;

import com.ngleanhvu.shopapp.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IOrderRepo extends JpaRepository<com.ngleanhvu.shopapp.entity.Order, Integer> {
    List<Order> findByUserId(Integer userId);
}
