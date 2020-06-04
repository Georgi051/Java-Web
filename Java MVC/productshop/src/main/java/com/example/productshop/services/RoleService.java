package com.example.productshop.services;

import com.example.productshop.services.serviceModels.RoleServiceModel;

import java.util.Set;

public interface RoleService {
    void seedRoleInDb();

    Set<RoleServiceModel> findAllRoles();

    RoleServiceModel findByAuthority(String authority);
}
