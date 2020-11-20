package com.emr.example.controller;

import com.emr.example.controller.resource.CarResource;
import com.emr.example.controller.resource.CarResponse;
import com.emr.example.exceptions.ObjectNotFoundException;
import com.emr.example.model.Car;
import com.emr.example.repository.CarRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/cars")
public class CarController {

    private final CarRepository carRepository;

    @GetMapping
    public List<CarResponse> getCars() {

        var cars = carRepository.findAll();

        return cars.stream().map(Car::toResourceObject).collect(Collectors.toList());
    }

    @PostMapping(consumes = "application/json", produces = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    public CarResponse createCreate(@RequestBody CarResource carResource) {

        var car = carResource.toModelObject();
        carRepository.save(car);

        return car.toResourceObject();
    }

    @GetMapping("/{id}")
    public CarResponse getCar(@PathVariable Integer id) {

        var carOpt = carRepository.findById(id);

        if (carOpt.isEmpty()) {
            throw new ObjectNotFoundException(id);
        }

        return carOpt.get().toResourceObject();
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCar(@PathVariable Integer id) {

        carRepository.deleteById(id);
    }
}
