package com.ngleanhvu.shopapp.service.impl;

import com.ngleanhvu.shopapp.constant.Constant;
import com.ngleanhvu.shopapp.dto.CategoryDTO;
import com.ngleanhvu.shopapp.entity.Category;
import com.ngleanhvu.shopapp.repo.ICategoryRepo;
import com.ngleanhvu.shopapp.service.ICategoryService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryService implements ICategoryService {
    @Autowired
    private ICategoryRepo iCategoryRepo;
    @Autowired
    private ModelMapper modelMapper;

    @Override
    @Transactional
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
    @Transactional
    public CategoryDTO updateCategory(Integer id, CategoryDTO categoryDTO) {
        Category existingCategory = iCategoryRepo.findById(id)
                .orElseThrow(() -> new RuntimeException(Constant.CATEGORY_NOT_FOUND));
        existingCategory.setName(categoryDTO.getName());
        iCategoryRepo.save(existingCategory);
        return modelMapper.map(existingCategory, CategoryDTO.class);
    }

    @Override
    @Transactional
    public void deleteCategoryById(Integer id) {
        Category category = iCategoryRepo.findById(id)
                .orElseThrow(() -> new RuntimeException(Constant.CATEGORY_NOT_FOUND));
        iCategoryRepo.delete(category);
    }
}
