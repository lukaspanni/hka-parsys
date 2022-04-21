package de.lukaspanni.hkaparsys.carwash;

import java.util.UUID;

public class Car extends Thread {

    private final UUID id = UUID.randomUUID();
    private boolean carWashRequested;
    private boolean interiorCleaningRequested;
    private CarWash carWash;


    public Car() {
        this(false);
    }

    public Car(boolean needsInteriorCleaning) {
        carWashRequested = true;
        interiorCleaningRequested = needsInteriorCleaning;
        System.out.printf("New %s created, interior cleaning requested: %b\n", this, interiorCleaningRequested);
    }

    public boolean needsCarWash() {
        return carWashRequested;
    }

    public boolean needsInteriorCleaning() {
        return interiorCleaningRequested;
    }

    public void completeCarWash() {
        System.out.println(this + " Automatic car wash completed");
        carWashRequested = false;
    }

    public void completeInteriorCleaning() {
        System.out.println(this + " Interior cleaning completed");
        interiorCleaningRequested = false;
    }

    public void arriveAtCarWash(CarWash carWash) {
        this.carWash = carWash;
        start();
    }

    public boolean isCarClean() {
        return !carWashRequested && !interiorCleaningRequested;
    }

    @Override
    public void run() {
        carWash.washCar(this);
    }

    @Override
    public String toString() {
        return String.format("Car [%s]", id);
    }
}
