package de.lukaspanni.hkaparsys.carwash;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.Collectors;

public class CarWash {
    private final List<CarWashStation> carWashStations = new ArrayList<>();
    private final ReentrantLock lock = new ReentrantLock();
    private final Condition condition = lock.newCondition();
    private ExecutorService pool = Executors.newCachedThreadPool();

    public CarWash() {
        carWashStations.add(new InteriorCarWash());
        carWashStations.add(new InteriorCarWash());
        carWashStations.add(new AutomaticCarWash());
        carWashStations.add(new AutomaticCarWash());
        carWashStations.add(new AutomaticCarWash());
    }

    public void arrive(Iterable<Car> cars) {
        cars.forEach(car -> {
            System.out.printf("%s arrived\n", car);
            pool.submit(() -> washCar(car));
        });
    }

    public void washCar(Car car) {
        if (car.isCarClean()) return;

        CarWashStation usedStation;
        List<CarWashStation> suitableStations = getSuitableStations(car);


        lock.lock();
        try {
            while (suitableStations.stream().allMatch(CarWashStation::isOccupied)) {
                //nothing free, wait
                try {
                    System.out.printf("No suitable station free for %s, waiting\n", car);
                    condition.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            usedStation = suitableStations.stream().filter(CarWashStation::isFree).findAny().get();
            usedStation.setOccupied(true);
        } finally {
            lock.unlock();
        }

        usedStation.wash(car);

        lock.lock();
        try {
            usedStation.setOccupied(false);
            condition.signalAll();  // allow every sleeping thread to check if a suitable station is free
            // otherwise it can happen that a free station is not used even though it is suitable for a thread (but not suitable for the awoken thread)
        } finally {
            lock.unlock();
        }


        if (car.isCarClean()) {
            System.out.printf("%s is now clean\n", car);
            return;
        }
        washCar(car);
    }

    private List<CarWashStation> getSuitableStations(Car car) {
        List<CarWashStation> suitableStations = carWashStations;
        if (car.needsCarWash() && !car.needsInteriorCleaning()) {
            suitableStations = carWashStations.stream().filter(station -> station instanceof AutomaticCarWash).collect(Collectors.toList());
        } else if (car.needsInteriorCleaning() && !car.needsCarWash()) {
            suitableStations = carWashStations.stream().filter(station -> station instanceof InteriorCarWash).collect(Collectors.toList());
        }
        return suitableStations;
    }

    public boolean everyStationFree() {
        return this.carWashStations.stream().allMatch(CarWashStation::isFree);
    }

    public void close() {
        pool.shutdown();
    }
}

