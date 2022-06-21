package de.lukaspanni.hkaparsys.carwash;

import java.util.UUID;

public class Car implements Comparable<Car> {

    private final UUID id = UUID.randomUUID();
    private boolean carWashRequested;
    private boolean interiorCleaningRequested;
    private final boolean isGoldenCustomer;


    public Car() {
        this(false);
    }

    public Car(boolean needsInteriorCleaning) {
        this.isGoldenCustomer = Math.random() < 0.3;
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


    public boolean isCarClean() {
        return !carWashRequested && !interiorCleaningRequested;
    }


    @Override
    public String toString() {
        return String.format("Car [%s]", id);
    }

    @Override
    public int compareTo(Car o) {
        // comparison prefers golden customers
        if (isGoldenCustomer == o.isGoldenCustomer)
            return this.id.compareTo(o.id);
        else if (isGoldenCustomer)
            return -1;
        else
            return 0;
    }
}
