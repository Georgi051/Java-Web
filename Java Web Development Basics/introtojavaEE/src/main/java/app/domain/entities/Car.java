package app.domain.entities;

import javax.persistence.Column;
import javax.validation.constraints.Size;


public class Car{

   private String model;
   private String brand;
   private Integer year;
   private String engine;

    public Car() {
    }

    @Column(unique = true)
    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    @Column
    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    @Column
    @Size(min = 4,max = 4)
    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    @Column
    public String getEngine() {
        return engine;
    }

    public void setEngine(String engine) {
        this.engine = engine;
    }
}
