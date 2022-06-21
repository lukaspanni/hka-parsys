package de.lukaspanni.hkaparsys.carwash;

import java.util.Arrays;

public class Main {
    public static final int SLEEP_MULTIPLIER = 20;

    public static final int ITERATIONS = 10;

    public static void main(String[] args) {

        int[] times = new int[ITERATIONS];
        for (int i = 0; i < times.length; i++) {
            int timer = runCarWash();
            System.out.printf("CarWash took %d:%d to complete\n", (timer / 60), (timer % 60));
            times[i] = timer;
        }
        var oAvg = Arrays.stream(times).average();
        var oMax = Arrays.stream(times).max();
        var oMin = Arrays.stream(times).min();
        double avg = oAvg.orElse(0.0);
        int max = oMax.orElse(0);
        int min = oMin.orElse(0);
        System.out.printf("Average time to complete: %d:%d \n", (int)(avg / 60), (int)(avg % 60));
        System.out.printf("Maximum time to complete: %d:%d \n", (max / 60), (max % 60));
        System.out.printf("Minimum time to complete: %d:%d \n", (min / 60), (min % 60));

    }

    private static int runCarWash() {
        CarWash carWash = new CarWash();

        int limit = 3 * 60;
        int timer = 0;

        while (timer < limit || !carWash.everyStationFree()) {
            System.out.printf("[Current Time: %d:%d]\n", (timer / 60), timer % 60);
            if (timer < limit) {
                var cars = CarSource.getCars(timer);
                carWash.arrive(cars);
            }
            try {
                Thread.sleep(SLEEP_MULTIPLIER);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            timer++;
        }
        return timer;
    }


}
