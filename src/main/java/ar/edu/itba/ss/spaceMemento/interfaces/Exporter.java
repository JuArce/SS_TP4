package ar.edu.itba.ss.spaceMemento.interfaces;

import ar.edu.itba.ss.spaceMemento.models.SolarSystem;

public interface Exporter {

    void open();

    void export(SolarSystem solarSystem);

    void close();
}
