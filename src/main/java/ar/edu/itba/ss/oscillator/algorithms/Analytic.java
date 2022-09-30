package ar.edu.itba.ss.oscillator.algorithms;

import ar.edu.itba.ss.oscillator.interfaces.OscillatorAlgorithm;
import ar.edu.itba.ss.oscillator.models.Oscillator;

public class Analytic implements OscillatorAlgorithm {

    @Override
    public void run(Oscillator oscillator) {
        double r = oscillator.getAmplitude()
                * Math.exp(-(oscillator.getGamma() / (2 * oscillator.getMass())) * oscillator.getT())
                * Math.cos(Math.sqrt(oscillator.getK() / oscillator.getMass() - Math.pow(oscillator.getGamma(), 2) / (4 * Math.pow(oscillator.getMass(), 2))) * oscillator.getT());
        oscillator.setPosition(r);
    }
}
