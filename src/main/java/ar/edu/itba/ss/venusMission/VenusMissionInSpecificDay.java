package ar.edu.itba.ss.venusMission;

import ar.edu.itba.ss.venusMission.interfaces.Exporter;
import ar.edu.itba.ss.venusMission.models.SolarSystem;
import ar.edu.itba.ss.venusMission.utils.DistanceExporter;
import ar.edu.itba.ss.venusMission.utils.OvitoExporter;
import ar.edu.itba.ss.venusMission.utils.VelocityExporter;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static java.time.temporal.ChronoUnit.DAYS;

public class VenusMissionInSpecificDay {
    public static void main(String[] args) {
        System.out.println("******************************");
        long startTime = System.currentTimeMillis();

        final double dt = 300;
        final double tf = 365.25 * 24 * 3600;

        final LocalDate initialDate = LocalDate.of(2022, 9, 23);
        final LocalDateTime launchDate = LocalDateTime.of(2023, 5, 8, 21, 10, 0);
        final int minutesOffset = launchDate.getHour() * 60 + launchDate.getMinute();

        final String distanceFilename = "distance_" + launchDate + ".csv";
        final Exporter distanceExporter = new DistanceExporter(distanceFilename.replaceAll(":", "-"), launchDate.toLocalDate());
        distanceExporter.open();

        final String ovitoFilename = "venusMissionSpecific_" + launchDate + ".txt";
        final Exporter ovitoExporter = new OvitoExporter(ovitoFilename.replaceAll(":", "-"));
        ovitoExporter.open();

        final String velocityFilename = "velocity_" + launchDate + ".csv";
        final Exporter velocityExporter = new VelocityExporter(velocityFilename.replaceAll(":", "-"), initialDate.atStartOfDay());
        velocityExporter.open();

        final int spaceshipLaunchDay = (int) DAYS.between(initialDate, launchDate);

        final SolarSystem solarSystem = new SolarSystem(ovitoExporter, distanceExporter, velocityExporter, dt, tf, initialDate, spaceshipLaunchDay, minutesOffset);

        solarSystem.run();

        distanceExporter.close();
        ovitoExporter.close();
        velocityExporter.close();

        long endTime = System.currentTimeMillis();
        System.out.println("That took " + (endTime - startTime) + " milliseconds");
        System.out.println("******************************");
    }
}