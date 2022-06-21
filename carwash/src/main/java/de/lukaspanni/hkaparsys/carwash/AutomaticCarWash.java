package de.lukaspanni.hkaparsys.carwash;

import java.util.Random;
import java.util.concurrent.PriorityBlockingQueue;

public class AutomaticCarWash extends CarWashStation {


    public AutomaticCarWash(PriorityBlockingQueue<Car> queue){
        super(queue);
        duration = new Random().nextInt(8) + 5;
    }

    @Override
    protected void completeWash(Car car) {
        car.completeCarWash();
    }

    @Override
    protected boolean checkCondition(Car car) {
        return car.needsCarWash();
    }
}


