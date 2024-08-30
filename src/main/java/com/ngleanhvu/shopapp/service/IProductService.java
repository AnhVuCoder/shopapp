package com.ngleanhvu.shopapp.service;

import com.ngleanhvu.shopapp.dto.ProductDTO;
import com.ngleanhvu.shopapp.dto.ProductImageDTO;
import com.ngleanhvu.shopapp.entity.Product;
import com.ngleanhvu.shopapp.exception.DataNotFoundException;
import com.ngleanhvu.shopapp.exception.InvalidParamException;
import com.ngleanhvu.shopapp.response.ProductResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.List;

public interface IProductService {
    ProductDTO createProduct(ProductDTO productDTO) throws Exception;

    ProductResponse getProductById(Integer id) throws Exception;

    Page<ProductResponse> getAllProducts(String keyword, Integer categoryId, PageRequest pageRequest);

    ProductDTO updateProduct(Integer id, ProductDTO productDTO) throws DataNotFoundException;

    void deleteProductByIdd(Integer id) throws DataNotFoundException;

    boolean existsByName(String name);

    ProductImageDTO createProductImage(Integer productId, ProductImageDTO productImageDTO) throws DataNotFoundException, InvalidParamException;
    List<ProductResponse> findProductByIds(List<Integer> productIds);
}
