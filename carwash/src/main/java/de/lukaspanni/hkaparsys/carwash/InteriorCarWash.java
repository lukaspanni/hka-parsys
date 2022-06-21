package de.lukaspanni.hkaparsys.carwash;

import java.util.Random;
import java.util.concurrent.PriorityBlockingQueue;

public class InteriorCarWash extends CarWashStation {
    public InteriorCarWash(PriorityBlockingQueue<Car> queue){
        super(queue);
        int multiplier = new Random().nextInt(4) + 1;
        duration = multiplier * 5;
    }

    @Override
    protected void completeWash(Car car) {
        car.completeInteriorCleaning();
    }

    @Override
    protected boolean checkCondition(Car car) {
        return car.needsInteriorCleaning();
    }
}
