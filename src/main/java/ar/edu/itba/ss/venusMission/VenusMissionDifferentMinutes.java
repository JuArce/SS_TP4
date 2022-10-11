package ar.edu.itba.ss.venusMission;

import ar.edu.itba.ss.venusMission.interfaces.Exporter;
import ar.edu.itba.ss.venusMission.models.CelestialBody;
import ar.edu.itba.ss.venusMission.models.SolarSystem;
import ar.edu.itba.ss.venusMission.utils.CelestialBodyFactory;
import ar.edu.itba.ss.venusMission.utils.DistanceExporter;
import ar.edu.itba.ss.venusMission.utils.OvitoExporter;

import java.time.LocalDate;

import static java.time.temporal.ChronoUnit.DAYS;


public class VenusMissionDifferentMinutes {

    public static void main(String[] args) {


        final LocalDate initialDate = LocalDate.of(2022, 9, 23);
        final LocalDate launchDate = LocalDate.of(2023, 5, 8);
        final int minutesOffset = 10;
        final int days = 1;

        final Exporter distanceExporter = new DistanceExporter("distance_" + launchDate + "_offset_" + minutesOffset + "_min" + ".csv", launchDate, minutesOffset);
        distanceExporter.open();

        for (int i = 0; i < days * 24 * 60 / minutesOffset; i++) {
            System.out.println("******************************");
            long startTime = System.currentTimeMillis();

            final CelestialBody earth = CelestialBodyFactory.getEarth();
            final CelestialBody venus = CelestialBodyFactory.getVenus();

            final String fileName = "venusMission_" + launchDate.atStartOfDay().plusMinutes(minutesOffset * i) + ".txt";
            System.out.println(fileName);
            final Exporter exporter = new OvitoExporter(fileName.replaceAll(":", "-"));
            exporter.open();

            final double dt = 300;
            final double tf = 365.25 * 24 * 3600; //1.944e+7

            final SolarSystem solarSystem = new SolarSystem(earth, venus, exporter, distanceExporter, null, dt, tf, initialDate, (int) DAYS.between(initialDate, launchDate), minutesOffset * i, 8.0);

            solarSystem.run();

            exporter.close();

            long endTime = System.currentTimeMillis();
            System.out.println("That took " + (endTime - startTime) + " milliseconds");
            System.out.println("******************************");
        }
        distanceExporter.close();
    }
}
