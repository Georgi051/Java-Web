package com.example.productshop.web;

import com.example.productshop.domain.models.OrderBindingModel;
import com.example.productshop.services.OrderService;
import com.example.productshop.services.ProductService;
import com.example.productshop.services.serviceModels.OrderServiceModel;
import com.example.productshop.web.models.OrderDetailsViewModel;
import com.example.productshop.web.models.OrderViewModel;
import com.example.productshop.web.models.ProductViewModel;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/orders")
public class OrderController extends BaseController {
    private final ProductService productService;
    private final OrderService orderService;
    private final ModelMapper modelMapper;

    @Autowired
    public OrderController(ProductService productService, OrderService orderService, ModelMapper modelMapper) {
        this.productService = productService;
        this.orderService = orderService;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/product/{id}")
    @PreAuthorize("isAuthenticated()")
    public ModelAndView orderProduct(@PathVariable String id, Principal principal, ModelAndView modelAndView){
        String customerName = principal.getName();
        ProductViewModel product = this.modelMapper.map(this.productService.findProductById(id),ProductViewModel.class);
        modelAndView.addObject("product",product);
        modelAndView.addObject("customerName",customerName);
        return super.view("order-product",modelAndView);
    }

    @PostMapping("/product/{id}")
    @PreAuthorize("isAuthenticated()")
    public ModelAndView confirmOrderProduct(@PathVariable String id,@ModelAttribute OrderBindingModel model, Principal principal){
        this.orderService.seedOrderInDb(id,model,principal.getName());
        return super.redirect("/orders/my");
    }

    @GetMapping("/my")
    @PreAuthorize("isAuthenticated()")
    public ModelAndView myOrder(ModelAndView model,Principal principal){
        List<OrderViewModel> orders = this.orderService.findAllByUserName(principal.getName());
        model.addObject("orders",orders);
        return super.view("my-orders",model);
    }

    @GetMapping("/details/{id}")
    @PreAuthorize("isAuthenticated()")
    public ModelAndView detailsOrder(@PathVariable String id, ModelAndView model){
        OrderServiceModel orderDb = this.orderService.findById(id);
        OrderDetailsViewModel order = mappingOrder(orderDb);
        model.addObject("order",order);
        return super.view("order-details",model);
    }

    @GetMapping("/all")
    @PreAuthorize("hasRole('ADMIN')")
    public ModelAndView allOrders(ModelAndView model){
        List<OrderViewModel> orders = this.orderService.findAll();
        model.addObject("orders",orders);
        return super.view("all-orders",model);
    }

    private OrderDetailsViewModel mappingOrder(OrderServiceModel orderDb) {
        OrderDetailsViewModel order = this.modelMapper.map(orderDb,OrderDetailsViewModel.class);
        order.setName(orderDb.getProduct().getName());
        order.setCustomerUsername(orderDb.getUser().getUsername());
        order.setDescription(orderDb.getProduct().getDescription());
        return order;
    }
}
