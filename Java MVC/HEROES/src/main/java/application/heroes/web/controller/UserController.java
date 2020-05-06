package application.heroes.web.controller;

import application.heroes.service.UserService;
import application.heroes.service.serviceModels.UserServiceModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;


@Controller
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/users/profile")
    public ModelAndView profile(ModelAndView modelAndView, HttpSession session ){
        UserServiceModel userServiceModel = this.userService.getUserProfile((String) session.getAttribute("username")) ;
        modelAndView.addObject("heroName", userServiceModel.getHero().getName());
        modelAndView.addObject("level", userServiceModel.getHero().getLevel());
        modelAndView.addObject("email", userServiceModel.getEmail());
        modelAndView.addObject("gender", userServiceModel.getHero().getGender());
        modelAndView.setViewName("user/profile");
        return modelAndView;
    }
}
