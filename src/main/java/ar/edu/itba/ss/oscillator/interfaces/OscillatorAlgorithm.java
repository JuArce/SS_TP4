package ar.edu.itba.ss.oscillator.interfaces;

import ar.edu.itba.ss.oscillator.models.Oscillator;

public interface OscillatorAlgorithm {

    void run(Oscillator oscillator, double dt);
}
