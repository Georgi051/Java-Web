package com.example.productshop.services.impl;

import com.example.productshop.domain.entities.Category;
import com.example.productshop.domain.entities.Product;
import com.example.productshop.repository.ProductRepository;
import com.example.productshop.services.CategoryService;
import com.example.productshop.services.ProductService;
import com.example.productshop.services.serviceModels.ProductServiceModel;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    private final CategoryService categoryService;
    private final ModelMapper modelMapper;

    @Autowired
    public ProductServiceImpl(ProductRepository productRepository, CategoryService categoryService, ModelMapper modelMapper) {
        this.productRepository = productRepository;
        this.categoryService = categoryService;
        this.modelMapper = modelMapper;
    }

    @Override
    public void seedProductInDB(ProductServiceModel productServiceModel) {
        if (this.productRepository.findByName(productServiceModel.getName()) == null) {
            this.productRepository.saveAndFlush(this.modelMapper.map(productServiceModel, Product.class));
        }
    }

    @Override
    public List<ProductServiceModel> allProducts() {
        return this.productRepository.findAll().stream()
                .map(p -> this.modelMapper.map(p, ProductServiceModel.class))
                .collect(Collectors.toList());
    }

    @Override
    public ProductServiceModel findProductById(String id) {
        return this.productRepository.findById(id)
                .map(p -> this.modelMapper.map(p, ProductServiceModel.class))
                .orElseThrow(() -> new IllegalArgumentException(String.format("Product with ID %s not found", id)));
    }

    @Override
    public void editProduct(String id, Set<String> categories, ProductServiceModel model) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException(String.format("Product with ID %s not found", id)));

        model.setCategories(this.categoryService.allCategories().stream()
        .filter(c -> categories.contains(c.getId()))
                .collect(Collectors.toSet()));

        product.setName(model.getName());
        product.setPrice(model.getPrice());
        product.setDescription(model.getDescription());
        product.setCategories(model.getCategories().stream()
        .map(c -> this.modelMapper.map(c, Category.class))
                .collect(Collectors.toSet()));

        this.modelMapper.map(this.productRepository.saveAndFlush(product), ProductServiceModel.class);
    }

    @Override
    public void deleteProduct(String id) {
     Product product = this.productRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException(String.format("Product with ID %s not found", id)));
        this.productRepository.delete(product);
    }
}
