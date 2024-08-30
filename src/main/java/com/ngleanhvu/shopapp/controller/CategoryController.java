package com.ngleanhvu.shopapp.controller;

import com.ngleanhvu.shopapp.constant.Constant;
import com.ngleanhvu.shopapp.dto.CategoryDTO;
import com.ngleanhvu.shopapp.service.ICategoryService;
import com.ngleanhvu.shopapp.util.LocalizationUtils;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("${api.prefix}/categories")
public class CategoryController {
    @Autowired
    private ICategoryService iCategoryService;
    @Autowired
    private LocalizationUtils localizationUtils;
    @GetMapping("/{id}")
    public ResponseEntity<?> getCategoryById(@PathVariable("id") Integer id) {
        try {
            return ResponseEntity.ok().body(iCategoryService.getCategoryById(id));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<List<CategoryDTO>> getAllCategories() {
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
            return ResponseEntity.ok().body(localizationUtils.getLocalizationUtils(Constant.DELETE_CATEGORY_SUCCESSFULLY, id));
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .header("Content-Type","text/plain; charset=  UTF-8")
                    .body(localizationUtils.getLocalizationUtils(Constant.DELETE_CATEGORY_FAILED, id, e.getMessage()));
        }
    }
}
