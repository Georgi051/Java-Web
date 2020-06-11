package com.example.productshop.services;

import com.example.productshop.services.serviceModels.ProductServiceModel;

import java.util.List;
import java.util.Set;

public interface ProductService {
    void seedProductInDB(ProductServiceModel productServiceModel);

    List<ProductServiceModel> allProducts();

    ProductServiceModel findProductById(String product);

    void editProduct(String id, Set<String> categories, ProductServiceModel model);

    void deleteProduct(String id);

    List<ProductServiceModel> findAllByCategory(String category);
}
