package ar.edu.itba.ss.spaceMemento;

import ar.edu.itba.ss.spaceMemento.interfaces.Exporter;
import ar.edu.itba.ss.spaceMemento.models.CelestialBody;
import ar.edu.itba.ss.spaceMemento.models.SolarSystem;
import ar.edu.itba.ss.spaceMemento.utils.CelestialBodyFactory;
import ar.edu.itba.ss.spaceMemento.utils.DistanceExporter;
import ar.edu.itba.ss.spaceMemento.utils.OvitoExporter;

import java.time.LocalDate;

public class VenusMissionDifferentDays {
    public static void main(String[] args) {

        int days = 365;
        final LocalDate initialDate = LocalDate.of(2022, 9, 23);
        final Exporter distanceExporter = new DistanceExporter("venusMission/output/", "distance.csv", initialDate);
        distanceExporter.open();

        for (int i = 0; i < days; i++) {
            System.out.println("******************************");
            long startTime = System.currentTimeMillis();

            final LocalDate launchDate = initialDate.plusDays(i);

            final CelestialBody earth = CelestialBodyFactory.getEarth();
            final CelestialBody venus = CelestialBodyFactory.getVenus();

            final Exporter exporter = new OvitoExporter("venusMission/output/ovito/", "venusMission_" + launchDate + ".txt");
            exporter.open();

            final double dt = 300;
            final double tf = 365.25 * 24 * 3600; //1.944e+7

            final SolarSystem solarSystem = new SolarSystem(earth, venus, dt, tf, initialDate, i, 8.0, exporter, distanceExporter, null);

            solarSystem.run();

            exporter.close();

            long endTime = System.currentTimeMillis();
            System.out.println("That took " + (endTime - startTime) + " milliseconds");
            System.out.println("******************************");
        }
        distanceExporter.close();
    }
}
