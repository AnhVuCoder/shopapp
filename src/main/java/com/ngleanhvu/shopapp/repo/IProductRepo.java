package com.ngleanhvu.shopapp.repo;

import com.ngleanhvu.shopapp.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface IProductRepo extends JpaRepository<Product, Integer> {
    boolean existsByName(@Param("name") String name);
//    Page<Product> findAll(Pageable pageable);
}
