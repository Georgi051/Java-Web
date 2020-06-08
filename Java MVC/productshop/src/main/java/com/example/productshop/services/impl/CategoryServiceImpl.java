package com.example.productshop.services.impl;

import com.example.productshop.domain.entities.Category;
import com.example.productshop.repository.CategoryRepository;
import com.example.productshop.services.CategoryService;
import com.example.productshop.services.serviceModels.CategoryServiceModel;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;
    private final ModelMapper modelMapper;


    @Autowired
    public CategoryServiceImpl(CategoryRepository categoryRepository, ModelMapper modelMapper) {
        this.categoryRepository = categoryRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public void seedCategoryInDb(CategoryServiceModel categoryServiceModel) {
        if (this.categoryRepository.findByName(categoryServiceModel.getName()) == null){
            Category category = this.modelMapper.map(categoryServiceModel,Category.class);
            this.categoryRepository.saveAndFlush(category);
        }
    }

    @Override
    public List<CategoryServiceModel> allCategories() {
        return this.categoryRepository.findAll().stream()
                .map(c -> this.modelMapper.map(c,CategoryServiceModel.class))
                .collect(Collectors.toList());
    }

    @Override
    public CategoryServiceModel findCategory(String id) {
        Category category = this.categoryRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException(String.format("Category with ID %s not found", id)));
        return this.modelMapper.map(category, CategoryServiceModel.class);
    }

    @Override
    public void editCategory(String id, CategoryServiceModel model) {
        Category category = this.categoryRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException(String.format("Category with ID %s not found", id)));
        category.setName(model.getName());
        this.modelMapper.map(this.categoryRepository.saveAndFlush(category), CategoryServiceModel.class);
    }

    @Override
    public void deleteCategory(String id) {
        Category category = this.categoryRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException(String.format("Category with ID %s not found", id)));
        this.categoryRepository.delete(category);
    }
}
