package de.lukaspanni.hkaparsys.carwash;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.stream.Collectors;

public class CarWash {
    private final List<CarWashStation> carWashStations = new ArrayList<>();
    private final PriorityBlockingQueue<Car> queue = new PriorityBlockingQueue<>();

    public CarWash() {
        carWashStations.add(new InteriorCarWash(queue));
        carWashStations.add(new InteriorCarWash(queue));
        carWashStations.add(new AutomaticCarWash(queue));
        carWashStations.add(new AutomaticCarWash(queue));
        carWashStations.add(new AutomaticCarWash(queue));

        carWashStations.forEach(Thread::start);
    }

    public void arrive(Iterable<Car> cars) {
        cars.forEach(car -> {
            System.out.printf("%s arrived\n", car);
            queue.add(car);
        });
    }


    public boolean everyStationFree() {
        return this.carWashStations.stream().allMatch(CarWashStation::isFree);
    }
}

