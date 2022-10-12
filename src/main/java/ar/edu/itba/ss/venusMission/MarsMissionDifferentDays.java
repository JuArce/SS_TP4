package ar.edu.itba.ss.venusMission;

import ar.edu.itba.ss.venusMission.interfaces.Exporter;
import ar.edu.itba.ss.venusMission.models.CelestialBody;
import ar.edu.itba.ss.venusMission.models.SolarSystem;
import ar.edu.itba.ss.venusMission.utils.CelestialBodyFactory;
import ar.edu.itba.ss.venusMission.utils.DistanceExporter;
import ar.edu.itba.ss.venusMission.utils.OvitoExporter;

import java.time.LocalDate;

public class MarsMissionDifferentDays {
    public static void main(String[] args) {

        int days = 365 * 3;
        final LocalDate initialDate = LocalDate.of(2022, 9, 23);
        final Exporter distanceExporter = new DistanceExporter("marsMission/output/", "distance.csv", initialDate);
        distanceExporter.open();

        for (int i = 0; i < days; i++) {
            System.out.println("******************************");
            long startTime = System.currentTimeMillis();

            final LocalDate launchDate = initialDate.plusDays(i);

            final CelestialBody earth = CelestialBodyFactory.getEarth();
            final CelestialBody mars = CelestialBodyFactory.getMars();

            final Exporter exporter = new OvitoExporter("marsMission/output/ovito/", "marsMission_" + launchDate + ".txt");
            exporter.open();

            final double dt = 300;
            final double tf = 365.25 * 24 * 3600 * 3;

            final SolarSystem solarSystem = new SolarSystem(earth, mars, dt, tf, initialDate, i, 8.0, exporter, distanceExporter, null);

            solarSystem.run();

            exporter.close();

            long endTime = System.currentTimeMillis();
            System.out.println("That took " + (endTime - startTime) + " milliseconds");
            System.out.println("******************************");
        }
        distanceExporter.close();
    }
}
