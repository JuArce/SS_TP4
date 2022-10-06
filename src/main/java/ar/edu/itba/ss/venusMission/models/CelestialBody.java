package ar.edu.itba.ss.venusMission.models;

import ar.edu.itba.ss.venusMission.interfaces.Force;
import ar.edu.itba.ss.venusMission.interfaces.Movable;
import ar.edu.itba.ss.venusMission.utils.Pair;
import lombok.Getter;

import java.util.List;
import java.util.Objects;

public class CelestialBody implements Movable, Force {

    @Getter
    private final String name;
    @Getter
    private final double mass;
    @Getter
    private final double radius;
    @Getter
    private final Point position;
    @Getter
    private final Pair velocity;
    @Getter
    private final Pair acceleration;

    private static final double G = 6.694 * Math.pow(10, -5); //kilometers

    public CelestialBody(String name, double mass, double radius, Point position, Pair velocity, Pair acceleration) {
        this.name = name;
        this.mass = mass;
        this.radius = radius;
        this.position = position;
        this.velocity = velocity;
        this.acceleration = acceleration;
    }

    public double distanceTo(CelestialBody other) {
        return this.position.distanceTo(other.position) - this.radius - other.radius;
    }


    @Override
    public void move(double dt) {

    }

    @Override
    public Pair apply(Force other) {
        final double distance = this.distanceTo((CelestialBody) other);
        final double gForce = G * this.mass * other.getMass() / Math.pow(distance, 2);

        final double enX = (other.getPosition().getX() - this.position.getX()) / distance;
        final double enY = (other.getPosition().getY() - this.position.getY()) / distance;

        final double fx = gForce * enX;
        final double fy = gForce * enY;

        return new Pair(fx, fy);

    }

    @Override
    public Pair apply(List<Force> others) {
        return others.stream().map(this::apply).reduce(Pair::sum).orElse(new Pair(0, 0));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CelestialBody that = (CelestialBody) o;
        return name.equals(that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    // Builder
    public static class Builder {
        private String name;
        private double mass;
        private double radius;
        private Point position;
        private Pair velocity;
        private Pair acceleration;

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder mass(double mass) {
            this.mass = mass;
            return this;
        }

        public Builder radius(double radius) {
            this.radius = radius;
            return this;
        }

        public Builder position(Point position) {
            this.position = position;
            return this;
        }

        public Builder velocity(Pair velocity) {
            this.velocity = velocity;
            return this;
        }

        public Builder acceleration(Pair acceleration) {
            this.acceleration = acceleration;
            return this;
        }

        public CelestialBody build() {
            return new CelestialBody(name, mass, radius, position, velocity, acceleration);
        }
    }
}
