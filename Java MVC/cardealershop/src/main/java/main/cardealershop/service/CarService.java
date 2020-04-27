package main.cardealershop.service;

import main.cardealershop.domain.models.service.CarServiceModel;

import java.util.List;

public interface CarService {

    CarServiceModel save(CarServiceModel carServiceModel);

    List<CarServiceModel> getAllCars();
}
