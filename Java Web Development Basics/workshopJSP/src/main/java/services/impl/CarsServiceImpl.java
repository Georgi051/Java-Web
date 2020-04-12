package services.impl;

import models.entity.Car;
import models.entity.Engine;
import models.entity.User;
import models.service.CarServiceModel;
import models.view.CarCreateModel;
import org.modelmapper.ModelMapper;
import services.CarsService;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.util.List;
import java.util.stream.Collectors;

public class CarsServiceImpl implements CarsService {
    private final ModelMapper modelMapper;
    private final EntityManager entityManager;
    private final UsersServiceImpl usersService;

    @Inject
    public CarsServiceImpl(ModelMapper modelMapper, EntityManager entityManager, UsersServiceImpl usersService) {
        this.modelMapper = modelMapper;
        this.entityManager = entityManager;
        this.usersService = usersService;
    }

    @Override
    public List<CarServiceModel> getCarsForUser() {
        return entityManager.createQuery("select c from  Car as c", Car.class)
                .getResultList()
                .stream()
                .map(car -> modelMapper.map(car,CarServiceModel.class))
                .collect(Collectors.toList());
    }

    @Override
    public void createCar(String brand, String model, String year, String engine) {
        CarCreateModel carCreateModel = new CarCreateModel();
        carCreateModel.setBrand(brand);
        carCreateModel.setModel(model);
        carCreateModel.setYear(year);
        carCreateModel.setEngine(Engine.valueOf(engine.substring(0, 1).toUpperCase() + engine.substring(1)));
        User user = usersService.getUser();
        Car car = this.modelMapper.map(carCreateModel, Car.class);
        car.setUser(user);
        this.entityManager.getTransaction().begin();
        this.entityManager.persist(car);
        this.entityManager.getTransaction().commit();
    }
}
