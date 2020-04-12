package app.repository;

import app.domain.entities.Car;

import java.util.ArrayList;
import java.util.List;

public final class CarStorage {

    private final static List<Car> carList = new ArrayList<>();

    public static void addCar (Car car){
        carList.add(car);
    }

    public static List<Car> getList(){
        return carList;
    }
}
