package de.lukaspanni.hkaparsys.carwash;

import java.util.UUID;

import static de.lukaspanni.hkaparsys.carwash.Main.SLEEP_MULTIPLIER;

public abstract class CarWashStation {

    private final UUID id = UUID.randomUUID();
    protected int duration;
    private boolean occupied = false;

    public void wash(Car car) {
        System.out.printf("Start washing %s using %s\n", car, this);
        try {
            Thread.sleep(duration * SLEEP_MULTIPLIER);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        this.completeWash(car);
    }

    protected void completeWash(Car car) {
    }

    public void setOccupied(boolean occupied) {
        this.occupied = occupied;
    }

    public boolean isOccupied() {
        return occupied;
    }

    public boolean isFree() {
        return !occupied;
    }

    @Override
    public String toString() {
        return String.format("Station (%s) [%s]", this.getClass(), id);
    }
}

