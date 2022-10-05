package ar.edu.itba.ss.venusMission.interfaces;

import ar.edu.itba.ss.venusMission.models.CelestialBody;

import java.util.List;

public interface Exporter {

    void open();

    void export(List<CelestialBody> bodies);

    void close();
}
