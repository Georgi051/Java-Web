package main.cardealershop.web.contollers;

import main.cardealershop.domain.models.binding.CarBindingModel;
import main.cardealershop.domain.models.service.CarServiceModel;
import main.cardealershop.domain.models.view.CarViewModel;
import main.cardealershop.service.CarService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/")
public class CarController {
    private final CarService carService;
    private final ModelMapper modelMapper;

    @Autowired
    public CarController(CarService carService, ModelMapper modelMapper) {
        this.carService = carService;
        this.modelMapper = modelMapper;
    }

    @GetMapping("create")
    public ModelAndView register() {
        return new ModelAndView("create");
    }

    @PostMapping("create")
    public ModelAndView confirmRegister(CarBindingModel carBindingModel) {
        this.carService.save(modelMapper.map(carBindingModel, CarServiceModel.class));
        return new ModelAndView("redirect:all");
    }

    @GetMapping("all")
    public ModelAndView getAll(ModelAndView modelAndView) {
        List<CarViewModel> cars = this.carService.getAllCars().stream()
                .map(carServiceModel -> this.modelMapper.map(carServiceModel, CarViewModel.class))
                .collect(Collectors.toList());
        modelAndView.addObject("cars", cars);
        modelAndView.setViewName("all");
        return modelAndView;
    }
}
