package ar.edu.itba.ss.spaceMemento.interfaces;

import ar.edu.itba.ss.spaceMemento.models.CelestialBody;

import java.util.List;

public interface Algorithm {
    void run(List<CelestialBody> object, double dt);

}
