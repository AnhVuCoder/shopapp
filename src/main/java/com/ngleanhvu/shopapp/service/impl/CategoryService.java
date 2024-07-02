package com.ngleanhvu.shopapp.service.impl;

import com.ngleanhvu.shopapp.constant.Constant;
import com.ngleanhvu.shopapp.dto.CategoryDTO;
import com.ngleanhvu.shopapp.entity.Category;
import com.ngleanhvu.shopapp.repo.ICategoryRepo;
import com.ngleanhvu.shopapp.service.ICategoryService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CategoryService implements ICategoryService {

    private final ICategoryRepo iCategoryRepo;

    private final ModelMapper modelMapper;

    @Override
    public CategoryDTO createCategory(CategoryDTO categoryDTO) {
        Category category = modelMapper.map(categoryDTO, Category.class);
        iCategoryRepo.save(category);
        return modelMapper.map(category, CategoryDTO.class);
    }

    @Override
    public CategoryDTO getCategoryById(Integer id) {
        Category category = iCategoryRepo.findById(id)
                .orElseThrow(() -> new RuntimeException(Constant.CATEGORY_NOT_FOUND));
        return modelMapper.map(category, CategoryDTO.class);
    }

    @Override
    public List<CategoryDTO> getAllCategories() {
        return iCategoryRepo.findAll()
                .stream()
                .map(category -> modelMapper.map(category, CategoryDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public CategoryDTO updateCategory(Integer id, CategoryDTO categoryDTO) {
        Category existingCategory = iCategoryRepo.findById(id)
                .orElseThrow(() -> new RuntimeException(Constant.CATEGORY_NOT_FOUND));
        existingCategory.setName(categoryDTO.getName());
        iCategoryRepo.save(existingCategory);
        return modelMapper.map(existingCategory, CategoryDTO.class);
    }

    @Override
    public void deleteCategoryById(Integer id) {
        Category category = iCategoryRepo.findById(id)
                .orElseThrow(() -> new RuntimeException(Constant.CATEGORY_NOT_FOUND));
        iCategoryRepo.delete(category);
    }
}
