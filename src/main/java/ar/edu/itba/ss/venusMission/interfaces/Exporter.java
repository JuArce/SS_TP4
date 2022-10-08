package ar.edu.itba.ss.venusMission.interfaces;

import ar.edu.itba.ss.venusMission.models.CelestialBody;
import ar.edu.itba.ss.venusMission.models.SolarSystem;

import java.util.List;

public interface Exporter {

    void open();

    void export(SolarSystem solarSystem);

    void close();
}
