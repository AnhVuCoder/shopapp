package com.ngleanhvu.shopapp.repo;

import com.ngleanhvu.shopapp.entity.ProductImage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IProductImageRepo extends JpaRepository<ProductImage, Integer> {

    List<ProductImage> findByProductId(Integer productId);
}
