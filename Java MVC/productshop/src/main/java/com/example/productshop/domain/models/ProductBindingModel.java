package com.example.productshop.domain.models;

import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.util.Set;

public class ProductBindingModel {
    private String name;
    private String description;
    private BigDecimal price;
    private MultipartFile image;
    private Set<String> categories;

    public ProductBindingModel() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Set<String> getCategories() {
        return categories;
    }

    public void setCategories(Set<String> categories) {
        this.categories = categories;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public MultipartFile getImage() {
        return image;
    }

    public void setImage(MultipartFile image) {
        this.image = image;
    }
}
