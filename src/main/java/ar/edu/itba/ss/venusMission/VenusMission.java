package ar.edu.itba.ss.venusMission;

import ar.edu.itba.ss.venusMission.interfaces.Exporter;
import ar.edu.itba.ss.venusMission.models.SolarSystem;
import ar.edu.itba.ss.venusMission.utils.DistanceExporter;
import ar.edu.itba.ss.venusMission.utils.OvitoExporter;

import java.time.LocalDate;

import static java.time.temporal.ChronoUnit.DAYS;

public class VenusMission {
    public static void main(String[] args) {

        int days = 365;
        final LocalDate initialDate = LocalDate.of(2022, 9, 22);
        final Exporter distanceExporter = new DistanceExporter("distance.csv", initialDate);
        distanceExporter.open();

        for (int i = 0; i < days; i++) {
            long startTime = System.currentTimeMillis();

//            final LocalDate launchDate = LocalDate.of(2023, 5, 2);
//            final int spaceshipLaunchDay = (int) DAYS.between(initialDate, launchDate);
            final LocalDate launchDate = initialDate.plusDays(i);

            final Exporter exporter = new OvitoExporter("venusMission_" + launchDate + ".txt");
            exporter.open();

            final double dt = 300;
            final double tf = 365.25 * 24 * 3600 * 2; //1.944e+7


            final SolarSystem solarSystem = new SolarSystem(exporter, distanceExporter, dt, tf, initialDate, i);

            solarSystem.run();

            exporter.close();

            long endTime = System.currentTimeMillis();
            System.out.println("That took " + (endTime - startTime) + " milliseconds");
        }
        distanceExporter.close();
    }
}
