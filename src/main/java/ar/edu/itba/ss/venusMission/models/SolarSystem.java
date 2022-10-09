package ar.edu.itba.ss.venusMission.models;

import ar.edu.itba.ss.venusMission.algorithms.GearPredictorCorrector;
import ar.edu.itba.ss.venusMission.interfaces.Exporter;
import ar.edu.itba.ss.venusMission.utils.Pair;
import lombok.Getter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class SolarSystem {

    @Getter
    private final List<CelestialBody> bodies;
    private final CelestialBody sun;
    private final CelestialBody venus;
    private final CelestialBody earth;
    private CelestialBody spaceship;

    private double t = 0;
    private final double dt;
    private final double tf;

    private final LocalDate initialDate;
    private final double spaceshipLaunchOffset;

    private final Exporter exporter;
    private final Exporter distanceExporter;

    private final GearPredictorCorrector algorithm;

    @Getter
    private final List<Double> spaceshipDistances;

    public SolarSystem(Exporter exporter, Exporter distanceExporter, double dt, double tf, LocalDate initialDate, int spaceshipLaunchDay) {
        this(exporter, distanceExporter, dt, tf, initialDate, spaceshipLaunchDay, 0);
    }

    public SolarSystem(Exporter exporter, Exporter distanceExporter, double dt, double tf, LocalDate initialDate, int spaceshipLaunchDay, int minutesOffset) {
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
        final Pair venusForce = this.venus.calculateForce(List.of(sun, earth));
        this.venus.setAcceleration(venusForce.getX() / this.venus.getMass(), venusForce.getY() / this.venus.getMass());
        final Pair earthForce = this.earth.calculateForce(List.of(sun, venus));
        this.earth.setAcceleration(earthForce.getX() / this.earth.getMass(), earthForce.getY() / this.earth.getMass());

        this.exporter = exporter;
        this.distanceExporter = distanceExporter;
        this.dt = dt;
        this.tf = tf;
        this.initialDate = initialDate;
        this.spaceshipLaunchOffset = spaceshipLaunchDay * 24 * 3600 + minutesOffset * 60;
        this.bodies = new ArrayList<>(List.of(sun, venus, earth));
        this.algorithm = new GearPredictorCorrector(this.bodies);
        this.spaceshipDistances = new ArrayList<>();
    }

    public void run() {
        int i = 0;
        this.t = 0;
        while (this.t < this.tf) {
            if (Double.compare(this.t, this.spaceshipLaunchOffset) == 0) {
                launchSpaceship();
            }
            if (i++ % 100 == 0) {
                this.exporter.export(this);
            }
            if (this.spaceship != null) {
                spaceshipDistances.add(this.spaceship.getPosition().distanceTo(this.venus.getPosition()));
            }
            this.t += this.dt;
            algorithm.run(this.bodies, this.dt);
        }
        this.distanceExporter.export(this);
    }

    private void launchSpaceship() {
        /**
         * Spaceship initial position and velocity
         */
        final double alpha = Math.atan2(earth.getPosition().getY() - sun.getPosition().getY(), earth.getPosition().getX() - sun.getPosition().getX());
        final double x = this.earth.getPosition().getX() - (this.earth.getRadius() + 1500) * Math.cos(alpha);
        final double y = this.earth.getPosition().getY() - (this.earth.getRadius() + 1500) * Math.sin(alpha);
        final double v0 = 7.12 + 8;
        final double ve = Math.sqrt(Math.pow(this.earth.getVelocity().getX(), 2) + Math.pow(this.earth.getVelocity().getY(), 2));
        final double vx = (ve - v0) * - Math.sin(alpha);
        final double vy = (ve - v0) * Math.cos(alpha);

//        System.out.println("ve = " + ve);
        System.out.println("vx = " + vx);
        System.out.println("vy = " + vy);

        this.spaceship = new CelestialBody.Builder()
                .name("Spaceship")
                .mass(2 * Math.pow(10, 5))
                .radius(0)
                .position(new Point(x, y))
                .velocity(new Pair(vx, vy))
                .acceleration(new Pair(0, 0))
                .build();

        final Pair spaceshipForce = this.spaceship.calculateForce(List.of(sun, venus, earth));
        this.spaceship.setAcceleration(spaceshipForce.getX() / this.spaceship.getMass(), spaceshipForce.getY() / this.spaceship.getMass());

        this.bodies.add(this.spaceship);
        this.algorithm.addObject(this.spaceship);
        System.out.println("Spaceship launched on " + this.initialDate.atStartOfDay().plusMinutes((int) spaceshipLaunchOffset / 60));
    }
}
