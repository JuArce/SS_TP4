package ar.edu.itba.ss.spaceMemento.utils;

import lombok.Getter;
import lombok.Setter;

public class Pair {

    @Getter @Setter
    private double x;
    @Getter @Setter
    private double y;

    public Pair(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public Pair sum(Pair other) {
        return new Pair(this.x + other.x, this.y + other.y);
    }

    public double distanceTo(Pair other) {
        return Math.sqrt(Math.pow(this.x - other.x, 2) + Math.pow(this.y - other.y, 2));
    }

    @Override
    public String toString() {
        return "x: " + x + ", y: " + y;
    }
}
