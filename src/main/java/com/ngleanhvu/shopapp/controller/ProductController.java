package com.ngleanhvu.shopapp.controller;

import com.github.javafaker.Faker;
import com.ngleanhvu.shopapp.constant.Constant;
import com.ngleanhvu.shopapp.dto.ProductDTO;
import com.ngleanhvu.shopapp.dto.ProductImageDTO;
import com.ngleanhvu.shopapp.entity.Product;
import com.ngleanhvu.shopapp.exception.DataNotFoundException;
import com.ngleanhvu.shopapp.exception.InvalidParamException;
import com.ngleanhvu.shopapp.repo.IProductRepo;
import com.ngleanhvu.shopapp.response.ProductListResponse;
import com.ngleanhvu.shopapp.response.ProductResponse;
import com.ngleanhvu.shopapp.service.IProductService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.UrlResource;
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
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("${api.prefix}/products")
public class ProductController {
    private static final Logger logger = LoggerFactory.getLogger(ProductController.class);
    @Autowired
    private IProductService iProductService;

    @GetMapping
    public ResponseEntity<ProductListResponse> getAllProducts(@RequestParam(name = "page", defaultValue = "1") int page,
                                                              @RequestParam(name = "limit", defaultValue = "10") int limit,
                                                              @RequestParam(name = "keyword", defaultValue = "") String keyword,
                                                              @RequestParam(name = "category_id", defaultValue = "0") Integer categoryId) {
        PageRequest pageRequest = PageRequest.of(
                page - 1, limit,
                Sort.by("id").ascending()
        );
        logger.info(String.format("keyword = %s", keyword));
        Page<ProductResponse> productPage = iProductService.getAllProducts(keyword, categoryId, pageRequest);
        List<ProductResponse> list = productPage.getContent();
        return ResponseEntity.ok().body(ProductListResponse.builder()
                .productResponseList(list)
                .totalPages(productPage.getTotalPages()).build());
    }

    @GetMapping("/images/{imageName}")
    public ResponseEntity<?> viewImage(@PathVariable String imageName) {
        try {
            java.nio.file.Path imagePath = Paths.get("uploads/" + imageName);
            UrlResource resource = new UrlResource(imagePath.toUri());
            if (resource.exists()) {
                return ResponseEntity.ok()
                        .contentType(MediaType.IMAGE_JPEG)
                        .body(resource);
            } else {
                return ResponseEntity.ok()
                        .contentType(MediaType.IMAGE_JPEG)
                        .body(new UrlResource(Paths.get("uploads/notFound.jpeg").toUri()));
            }
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
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
            files = files == null ? new ArrayList<MultipartFile>() : files;
            if (files.size() > 5) {
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
        for (int i = 0; i < 100; i++) {
            String name = faker.commerce().productName();
            if (iProductService.existsByName(name)) continue;
            ProductDTO productDTO = ProductDTO.builder()
                    .name(name)
                    .price((float) faker.number().numberBetween(10, 90000000))
                    .categoryId(faker.number().numberBetween(1, 5))
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

    @GetMapping("/{id}")
    public ResponseEntity<?> getProductById(@PathVariable Integer id) {
        try {
            ProductResponse productResponse = iProductService.getProductById(id);
            return ResponseEntity.ok().body(productResponse);
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

    @GetMapping("/by-ids")
    public ResponseEntity<?> getProductByIds(@RequestParam("ids") String ids) {
        try {
            List<Integer> listIds = Arrays.stream(ids.split(","))
                    .map(Integer::parseInt)
                    .toList();
            List<ProductResponse> productResponses = iProductService.findProductByIds(listIds);
            return ResponseEntity.ok().body(productResponses);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
