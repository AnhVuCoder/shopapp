package com.ngleanhvu.shopapp.repo;

import com.ngleanhvu.shopapp.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ICategoryRepo extends JpaRepository<Category, Integer> {
//    List<OrderDetail> findByOrderId(Integer orderId);
}
