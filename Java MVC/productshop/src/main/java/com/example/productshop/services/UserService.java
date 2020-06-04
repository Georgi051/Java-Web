package com.example.productshop.services;

import com.example.productshop.services.serviceModels.UserServiceModel;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface UserService extends UserDetailsService {
    UserServiceModel registerUser(UserServiceModel userServiceModel);

    UserServiceModel findUserByName(String username);

    UserServiceModel editUserProfile(UserServiceModel userServiceModel,String oldPassword);

    List<UserServiceModel> allUsers();

    void setUserRole(String id,String role);
}
