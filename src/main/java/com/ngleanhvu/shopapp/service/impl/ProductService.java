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

import java.util.Optional;

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
    public ProductDTO createProduct(ProductDTO productDTO) throws Exception {
        Category category = iCategoryRepo.findById(productDTO.getCategoryId())
                .orElseThrow(() -> new DataNotFoundException(Constant.CATEGORY_NOT_FOUND));
        Product product = modelMapper.map(productDTO, Product.class);
        iProductRepo.save(product);
        return modelMapper.map(product, ProductDTO.class);
    }

    @Override
    public ProductDTO getProductById(Integer id) throws DataNotFoundException {
        Product product = iProductRepo.findById(id)
                .orElseThrow(() -> new DataNotFoundException(Constant.PRODUCT_NOT_FOUND));
        return modelMapper.map(product, ProductDTO.class);
    }

    @Override
    public Page<ProductResponse> getAllProducts(PageRequest pageRequest) {
        return iProductRepo.findAll(pageRequest)
                .map(ProductResponse::fromProductResponse);
    }


    @Override
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
    public void deleteProductByIdd(Integer id) throws DataNotFoundException {
        Optional<Product> optionalProduct = iProductRepo.findById(id);
        optionalProduct.ifPresent(iProductRepo::delete);
    }

    @Override
    public boolean existsByName(String name) {
        return iProductRepo.existsByName(name);
    }

    @Override
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
}
