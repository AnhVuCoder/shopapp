package com.ngleanhvu.shopapp.controller;

import com.ngleanhvu.shopapp.constant.Constant;
import com.ngleanhvu.shopapp.dto.ProductDTO;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

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
    public ResponseEntity<?> insertProduct(@Valid @RequestBody ProductDTO productDTO,
                                            @RequestPart("file") MultipartFile file,
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
            // Check size and format file
            if(file.getSize()> Constant.FILE_SIZE){
                
            }
            return ResponseEntity.ok().body("POST");
        } catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage())
        }
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
