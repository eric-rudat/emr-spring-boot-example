package com.emr.example.service;

import com.emr.example.model.Car;

import java.util.List;

public interface CarService {

    List<Car> getCars();

    Car createCar(Car car);

    Car getCar(Integer id);

    Car updateCar(Integer id, Car updatedCar);

    void deleteCar(Integer id);
}
