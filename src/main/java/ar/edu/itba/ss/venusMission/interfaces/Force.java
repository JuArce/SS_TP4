package ar.edu.itba.ss.venusMission.interfaces;

import ar.edu.itba.ss.venusMission.models.Point;
import ar.edu.itba.ss.venusMission.utils.Pair;

import java.util.List;

public interface Force {

    Pair calculateForce(Force other);

    Pair calculateForce(List<Force> others);

    Pair calculateNextForce(Pair nextPosition, Force other, Pair otherNextPosition);

    Pair calculateNextForce(Pair nextPosition, List<Force> others, List<Pair> otherNextPositions);

    Point getPosition();

    Pair getVelocity();

    Pair getAcceleration();

    double getMass();
}
