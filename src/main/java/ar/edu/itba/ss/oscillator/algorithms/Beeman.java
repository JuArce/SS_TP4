package ar.edu.itba.ss.oscillator.algorithms;

import ar.edu.itba.ss.oscillator.interfaces.OscillatorAlgorithm;
import ar.edu.itba.ss.oscillator.models.Oscillator;
import lombok.Getter;
import lombok.Setter;

public class Beeman implements OscillatorAlgorithm {

    private double prevAcceleration;

    public Beeman(Oscillator oscillator) {
        // calculate initial acceleration
        final double prevPosition = oscillator.getPosition() - oscillator.getVelocity() * oscillator.getDt() + 0.5 * oscillator.getAcceleration() * Math.pow(oscillator.getDt(), 2);
        final double prevVelocity = oscillator.getVelocity() - oscillator.getAcceleration() * oscillator.getDt();
        this.prevAcceleration = (-oscillator.getK() * prevPosition - oscillator.getGamma() * prevVelocity) / oscillator.getMass();
    }

    @Override
    public void run(Oscillator oscillator) {
        // Beeman's algorithm
        final double r = oscillator.getPosition() + oscillator.getVelocity() * oscillator.getDt()
                + (2.0 / 3.0) * oscillator.getAcceleration() * Math.pow(oscillator.getDt(), 2)
                - (1.0 / 6.0) * prevAcceleration * Math.pow(oscillator.getDt(), 2);
        final double vPredicted = oscillator.getVelocity()
                + (3.0 / 2.0) * oscillator.getAcceleration() * oscillator.getDt()
                - (1.0 / 2.0) * prevAcceleration * oscillator.getDt();
        final double a = (- oscillator.getK() * r - oscillator.getGamma() * vPredicted)/ oscillator.getMass();
        final double vCorrected = oscillator.getVelocity() + (1.0 / 3.0) * a * oscillator.getDt()
                + (5.0 / 6.0) * oscillator.getAcceleration() * oscillator.getDt()
                - (1.0 / 6.0) * prevAcceleration * oscillator.getDt();
        prevAcceleration = oscillator.getAcceleration();
        oscillator.setPosition(r);
        oscillator.setVelocity(vCorrected);
        oscillator.setAcceleration(a);
    }

}
