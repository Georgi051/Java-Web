package app.service;

import app.domain.models.binding.CarCreateModel;

import java.util.List;

public interface CarService {

    void createCar(CarCreateModel carCreateModel);

    List<CarCreateModel> allCars();
}
