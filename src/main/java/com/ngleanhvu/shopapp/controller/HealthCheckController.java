package com.ngleanhvu.shopapp.controller;

import com.ngleanhvu.shopapp.service.ICategoryService;
import com.ngleanhvu.shopapp.service.impl.CategoryService;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("${api.prefix}/health_check")
@RequiredArgsConstructor
public class HealthCheckController {
    private final ICategoryService iCategoryService;

    @GetMapping("health")
    public ResponseEntity<?> healthCheck() {
        try {
            return ResponseEntity.ok().body(iCategoryService.getAllCategories());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
