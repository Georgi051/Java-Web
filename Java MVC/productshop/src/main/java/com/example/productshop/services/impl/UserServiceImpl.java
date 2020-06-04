package com.example.productshop.services.impl;

import com.example.productshop.domain.entities.User;
import com.example.productshop.repository.UserRepository;
import com.example.productshop.services.RoleService;
import com.example.productshop.services.UserService;
import com.example.productshop.services.serviceModels.RoleServiceModel;
import com.example.productshop.services.serviceModels.UserServiceModel;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final RoleService roleService;
    private final ModelMapper modelMapper;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, RoleService roleService, ModelMapper modelMapper, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.roleService = roleService;
        this.modelMapper = modelMapper;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Override
    public UserServiceModel registerUser(UserServiceModel userServiceModel) {
        this.roleService.seedRoleInDb();
        if (this.userRepository.count() == 0){
            userServiceModel.setAuthorities(this.roleService.findAllRoles());
        }else {
            userServiceModel.setAuthorities(new LinkedHashSet<>());
            userServiceModel.getAuthorities().add(this.roleService.findByAuthority(("ROLE_USER")));
        }

        User user = this.modelMapper.map(userServiceModel,User.class);
        user.setPassword(this.bCryptPasswordEncoder.encode(userServiceModel.getPassword()));
        return this.modelMapper.map(this.userRepository.saveAndFlush(user),UserServiceModel.class);
    }

    @Override
    public UserServiceModel findUserByName(String username) {
        return this.userRepository.findUserByUsername(username)
                .map(u -> this.modelMapper.map(u,UserServiceModel.class))
                .orElseThrow(()-> new UsernameNotFoundException("User not found"));
    }

    @Override
    public UserServiceModel editUserProfile(UserServiceModel userServiceModel, String oldPassword) {
        User user = this.userRepository.findUserByUsername(userServiceModel.getUsername())
                .orElseThrow(()-> new UsernameNotFoundException("User not found"));
        if (!this.bCryptPasswordEncoder.matches(oldPassword,user.getPassword())){
            throw  new IllegalArgumentException("Incorrect password!");
        }
        user.setPassword("".equals(userServiceModel.getPassword()) ? user.getPassword() :
                this.bCryptPasswordEncoder.encode(userServiceModel.getPassword()));
        user.setEmail(userServiceModel.getEmail());
        return this.modelMapper.map(this.userRepository.saveAndFlush(user),UserServiceModel.class);
    }

    @Override
    public List<UserServiceModel> allUsers() {
        return this.userRepository.findAll().stream()
                .map(u -> this.modelMapper.map(u,UserServiceModel.class))
                .collect(Collectors.toList());
    }

    @Override
    public void setUserRole(String id, String role) {
       UserServiceModel userServiceModel = this.userRepository.findById(id)
               .map(u -> this.modelMapper.map(u,UserServiceModel.class))
               .orElseThrow(()->new IllegalArgumentException("User not found"));

       userServiceModel.getAuthorities().clear();

       switch (role){
           case "user":
               userServiceModel.getAuthorities().add(this.roleService.findByAuthority("ROLE_USER"));
               break;
           case "moderator":
               userServiceModel.getAuthorities().add(this.roleService.findByAuthority("ROLE_USER"));
               userServiceModel.getAuthorities().add(this.roleService.findByAuthority("ROLE_MODERATOR"));
               break;
           case "admin":
               userServiceModel.getAuthorities().add(this.roleService.findByAuthority("ROLE_USER"));
               userServiceModel.getAuthorities().add(this.roleService.findByAuthority("ROLE_MODERATOR"));
               userServiceModel.getAuthorities().add(this.roleService.findByAuthority("ROLE_ADMIN"));
               break;
       }
        this.userRepository.saveAndFlush(this.modelMapper.map(userServiceModel,User.class));
    }


    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        return this.userRepository.findUserByUsername(s)
                .orElseThrow(()-> new UsernameNotFoundException("User not found"));
    }
}
