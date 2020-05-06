package application.heroes.web.controller;

import application.heroes.domain.bindingModels.UserLoginBindingModel;
import application.heroes.domain.bindingModels.UserRegistrationBindingModel;
import application.heroes.service.UserService;
import application.heroes.service.serviceModels.UserServiceModel;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;

@Controller
public class AuthController {
    private final UserService userService;
    private final ModelMapper modelMapper;

    @Autowired
    public AuthController(UserService userService, ModelMapper modelMapper) {
        this.userService = userService;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/users/register")
    public ModelAndView register(ModelAndView modelAndView) {
        modelAndView.setViewName("/auth/register");
        return modelAndView;
    }

    @PostMapping("/users/register")
    public ModelAndView confirmRegister(ModelAndView modelAndView, UserRegistrationBindingModel model) {
        if (!model.getPassword().equals(model.getConfirmPassword()) || model.getUsername().isEmpty()
                || model.getEmail().isEmpty()) {
            modelAndView.setViewName("redirect:/users/register");
            return modelAndView;
        }
        if (!this.userService.register(this.modelMapper.map(model, UserServiceModel.class))) {
            modelAndView.setViewName("redirect:/users/register");
            return modelAndView;
        }
        modelAndView.setViewName("redirect:/users/login");
        return modelAndView;
    }


    @GetMapping("/users/login")
    public ModelAndView login(ModelAndView modelAndView) {
        modelAndView.setViewName("/auth/login");
        return modelAndView;
    }

    @PostMapping("/users/login")
    public ModelAndView confirmLogin(ModelAndView modelAndView, UserLoginBindingModel model, HttpSession session) {
        UserServiceModel user = this.userService.login(this.modelMapper.map(model, UserServiceModel.class));

        if (user == null) {
            modelAndView.setViewName("redirect:/users/login");
        } else {
            session.setAttribute("userId", user.getId());
            session.setAttribute("username", user.getUsername());
            session.setAttribute("role", user.getRole());
            if (user.getHero() == null) {
                modelAndView.setViewName("redirect:/home/home-hero-not-created");
            } else {
                session.setAttribute("heroname", user.getHero().getName());
                modelAndView.setViewName("redirect:/home/home-with-created-hero");
            }
        }
        return modelAndView;
    }

    @GetMapping("/logout")
    public ModelAndView logout(ModelAndView modelAndView, HttpSession session) {
        session.invalidate();
        modelAndView.setViewName("redirect:/");
        return modelAndView;
    }
}
