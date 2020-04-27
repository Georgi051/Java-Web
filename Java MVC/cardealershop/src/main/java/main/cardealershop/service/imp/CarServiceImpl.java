package main.cardealershop.service.imp;

import main.cardealershop.domain.entities.Car;
import main.cardealershop.domain.models.service.CarServiceModel;
import main.cardealershop.repository.CarRepository;
import main.cardealershop.service.CarService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CarServiceImpl implements CarService {
    private final CarRepository carRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public CarServiceImpl(CarRepository carRepository, ModelMapper modelMapper) {
        this.carRepository = carRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public CarServiceModel save(CarServiceModel carServiceModel) {
        this.carRepository.save(this.modelMapper.map(carServiceModel, Car.class));
        return carServiceModel;
    }

    @Override
    public List<CarServiceModel> getAllCars() {
        return this.carRepository.findAll().stream()
                .map(car -> this.modelMapper.map(car, CarServiceModel.class))
                .collect(Collectors.toList());
    }
}
