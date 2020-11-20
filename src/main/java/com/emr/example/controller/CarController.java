package com.emr.example.controller;

import com.emr.example.controller.resource.CarResource;
import com.emr.example.controller.resource.CarResponse;
import com.emr.example.exceptions.ObjectNotFoundException;
import com.emr.example.model.Car;
import com.emr.example.repository.CarRepository;
import com.emr.example.service.CarService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/cars")
public class CarController {

    private final CarService carService;

    @GetMapping
    public List<CarResponse> getAll() {

        var cars = carService.getCars();

        return cars.stream().map(Car::toResourceObject).collect(Collectors.toList());
    }

    @PostMapping(consumes = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    public CarResponse create(@RequestBody CarResource carResource) {

        var car = carResource.toModelObject();

        var createdCar = carService.createCar(car);

        return createdCar.toResourceObject();
    }

    @GetMapping("/{id}")
    public CarResponse get(@PathVariable Integer id) {

        var car = carService.getCar(id);

        return car.toResourceObject();
    }

    @PutMapping(value = "/{id}", consumes = "application/json")
    public CarResponse update(@PathVariable Integer id, @RequestBody CarResource carResource) {

        var car = carResource.toModelObject();

        var updatedCar = carService.updateCar(id, car);

        return updatedCar.toResourceObject();
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Integer id) {

        carService.deleteCar(id);
    }
}
