package application.heroes.web.controller;

import application.heroes.domain.bindingModels.ItemCreateBindingModel;
import application.heroes.service.HeroService;
import application.heroes.service.ItemService;
import application.heroes.service.UserService;
import application.heroes.service.serviceModels.ItemServiceModel;
import application.heroes.web.viewModels.ItemsViewModel;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class ItemController {
    private final ItemService itemService;
    private final UserService userService;
    private final HeroService heroService;
    private final ModelMapper modelMapper;

    @Autowired
    public ItemController(ItemService itemService, UserService userService, HeroService heroService, ModelMapper modelMapper) {
        this.itemService = itemService;
        this.userService = userService;
        this.heroService = heroService;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/items/create")
    public ModelAndView createItem(ModelAndView modelAndView){
        modelAndView.setViewName("items/create-item");
        return modelAndView;
    }

    @PostMapping("/items/create")
    public ModelAndView confirmCreateItem(@ModelAttribute ItemCreateBindingModel model, ModelAndView modelAndView){
        this.itemService.createItem(this.modelMapper.map(model, ItemServiceModel.class));
        modelAndView.setViewName("redirect:/items/create");
        return modelAndView;
    }

    @GetMapping("/items/merchant")
    public ModelAndView merchant(ModelAndView modelAndView){
        List<ItemsViewModel> modelList = this.itemService.getAllItems().stream()
                .map(i -> this.modelMapper.map(i,ItemsViewModel.class))
                .filter(i -> !i.getDeletedItem().equals("deleted"))
                .collect(Collectors.toList());

        modelAndView.addObject(modelList);
        modelAndView.setViewName("items/merchant");
        return modelAndView;
    }

    @PostMapping("/items/merchant/{i}")
    public ModelAndView confirmMerchant(@PathVariable("i") String itemId,
                                        ModelAndView modelAndView,HttpSession session){

        ItemServiceModel item = this.itemService.getItem(itemId);
        if (item != null){
            boolean hero = this.heroService.addItemInHeroAndReturnHero(item.getId(),
                    session.getAttribute("heroname").toString());

            if (hero){
                session.setAttribute(item.getSlot(),item.getSlot());
                session.setAttribute("strength",item.getStrength());
                session.setAttribute("stamina",item.getStamina());
                session.setAttribute("attack",item.getAttack());
                session.setAttribute("defence",item.getDefence());
                this.userService.addUserNewStats((String) session.getAttribute("username"), item);
                this.itemService.deleteItemById(itemId);
            }
        }
        modelAndView.setViewName("redirect:/heroes/details/" + session.getAttribute("heroname"));
        return modelAndView;
    }
}
