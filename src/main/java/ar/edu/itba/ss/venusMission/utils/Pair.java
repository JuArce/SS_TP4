package ar.edu.itba.ss.venusMission.utils;

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

    @Override
    public String toString() {
        return "x: " + x + ", y: " + y;
    }
}
