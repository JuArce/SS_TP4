package ar.edu.itba.ss.spaceMemento.algorithms;

import ar.edu.itba.ss.spaceMemento.interfaces.Algorithm;
import ar.edu.itba.ss.spaceMemento.interfaces.Force;
import ar.edu.itba.ss.spaceMemento.models.CelestialBody;
import ar.edu.itba.ss.spaceMemento.utils.Pair;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class GearPredictorCorrector implements Algorithm {

    @Getter
    private final List<Pair> r0;
    @Getter
    private final List<Pair> r1;
    @Getter
    private final List<Pair> r2;
    private final List<Pair> r3;
    private final List<Pair> r4;
    private final List<Pair> r5;

    private final List<Pair> rp0;
    private final List<Pair> rp1;
    private final List<Pair> rp2;
    private final List<Pair> rp3;
    private final List<Pair> rp4;
    private final List<Pair> rp5;

    private List<Pair> dR2;

    private final double alpha0 = 3 / 20.;
    private final double alpha1 = 251 / 360.;
    private final double alpha2 = 1;
    private final double alpha3 = 11 / 18.;
    private final double alpha4 = 1 / 6.;
    private final double alpha5 = 1 / 60.;

    public GearPredictorCorrector(List<CelestialBody> objects) {
        this.r0 = new ArrayList<>();
        this.r1 = new ArrayList<>();
        this.r2 = new ArrayList<>();
        this.r3 = new ArrayList<>();
        this.r4 = new ArrayList<>();
        this.r5 = new ArrayList<>();
        this.rp0 = new ArrayList<>();
        this.rp1 = new ArrayList<>();
        this.rp2 = new ArrayList<>();
        this.rp3 = new ArrayList<>();
        this.rp4 = new ArrayList<>();
        this.rp5 = new ArrayList<>();
        this.dR2 = new ArrayList<>();
        objects.forEach(this::addObject);

    }

    public void addObject(CelestialBody object) {
        this.r0.add(new Pair(object.getPosition().getX(), object.getPosition().getY()));
        this.r1.add(new Pair(object.getVelocity().getX(), object.getVelocity().getY()));
        this.r2.add(new Pair(object.getAcceleration().getX(), object.getAcceleration().getY()));
        this.r3.add(new Pair(0, 0));
        this.r4.add(new Pair(0, 0));
        this.r5.add(new Pair(0, 0));
        this.rp0.add(new Pair(0, 0));
        this.rp1.add(new Pair(0, 0));
        this.rp2.add(new Pair(0, 0));
        this.rp3.add(new Pair(0, 0));
        this.rp4.add(new Pair(0, 0));
        this.rp5.add(new Pair(0, 0));
    }

    @Override
    public void run(List<CelestialBody> objects, double dt) {
        // Predict each object
        for (int i = 0; i < objects.size(); i++) {
            final Pair r0 = this.r0.get(i);
            final Pair r1 = this.r1.get(i);
            final Pair r2 = this.r2.get(i);
            final Pair r3 = this.r3.get(i);
            final Pair r4 = this.r4.get(i);
            final Pair r5 = this.r5.get(i);
            final Pair rp0 = this.rp0.get(i);
            final Pair rp1 = this.rp1.get(i);
            final Pair rp2 = this.rp2.get(i);
            final Pair rp3 = this.rp3.get(i);
            final Pair rp4 = this.rp4.get(i);
            final Pair rp5 = this.rp5.get(i);
            predict(dt, rp0, rp1, rp2, rp3, rp4, rp5, r0, r1, r2, r3, r4, r5);
        }

        // Evaluate
        // for each object calculate dRP2
        for (int i = 0; i < objects.size(); i++) {
            final CelestialBody o = objects.get(i);
            final List<Force> others = objects.stream().filter(m -> !m.equals(o)).collect(Collectors.toList());
            final List<Pair> rp0aux = new ArrayList<>();
            Pair nextPosition = null;
            for (int j = 0; j < objects.size(); j++) {
                if (i != j) {
                    rp0aux.add(this.rp0.get(j));
                } else {
                    nextPosition = this.rp0.get(j);
                }
            }

            final Pair totalForce = o.calculateNextForce(nextPosition, others, rp0aux);

            final double ax = totalForce.getX() / o.getMass();
            final double ay = totalForce.getY() / o.getMass();

            this.dR2.add(new Pair(getDR2(dt, ax, this.rp2.get(i).getX()), getDR2(dt, ay, this.rp2.get(i).getY())));
        }

        // Correct each object
        for (int i = 0; i < objects.size(); i++) {
            final Pair rp0 = this.rp0.get(i);
            final Pair rp1 = this.rp1.get(i);
            final Pair rp2 = this.rp2.get(i);
            final Pair rp3 = this.rp3.get(i);
            final Pair rp4 = this.rp4.get(i);
            final Pair rp5 = this.rp5.get(i);
            correct(dt, this.dR2.get(i), rp0, rp1, rp2, rp3, rp4, rp5);
        }

        // Update each object for the algorithm and the model itself
        for (int i = 0; i < objects.size(); i++) {
            final CelestialBody o = objects.get(i);
            final Pair rp0 = this.rp0.get(i);
            final Pair rp1 = this.rp1.get(i);
            final Pair rp2 = this.rp2.get(i);
            final Pair rp3 = this.rp3.get(i);
            final Pair rp4 = this.rp4.get(i);
            final Pair rp5 = this.rp5.get(i);
            final Pair r0 = this.r0.get(i);
            final Pair r1 = this.r1.get(i);
            final Pair r2 = this.r2.get(i);
            final Pair r3 = this.r3.get(i);
            final Pair r4 = this.r4.get(i);
            final Pair r5 = this.r5.get(i);
            update(rp0, rp1, rp2, rp3, rp4, rp5, r0, r1, r2, r3, r4, r5);
            update(o, r0, r1, r2);
        }
        this.dR2 = new ArrayList<>();
    }

    private void predict(double dt, Pair rp0, Pair rp1, Pair rp2, Pair rp3, Pair rp4, Pair rp5, Pair r0, Pair r1, Pair r2, Pair r3, Pair r4, Pair r5) {
        // Predict
        rp0.setX(predictRp0(dt, r0.getX(), r1.getX(), r2.getX(), r3.getX(), r4.getX(), r5.getX()));
        rp0.setY(predictRp0(dt, r0.getY(), r1.getY(), r2.getY(), r3.getY(), r4.getY(), r5.getY()));

        rp1.setX(predictRp1(dt, r1.getX(), r2.getX(), r3.getX(), r4.getX(), r5.getX()));
        rp1.setY(predictRp1(dt, r1.getY(), r2.getY(), r3.getY(), r4.getY(), r5.getY()));

        rp2.setX(predictRp2(dt, r2.getX(), r3.getX(), r4.getX(), r5.getX()));
        rp2.setY(predictRp2(dt, r2.getY(), r3.getY(), r4.getY(), r5.getY()));

        rp3.setX(predictRp3(dt, r3.getX(), r4.getX(), r5.getX()));
        rp3.setY(predictRp3(dt, r3.getY(), r4.getY(), r5.getY()));

        rp4.setX(predictRp4(dt, r4.getX(), r5.getX()));
        rp4.setY(predictRp4(dt, r4.getY(), r5.getY()));

        rp5.setX(predictRp5(dt, r5.getX()));
        rp5.setY(predictRp5(dt, r5.getY()));
    }

    private void correct(double dt, Pair dR2, Pair rp0, Pair rp1, Pair rp2, Pair rp3, Pair rp4, Pair rp5) {
        // Correct
        rp0.setX(correctRp0(dt, dR2.getX(), rp0.getX()));
        rp0.setY(correctRp0(dt, dR2.getY(), rp0.getY()));

        rp1.setX(correctRp1(dt, dR2.getX(), rp1.getX()));
        rp1.setY(correctRp1(dt, dR2.getY(), rp1.getY()));

        rp2.setX(correctRp2(dt, dR2.getX(), rp2.getX()));
        rp2.setY(correctRp2(dt, dR2.getY(), rp2.getY()));

        rp3.setX(correctRp3(dt, dR2.getX(), rp3.getX()));
        rp3.setY(correctRp3(dt, dR2.getY(), rp3.getY()));

        rp4.setX(correctRp4(dt, dR2.getX(), rp4.getX()));
        rp4.setY(correctRp4(dt, dR2.getY(), rp4.getY()));

        rp5.setX(correctRp5(dt, dR2.getX(), rp5.getX()));
        rp5.setY(correctRp5(dt, dR2.getY(), rp5.getY()));
    }

    private void update(Pair rp0, Pair rp1, Pair rp2, Pair rp3, Pair rp4, Pair rp5, Pair r0, Pair r1, Pair r2, Pair r3, Pair r4, Pair r5) {
        // Update
        update(rp0, r0);
        update(rp1, r1);
        update(rp2, r2);
        update(rp3, r3);
        update(rp4, r4);
        update(rp5, r5);
    }

    private void update(Pair rp, Pair r) {
        r.setX(rp.getX());
        r.setY(rp.getY());
    }

    private void update(CelestialBody o, Pair r0, Pair r1, Pair r2) {
        o.setPosition(r0.getX(), r0.getY());
        o.setVelocity(r1.getX(), r1.getY());
        o.setAcceleration(r2.getX(), r2.getY());
    }

    private int factorial(int n) {
        if (n == 0) {
            return 1;
        }
        return n * factorial(n - 1);
    }

    private double predictRp0(double dt, double position, double velocity, double acceleration, double r3, double r4, double r5) {
        return position +
                velocity * dt +
                (1.0 / 2.0) * Math.pow(dt, 2) * acceleration +
                (1.0 / 6.0) * Math.pow(dt, 3) * r3 +
                (1.0 / 24.0) * Math.pow(dt, 4) * r4 +
                (1.0 / 120.0) * Math.pow(dt, 5) * r5;
    }

    private double predictRp1(double dt, double velocity, double acceleration, double r3, double r4, double r5) {
        return velocity +
                dt * acceleration +
                (1.0 / 2.0) * Math.pow(dt, 2) * r3 +
                (1.0 / 6.0) * Math.pow(dt, 3) * r4 +
                (1.0 / 24.0) * Math.pow(dt, 4) * r5;
    }

    private double predictRp2(double dt, double acceleration, double r3, double r4, double r5) {
        return acceleration +
                dt * r3 +
                (1.0 / 2.0) * Math.pow(dt, 2) * r4 +
                (1.0 / 6.0) * Math.pow(dt, 3) * r5;
    }

    private double predictRp3(double dt, double r3, double r4, double r5) {
        return r3 +
                dt * r4 +
                (1.0 / 2.0) * Math.pow(dt, 2) * r5;
    }

    private double predictRp4(double dt, double r4, double r5) {
        return r4 +
                dt * r5;
    }

    private double predictRp5(double dt, double r5) {
        return r5;
    }

    private double getDR2(double dt, double r2, double rp2) {
        return (1.0 / 2.0) * Math.pow(dt, 2) * (r2 - rp2);
    }

    private double correctRp0(double dt, double dR2, double rp0) {
        return rp0 + dR2 * alpha0 * factorial(0) / Math.pow(dt, 0);
    }

    private double correctRp1(double dt, double dR2, double rp1) {
        return rp1 + dR2 * alpha1 * factorial(1) / Math.pow(dt, 1);
    }

    private double correctRp2(double dt, double dR2, double rp2) {
        return rp2 + dR2 * alpha2 * factorial(2) / Math.pow(dt, 2);
    }

    private double correctRp3(double dt, double dR2, double rp3) {
        return rp3 + dR2 * alpha3 * factorial(3) / Math.pow(dt, 3);
    }

    private double correctRp4(double dt, double dR2, double rp4) {
        return rp4 + dR2 * alpha4 * factorial(4) / Math.pow(dt, 4);
    }

    private double correctRp5(double dt, double dR2, double rp5) {
        return rp5 + dR2 * alpha5 * factorial(5) / Math.pow(dt, 5);
    }


}
