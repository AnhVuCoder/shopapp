package com.ngleanhvu.shopapp.service;

import com.ngleanhvu.shopapp.dto.CategoryDTO;

import java.util.List;

public interface ICategoryService {
    CategoryDTO createCategory(CategoryDTO categoryDTO);

    CategoryDTO getCategoryById(Integer id);

    List<CategoryDTO> getAllCategories();

    CategoryDTO updateCategory(Integer id, CategoryDTO categoryDTO);

    void deleteCategoryById(Integer id);
}
