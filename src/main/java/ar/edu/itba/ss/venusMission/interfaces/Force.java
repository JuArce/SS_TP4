package ar.edu.itba.ss.venusMission.interfaces;

import ar.edu.itba.ss.venusMission.models.Point;
import ar.edu.itba.ss.venusMission.utils.Pair;

import java.util.List;

public interface Force {
    Pair apply(Force other);

    Pair apply(List<Force> others);

    Point getPosition();

    Pair getVelocity();

    Pair getAcceleration();

    double getMass();
}
