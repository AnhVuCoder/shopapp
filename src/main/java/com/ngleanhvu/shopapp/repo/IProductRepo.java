package com.ngleanhvu.shopapp.repo;

import com.ngleanhvu.shopapp.entity.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IProductRepo extends JpaRepository<Product, Integer> {
    boolean existsByName(@Param("name") String name);
//    Page<Product> findAll(Pageable pageable);
    @Query("SELECT p FROM Product p WHERE " +
           "(:categoryId IS NULL OR :categoryId = 0 OR p.category.id = :categoryId) " +
           "AND (:keyword IS NULL OR :keyword = '' OR p.name LIKE CONCAT('%',:keyword,'%') OR p.description LIKE CONCAT('%', :keyword ,'%'))")
    Page<Product> searchProducts(@Param("categoryId") Integer categoryId,
                                 @Param("keyword") String keyword, Pageable pageable);
    @Query("SELECT p FROM Product p LEFT JOIN FETCH p.productImageList WHERE p.id = :productId")
    Product getDetailProduct(@Param("productId") Integer productId);
    @Query("SELECT p FROM Product p WHERE p.id IN :productIds")
    List<Product> findProductByIds(@Param("productIds") List<Integer> productIds);
}
