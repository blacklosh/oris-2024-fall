package ru.itis.service;

import ru.itis.model.CarEntity;

import java.util.List;

public interface CarService {

    List<CarEntity> findAll();

    CarEntity findCarById(Long id);

    CarEntity findCarByTitle(String title);

    CarEntity saveNewCar(CarEntity car);

    boolean deleteCarById(Long id);

    CarEntity updateCarById(CarEntity car, Long id);

}
