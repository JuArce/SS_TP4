package ar.edu.itba.ss.oscillator.models;

import ar.edu.itba.ss.oscillator.interfaces.Movable;
import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

public class Particle implements Movable {

    public static int sequence = 0;

    @Getter @Setter
    private int id;
    @Getter @Setter
    private double radius;
    @Getter @Setter
    private double mass;
    @Getter @Setter
    private Point position;
    @Getter @Setter
    private Velocity velocity;

    public Particle(double radius, double mass, Point position, Velocity velocity) {
        this.id = sequence++;
        this.radius = radius;
        this.mass = mass;
        this.position = position;
        this.velocity = velocity;
    }

    public Particle(int id, double radius, double mass, Point position, Velocity velocity) {
        this.id = id;
        this.radius = radius;
        this.mass = mass;
        this.position = position;
        this.velocity = velocity;
    }

    public Particle(double radius, double mass) {
        this(radius, mass, new Point(0, 0), new Velocity(0, 0));
    }

    public double distanceTo(Particle particle) {
        return this.position.distanceTo(particle.position) - this.radius - particle.radius;
    }

    @Override
    public void move(double dt) {
        this.position.setX(this.position.getX() + this.velocity.getXSpeed() * dt);
        this.position.setY(this.position.getY() + this.velocity.getYSpeed() * dt);
    }

    @Override
    public String toString() {
        return "Particle{" +
                "id=" + id +
                ", radius=" + radius +
                ", mass=" + mass +
                ", position=" + position +
                ", velocity=" + velocity +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Particle particle = (Particle) o;
        return id == particle.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
