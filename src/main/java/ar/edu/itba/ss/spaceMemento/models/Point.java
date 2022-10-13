package ar.edu.itba.ss.spaceMemento.models;

import lombok.Getter;
import lombok.Setter;

public class Point {
    @Getter @Setter
    private double x;
    @Getter @Setter
    private double y;

    public Point(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public double distanceTo(Point other) {
        return Math.sqrt(Math.pow(this.x - other.x, 2) + Math.pow(this.y - other.y, 2));
    }

    @Override
    public String toString() {
        return "x: " + x + ", y: " + y;
    }
}
