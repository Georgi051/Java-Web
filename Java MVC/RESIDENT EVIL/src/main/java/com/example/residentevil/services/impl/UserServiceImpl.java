package com.example.residentevil.services.impl;

import com.example.residentevil.domain.entities.Role;
import com.example.residentevil.domain.entities.User;
import com.example.residentevil.repository.RoleRepository;
import com.example.residentevil.repository.UserRepository;
import com.example.residentevil.services.UserService;
import com.example.residentevil.services.serviceModels.UserServiceModel;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final ModelMapper modelMapper;
    private final BCryptPasswordEncoder encoder;

    public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository, ModelMapper modelMapper, BCryptPasswordEncoder encoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.modelMapper = modelMapper;
        this.encoder = encoder;
    }

    @Override
    public boolean registerUser(UserServiceModel userServiceModel) {
        this.seedRoleInDB();
        User user = this.modelMapper.map(userServiceModel,User.class);
        user.setPassword(this.encoder.encode(userServiceModel.getPassword()));
        this.putRoleToUser(user);
        try {
            this.userRepository.saveAndFlush(user);
            return true;
        }catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }


    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        return this.userRepository.findUserByUsername(s)
                .orElseThrow(()-> new UsernameNotFoundException("User not found"));
    }

    private void seedRoleInDB(){
        if (this.roleRepository.count() == 0){
            Role admin = new Role();
            admin.setAuthority("ADMIN");

            Role moderator = new Role();
            moderator.setAuthority("MODERATOR");

            Role user = new Role();
            user.setAuthority("USER");

            this.roleRepository.saveAndFlush(admin);
            this.roleRepository.saveAndFlush(moderator);
            this.roleRepository.saveAndFlush(user);
        }
    }

    private void putRoleToUser(User user){
        if (this.userRepository.count() == 0){
            user.getAuthorities().add(this.roleRepository.findByAuthority("ADMIN"));
            user.getAuthorities().add(this.roleRepository.findByAuthority("MODERATOR"));
            user.getAuthorities().add(this.roleRepository.findByAuthority("USER"));
        }else {
            user.getAuthorities().add(this.roleRepository.findByAuthority("USER"));
        }
    }
}
