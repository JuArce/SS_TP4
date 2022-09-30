package ar.edu.itba.ss.oscillator.algorithms;

import ar.edu.itba.ss.oscillator.interfaces.OscillatorAlgorithm;
import ar.edu.itba.ss.oscillator.models.Oscillator;

public class Verlet implements OscillatorAlgorithm {

    private double prevPosition;

    public Verlet(Oscillator oscillator) {
        this.prevPosition = oscillator.getPosition() + oscillator.getVelocity() * oscillator.getDt();
    }

    @Override
    public void run(Oscillator oscillator) {
        final double f = - oscillator.getK() * oscillator.getPosition() - oscillator.getGamma() * oscillator.getVelocity();
        final double r = 2 * oscillator.getPosition() - prevPosition + Math.pow(oscillator.getDt(), 2) * f / oscillator.getMass();
        final double v = oscillator.getVelocity() + oscillator.getDt() * f / oscillator.getMass();
        prevPosition = oscillator.getPosition();
        oscillator.setPosition(r);
        oscillator.setVelocity(v);
    }

}
