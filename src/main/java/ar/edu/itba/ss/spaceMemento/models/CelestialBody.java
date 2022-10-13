package ar.edu.itba.ss.spaceMemento.models;

import ar.edu.itba.ss.spaceMemento.interfaces.Force;
import ar.edu.itba.ss.spaceMemento.interfaces.Movable;
import ar.edu.itba.ss.spaceMemento.utils.Pair;
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

    private static final double G = 6.693 * Math.pow(10, -20); //kilometers

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

    public double distanceToCenters(CelestialBody other) {
        return this.position.distanceTo(other.position);
    }

    public void setPosition(double x, double y) {
        this.position.setX(x);
        this.position.setY(y);
    }

    public void setVelocity(double x, double y) {
        this.velocity.setX(x);
        this.velocity.setY(y);
    }

    public void setAcceleration(double x, double y) {
        this.acceleration.setX(x);
        this.acceleration.setY(y);
    }

    @Override
    public void move(double dt) {

    }

    @Override
    public Pair calculateForce(Force other) {
        final double distance = this.distanceToCenters((CelestialBody) other);
        final double gForce = G * this.mass * other.getMass() / Math.pow(distance, 2);

        final double enX = (other.getPosition().getX() - this.getPosition().getX()) / distance;
        final double enY = (other.getPosition().getY() - this.getPosition().getY()) / distance;

        final double fx = gForce * enX;
        final double fy = gForce * enY;

        return new Pair(fx, fy);
    }

    @Override
    public Pair calculateForce(List<Force> others) {
        return others.stream().map(this::calculateForce).reduce(Pair::sum).orElse(new Pair(0, 0));
    }

    @Override
    public Pair calculateNextForce(Pair nextPosition, Force other, Pair otherNextPosition) {
        final double distance = nextPosition.distanceTo(otherNextPosition);
        final double gForce = G * this.mass * other.getMass() / Math.pow(distance, 2);

        final double enX = (otherNextPosition.getX() - nextPosition.getX()) / distance;
        final double enY = (otherNextPosition.getY() - nextPosition.getY()) / distance;

        final double fx = gForce * enX;
        final double fy = gForce * enY;

        return new Pair(fx, fy);

    }

    @Override
    public Pair calculateNextForce(Pair nextPosition, List<Force> others, List<Pair> otherNextPositions) {
        Pair sum = new Pair(0, 0);
        for (int i = 0; i < others.size(); i++) {
            sum = sum.sum(calculateNextForce(nextPosition, others.get(i), otherNextPositions.get(i)));
        }
        return sum;
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
