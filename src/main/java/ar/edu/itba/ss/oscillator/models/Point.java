package ar.edu.itba.ss.oscillator.models;

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

    public double distanceTo(Point point) {
        final double deltaX = this.x - point.x;
        final double deltaY = this.y - point.y;

        return Math.sqrt(deltaX * deltaX + deltaY * deltaY);
    }

    @Override
    public String toString() {
        return "Point{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }
}
