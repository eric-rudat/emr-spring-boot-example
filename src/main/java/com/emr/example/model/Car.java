package com.emr.example.model;

import com.emr.example.controller.resource.CarResource;
import com.emr.example.controller.resource.CarResponse;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
public class Car implements ModelObject<CarResponse> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String vin;

    @Column(nullable = false)
    private String make;

    @Column(nullable = false)
    private String model;

    @Override
    public CarResponse toResourceObject() {
        CarResponse carResponse = new CarResponse();
        carResponse.setId(id);
        carResponse.setVin(vin);
        carResponse.setMake(make);
        carResponse.setModel(model);

        return carResponse;
    }
}
