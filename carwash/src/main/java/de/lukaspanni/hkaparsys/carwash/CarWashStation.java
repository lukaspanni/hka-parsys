package de.lukaspanni.hkaparsys.carwash;

import java.util.UUID;
import java.util.concurrent.PriorityBlockingQueue;

import static de.lukaspanni.hkaparsys.carwash.Main.SLEEP_MULTIPLIER;

public abstract class CarWashStation extends Thread{

    private final UUID id = UUID.randomUUID();
    protected int duration;
    private boolean occupied = false;
    private final PriorityBlockingQueue<Car> queue;

    public CarWashStation(PriorityBlockingQueue<Car> queue){
        this.queue = queue;
    }

    @Override
    public void run() {
        Car car;
        while(true) {
            try {
                car = queue.take();
            }catch (InterruptedException ie){
                continue;
            }
            System.out.printf("Start washing %s using %s\n", car, this);
            this.wash(car);
        }
    }

    private void wash(Car car){
        if (!this.checkCondition(car)) return;
        this.setOccupied(true);

        try {
            Thread.sleep(duration * SLEEP_MULTIPLIER);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        this.setOccupied(false);

        this.completeWash(car);
        if(!car.isCarClean()){
            queue.put(car);
        }
    }

    protected boolean checkCondition(Car car){
        return false;
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

