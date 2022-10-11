package ar.edu.itba.ss.venusMission.models;

import ar.edu.itba.ss.venusMission.algorithms.GearPredictorCorrector;
import ar.edu.itba.ss.venusMission.interfaces.Exporter;
import ar.edu.itba.ss.venusMission.utils.CelestialBodyFactory;
import ar.edu.itba.ss.venusMission.utils.Pair;
import lombok.Getter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class SolarSystem {

    @Getter
    private final List<CelestialBody> bodies;
    private final CelestialBody sun;
    private final CelestialBody departure;
    private final CelestialBody target;
    @Getter
    private CelestialBody spaceship;

    @Getter
    private double t = 0;
    private final double dt;
    private final double tf;

    private final LocalDate initialDate;
    private final double spaceshipLaunchOffset;
    private final double spaceshipVo;

    private final Exporter ovitoExporter;
    private final Exporter distanceExporter;
    private final Exporter velocityExporter;

    private final GearPredictorCorrector algorithm;

    @Getter
    private final List<Double> spaceshipDistances;

    public SolarSystem(CelestialBody departure, CelestialBody target, Exporter ovitoExporter, Exporter distanceExporter, Exporter velocityExporter, double dt, double tf, LocalDate initialDate, int spaceshipLaunchDay, double spaceshipVo) {
        this(departure, target, ovitoExporter, distanceExporter, velocityExporter, dt, tf, initialDate, spaceshipLaunchDay, 0, spaceshipVo);
    }

    public SolarSystem(CelestialBody departure, CelestialBody target, Exporter ovitoExporter, Exporter distanceExporter, Exporter velocityExporter, double dt, double tf, LocalDate initialDate, int spaceshipLaunchDay, int minutesOffset, double spaceshipVo) {
        this.sun = CelestialBodyFactory.getSun();
        this.departure = departure;
        this.target = target;

        /**
         * Set all accelerations
         */
        final Pair targetForce = this.target.calculateForce(List.of(sun, departure));
        this.target.setAcceleration(targetForce.getX() / this.target.getMass(), targetForce.getY() / this.target.getMass());
        final Pair departureForce = this.departure.calculateForce(List.of(sun, target));
        this.departure.setAcceleration(departureForce.getX() / this.departure.getMass(), departureForce.getY() / this.departure.getMass());

        this.ovitoExporter = ovitoExporter;
        this.distanceExporter = distanceExporter;
        this.velocityExporter = velocityExporter;

        this.dt = dt;
        this.tf = tf;

        this.initialDate = initialDate;
        this.spaceshipLaunchOffset = spaceshipLaunchDay * 24 * 3600 + minutesOffset * 60;
        this.spaceshipVo = spaceshipVo;

        this.bodies = new ArrayList<>(List.of(sun, target, departure));
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
            if (i % 100 == 0) {
                this.ovitoExporter.export(this);
            }
            if (this.spaceship != null) {
                final double distance = this.spaceship.distanceToCenters(this.target);
                spaceshipDistances.add(distance);
                if (this.velocityExporter != null && i % 100 == 0) {
                    this.velocityExporter.export(this);
                }
                if (distance < this.target.getRadius()) {
                    System.out.println("Travel days: " + (this.t - this.spaceshipLaunchOffset) / 3600 / 24);
                    System.out.println("Relative velocity: " + this.spaceship.getVelocity().distanceTo(this.target.getVelocity()));
                    System.out.println("Spaceship crashed!");
                    break;
                }
            }
            this.t += this.dt;
            i++;
            algorithm.run(this.bodies, this.dt);
        }
        this.distanceExporter.export(this);
    }

    private void launchSpaceship() {
        /**
         * Spaceship initial position and velocity
         */
        final double alpha = Math.atan2(departure.getPosition().getY() - sun.getPosition().getY(), departure.getPosition().getX() - sun.getPosition().getX());
        final double x = this.departure.getPosition().getX() - (this.departure.getRadius() + 1500) * Math.cos(alpha);
        final double y = this.departure.getPosition().getY() - (this.departure.getRadius() + 1500) * Math.sin(alpha);
        final double v0 = 7.12 + this.spaceshipVo;
        final double ve = Math.sqrt(Math.pow(this.departure.getVelocity().getX(), 2) + Math.pow(this.departure.getVelocity().getY(), 2));
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

        final Pair spaceshipForce = this.spaceship.calculateForce(List.of(sun, target, departure));
        this.spaceship.setAcceleration(spaceshipForce.getX() / this.spaceship.getMass(), spaceshipForce.getY() / this.spaceship.getMass());

        this.bodies.add(this.spaceship);
        this.algorithm.addObject(this.spaceship);
        System.out.println("Spaceship launched on " + this.initialDate.atStartOfDay().plusMinutes((int) spaceshipLaunchOffset / 60));
    }
}
