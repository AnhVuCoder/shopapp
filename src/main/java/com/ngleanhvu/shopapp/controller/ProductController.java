package com.ngleanhvu.shopapp.controller;

import com.ngleanhvu.shopapp.constant.Constant;
import com.ngleanhvu.shopapp.dto.ProductDTO;
import jakarta.validation.Valid;
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
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("api/v1/products")
public class ProductController {
    @GetMapping
    public ResponseEntity<String> getAllProducts(@RequestParam("page") int page,
                                                   @RequestParam("limit") int limit){
        return ResponseEntity.ok(String.format("Page=%d ,Limit=%d", page,limit));
    }
    // API add a new product
    @PostMapping(value = "", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> insertProduct(@Valid @ModelAttribute ProductDTO productDTO,
                                            BindingResult result){
        try{
            // Validate data from client
            if(result.hasErrors()){
                List<String> messageError=result.getFieldErrors()
                        .stream()
                        .map(FieldError::getDefaultMessage)
                        .toList();
                return ResponseEntity.badRequest().body(messageError);
            }
            List<MultipartFile> files=productDTO.getFiles();
            files = files == null ? new ArrayList<MultipartFile>(): files;
            // Check size and format file
            for(MultipartFile file:files){
                if(file!=null){
                    if(file.getSize()> Constant.FILE_SIZE){
                        return ResponseEntity.status(HttpStatus.PAYLOAD_TOO_LARGE).body("File is too large! Maximum size is 10MB");
                    }
                    String contentType= file.getContentType();
                    if(contentType==null || !contentType.startsWith("image/")){
                        return ResponseEntity.status(HttpStatus.UNSUPPORTED_MEDIA_TYPE).body("File must be an image!");
                    }
                    String fileName=storeFile(file);
                }
            }
            return ResponseEntity.ok().body("POST");
        } catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    // Store file in directory
    private String storeFile(MultipartFile file) throws IOException {
        String fileName= StringUtils.cleanPath(file.getOriginalFilename());
        String uniqueFileName= UUID.randomUUID().toString()+"_"+fileName;
        Path uploadDir= Paths.get("uploads");
        if(!Files.exists(uploadDir)){
            Files.createDirectory(uploadDir);
        }
        Path destination=Paths.get(uploadDir.toString(), uniqueFileName);
        Files.copy(file.getInputStream(), destination, StandardCopyOption.REPLACE_EXISTING);
        return uniqueFileName;
    }
    @PutMapping("/{id}")
    public ResponseEntity<String> updateProduct(@PathVariable Integer id){
        return ResponseEntity.ok("UPDATE "+id);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable Integer id){
        return ResponseEntity.status(HttpStatus.OK).body("DELETE");
    }
}
