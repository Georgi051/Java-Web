package com.example.residentevil.domain.bindingModels;

import com.example.residentevil.domain.entities.enums.Creator;
import com.example.residentevil.domain.entities.enums.Magnitude;
import com.example.residentevil.domain.entities.enums.Mutation;
import com.example.residentevil.web.validators.EnumValidator;
import com.example.residentevil.web.validators.LocalDateBefore;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.*;
import java.time.LocalDate;
import java.util.List;

public class VirusBindingModel {
    private String name;
    private String description;
    private String sideEffects;
    private String creator;
    private boolean isDeadly;
    private boolean isCurable;
    private Mutation mutation;
    private Integer turnoverRate;
    private Integer hoursUntilTurn;
    private Magnitude magnitude;
    private LocalDate releasedOn;
    private List<Long> capitals;

    public VirusBindingModel() {
    }

    @NotNull(message = "Invalid name")
    @Size(min = 3, max = 10, message = "Invalid name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @NotNull(message = "Invalid description")
    @Size(min = 5,max = 100, message = "Invalid description")
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


    @Size(max = 50,message = "Invalid side effects")
    public String getSideEffects() {
        return sideEffects;
    }

    public void setSideEffects(String sideEffects) {
        this.sideEffects = sideEffects;
    }

    @NotNull(message = "Invalid creator")

    @NotNull(message = "Invalid creator")
    @EnumValidator(enumClass = Creator.class, message = "Invalid creator")
    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public boolean isDeadly() {
        return isDeadly;
    }

    public void setDeadly(boolean deadly) {
        isDeadly = deadly;
    }

    public boolean isCurable() {
        return isCurable;
    }

    public void setCurable(boolean curable) {
        isCurable = curable;
    }

    @NotNull(message = "Incorrect data for mutation")
    public Mutation getMutation() {
        return mutation;
    }

    public void setMutation(Mutation mutation) {
        this.mutation = mutation;
    }

    @Min(value = 1,message = "Invalid turnover rate")
    @Max(value = 100,message = "Invalid turnover rate")
    public Integer getTurnoverRate() {
        return turnoverRate;
    }

    public void setTurnoverRate(Integer turnoverRate) {
        this.turnoverRate = turnoverRate;
    }

    @Min(value = 1,message = "Invalid turnover rate")
    @Max(value = 12,message = "Invalid turnover rate")
    public Integer getHoursUntilTurn() {
        return hoursUntilTurn;
    }

    public void setHoursUntilTurn(Integer hoursUntilTurn) {
        this.hoursUntilTurn = hoursUntilTurn;
    }

    @NotNull(message = "Incorrect data for magnitude")
    public Magnitude getMagnitude() {
        return magnitude;
    }

    public void setMagnitude(Magnitude magnitude) {
        this.magnitude = magnitude;
    }

    @NotNull(message = "Invalid date")
    @LocalDateBefore(message = "Invalid Date")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    public LocalDate getReleasedOn() {
        return releasedOn;
    }

    public void setReleasedOn(LocalDate releasedOn) {
        this.releasedOn = releasedOn;
    }

    @NotEmpty(message = "You must select capitals")
    public List<Long> getCapitals() {
        return capitals;
    }

    public void setCapitals(List<Long> capitals) {
        this.capitals = capitals;
    }
}
