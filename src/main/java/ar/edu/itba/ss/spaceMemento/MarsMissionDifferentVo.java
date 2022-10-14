package ar.edu.itba.ss.spaceMemento;

import ar.edu.itba.ss.spaceMemento.interfaces.Exporter;
import ar.edu.itba.ss.spaceMemento.models.CelestialBody;
import ar.edu.itba.ss.spaceMemento.models.SolarSystem;
import ar.edu.itba.ss.spaceMemento.utils.CelestialBodyFactory;
import ar.edu.itba.ss.spaceMemento.utils.DistanceVelocityExporter;
import ar.edu.itba.ss.spaceMemento.utils.OvitoExporter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static java.time.temporal.ChronoUnit.DAYS;

public class MarsMissionDifferentVo {
    public static void main(String[] args) {
        List<Double> vo = List.of(7.9997, 7.9998, 7.9999, 8.0, 8.0001, 8.0002, 8.0003, 8.0004, 8.0005, 8.0006);

        final LocalDate initialDate = LocalDate.of(2022, 9, 23);
        final LocalDateTime launchDate = LocalDateTime.of(2024, 10, 20, 17, 55, 0);
        final int minutesOffset = launchDate.getHour() * 60 + launchDate.getMinute();

        final String distanceFilename = "distance_diffVo_" + launchDate + ".csv";
        final Exporter distanceExporter = new DistanceVelocityExporter("marsMission/output/", distanceFilename.replaceAll(":", "-"), vo);
        distanceExporter.open();

        vo.forEach(spaceshipVo -> {
            System.out.println("******************************");
            long startTime = System.currentTimeMillis();

            final double dt = 300;
            final double tf = 365.25 * 24 * 3600 * 3;

            final CelestialBody earth = CelestialBodyFactory.getEarth();
            final CelestialBody mars = CelestialBodyFactory.getMars();

            final String ovitoFilename = "marsMissionSpecific_" + launchDate + "_vo_" + spaceshipVo + ".txt";
            final Exporter ovitoExporter = new OvitoExporter("marsMission/output/ovito/", ovitoFilename.replaceAll(":", "-"));
            ovitoExporter.open();

            final int spaceshipLaunchDay = (int) DAYS.between(initialDate, launchDate);

            final SolarSystem solarSystem = new SolarSystem(earth, mars, dt, tf, initialDate, spaceshipLaunchDay, minutesOffset, spaceshipVo, ovitoExporter, distanceExporter, null);

            solarSystem.run();

            ovitoExporter.close();

            long endTime = System.currentTimeMillis();
            System.out.println("That took " + (endTime - startTime) + " milliseconds");
            System.out.println("******************************");
        });
        distanceExporter.close();
    }
}
