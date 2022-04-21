package de.lukaspanni.hkaparsys.carwash;

public class Main {
    public static final int SLEEP_MULTIPLIER = 1000;


    public static void main(String[] args) throws InterruptedException {
        CarWash carWash = new CarWash();


        int limit = 3 * 60;
        int timer = 0;

        for (; timer < limit; timer++) {
            System.out.printf("[Current Time: %d:%d]\n", (timer / 60), timer % 60);
            var cars = CarSource.getCars(timer);
            cars.forEach(car -> car.arriveAtCarWash(carWash));
            try {
                Thread.sleep(SLEEP_MULTIPLIER);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        System.out.println("Wait for car wash to complete");
        while(!carWash.everyStationFree()){
            System.out.printf("[Current Time: %d:%d]\n", (timer / 60), timer % 60);
            try {
                Thread.sleep(SLEEP_MULTIPLIER);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            timer++;
        }


    }


}
