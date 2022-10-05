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
}
