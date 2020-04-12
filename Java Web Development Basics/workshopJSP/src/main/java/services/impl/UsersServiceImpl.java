package services.impl;

import models.entity.User;
import models.service.UserServiceModel;
import org.modelmapper.ModelMapper;
import services.HashingService;
import services.UserValidationService;
import services.UsersService;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.util.List;

public class UsersServiceImpl  implements UsersService {
    private static User LOGIN_USER;

    private final EntityManager entityManager;
    private final HashingService hashingService;
    private final ModelMapper modelMapper;
    private final UserValidationService userValidationService;

    @Inject
    public UsersServiceImpl(EntityManager entityManager, HashingService hashingService,
                            ModelMapper modelMapper, UserValidationService userValidationService){
        this.hashingService = hashingService;
        this.entityManager = entityManager;
        this.modelMapper = modelMapper;
        this.userValidationService = userValidationService;
    }

    @Override
    public void register(String username, String email, String password, String confirmPassword) throws Exception {
        if (!userValidationService.canCreateUser(username,email,password,confirmPassword)){
            throw new Exception("User cannot be create!");
        }
        User user = new User();
        user.setUserName(username);
        user.setEmail(email);
        user.setPassword(hashingService.hash(password));

        entityManager.getTransaction().begin();
        entityManager.persist(user);
        entityManager.getTransaction().commit();
    }

    @Override
    public UserServiceModel login(String userName, String password) {
        List<User> users = entityManager.createQuery("select  u from  User as u where u.userName = :userName",User.class)
                .setParameter("userName",userName)
                .getResultList();
        if (users.isEmpty()){
            return null;
        }

        User user = users.get(0);
        LOGIN_USER = user;
        return  modelMapper.map(user,UserServiceModel.class);
    }

    public User getUser() {
        return LOGIN_USER;
    }
}
