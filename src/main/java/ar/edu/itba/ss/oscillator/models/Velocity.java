package ar.edu.itba.ss.oscillator.models;

import lombok.Getter;
import lombok.Setter;

public class Velocity {

    @Getter @Setter
    private double speed;
    @Getter @Setter
    private double angle;

    public Velocity(double speed, double angle) {
        this.speed = speed;
        this.angle = angle;
    }

    @Override
    public String toString() {
        return "Velocity{" +
                "speed=" + speed +
                ", angle=" + angle +
                '}';
    }
}
