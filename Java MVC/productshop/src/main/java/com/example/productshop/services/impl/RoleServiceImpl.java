package com.example.productshop.services.impl;

import com.example.productshop.domain.entities.Role;
import com.example.productshop.repository.RoleRepository;
import com.example.productshop.services.RoleService;
import com.example.productshop.services.serviceModels.RoleServiceModel;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
public class RoleServiceImpl implements RoleService {
    private final RoleRepository roleRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public RoleServiceImpl(RoleRepository roleRepository, ModelMapper modelMapper) {
        this.roleRepository = roleRepository;
        this.modelMapper = modelMapper;
    }


    @Override
    public void seedRoleInDb() {
        if (this.roleRepository.count() == 0) {
            this.roleRepository.saveAndFlush(new Role("ROLE_ROOT"));
            this.roleRepository.saveAndFlush(new Role("ROLE_ADMIN"));
            this.roleRepository.saveAndFlush(new Role("ROLE_MODERATOR"));
            this.roleRepository.saveAndFlush(new Role("ROLE_USER"));
        }
    }

    @Override
    public Set<RoleServiceModel> findAllRoles() {
        return this.roleRepository.findAll().stream()
                .map(r -> this.modelMapper.map(r,RoleServiceModel.class))
                .collect(Collectors.toSet());
    }

    @Override
    public RoleServiceModel findByAuthority(String authority) {
        return this.modelMapper.map(this.roleRepository.findByAuthority(authority),RoleServiceModel.class);
    }
}
