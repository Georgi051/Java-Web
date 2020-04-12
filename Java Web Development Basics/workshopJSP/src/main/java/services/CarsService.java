package services;

import models.service.CarServiceModel;
import models.view.CarCreateModel;

import java.util.List;

public interface CarsService {
    List<CarServiceModel> getCarsForUser();

    void createCar(String brand, String model, String year, String engine);
}
