package org.softuni.cardealer.service;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.modelmapper.ModelMapper;
import org.softuni.cardealer.domain.entities.Car;
import org.softuni.cardealer.domain.models.service.CarServiceModel;
import org.softuni.cardealer.repository.CarRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class CarServiceTests {

    @Autowired
    private CarRepository carRepository;
    private ModelMapper modelMapper;
    private CarService carService;
    private Car car;

    @Before
    public void init() {
        modelMapper = new ModelMapper();
        carService = new CarServiceImpl(this.carRepository, this.modelMapper);

        car = new Car();
        car.setMake("Honda");
        car.setModel("Accord");
        car.setTravelledDistance(10000L);
        carRepository.saveAndFlush(car);
    }

    @Test
    public void  carService_saveCarWithCorrectValuesIntoDataBase(){
        CarServiceModel toBeSave = new CarServiceModel();
        toBeSave.setMake("BMW");
        toBeSave.setModel("3");
        toBeSave.setTravelledDistance(10000L);

        CarServiceModel actual = this.carService.saveCar(toBeSave);
        CarServiceModel expected = this.modelMapper.map(this.carRepository.findAll().get(1),CarServiceModel.class);

        Assert.assertEquals(expected.getMake(),actual.getMake());
        Assert.assertEquals(expected.getModel(),actual.getModel());
        Assert.assertEquals(expected.getTravelledDistance(),actual.getTravelledDistance());
    }


    @Test(expected = Exception.class)
    public void carService_saveCarWithInvalidValueForMakeShouldThrowException(){
        CarServiceModel toBeSave = new CarServiceModel();
        toBeSave.setMake(null);
        toBeSave.setModel("3");
        toBeSave.setTravelledDistance(10000L);
        this.carService.saveCar(toBeSave);
    }

    @Test(expected = Exception.class)
    public void carService_saveCarWithInvalidValueForModelShouldThrowException(){
        CarServiceModel toBeSave = new CarServiceModel();
        toBeSave.setMake("BMW");
        toBeSave.setModel(null);
        toBeSave.setTravelledDistance(10000L);
        this.carService.saveCar(toBeSave);
    }

    @Test(expected = Exception.class)
    public void carService_saveCarWithInvalidValueForTravelledDistanceShouldThrowException(){
        CarServiceModel toBeSave = new CarServiceModel();
        toBeSave.setMake("BMW");
        toBeSave.setModel("3");
        toBeSave.setTravelledDistance(null);
        this.carService.saveCar(toBeSave);
    }

    @Test
    public void carService_editCarWithCurrentValues(){
        CarServiceModel toBeSave = new CarServiceModel();
        toBeSave.setId(car.getId());
        toBeSave.setMake("VW");
        toBeSave.setModel("Golf");
        toBeSave.setTravelledDistance(1200L);

        CarServiceModel actual = this.carService.editCar(toBeSave);
        CarServiceModel expected = this.modelMapper.map(this.carRepository.findAll().get(0),CarServiceModel.class);

        Assert.assertEquals(expected.getMake(),actual.getMake());
        Assert.assertEquals(expected.getModel(),actual.getModel());
        Assert.assertEquals(expected.getTravelledDistance(),actual.getTravelledDistance());
    }

    @Test(expected = Exception.class)
    public void  carService_editCarWithInvalidValueForMakeShouldThrowException(){
        CarServiceModel toBeSave = new CarServiceModel();
        toBeSave.setId(car.getId());
        toBeSave.setMake(null);
        toBeSave.setModel("Golf");
        toBeSave.setTravelledDistance(1200L);
        this.carService.editCar(toBeSave);
    }

    @Test(expected = Exception.class)
    public void  carService_editCarWithInvalidValueForModelShouldThrowException(){
        CarServiceModel toBeSave = new CarServiceModel();
        toBeSave.setId(car.getId());
        toBeSave.setMake("VW");
        toBeSave.setModel(null);
        toBeSave.setTravelledDistance(1200L);
        this.carService.editCar(toBeSave);
    }

    @Test(expected = Exception.class)
    public void  carService_editCarWithInvalidValueForDistanceShouldThrowException(){
        CarServiceModel toBeSave = new CarServiceModel();
        toBeSave.setId(car.getId());
        toBeSave.setMake("VW");
        toBeSave.setModel("Golf");
        toBeSave.setTravelledDistance(null);
        this.carService.editCar(toBeSave);
    }

    @Test
    public void carService_deleteCarWithValidId(){
        this.carService.deleteCar(car.getId());
        Assert.assertEquals(0,this.carRepository.count());
    }

    @Test(expected = Exception.class)
    public void carService_deleteCarWithInvalidIdShouldThrowException(){
        this.carService.deleteCar("asdadaddg");
    }

    @Test
    public void carService_findCarByIdShouldReturnCorrectResult(){
        CarServiceModel actual = this.carService.findCarById(car.getId());
        Assert.assertEquals(this.car.getId(),actual.getId());
    }

    @Test(expected = IllegalArgumentException.class)
    public void carService_findCarByIncorrectIdShouldThrowException(){
      this.carService.findCarById("afdasfafafa");
    }
}
