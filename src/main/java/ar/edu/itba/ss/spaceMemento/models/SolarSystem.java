package ar.edu.itba.ss.spaceMemento.models;

import ar.edu.itba.ss.spaceMemento.algorithms.GearPredictorCorrector;
import ar.edu.itba.ss.spaceMemento.interfaces.Exporter;
import ar.edu.itba.ss.spaceMemento.interfaces.SpaceshipLauncher;
import ar.edu.itba.ss.spaceMemento.utils.CelestialBodyFactory;
import ar.edu.itba.ss.spaceMemento.utils.Pair;
import lombok.Getter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class SolarSystem {

    @Getter
    private final List<CelestialBody> bodies;
    @Getter
    private final CelestialBody sun;
    @Getter
    private final CelestialBody departure;
    @Getter
    private final CelestialBody target;
    @Getter
    private CelestialBody spaceship;

    @Getter
    private double t = 0;
    private final double dt;
    private final double tf;
    @Getter
    private double travelTime;

    private final SpaceshipLauncher launcher;
    private final LocalDate initialDate;
    private final double spaceshipLaunchOffset;
    @Getter
    private final double spaceshipVo;

    private final GearPredictorCorrector algorithm;

    private final Exporter ovitoExporter;
    private final Exporter distanceExporter;
    private final Exporter velocityExporter;

    @Getter
    private final List<Double> spaceshipDistances;

    public SolarSystem(CelestialBody departure,
                       CelestialBody target,
                       double dt,
                       double tf,
                       LocalDate initialDate,
                       int spaceshipLaunchDay,
                       double spaceshipVo,
                       Exporter ovitoExporter,
                       Exporter distanceExporter,
                       Exporter velocityExporter) {
        this(departure, target, dt, tf, initialDate, spaceshipLaunchDay, 0, spaceshipVo, ovitoExporter, distanceExporter, velocityExporter);
    }

    public SolarSystem(CelestialBody departure,
                       CelestialBody target,
                       double dt,
                       double tf,
                       LocalDate initialDate,
                       int spaceshipLaunchDay,
                       int minutesOffset,
                       double spaceshipVo,
                       Exporter ovitoExporter,
                       Exporter distanceExporter,
                       Exporter velocityExporter) {
        this.sun = CelestialBodyFactory.getSun();
        this.departure = departure;
        this.target = target;

        if (target.getName().equals("Venus")) {
            this.launcher = new VenusLauncher(this);
        } else {
            this.launcher = new MarsLauncher(this);
        }

        /*
          Set all accelerations
         */
        final Pair targetForce = this.target.calculateForce(List.of(sun, departure));
        this.target.setAcceleration(targetForce.getX() / this.target.getMass(), targetForce.getY() / this.target.getMass());
        final Pair departureForce = this.departure.calculateForce(List.of(sun, target));
        this.departure.setAcceleration(departureForce.getX() / this.departure.getMass(), departureForce.getY() / this.departure.getMass());

        this.dt = dt;
        this.tf = tf;

        this.initialDate = initialDate;
        this.spaceshipLaunchOffset = spaceshipLaunchDay * 24 * 3600 + minutesOffset * 60;
        this.spaceshipVo = spaceshipVo;

        this.bodies = new ArrayList<>(List.of(sun, target, departure));
        this.algorithm = new GearPredictorCorrector(this.bodies);
        this.spaceshipDistances = new ArrayList<>();

        this.ovitoExporter = ovitoExporter;
        this.distanceExporter = distanceExporter;
        this.velocityExporter = velocityExporter;
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
                    this.travelTime = (this.t - this.spaceshipLaunchOffset) / 3600 / 24;
                    System.out.println("Travel days: " + this.travelTime);
                    System.out.println("Relative velocity: " + this.spaceship.getVelocity().distanceTo(this.target.getVelocity()));
                    System.out.println("Spaceship arrived to " + this.target.getName() + " on " + this.initialDate.atStartOfDay().plusSeconds((long)this.t) + "!");
                    break;
                }
            }
            this.t += this.dt;
            i++;
            algorithm.run(this.bodies, this.dt);
        }
        if (this.spaceship == null) {
            System.out.println("Spaceship never launched!");
            return;
        }
        this.distanceExporter.export(this);
    }

    private void launchSpaceship() {
        this.spaceship = this.launcher.launch();
        if (this.spaceship == null) {
            throw new IllegalStateException("Spaceship not launched! Aborting mission.");
        }

        this.bodies.add(this.spaceship);
        this.algorithm.addObject(this.spaceship);
        System.out.println("Spaceship launched on " + this.initialDate.atStartOfDay().plusMinutes((int) spaceshipLaunchOffset / 60));
    }
}
