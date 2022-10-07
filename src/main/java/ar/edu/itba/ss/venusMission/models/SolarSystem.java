package ar.edu.itba.ss.venusMission.models;

import ar.edu.itba.ss.venusMission.algorithms.GearPredictorCorrector;
import ar.edu.itba.ss.venusMission.interfaces.Exporter;
import ar.edu.itba.ss.venusMission.utils.Pair;

import java.util.List;

public class SolarSystem {

    private final List<CelestialBody> bodies;
    private final CelestialBody sun;
    private final CelestialBody venus;
    private final CelestialBody earth;
//    private final CelestialBody spaceship;
    private double t = 0;
    private final double dt;
    private final double tf;
    private final Exporter exporter;
    private final GearPredictorCorrector algorithm;

    public SolarSystem(Exporter exporter, double dt, double tf) {
        this.sun = new CelestialBody.Builder()
                .name("Sun")
                .mass(1988500 * Math.pow(10, 24))
                .radius(695700)
                .position(new Point(0, 0))
                .velocity(new Pair(0, 0))
                .acceleration(new Pair(0, 0))
                .build();

        this.venus = new CelestialBody.Builder()
                .name("Venus")
                .mass(48.685 * Math.pow(10, 23))
                .radius(6051.84)
                .position(new Point(-1.014319519875520 * Math.pow(10, 8), 3.525562675248842 * Math.pow(10, 7)))
                .velocity(new Pair(-1.166353075744313 * 10, -3.324015683726970 * 10))
                .acceleration(new Pair(0, 0))
                .build();

        this.earth = new CelestialBody.Builder()
                .name("Earth")
                .mass(5.97219 * Math.pow(10, 24))
                .radius(6371)
                .position(new Point(1.501409394622880 * Math.pow(10, 8), -9.238096308876731 * Math.pow(10, 5)))
                .velocity(new Pair(-2.949925999285836 * Math.pow(10, -1), 2.968579130065282 * Math.pow(10, 1)))
                .acceleration(new Pair(0, 0))
                .build();

        /**
         * Set all accelerations
         */
        final Pair venusForce = this.venus.apply(List.of(sun, earth));
        this.venus.setAcceleration(venusForce.getX() / this.venus.getMass(), venusForce.getY() / this.venus.getMass());
        final Pair earthForce = this.earth.apply(List.of(sun, venus));
        this.earth.setAcceleration(earthForce.getX() / this.earth.getMass(), earthForce.getY() / this.earth.getMass());

        this.exporter = exporter;
        this.dt = dt;
        this.tf = tf;
        this.bodies = List.of(sun, venus, earth);
        this.algorithm = new GearPredictorCorrector(this.bodies);
    }

    public void run() {
        this.t = 0;
        while (this.t < this.tf) {
            exporter.export(this.bodies);
            this.t += this.dt;
            algorithm.run(this.bodies, this.dt);
        }
    }
}
