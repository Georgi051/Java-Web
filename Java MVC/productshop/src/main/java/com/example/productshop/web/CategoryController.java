package com.example.productshop.web;

import com.example.productshop.domain.models.CategoryBindingModel;
import com.example.productshop.services.CategoryService;
import com.example.productshop.services.serviceModels.CategoryServiceModel;
import com.example.productshop.web.models.CategoryViewModel;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/categories")
public class CategoryController extends BaseController {
    private final CategoryService categoryService;
    private final ModelMapper modelMapper;

    @Autowired
    public CategoryController(CategoryService categoryService, ModelMapper modelMapper) {
        this.categoryService = categoryService;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/add")
    @PreAuthorize("hasRole('MODERATOR')")
    public ModelAndView addCategory() {
        return super.view("add-category");
    }

    @PostMapping("/add")
    @PreAuthorize("hasRole('MODERATOR')")
    public ModelAndView confirmAddCategory(@ModelAttribute CategoryBindingModel model){
        this.categoryService.seedCategoryInDb(this.modelMapper.map(model, CategoryServiceModel.class));
        return super.redirect("/categories/all");
    }

    @GetMapping("/all")
    @PreAuthorize("hasRole('MODERATOR')")
    public ModelAndView allCategories(ModelAndView modelAndView){
        modelAndView.addObject("categories",this.categoryService.allCategories().stream()
        .map(c -> this.modelMapper.map(c, CategoryViewModel.class)).collect(Collectors.toList())
        );
        return super.view("all-categories",modelAndView);
    }

    @GetMapping("edit/{id}")
    @PreAuthorize("hasRole('MODERATOR')")
    public ModelAndView editCategory(@PathVariable String id, ModelAndView modelAndView){
        modelAndView.addObject("category",
                this.modelMapper.map( this.categoryService.findCategory(id),CategoryViewModel.class));
        return super.view("edit-category",modelAndView);
    }

    @PostMapping("edit/{id}")
    @PreAuthorize("hasRole('MODERATOR')")
    public ModelAndView confirmEditCategory(@PathVariable String id, @ModelAttribute CategoryBindingModel model){
        this.categoryService.editCategory(id,this.modelMapper.map(model,CategoryServiceModel.class));
        return super.redirect("/categories/all");
    }

    @GetMapping("delete/{id}")
    @PreAuthorize("hasRole('MODERATOR')")
    public ModelAndView deleteCategory(@PathVariable String id, ModelAndView modelAndView){
        modelAndView.addObject("category",
                this.modelMapper.map( this.categoryService.findCategory(id),CategoryViewModel.class));
        return super.view("delete-category",modelAndView);
    }

    @PostMapping("delete/{id}")
    @PreAuthorize("hasRole('MODERATOR')")
    public ModelAndView confirmDeleteCategory(@PathVariable String id){
        this.categoryService.deleteCategory(id);
        return super.redirect("/categories/all");
    }

    @GetMapping("/fetch")
    @PreAuthorize("hasRole('MODERATOR')")
    @ResponseBody
    public List<CategoryViewModel> fetchCategories(){
        return this.categoryService.allCategories()
                .stream()
                .map(c -> this.modelMapper.map(c,CategoryViewModel.class))
                .collect(Collectors.toList());
    }
}
