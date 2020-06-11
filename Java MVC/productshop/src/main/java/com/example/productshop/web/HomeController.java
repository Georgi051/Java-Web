package com.example.productshop.web;

import com.example.productshop.services.CategoryService;
import com.example.productshop.services.serviceModels.CategoryServiceModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.stream.Collectors;

@Controller
public class HomeController extends BaseController {
    private final CategoryService categoryService;

    @Autowired
    public HomeController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping("/")
    @PreAuthorize("isAnonymous()")
    public ModelAndView index(){
        return super.view("index");
    }

    @GetMapping("/home")
    @PreAuthorize("isAuthenticated()")
    public ModelAndView home(ModelAndView modelAndView){
        List<String> categories = this.categoryService.allCategories()
                .stream()
                .map(CategoryServiceModel::getName)
                .collect(Collectors.toList());
        modelAndView.addObject("categories", categories);
        return super.view("home",modelAndView);
    }
}
