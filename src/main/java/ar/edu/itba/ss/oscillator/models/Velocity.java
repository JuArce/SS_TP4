package ar.edu.itba.ss.oscillator.models;

import lombok.Getter;
import lombok.Setter;

public class Velocity {

    @Getter @Setter
    private double xSpeed;
    @Getter @Setter
    private double ySpeed;

    public Velocity(double speedX, double speedY) {
        this.xSpeed = speedX;
        this.ySpeed = speedY;
    }

    @Override
    public String toString() {
        return "Velocity{" +
                "xSpeed=" + xSpeed +
                ", ySpeed=" + ySpeed +
                '}';
    }
}
