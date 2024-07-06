package com.ngleanhvu.shopapp.controller;

import com.github.javafaker.Faker;
import com.ngleanhvu.shopapp.constant.Constant;
import com.ngleanhvu.shopapp.dto.ProductDTO;
import com.ngleanhvu.shopapp.dto.ProductImageDTO;
import com.ngleanhvu.shopapp.exception.DataNotFoundException;
import com.ngleanhvu.shopapp.exception.InvalidParamException;
import com.ngleanhvu.shopapp.response.ProductListResponse;
import com.ngleanhvu.shopapp.response.ProductResponse;
import com.ngleanhvu.shopapp.service.IProductService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@RestController
@RequestMapping("${api.prefix}/products")
public class ProductController {
    @Autowired
    private IProductService iProductService;

    @GetMapping
    public ResponseEntity<ProductListResponse> getAllProducts(@RequestParam("page") int page,
                                                              @RequestParam("limit") int limit) {
        PageRequest pageRequest = PageRequest.of(
                page, limit,
                Sort.by("createdAt").descending()
        );
        Page<ProductResponse> productPage = iProductService.getAllProducts(pageRequest);
        List<ProductResponse> list = productPage.getContent();
        return ResponseEntity.ok().body(ProductListResponse.builder()
                .productResponseList(list)
                .totalPages(productPage.getTotalPages()).build());
    }

    // API add a new product
    @PostMapping
    public ResponseEntity<?> insertProduct(@Valid @RequestBody ProductDTO productDTO,
                                           BindingResult result) {
        try {
            // Validate data from client
            if (result.hasErrors()) {
                List<String> messageError = result.getFieldErrors()
                        .stream()
                        .map(FieldError::getDefaultMessage)
                        .toList();
                return ResponseEntity.badRequest().body(messageError);
            }
            ProductDTO product = iProductService.createProduct(productDTO);
            return ResponseEntity.ok().body(product);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping(value = "uploads/{product_id}",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> uploadImage(@ModelAttribute("files") List<MultipartFile> files,
                                         @PathVariable("product_id") Integer productId) throws IOException, DataNotFoundException, InvalidParamException {
        try {
            ProductDTO productDTO = iProductService.getProductById(productId);
            files = files == null ? new ArrayList<MultipartFile>() : files;
            if (files.size() > Constant.FILE_SIZE) {
                throw new DataNotFoundException("Quantity of product image must be <= 5");
            }
            List<ProductImageDTO> list = new ArrayList<>();
            // Check size and format file
            for (MultipartFile file : files) {
                if (file.getSize() == 0) {
                    continue;
                }
                if (file.getSize() > Constant.FILE_SIZE) {
                    return ResponseEntity.status(HttpStatus.PAYLOAD_TOO_LARGE).body("File is too large! Maximum size is 10MB");
                }
                String contentType = file.getContentType();
                if (contentType == null || !contentType.startsWith("image/")) {
                    return ResponseEntity.status(HttpStatus.UNSUPPORTED_MEDIA_TYPE).body("File must be an image!");
                }
                String fileName = storeFile(file);
                ProductImageDTO productImageDTO = iProductService.createProductImage(productId, ProductImageDTO.builder()
                        .productId(productId)
                        .imageUrl(fileName)
                        .build());
                list.add(productImageDTO);
            }
            return ResponseEntity.ok().body(list);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // Store file in directory
    private String storeFile(MultipartFile file) throws IOException {
        if (!isImageFile(file) || file.getOriginalFilename() == null) {
            throw new IOException("Invalid file format");
        }
        String fileName = StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));
        String uniqueFileName = UUID.randomUUID().toString() + "_" + fileName;
        Path uploadDir = Paths.get("uploads");
        if (!Files.exists(uploadDir)) {
            Files.createDirectory(uploadDir);
        }
        Path destination = Paths.get(uploadDir.toString(), uniqueFileName);
        Files.copy(file.getInputStream(), destination, StandardCopyOption.REPLACE_EXISTING);
        return uniqueFileName;
    }

    private boolean isImageFile(MultipartFile file) {
        String contentFile = file.getContentType();
        return contentFile != null && contentFile.startsWith("image/");
    }

    //    @PostMapping("/generateFakeProductsData")
    private ResponseEntity<String> generateFakeProductsData() throws Exception {
        Faker faker = new Faker();
        for (int i = 0; i < 1000000; i++) {
            String name = faker.commerce().productName();
            if (iProductService.existsByName(name)) continue;
            ProductDTO productDTO = ProductDTO.builder()
                    .name(name)
                    .price((float) faker.number().numberBetween(10, 90000000))
                    .categoryId(faker.number().numberBetween(1, 3))
                    .description(faker.lorem().sentence())
                    .thumbnail("")
                    .build();
            try {
                iProductService.createProduct(productDTO);
            } catch (Exception e) {
                return ResponseEntity.badRequest().body(e.getMessage());
            }
        }
        return ResponseEntity.ok().body("Fake products is created successfully");
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> getProductById(@PathVariable Integer id) {
        try {
            return ResponseEntity.ok().body(iProductService.getProductById(id));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable Integer id) {
        try {
            iProductService.deleteProductByIdd(id);
            return ResponseEntity.ok().body("Delete successfully!!!");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
