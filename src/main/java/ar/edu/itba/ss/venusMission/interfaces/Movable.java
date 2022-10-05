package ar.edu.itba.ss.venusMission.interfaces;

import ar.edu.itba.ss.venusMission.models.Point;
import ar.edu.itba.ss.venusMission.utils.Pair;

public interface Movable {
    Point getPosition();

    Pair getVelocity();

    Pair getAcceleration();

    void move(double dt);
}
