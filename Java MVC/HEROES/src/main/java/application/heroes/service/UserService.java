package application.heroes.service;

import application.heroes.domain.entities.Hero;
import application.heroes.domain.entities.User;
import application.heroes.service.serviceModels.ItemServiceModel;
import application.heroes.service.serviceModels.UserServiceModel;

public interface UserService {
    boolean register(UserServiceModel userServiceModel);

    UserServiceModel login(UserServiceModel userServiceModel);

    boolean addHeroToUser(String username, Hero hero);

    User findUserByName(String name);

    UserServiceModel getUserProfile(String username);

    void addUserNewStats(String username, ItemServiceModel item);

}
