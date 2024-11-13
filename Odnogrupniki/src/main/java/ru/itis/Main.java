package ru.itis;

import ru.itis.model.CarEntity;
import ru.itis.service.CarService;
import ru.itis.service.PenaltyService;
import ru.itis.service.impl.CarServiceImpl;
import ru.itis.service.impl.PenaltyServiceImpl;

import java.sql.Date;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        CarService carService = new CarServiceImpl();
        PenaltyService penaltyService = new PenaltyServiceImpl();
        Scanner scanner = new Scanner(System.in);

        System.out.println("Сервис по управлению машинами и штрафами");
        System.out.println("help - список доступных команд");

        while (true) {
            System.out.print(">>> ");
            String input = scanner.nextLine().trim();
            try {
                if (input.startsWith("addCar ")) {
                    String title = input.substring(8);
                    CarEntity newCar = carService.saveNewCar(new CarEntity(null, title));
                    System.out.println("Добавлена новая машина: " + newCar);
                } else if (input.startsWith("findCarById ")) {
                    Long id = Long.valueOf(input.split(" ")[1]);
                    System.out.println(carService.findCarById(id));
                } else if (input.startsWith("deleteCar ")) {
                    Long id = Long.valueOf(input.split(" ")[1]);
                    boolean deleted = carService.deleteCarById(id);
                    System.out.println(deleted ? "Машина успешно удалена." : "Не удалось удалить машину.");
                } else if (input.startsWith("updateCar ")) {
                    String[] parts = input.split(" ");
                    Long id = Long.valueOf(parts[1]);
                    String title = parts[2];
                    CarEntity updatedCar = carService.updateCarById(new CarEntity(id, title), id);
                    System.out.println(updatedCar != null ? "Машина обновлена: " + updatedCar : "Не удалось обновить машину.");
                } else if (input.equalsIgnoreCase("listCars")) {
                    System.out.println(carService.findAll());
                } else if (input.startsWith("findPenaltiesByCarId ")) {
                    Long carId = Long.valueOf(input.split(" ")[1]);
                    System.out.println(penaltyService.findAllByCarId(carId));
                } else if (input.startsWith("findPenaltiesLargerThan ")) {
                    int minValue = Integer.parseInt(input.split(" ")[1]);
                    System.out.println(penaltyService.findAllWhereAmountLargerThan(minValue));
                } else if (input.startsWith("findPenaltiesOlderThan ")) {
                    Date date = Date.valueOf(input.split(" ")[1]);
                    System.out.println(penaltyService.findAllOlderThanDate(date));
                } else if (input.equalsIgnoreCase("help")) {
                    System.out.println("Список доступных команд:");
                    System.out.println("addCar <название машины>");
                    System.out.println("findCarById <id машины>");
                    System.out.println("deleteCar <id машины>");
                    System.out.println("updateCar <id машины> <новое название машины>");
                    System.out.println("listCars");
                    System.out.println("findPenaltiesByCarId <id машины>");
                    System.out.println("findPenaltiesLargerThan <сумма штрафа>");
                    System.out.println("findPenaltiesOlderThan <дата в формате YYYY-MM-DD>");
                }
                else {
                    System.out.println("Неизвестная команда. Пожалуйста, попробуйте еще раз.");
                }
            } catch (Exception e) {
                System.out.println("Произошла ошибка: " + e.getMessage());
            }
        }
    }
}