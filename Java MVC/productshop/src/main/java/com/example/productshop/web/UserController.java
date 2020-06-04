package com.example.productshop.web;

import com.example.productshop.domain.models.UserRegisterBindingModel;
import com.example.productshop.services.UserService;
import com.example.productshop.services.serviceModels.RoleServiceModel;
import com.example.productshop.services.serviceModels.UserServiceModel;
import com.example.productshop.web.models.EditUserViewModel;
import com.example.productshop.web.models.UserAllViewModel;
import com.example.productshop.web.models.UserProfileViewModel;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/users")
public class UserController extends BaseController{
    private final UserService userService;
    private final ModelMapper modelMapper;

    @Autowired
    public UserController(UserService userService, ModelMapper modelMapper) {
        this.userService = userService;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/register")
    @PreAuthorize("isAnonymous()")
    public ModelAndView register(){
        return super.view("register");
    }

    @PostMapping("/register")
    @PreAuthorize("isAnonymous()")
    public ModelAndView registerConfirm(@ModelAttribute UserRegisterBindingModel model){
        if (!model.getPassword().equals(model.getConfirmPassword())){
            return super.view("register");
        }
        this.userService.registerUser(this.modelMapper.map(model, UserServiceModel.class));
        return super.redirect("/login");
    }

    @GetMapping("/login")
    @PreAuthorize("isAnonymous()")
    public ModelAndView login(){
        return super.view("login");
    }


    @GetMapping("/profile")
    @PreAuthorize("isAuthenticated()")
    public ModelAndView edit(Principal principal,ModelAndView modelAndView){
        modelAndView.addObject("model"
                ,this.modelMapper.map(this.userService.findUserByName(principal.getName()),UserProfileViewModel.class));
        return super.view("profile",modelAndView);
    }

    @PostMapping("/profile")
    @PreAuthorize("isAuthenticated()")
    public ModelAndView confirmEditProfile(){
        return super.redirect("edit");
    }

    @GetMapping("/edit")
    @PreAuthorize("isAuthenticated()")
    public ModelAndView editProfile(Principal principal,ModelAndView modelAndView){
        modelAndView.addObject("model"
                ,this.modelMapper.map(this.userService.findUserByName(principal.getName()),UserProfileViewModel.class));
        return super.view("edit-profile",modelAndView);
    }

    @PostMapping("/edit")
    @PreAuthorize("isAuthenticated()")
    public  ModelAndView confirmEditProfile(@ModelAttribute EditUserViewModel model){
        if (!model.getPassword().equals(model.getConfirmPassword())){
            return super.redirect("edit-profile");
        }
        this.userService.editUserProfile(this.modelMapper.map(model,UserServiceModel.class),model.getOldPassword());
        return super.redirect("/users/profile");
    }

    @GetMapping("/all")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public  ModelAndView allUsers(ModelAndView modelAndView){
        List<UserAllViewModel> allUsers = this.userService.allUsers().stream()
                .map(u -> {
                    UserAllViewModel allViewModel = this.modelMapper.map(u, UserAllViewModel.class);
                    allViewModel.setAuthorities(u.getAuthorities().stream()
                            .map(RoleServiceModel::getAuthority).collect(Collectors.toSet()));
                    return allViewModel;
                }).collect(Collectors.toList());
        modelAndView.addObject("users",allUsers);
        return super.view("all-users",modelAndView);
    }


    @PostMapping("/set-user/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ModelAndView setUser(@PathVariable String id ){
        this.userService.setUserRole(id,"user");
        return super.redirect("/users/all");
    }

    @PostMapping("/set-moderator/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ModelAndView setModerator(@PathVariable String id ){
        this.userService.setUserRole(id,"moderator");
        return super.redirect("/users/all");
    }

    @PostMapping("/set-admin/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ModelAndView setAdmin(@PathVariable String id ){
        this.userService.setUserRole(id,"admin");
        return super.redirect("/users/all");
    }
}
