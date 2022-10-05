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
        return new Pair(0, 0);
    }

    @Override
    public Pair apply(List<Force> others) {
        return null;
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
