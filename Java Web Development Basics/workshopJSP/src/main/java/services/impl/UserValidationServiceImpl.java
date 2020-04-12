package services.impl;

import models.entity.User;
import services.UserValidationService;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UserValidationServiceImpl implements UserValidationService {
    private static final Pattern VALID_EMAIL_ADDRESS_REGEX =
            Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);
    private EntityManager entityManager;

    @Inject
    public UserValidationServiceImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public boolean canCreateUser(String username, String email, String password, String confirmPassword) {
        return isEmailValid(email) && isPasswordMaching(password,confirmPassword)
                && !isUserNameTaken(username);
    }

    private boolean isPasswordMaching(String password, String confirmPassword) {
        return password.equals(confirmPassword);
    }

    private boolean isEmailValid(String email) {
        Matcher matcher = VALID_EMAIL_ADDRESS_REGEX .matcher(email);
        return matcher.find();
    }

    private boolean isUserNameTaken(String userName) {
      List< User > users = entityManager.createQuery("select  u from  User as u where u.userName = :userName",User.class)
             .setParameter("userName",userName)
             .getResultList();
      return !users.isEmpty();
    }
}
