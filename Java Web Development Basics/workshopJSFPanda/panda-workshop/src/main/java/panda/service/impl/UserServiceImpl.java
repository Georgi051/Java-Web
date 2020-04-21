package panda.service.impl;

import panda.domain.entities.User;
import panda.domain.models.service.UserServiceModel;
import org.apache.commons.codec.digest.DigestUtils;
import org.modelmapper.ModelMapper;
import panda.repositoty.UserRepository;
import panda.service.UserService;

import javax.inject.Inject;
import java.util.List;
import java.util.stream.Collectors;

public class UserServiceImpl implements UserService {
    private final ModelMapper modelMapper;
    private final UserRepository userRepository;

    @Inject
    public UserServiceImpl(ModelMapper modelMapper, UserRepository userRepository) {
        this.modelMapper = modelMapper;
        this.userRepository = userRepository;
    }

    @Override
    public void registerUser(UserServiceModel userServiceModel) {
        User user = this.modelMapper.map(userServiceModel,User.class);
        user.setPassword(DigestUtils.sha256Hex(user.getPassword()));
        setUserRole(user);
        this.userRepository.save(user);
    }

    @Override
    public UserServiceModel loginUser(UserServiceModel userServiceModel) {
        User user = this.userRepository.findByName(userServiceModel.getUsername());
        if (user == null || !DigestUtils.sha256Hex(userServiceModel.getPassword()).equals(user.getPassword())){
            return null;
        }
        return this.modelMapper.map(user,UserServiceModel.class);
    }

    private void  setUserRole(User user){
        user.setRole(this.userRepository.size() == 0 ? "Admin" : "User");
    }

    @Override
    public UserServiceModel findByUserName(String username) {
        return this.modelMapper.map(this.userRepository.findByName(username),UserServiceModel.class);
    }

    @Override
    public List<UserServiceModel> findAll() {
        return this.userRepository.findAll().stream()
                .map(u -> this.modelMapper.map(u,UserServiceModel.class))
                .collect(Collectors.toList());
    }
}
