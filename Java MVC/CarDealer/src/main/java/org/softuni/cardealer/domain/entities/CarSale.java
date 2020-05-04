package org.softuni.cardealer.domain.entities;

import javax.persistence.*;

@Entity
@Table(name = "car_sales")
public class CarSale extends Sale {

    private Car car;

    public CarSale() {
    }

    @ManyToOne(targetEntity = Car.class)
    @JoinColumn(name = "car_id", referencedColumnName = "id")
    public Car getCar() {
        return car;
    }

    public void setCar(Car car) {
        this.car = car;
    }
}
