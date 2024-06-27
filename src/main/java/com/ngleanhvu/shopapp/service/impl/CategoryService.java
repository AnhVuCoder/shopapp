//package com.ngleanhvu.shopapp.service.impl;
//
//import com.ngleanhvu.shopapp.dto.CategoryDTO;
//import com.ngleanhvu.shopapp.entity.Category;
//import com.ngleanhvu.shopapp.repo.ICategoryRepo;
//import com.ngleanhvu.shopapp.service.ICategoryService;
//import org.modelmapper.ModelMapper;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import java.util.Optional;
//
//@Service
//public class CategoryService implements ICategoryService {
//    @Autowired
//    private ICategoryRepo iCategoryRepo;
//    @Autowired
//    private ModelMapper modelMapper;
//    @Override
//    public CategoryDTO create(CategoryDTO categoryDTO) {
//        Category category=modelMapper.map(categoryDTO, Category.class);
//        iCategoryRepo.save(category);
//        return modelMapper.map(category, CategoryDTO.class);
//    }
//}
