package com.ngleanhvu.shopapp.service.impl;

import com.ngleanhvu.shopapp.constant.Constant;
import com.ngleanhvu.shopapp.dto.ProductDTO;
import com.ngleanhvu.shopapp.dto.ProductImageDTO;
import com.ngleanhvu.shopapp.entity.Category;
import com.ngleanhvu.shopapp.entity.Product;
import com.ngleanhvu.shopapp.entity.ProductImage;
import com.ngleanhvu.shopapp.exception.DataNotFoundException;
import com.ngleanhvu.shopapp.exception.InvalidParamException;
import com.ngleanhvu.shopapp.repo.ICategoryRepo;
import com.ngleanhvu.shopapp.repo.IProductImageRepo;
import com.ngleanhvu.shopapp.repo.IProductRepo;
import com.ngleanhvu.shopapp.response.ProductResponse;
import com.ngleanhvu.shopapp.service.IProductService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductService implements IProductService {
    @Autowired
    private ICategoryRepo iCategoryRepo;
    @Autowired
    private IProductRepo iProductRepo;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private IProductImageRepo iProductImageRepo;

    @Override
    @Transactional
    public ProductDTO createProduct(ProductDTO productDTO) throws Exception {
        Category category = iCategoryRepo.findById(productDTO.getCategoryId())
                .orElseThrow(() -> new DataNotFoundException(Constant.CATEGORY_NOT_FOUND));
        Product product = modelMapper.map(productDTO, Product.class);
        iProductRepo.save(product);
        return modelMapper.map(product, ProductDTO.class);
    }

    @Override
    public ProductResponse getProductById(Integer id) throws DataNotFoundException {
//        Product product = iProductRepo.findById(id)
//                .orElseThrow(() -> new DataNotFoundException(Constant.PRODUCT_NOT_FOUND));
//        return ProductResponse.fromProductResponse(product);
        Product product = iProductRepo.getDetailProduct(id);
        return ProductResponse.fromProductResponse(product);
    }

    @Override
    public Page<ProductResponse> getAllProducts(String keyword, Integer categoryId, PageRequest pageRequest) {
        Page<Product> productsPage;
        productsPage = iProductRepo.searchProducts(categoryId, keyword, pageRequest);
        return productsPage.map(ProductResponse::fromProductResponse);
    }


    @Override
    @Transactional
    public ProductDTO updateProduct(Integer id, ProductDTO productDTO) throws DataNotFoundException {
        Product existingProduct = iProductRepo.findById(id)
                .orElseThrow(() -> new DataNotFoundException(Constant.PRODUCT_NOT_FOUND));
        Category category = iCategoryRepo.findById(productDTO.getCategoryId())
                .orElseThrow(() -> new DataNotFoundException(Constant.CATEGORY_NOT_FOUND));
        productDTO.setId(id);
        Product newProduct = modelMapper.map(productDTO, Product.class);
        iProductRepo.save(newProduct);
        return modelMapper.map(newProduct, ProductDTO.class);
    }

    @Override
    @Transactional
    public void deleteProductByIdd(Integer id) throws DataNotFoundException {
        Optional<Product> optionalProduct = iProductRepo.findById(id);
        optionalProduct.ifPresent(iProductRepo::delete);
    }

    @Override
    public boolean existsByName(String name) {
        return iProductRepo.existsByName(name);
    }

    @Override
    @Transactional
    public ProductImageDTO createProductImage(Integer productId, ProductImageDTO productImageDTO) throws DataNotFoundException, InvalidParamException {
        Product product = iProductRepo.findById(productId)
                .orElseThrow(() -> new DataNotFoundException(Constant.PRODUCT_NOT_FOUND));
        ProductImage productImage = modelMapper.map(productImageDTO, ProductImage.class);
        int size = iProductImageRepo.findByProductId(productId).size();
        if (size >= Constant.PRODUCT_PICTURE_QUANTITY) {
            throw new InvalidParamException("Number of product image must <= 5");
        }
        iProductImageRepo.save(productImage);
        return modelMapper.map(productImage, ProductImageDTO.class);
    }

    @Override
    public List<ProductResponse> findProductByIds(List<Integer> productIds) {
        List<Product> products = iProductRepo.findProductByIds(productIds);
        return products.stream()
                .map(product -> modelMapper.map(product, ProductResponse.class))
                .collect(Collectors.toList());
    }
}
