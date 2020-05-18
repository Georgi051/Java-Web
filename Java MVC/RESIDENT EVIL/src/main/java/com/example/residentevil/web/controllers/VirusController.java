package com.example.residentevil.web.controllers;

import com.example.residentevil.domain.bindingModels.VirusBindingModel;
import com.example.residentevil.domain.entities.enums.Mutation;
import com.example.residentevil.services.CapitalService;
import com.example.residentevil.services.VirusService;
import com.example.residentevil.services.serviceModels.VirusServiceModel;
import com.example.residentevil.services.serviceModels.VirusServiceWithCapitalsModel;
import com.example.residentevil.web.viewModels.CapitalsNameViewModel;
import com.example.residentevil.web.viewModels.VirusesViewModel;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/viruses")
public class VirusController extends BaseController {
    private final CapitalService capitalService;
    private final VirusService virusService;
    private final ModelMapper modelMapper;

    @Autowired
    public VirusController(CapitalService capitalService, VirusService virusService, ModelMapper modelMapper) {
        this.capitalService = capitalService;
        this.virusService = virusService;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/add")
    public ModelAndView add(ModelAndView modelAndView, @ModelAttribute(name = "model") VirusBindingModel model) {
        modelAndView.addObject("model", model);
        modelAndView.addObject("capitals", this.capitalService.getAllCapitals().stream()
                .map(c -> this.modelMapper.map(c, CapitalsNameViewModel.class))
                .collect(Collectors.toList()));
        return super.view("add-virus", modelAndView);
    }

    @PostMapping("/add")
    public ModelAndView addConfirm(@Valid @ModelAttribute(name = "model") VirusBindingModel model, BindingResult bindingResult
            , ModelAndView modelAndView) {
        if (bindingResult.hasErrors()) {
            modelAndView.addObject("model", model);
            return super.view("add-virus", modelAndView);
        }
        boolean virusIsFound = virusService.addVirus(this.modelMapper.map(model, VirusServiceModel.class));
        if (!virusIsFound) {
            modelAndView.addObject("model", model);
            return super.view("add-virus", modelAndView);
        }
        return super.redirect("/");
    }

    @GetMapping("/show")
    public ModelAndView showAllViruses(ModelAndView modelAndView) {
        List<VirusesViewModel> viewModels = this.virusService.allViruses().stream()
                .map(v -> this.modelMapper.map(v, VirusesViewModel.class))
                .collect(Collectors.toList());
        modelAndView.addObject("viruses", viewModels);
        return super.view("show-all-viruses", modelAndView);
    }


    @GetMapping("/edit/{id}")
    public ModelAndView editVirus(@PathVariable(name = "id") String id, ModelAndView modelAndView) {
        VirusServiceWithCapitalsModel findVirus = this.virusService.findById(id);
        modelAndView.addObject("virus", findVirus);
        modelAndView.addObject("capitals", this.capitalService.getAllCapitals().stream()
                .map(c -> this.modelMapper.map(c, CapitalsNameViewModel.class))
                .collect(Collectors.toList()));
        modelAndView.addObject("mutations", Mutation.values());
        return super.view("edit-virus", modelAndView);
    }


    @PostMapping("/edit/{id}")
    public ModelAndView confirmEditVirus(ModelAndView modelAndView, @PathVariable(name = "id") String id,
                                         @ModelAttribute(name = "virus") VirusBindingModel model, BindingResult result) {
        if (result.hasErrors()) {
            modelAndView.addObject("mutations", Mutation.values());
            modelAndView.addObject("capitals", this.capitalService.getAllCapitals());
            modelAndView.addObject("virus", model);
            return super.view("edit-virus", modelAndView);
        }
        modelAndView.addObject("id", id);
        changeVirusDetails(model, id);
        return super.redirect("/viruses/show");
    }

    private void changeVirusDetails(VirusBindingModel model, String id) {
        this.virusService.changeVirusParams(this.modelMapper.map(model, VirusServiceModel.class), id);
    }


    @PostMapping("/delete/{id}")
    public ModelAndView deleteVirus(@PathVariable(name = "id") String virusId) {
        virusService.removeVirusById(virusId);
        return super.redirect("/viruses/show");
    }
}
