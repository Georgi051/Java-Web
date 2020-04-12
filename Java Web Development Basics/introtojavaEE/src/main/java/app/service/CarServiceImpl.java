package app.service;

import app.domain.entities.Car;
import app.domain.models.binding.CarCreateModel;
import app.repository.CarStorage;
import org.modelmapper.ModelMapper;

import javax.inject.Inject;
import java.util.List;
import java.util.stream.Collectors;

public class CarServiceImpl implements CarService {

    private final ModelMapper modelMapper;

    @Inject
    public CarServiceImpl(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }


    @Override
    public void createCar(CarCreateModel carCreateModel) {
        CarStorage.addCar(this.modelMapper.map(carCreateModel, Car.class));
    }

    @Override
    public List<CarCreateModel> allCars() {
        return CarStorage.getList().stream().map(c -> this.modelMapper.map(c,CarCreateModel.class))
                .collect(Collectors.toList());
    }
}
