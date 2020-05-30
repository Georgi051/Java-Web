package com.example.residentevil.services;

import com.example.residentevil.services.serviceModels.UserServiceModel;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {
    boolean registerUser(UserServiceModel userServiceModel);
}
