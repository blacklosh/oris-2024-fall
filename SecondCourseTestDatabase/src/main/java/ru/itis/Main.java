package ru.itis;

import ru.itis.model.CarEntity;
import ru.itis.service.CarService;
import ru.itis.service.PenaltyService;
import ru.itis.service.impl.CarServiceImpl;
import ru.itis.service.impl.PenaltyServiceImpl;

import java.sql.Date;

public class Main {
    public static void main(String[] args) {
        CarService carService = new CarServiceImpl();
        PenaltyService penaltyService = new PenaltyServiceImpl();

        //System.out.println(carService.findAll());
        //System.out.println(carService.findCarById(null));
        //System.out.println(carService.findCarByTitle("123'; truncate table CAR; select * from CAR where title = '123"));
        //System.out.println(carService.saveNewCar(new CarEntity(null, "My Best Car")));
        //System.out.println(carService.deleteCarById(40L));
        //System.out.println(carService.updateCarById(new CarEntity(-100L, "Suzuki Grand Vitara"), 200L));

        System.out.println(penaltyService.findAllByCarId(2L));
        System.out.println(penaltyService.findAllWhereAmountLargerThan(50));
        System.out.println(penaltyService.findAllOlderThanDate(Date.valueOf("2024-10-10")));
    }
}