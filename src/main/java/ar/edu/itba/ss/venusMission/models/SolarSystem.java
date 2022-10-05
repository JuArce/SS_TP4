package ar.edu.itba.ss.venusMission.models;

import ar.edu.itba.ss.venusMission.interfaces.Exporter;
import ar.edu.itba.ss.venusMission.utils.Pair;

import java.util.List;

public class SolarSystem {

    private final CelestialBody sun;
    private final CelestialBody venus;
//    private final CelestialBody earth;
//    private final CelestialBody spaceship;
    private double t = 0;
    private final double dt;
    private final double tf;
    private final Exporter exporter;

    public SolarSystem(Exporter exporter, double dt, double tf) {
        this.sun = new CelestialBody.Builder()
                .mass(1988500 * Math.pow(10, 24))
                .radius(695700)
                .position(new Point(0, 0))
                .velocity(new Pair(0, 0))
                .build();
        this.venus = new CelestialBody.Builder()
                .mass(48.685 * Math.pow(10, 23))
                .radius(6051.84)
                .position(new Point(-1.014319519875520 * Math.pow(10, 8), 3.525562675248842 * Math.pow(10, 7)))
                .velocity(new Pair(-1.166353075744313 * 10, -3.324015683726970 * 10))
                .build();
        this.exporter = exporter;
        this.dt = dt;
        this.tf = tf;
    }

    public void run() {
        this.t = 0;
        while (this.t < this.tf) {
            exporter.export(List.of(sun, venus));
            this.t += this.dt;
//            final Pair r0 =

        }
    }
}
