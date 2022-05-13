package de.lukaspanni.hkaparsys.carwash;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Random;

public class CarSource {
    static Random random = new Random();

    public static Collection<Car> getCars(int time) {
        if (time % 5 != 0) return new ArrayList<>(); // only return cars every five minutes
        int hour = (time / 60) + 1;
        switch (hour) {
            case 1:
                return createCars(1, 3, 2);
            case 2:
                return createCars(3, 6, 3);
            case 3:
                return createCars(1, 2, 1);
            default:
                throw new RuntimeException("Invalid Hour, Simulation already ended");
        }

    }

    private static Collection<Car> createCars(int min, int max, int interiorInterval) {
        int count = random.nextInt((max - min) + 1) + min;
        List<Car> cars = new ArrayList<>();
        int interiorCounter = 0;
        for (int i = 0; i < count; i++) {
            cars.add(new Car(++interiorCounter % interiorInterval == 0));
        }
        return cars;
    }
}
