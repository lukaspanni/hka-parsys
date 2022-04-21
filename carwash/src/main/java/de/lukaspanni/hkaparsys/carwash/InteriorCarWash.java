package de.lukaspanni.hkaparsys.carwash;

import java.util.Random;

public class InteriorCarWash extends CarWashStation {
    public InteriorCarWash(){
        int multiplier = new Random().nextInt(4) + 1;
        duration = multiplier * 5;
    }

    @Override
    protected void completeWash(Car car) {
        car.completeInteriorCleaning();
    }
}
