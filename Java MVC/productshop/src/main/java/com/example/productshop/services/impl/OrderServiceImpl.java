package com.example.productshop.services.impl;

import com.example.productshop.domain.entities.Order;
import com.example.productshop.domain.entities.Product;
import com.example.productshop.domain.entities.User;
import com.example.productshop.domain.models.OrderBindingModel;
import com.example.productshop.repository.OrderRepository;
import com.example.productshop.services.OrderService;
import com.example.productshop.services.ProductService;
import com.example.productshop.services.UserService;
import com.example.productshop.services.serviceModels.OrderServiceModel;
import com.example.productshop.services.serviceModels.ProductServiceModel;
import com.example.productshop.services.serviceModels.UserServiceModel;
import com.example.productshop.web.models.OrderViewModel;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService {
    private final ProductService productService;
    private final UserService userService;
    private final OrderRepository orderRepository;
    private final ModelMapper modelMapper;


    @Autowired
    public OrderServiceImpl(ProductService productService, UserService userService, OrderRepository orderRepository, ModelMapper modelMapper) {
        this.productService = productService;
        this.userService = userService;
        this.orderRepository = orderRepository;
        this.modelMapper = modelMapper;
    }


    @Override
    public void seedOrderInDb(String id, OrderBindingModel model, String userName) {
        ProductServiceModel product = this.productService.findProductById(id);
        UserServiceModel user = this.userService.findUserByName(userName);
        Product productDb = this.modelMapper.map(product, Product.class);
        User userDb = this.modelMapper.map(user,User.class);

        Order order = new Order();
        order.setUser(userDb);
        order.setProduct(productDb);
        order.setQuantity(model.getQuantity());
        BigDecimal price = product.getPrice().multiply(BigDecimal.valueOf(order.getQuantity()));
        order.setPrice(price);
        order.setOrderTime(LocalDateTime.now());

        this.orderRepository.save(order);
    }

    @Override
    public List<OrderViewModel> findAllByUserName(String name) {
        List<Order> orders = this.orderRepository.findAllByUser_Username(name);
        return orders.stream()
                .map(o -> {
                    OrderViewModel model = this.modelMapper.map(o, OrderViewModel.class);
                    model.setCustomerUsername(name);
                    return model;
                })
                .collect(Collectors.toList());
    }

    @Override
    public OrderServiceModel findById(String id) {
        Order order = this.orderRepository.findById(id)
                .orElseThrow(()-> new IllegalArgumentException(String.format("Order with %s not found!",id)));
        return this.modelMapper.map(order,OrderServiceModel.class);
    }

    @Override
    public List<OrderViewModel> findAll() {
        return this.orderRepository.findAll().stream()
                .map(order -> {
                    OrderViewModel viewModel = this.modelMapper.map(order, OrderViewModel.class);
                    viewModel.setCustomerUsername(order.getUser().getUsername());
                    return viewModel;
                })
                .collect(Collectors.toList());
    }
}
