package softuni.realestateagency.web.controllers;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import softuni.realestateagency.domain.models.binding.OfferRegisterBindingModel;
import softuni.realestateagency.domain.models.service.OfferServiceModel;
import softuni.realestateagency.service.OfferService;

@Controller
public class OfferController {
    private final OfferService offerService;
    private final ModelMapper modelMapper;

    @Autowired
    public OfferController(OfferService offerService, ModelMapper modelMapper) {
        this.offerService = offerService;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/reg")
    public String register(){
        return "register.html";
    }

    @PostMapping("/reg")
    public String registerOffer(@ModelAttribute(name = "value")OfferRegisterBindingModel offerRegisterBindingModel){
        try {
            this.offerService.registerOffer(this.modelMapper.map(offerRegisterBindingModel, OfferServiceModel.class));
        }catch (IllegalArgumentException ex){
            ex.printStackTrace();
            return "redirect:/register.html";
        }
        return "redirect:/";
    }
}
