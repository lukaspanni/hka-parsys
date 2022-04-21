package de.lukaspanni.hkaparsys.carwash;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class CarWash {
    // Task:
    //      every car: -> automatic car wash, random (5-12) min
    //      interior cleaning: random (5,10,15) min
    //      order not relevant
    //
    //      first hour:     every 5 min: random (1-3) cars, interior cleaning for every other car
    //      second hour:    every 5 min: random (3-6) cars, interior cleaning for every third car
    //      third hour:     every 5 min: random (1-2) cars, interior cleaning for every car
    //
    //      3 automatic car wash lines
    //      2 interior cleaning stations
    // -> solve with Threads, synchronized, wait, notify; minutes -> seconds


    // Solution
    // CarWash manages automatic and interior stations
    // car (each with own thread) tries to enter, thread sleeps random amount based on type of current process
    //  wait if nothing free, notify on leave
    //      where should they enter?
    // cars created randomly and contain properties to determine which cleaning steps are still needed


    private final List<CarWashStation> carWashStations = new ArrayList<>();

    public CarWash() {
        carWashStations.add(new AutomaticCarWash());
        carWashStations.add(new AutomaticCarWash());
        carWashStations.add(new AutomaticCarWash());
        carWashStations.add(new InteriorCarWash());
        carWashStations.add(new InteriorCarWash());
    }

    public void washCar(Car car) {
        if (car.isCarClean()) return;

        CarWashStation availableStation;
        List<CarWashStation> suitableStations = carWashStations;
        if (car.needsCarWash() && !car.needsInteriorCleaning()) {
            suitableStations = carWashStations.stream().filter(station -> station instanceof AutomaticCarWash).collect(Collectors.toList());
        } else if (car.needsInteriorCleaning() && !car.needsCarWash()) {
            suitableStations = carWashStations.stream().filter(station -> station instanceof InteriorCarWash).collect(Collectors.toList());
        }

        synchronized (this) {
            while (suitableStations.stream().allMatch(CarWashStation::isOccupied)) {
                //nothing free, wait
                try {
                    System.out.printf("No suitable station free for %s, waiting\n", car);
                    wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            availableStation = suitableStations.stream().filter(CarWashStation::isFree).findAny().get();
            availableStation.setOccupied(true);
        }
        availableStation.wash(car);

        synchronized (this) {
            notify();
            availableStation.setOccupied(false);
        }

        if (car.isCarClean()) {
            System.out.printf("%s is now clean\n", car);
            return;

        }
        washCar(car);
    }

    public boolean everyStationFree(){
        return this.carWashStations.stream().allMatch(CarWashStation::isFree);
    }
}

