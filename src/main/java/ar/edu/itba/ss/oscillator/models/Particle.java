package ar.edu.itba.ss.oscillator.models;

import ar.edu.itba.ss.oscillator.interfaces.Movable;
import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

public class Particle {

    public static int sequence = 0;

    @Getter @Setter
    private int id;
    @Getter @Setter
    private double radius;
    @Getter @Setter
    private double mass;
    @Getter @Setter
    private double position;
    @Getter @Setter
    private Velocity velocity;

    public Particle(double radius, double mass, double position, Velocity velocity) {
        this.id = sequence++;
        this.radius = radius;
        this.mass = mass;
        this.position = position;
        this.velocity = velocity;
    }

    public Particle(int id, double radius, double mass, double position, Velocity velocity) {
        this.id = id;
        this.radius = radius;
        this.mass = mass;
        this.position = position;
        this.velocity = velocity;
    }

    public Particle(double radius, double mass) {
        this(radius, mass,0, new Velocity(0, 0));
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
