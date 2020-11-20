package com.emr.example.controller.resource;

import com.emr.example.model.Car;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CarResource implements ResourceObject<Car> {

    private String vin;
    private String make;
    private String model;

    @Override
    public Car toModelObject() {
        return new Car().setVin(vin).setMake(make).setModel(model);
    }
}
