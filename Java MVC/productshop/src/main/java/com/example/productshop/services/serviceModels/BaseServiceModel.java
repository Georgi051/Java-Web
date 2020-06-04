package com.example.productshop.services.serviceModels;

public class BaseServiceModel {
    private String id;

    protected BaseServiceModel(){
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
