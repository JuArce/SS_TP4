package ar.edu.itba.ss.venusMission;

import ar.edu.itba.ss.venusMission.interfaces.Exporter;
import ar.edu.itba.ss.venusMission.models.CelestialBody;
import ar.edu.itba.ss.venusMission.models.SolarSystem;
import ar.edu.itba.ss.venusMission.utils.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static java.time.temporal.ChronoUnit.DAYS;

public class VenusMissionDifferentVo {
    public static void main(String[] args) {
        List<Double> vo = List.of(7.998, 7.999, 8.0, 8.001, 8.002, 8.003, 8.004, 8.005);

        final LocalDate initialDate = LocalDate.of(2022, 9, 23);
        final LocalDateTime launchDate = LocalDateTime.of(2023, 5, 8, 21, 10, 0);
        final int minutesOffset = launchDate.getHour() * 60 + launchDate.getMinute();

        final String distanceFilename = "distance_diffVo_" + launchDate + ".csv";
        final Exporter distanceExporter = new DistanceVelocityExporter("venusMission/output/", distanceFilename.replaceAll(":", "-"), vo);
        distanceExporter.open();

        vo.forEach(spaceshipVo -> {
            System.out.println("******************************");
            long startTime = System.currentTimeMillis();

            final double dt = 300;
            final double tf = 365.25 * 24 * 3600;

            final CelestialBody earth = CelestialBodyFactory.getEarth();
            final CelestialBody venus = CelestialBodyFactory.getVenus();

            final String ovitoFilename = "venusMissionSpecific_" + launchDate + "_vo_" + spaceshipVo + ".txt";
            final Exporter ovitoExporter = new OvitoExporter("venusMission/output/ovito/", ovitoFilename.replaceAll(":", "-"));
            ovitoExporter.open();

            final int spaceshipLaunchDay = (int) DAYS.between(initialDate, launchDate);

            final SolarSystem solarSystem = new SolarSystem(earth, venus, dt, tf, initialDate, spaceshipLaunchDay, minutesOffset, spaceshipVo, ovitoExporter, distanceExporter, null);

            solarSystem.run();

            ovitoExporter.close();

            long endTime = System.currentTimeMillis();
            System.out.println("That took " + (endTime - startTime) + " milliseconds");
            System.out.println("******************************");
        });
        distanceExporter.close();
    }
}
