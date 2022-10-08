package ar.edu.itba.ss.venusMission;

import ar.edu.itba.ss.venusMission.interfaces.Exporter;
import ar.edu.itba.ss.venusMission.models.SolarSystem;
import ar.edu.itba.ss.venusMission.utils.DistanceExporter;
import ar.edu.itba.ss.venusMission.utils.OvitoExporter;

import java.time.LocalDate;

import static java.time.temporal.ChronoUnit.DAYS;

public class VenusMissionInSpecificDay {
    public static void main(String[] args) {
        System.out.println("******************************");
        long startTime = System.currentTimeMillis();

        final LocalDate initialDate = LocalDate.of(2022, 9, 23);
        final LocalDate launchDate = LocalDate.of(2023, 4, 28);

        final Exporter distanceExporter = new DistanceExporter("distance_" + launchDate + ".csv", launchDate);
        distanceExporter.open();
        final Exporter ovitoExporter = new OvitoExporter("venusMissionSpecific_" + launchDate + ".txt");
        ovitoExporter.open();

        final int spaceshipLaunchDay = (int) DAYS.between(initialDate, launchDate);

        final double dt = 300;
        final double tf = 365.25 * 24 * 3600 * 2;

        final SolarSystem solarSystem = new SolarSystem(ovitoExporter, distanceExporter, dt, tf, initialDate, spaceshipLaunchDay);

        solarSystem.run();

        distanceExporter.close();
        ovitoExporter.close();

        long endTime = System.currentTimeMillis();
        System.out.println("That took " + (endTime - startTime) + " milliseconds");
        System.out.println("******************************");
    }
}
