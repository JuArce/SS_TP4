package ar.edu.itba.ss.venusMission.models;

import ar.edu.itba.ss.venusMission.interfaces.SpaceshipLauncher;

public class MarsLauncher implements SpaceshipLauncher {

    private final SolarSystem solarSystem;

    public MarsLauncher(SolarSystem solarSystem) {
        this.solarSystem = solarSystem;
    }

    @Override
    public CelestialBody launch() {
        System.out.println("Launching to Mars!");
        return null;
    }

}
