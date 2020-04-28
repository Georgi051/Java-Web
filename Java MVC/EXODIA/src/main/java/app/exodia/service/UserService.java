package app.exodia.service;

import app.exodia.domain.models.service.UserServiceModel;

public interface UserService {

    boolean registration(UserServiceModel userServiceModel);

    UserServiceModel login(UserServiceModel userServiceModel);
}
