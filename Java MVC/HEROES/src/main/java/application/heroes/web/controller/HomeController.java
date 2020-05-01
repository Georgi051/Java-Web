package application.heroes.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class HomeController {

    @GetMapping("/")
    public ModelAndView index(ModelAndView modelAndView) {
        modelAndView.setViewName("home/index");
        return modelAndView;
    }

    @GetMapping("home/home-hero-not-created")
    public ModelAndView homeWithoutHero(ModelAndView modelAndView) {
        modelAndView.setViewName("home/home-hero-not-created");
        return modelAndView;
    }


    @PostMapping("home/home-hero-not-created")
    public ModelAndView confirmHomeWithoutHero(ModelAndView modelAndView) {
        modelAndView.setViewName("redirect:/heroes/create");
        return modelAndView;
    }

    @GetMapping("home/home-with-created-hero")
    public ModelAndView homeWithHero(ModelAndView modelAndView){
        modelAndView.setViewName("home/home-with-created-hero");
        return modelAndView;
    }
}
