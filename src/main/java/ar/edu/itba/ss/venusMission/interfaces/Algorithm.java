package ar.edu.itba.ss.venusMission.interfaces;

import ar.edu.itba.ss.venusMission.models.CelestialBody;

import java.util.List;

public interface Algorithm {
    void run(List<CelestialBody> object, double dt);

}
