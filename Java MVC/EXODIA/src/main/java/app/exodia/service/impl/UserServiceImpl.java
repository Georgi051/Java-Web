package app.exodia.service.impl;

import app.exodia.domain.entities.User;
import app.exodia.domain.models.service.UserServiceModel;
import app.exodia.repository.UserRepository;
import app.exodia.service.UserService;
import org.apache.commons.codec.digest.DigestUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, ModelMapper modelMapper) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public boolean registration(UserServiceModel userServiceModel) {
        User user = this.modelMapper.map(userServiceModel, User.class);
        user.setPassword(DigestUtils.sha256Hex(user.getPassword()));
        try {
            this.userRepository.saveAndFlush(user);
            return true;
        }catch (IllegalArgumentException ex){
            return false;
        }
    }

    @Override
    public UserServiceModel login(UserServiceModel userServiceModel) {
       User user = this.userRepository.findUserByUsername(userServiceModel.getUsername())
               .orElse(null);
       if (user == null || !user.getPassword().equals(DigestUtils.sha256Hex(userServiceModel.getPassword()))){
           return null;
       }
       return this.modelMapper.map(user,UserServiceModel.class);
    }
}
