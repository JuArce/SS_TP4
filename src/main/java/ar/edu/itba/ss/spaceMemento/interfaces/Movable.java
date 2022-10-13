package ar.edu.itba.ss.spaceMemento.interfaces;

import ar.edu.itba.ss.spaceMemento.models.Point;
import ar.edu.itba.ss.spaceMemento.utils.Pair;

public interface Movable {
    Point getPosition();

    Pair getVelocity();

    Pair getAcceleration();

    void move(double dt);
}
