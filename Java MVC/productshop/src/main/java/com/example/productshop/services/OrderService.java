package com.example.productshop.services;


import com.example.productshop.domain.models.OrderBindingModel;
import com.example.productshop.services.serviceModels.OrderServiceModel;
import com.example.productshop.web.models.OrderViewModel;

import java.util.List;

public interface OrderService {
    void seedOrderInDb(String id, OrderBindingModel model, String userName);

    List<OrderViewModel> findAllByUserName(String name);

    OrderServiceModel findById(String id);

    List<OrderViewModel> findAll();
}
