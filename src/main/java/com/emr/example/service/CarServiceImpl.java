package com.emr.example.service;

import com.emr.example.exceptions.ObjectNotFoundException;
import com.emr.example.model.Car;
import com.emr.example.repository.CarRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CarServiceImpl implements CarService {

    private final CarRepository carRepository;

    @Override
    public List<Car> getCars() {

        return carRepository.findAll();
    }

    @Override
    public Car createCar(Car car) {

        carRepository.save(car);

        return car;
    }

    @Override
    public Car getCar(Integer id) {

        return findById(id);
    }

    @Override
    public Car updateCar(Integer id, Car updatedCar) {

        var existingCar = findById(id);

        setReadOnlyFields(existingCar, updatedCar);

        carRepository.save(updatedCar);

        return updatedCar;
    }

    private void setReadOnlyFields(Car existingCar, Car updatedCar) {

        updatedCar.setId(existingCar.getId());
        updatedCar.setVin(existingCar.getVin());
        updatedCar.setMake(existingCar.getMake());
        updatedCar.setModel(existingCar.getModel());
    }

    @Override
    public void deleteCar(Integer id) {

        if (!carRepository.existsById(id)) {
            throw  new ObjectNotFoundException(id);
        }

        carRepository.deleteById(id);
    }

    private Car findById(Integer id) {

        var carOpt = carRepository.findById(id);

        if (carOpt.isEmpty()) {
            throw new ObjectNotFoundException(id);
        }

        return carOpt.get();
    }
}
