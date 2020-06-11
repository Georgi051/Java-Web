package com.example.productshop.web;

import com.example.productshop.domain.models.ProductBindingModel;
import com.example.productshop.services.CategoryService;
import com.example.productshop.services.CloudinaryService;
import com.example.productshop.services.ProductService;
import com.example.productshop.services.serviceModels.CategoryServiceModel;
import com.example.productshop.services.serviceModels.ProductServiceModel;
import com.example.productshop.web.models.AllProductViewModel;
import com.example.productshop.web.models.ProductViewModel;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.security.Principal;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/products")
public class ProductController extends BaseController {
    private final ProductService productService;
    private final CategoryService categoryService;
    private final CloudinaryService cloudinaryService;
    private final ModelMapper modelMapper;

    @Autowired
    public ProductController(ProductService productService, CategoryService categoryService,
                             CloudinaryService cloudinaryService, ModelMapper modelMapper) {
        this.productService = productService;
        this.categoryService = categoryService;
        this.cloudinaryService = cloudinaryService;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/add")
    @PreAuthorize("hasRole('MODERATOR')")
    public ModelAndView addProduct() {
        return super.view("add-product");
    }

    @PostMapping("/add")
    @PreAuthorize("hasRole('MODERATOR')")
    public ModelAndView confirmAddProduct(@ModelAttribute ProductBindingModel model) throws IOException {
        ProductServiceModel product = this.modelMapper.map(model, ProductServiceModel.class);
        product.setCategories(categoryService.allCategories().stream()
                .filter(c -> model.getCategories().contains(c.getId()))
                .collect(Collectors.toSet()));
        product.setImageUrl(this.cloudinaryService.uploadImage(model.getImage()));
        this.productService.seedProductInDB(product);
        return super.redirect("/products/all");
    }

    @GetMapping("/all")
    @PreAuthorize("hasRole('MODERATOR')")
    public ModelAndView allProduct(ModelAndView modelAndView) {
        modelAndView.addObject("products", this.productService.allProducts().stream()
                .map(p -> this.modelMapper.map(p, AllProductViewModel.class)).collect(Collectors.toList()));
        return super.view("all-products", modelAndView);
    }


    @GetMapping("/details/{id}")
    @PreAuthorize("isAuthenticated()")
    public ModelAndView productDetails(@PathVariable String id, ModelAndView modelAndView) {
        modelAndView.addObject("product", this.modelMapper.map(this.productService.findProductById(id),
                ProductViewModel.class));
        return super.view("details", modelAndView);
    }


    @GetMapping("/edit/{id}")
    @PreAuthorize("hasRole('MODERATOR')")
    public ModelAndView editProduct(@PathVariable String id, ModelAndView modelAndView) {
        ProductBindingModel product = mapProductDetails(id);
        modelAndView.addObject("product", product);
        modelAndView.addObject("productId", id);
        return super.view("edit-product", modelAndView);
    }

    @PostMapping("/edit/{id}")
    @PreAuthorize("hasRole('MODERATOR')")
    public ModelAndView confirmEditProduct(@PathVariable String id, @ModelAttribute ProductBindingModel model) {
        this.productService.editProduct(id,new HashSet<>(model.getCategories()),this.modelMapper.map(model,ProductServiceModel.class));
        return super.redirect("/products/all");
    }

    @GetMapping("/delete/{id}")
    @PreAuthorize("hasRole('MODERATOR')")
    public ModelAndView deleteProduct(@PathVariable String id, ModelAndView modelAndView){
        ProductBindingModel product = mapProductDetails(id);
        modelAndView.addObject("product", product);
        modelAndView.addObject("productId", id);
        return super.view("delete-product", modelAndView);
    }

    @PostMapping("/delete/{id}")
    @PreAuthorize("hasRole('MODERATOR')")
    public ModelAndView confirmDeleteProduct(@PathVariable String id) {
        this.productService.deleteProduct(id);
        return super.redirect("/products/all");
    }

    private ProductBindingModel mapProductDetails(String id) {
        ProductServiceModel findProduct = this.productService.findProductById(id);
        ProductBindingModel product = this.modelMapper.map(findProduct, ProductBindingModel.class);
        product.setCategories(findProduct.getCategories().stream().map(CategoryServiceModel::getName).collect(Collectors.toSet()));
        return  product;
    }

    @GetMapping("/fetch/{category}")
    @ResponseBody
    public List<AllProductViewModel> fetch(@PathVariable(name = "category") String category) {
        if ("All".equals(category)) {
           return this.productService.allProducts().stream()
                   .map(p -> this.modelMapper.map(p,AllProductViewModel.class))
                   .collect(Collectors.toList());
        }

        return this.productService.findAllByCategory(category)
                .stream()
                .map(product -> this.modelMapper.map(product, AllProductViewModel.class))
                .collect(Collectors.toList());
    }
}
