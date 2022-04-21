package de.lukaspanni.hkaparsys.carwash;

import java.util.Random;

public class AutomaticCarWash extends CarWashStation {


    public AutomaticCarWash(){
        duration = new Random().nextInt(8) + 5;
    }

    @Override
    protected void completeWash(Car car) {
        car.completeCarWash();
    }
}


