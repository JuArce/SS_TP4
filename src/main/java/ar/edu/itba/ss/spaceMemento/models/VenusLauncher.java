package ar.edu.itba.ss.spaceMemento.models;

import ar.edu.itba.ss.spaceMemento.interfaces.SpaceshipLauncher;
import ar.edu.itba.ss.spaceMemento.utils.Pair;

import java.util.List;

public class VenusLauncher implements SpaceshipLauncher {

    private final SolarSystem solarSystem;

    public VenusLauncher(SolarSystem solarSystem) {
        this.solarSystem = solarSystem;
    }

    @Override
    public CelestialBody launch() {
        System.out.println("Launching to Venus!");
        final CelestialBody departure = solarSystem.getDeparture();
        final CelestialBody target = solarSystem.getTarget();
        final CelestialBody sun = solarSystem.getSun();
        /**
         * Spaceship initial position and velocity
         */
        final double alpha = Math.atan2(departure.getPosition().getY() - sun.getPosition().getY(), departure.getPosition().getX() - sun.getPosition().getX());
        final double x = departure.getPosition().getX() - (departure.getRadius() + 1500) * Math.cos(alpha);
        final double y = departure.getPosition().getY() - (departure.getRadius() + 1500) * Math.sin(alpha);
        final double v0 = 7.12 + solarSystem.getSpaceshipVo();
        final double ve = Math.sqrt(Math.pow(departure.getVelocity().getX(), 2) + Math.pow(departure.getVelocity().getY(), 2));
        final double vx = (ve - v0) * - Math.sin(alpha);
        final double vy = (ve - v0) * Math.cos(alpha);

        System.out.println("vx = " + vx);
        System.out.println("vy = " + vy);

        final CelestialBody spaceship = new CelestialBody.Builder()
                .name("Spaceship")
                .mass(2 * Math.pow(10, 5))
                .radius(0)
                .position(new Point(x, y))
                .velocity(new Pair(vx, vy))
                .acceleration(new Pair(0, 0))
                .build();

        final Pair spaceshipForce = spaceship.calculateForce(List.of(sun, target, departure));
        spaceship.setAcceleration(spaceshipForce.getX() / spaceship.getMass(), spaceshipForce.getY() / spaceship.getMass());

        return spaceship;
    }
}
