package com.ngleanhvu.shopapp.repo;

import com.ngleanhvu.shopapp.entity.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface IOrderRepo extends JpaRepository<com.ngleanhvu.shopapp.entity.Order, Integer> {
    List<Order> findByUserId(Integer userId);

    @Query("SELECT o FROM Order o WHERE o.active = true AND " +
            "(:keyword IS NULL OR :keyword = '' OR o.fullName LIKE %:keyword% " +
            "OR o.address LIKE %:keyword% OR o.note LIKE %:keyword% OR o.email LIKE %:keyword%)"
    )
    Page<Order> findByKeyword(@Param("keyword") String keyword, Pageable pageable);
}
