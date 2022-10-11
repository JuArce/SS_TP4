package ar.edu.itba.ss.venusMission.utils;

import ar.edu.itba.ss.venusMission.models.CelestialBody;
import ar.edu.itba.ss.venusMission.models.Point;

public class CelestialBodyFactory {

    public CelestialBodyFactory() {
        throw new IllegalStateException("Utility class");
    }

    public static CelestialBody getSun() {
        return new CelestialBody.Builder()
                .name("Sun")
                .mass(1988500 * Math.pow(10, 24))
                .radius(695700)
                .position(new Point(0, 0))
                .velocity(new Pair(0, 0))
                .acceleration(new Pair(0, 0))
                .build();
    }

    public static CelestialBody getVenus() {
        return new CelestialBody.Builder()
                .name("Venus")
                .mass(48.685 * Math.pow(10, 23))
                .radius(6051.84)
                .position(new Point(-1.014319519875520 * Math.pow(10, 8), 3.525562675248842 * Math.pow(10, 7)))
                .velocity(new Pair(-1.166353075744313 * 10, -3.324015683726970 * 10))
                .acceleration(new Pair(0, 0))
                .build();
    }

    public static CelestialBody getEarth() {
        return new CelestialBody.Builder()
                .name("Earth")
                .mass(5.97219 * Math.pow(10, 24))
                .radius(6371)
                .position(new Point(1.501409394622880 * Math.pow(10, 8), -9.238096308876731 * Math.pow(10, 5)))
                .velocity(new Pair(-2.949925999285836 * Math.pow(10, -1), 2.968579130065282 * Math.pow(10, 1)))
                .acceleration(new Pair(0, 0))
                .build();
    }

    public static CelestialBody getMars() {
        return new CelestialBody.Builder()
                .name("Mars")
                .mass(6.4171 * Math.pow(10, 23))
                .radius(3389.92)
                .position(new Point(1.772575363952422 * Math.pow(10, 8), 1.201814449256149 * Math.pow(10, 8)))
                .velocity(new Pair(-1.259022434031510 * Math.pow(10, 1), 2.216576613995795 * Math.pow(10, 1)))
                .acceleration(new Pair(0, 0))
                .build();
    }
}
