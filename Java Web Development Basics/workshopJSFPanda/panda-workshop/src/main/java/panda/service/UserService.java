package panda.service;

import panda.domain.models.service.UserServiceModel;

import java.util.List;

public interface UserService {

    void registerUser(UserServiceModel userServiceModel);

    UserServiceModel loginUser(UserServiceModel userServiceModel);

    UserServiceModel findByUserName(String username);

    List<UserServiceModel> findAll();
}
