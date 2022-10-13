package ar.edu.itba.ss.spaceMemento;

import ar.edu.itba.ss.spaceMemento.interfaces.Exporter;
import ar.edu.itba.ss.spaceMemento.models.CelestialBody;
import ar.edu.itba.ss.spaceMemento.models.SolarSystem;
import ar.edu.itba.ss.spaceMemento.utils.CelestialBodyFactory;
import ar.edu.itba.ss.spaceMemento.utils.DistanceExporter;
import ar.edu.itba.ss.spaceMemento.utils.OvitoExporter;

import java.time.LocalDate;

import static java.time.temporal.ChronoUnit.DAYS;

public class MarsMissionDifferentMinutes {
    public static void main(String[] args) {
        final LocalDate initialDate = LocalDate.of(2022, 9, 23);
        final LocalDate launchDate = LocalDate.of(2024, 10, 29);
        final int minutesOffset = 10;
        final int days = 3;

        final Exporter distanceExporter = new DistanceExporter("marsMission/output/", "distance_" + launchDate + "_offset_" + minutesOffset + "_min" + ".csv", launchDate, minutesOffset);
        distanceExporter.open();

        for (int i = 0; i < days * 24 * 60 / minutesOffset; i++) {
            System.out.println("******************************");
            long startTime = System.currentTimeMillis();

            final CelestialBody earth = CelestialBodyFactory.getEarth();
            final CelestialBody mars = CelestialBodyFactory.getMars();

            final String filename = "marsMission_" + launchDate.atStartOfDay().plusMinutes(minutesOffset * i) + ".txt";
            System.out.println(filename);
            final Exporter exporter = new OvitoExporter("marsMission/output/ovito/", filename.replaceAll(":", "-"));
            exporter.open();

            final double dt = 300;
            final double tf = 365.25 * 24 * 3600 * 3; //1.944e+7

            final SolarSystem solarSystem = new SolarSystem(earth, mars, dt, tf, initialDate, (int) DAYS.between(initialDate, launchDate), minutesOffset * i, 8.0, exporter, distanceExporter, null);

            solarSystem.run();

            exporter.close();

            long endTime = System.currentTimeMillis();
            System.out.println("That took " + (endTime - startTime) + " milliseconds");
            System.out.println("******************************");
        }
        distanceExporter.close();

    }
}
