package ar.edu.itba.ss.venusMission;

import ar.edu.itba.ss.venusMission.interfaces.Exporter;
import ar.edu.itba.ss.venusMission.models.SolarSystem;
import ar.edu.itba.ss.venusMission.utils.OvitoExporter;

public class VenusMission {
    public static void main(String[] args) {
        long startTime = System.currentTimeMillis();

        final Exporter exporter = new OvitoExporter("venusMission.txt");
        exporter.open();
        final double dt = 3600;
        final double tf = 365.25 * 24 * 3600 * 1; //1.944e+7
        final SolarSystem solarSystem = new SolarSystem(exporter, dt, tf);

        solarSystem.run();

        exporter.close();

        long endTime = System.currentTimeMillis();
        System.out.println("That took " + (endTime - startTime) + " milliseconds");
    }
}
