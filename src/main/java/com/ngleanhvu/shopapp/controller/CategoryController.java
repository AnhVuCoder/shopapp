package com.ngleanhvu.shopapp.controller;

import com.ngleanhvu.shopapp.dto.CategoryDTO;
import com.ngleanhvu.shopapp.service.ICategoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("${api.prefix}/categories")
@RequiredArgsConstructor
public class CategoryController {

    private final ICategoryService iCategoryService;

    @GetMapping("/{id}")
    public ResponseEntity<?> getCategoryById(@PathVariable("id") Integer id) {
        try {
            return ResponseEntity.ok().body(iCategoryService.getCategoryById(id));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<List<CategoryDTO>> getAllCategories(@RequestParam(value = "page", defaultValue = "0") Integer page,
                                                              @RequestParam(value = "limit", defaultValue = "0") Integer limit) {
        return ResponseEntity.ok().body(iCategoryService.getAllCategories());
    }

    @PostMapping
    public ResponseEntity<?> insertCategory(@Valid @RequestBody CategoryDTO categoryDTO,
                                            BindingResult result) {
        try {
            if (result.hasErrors()) {
                List<String> messageError = result.getFieldErrors()
                        .stream()
                        .map(DefaultMessageSourceResolvable::getDefaultMessage)
                        .toList();
                return ResponseEntity.badRequest().body(messageError);
            }
            return ResponseEntity.ok().body(iCategoryService.createCategory(categoryDTO));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateCategory(@PathVariable Integer id,
                                            @Valid @RequestBody CategoryDTO categoryDTO,
                                            BindingResult result) {
        try {
            if (result.hasErrors()) {
                List<String> messageError = result.getFieldErrors()
                        .stream()
                        .map(DefaultMessageSourceResolvable::getDefaultMessage)
                        .toList();
                return ResponseEntity.badRequest().body(messageError);
            }
            return ResponseEntity.ok().body(iCategoryService.updateCategory(id, categoryDTO));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCategory(@PathVariable Integer id) {
        try {
            iCategoryService.deleteCategoryById(id);
            return ResponseEntity.ok().body("Delete successfully");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
