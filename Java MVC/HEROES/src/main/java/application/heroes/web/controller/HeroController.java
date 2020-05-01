package application.heroes.web.controller;

import application.heroes.domain.bindingModels.CreateHeroBindingModel;
import application.heroes.domain.bindingModels.ItemCreateBindingModel;
import application.heroes.service.HeroService;
import application.heroes.service.serviceModels.HeroServiceModel;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.util.Enumeration;

@Controller
public class HeroController {
    private final HeroService heroService;
    private final ModelMapper modelMapper;

    @Autowired
    public HeroController(HeroService heroService, ModelMapper modelMapper) {
        this.heroService = heroService;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/heroes/create")
    public ModelAndView createHero(ModelAndView modelAndView) {
        modelAndView.setViewName("hero/create-hero");
        return modelAndView;
    }

    @PostMapping("/heroes/create")
    public ModelAndView createHeroConfirm(@ModelAttribute CreateHeroBindingModel model,
                                          ModelAndView modelAndView, HttpSession session) {
        String username = (String) session.getAttribute("username");
        HeroServiceModel hero = this.heroService.createHero(username, this.modelMapper.map(model, HeroServiceModel.class));

        if (hero == null) {
            modelAndView.setViewName("redirect:hero/create-hero");
        } else {
            session.setAttribute("heroname", hero.getName());
            modelAndView.setViewName("redirect:/home/home-with-created-hero");
        }
        return modelAndView;
    }

    @GetMapping("/heroes/details/{name}")
    public ModelAndView heroDetails(@PathVariable("name") String name, ModelAndView modelAndView, HttpSession session) {
        HeroServiceModel hero = this.heroService.getHeroByName(name);
        session.setAttribute("level", hero.getLevel());
        session.setAttribute("strength", hero.getStrength());
        session.setAttribute("stamina", hero.getStamina());
        session.setAttribute("attack", hero.getAttack());
        session.setAttribute("gender", hero.getGender());
        session.setAttribute("defence", hero.getDefence());

        modelAndView.setViewName("hero/hero-details");
        return modelAndView;
    }

    public ModelAndView confirmHeroDetails(ModelAndView modelAndView) {

        return modelAndView;
    }
}
