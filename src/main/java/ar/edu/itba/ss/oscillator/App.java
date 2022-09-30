package ar.edu.itba.ss.oscillator;

import ar.edu.itba.ss.oscillator.algorithms.Analytic;
import ar.edu.itba.ss.oscillator.interfaces.OscillatorAlgorithm;
import ar.edu.itba.ss.oscillator.models.Oscillator;

public class App {
    public static void main(String[] args) {
        final OscillatorAlgorithm algorithm = new Analytic();
        final Oscillator oscillator = new Oscillator.Builder()
                .amplitude(1)
                .position(1)
                .mass(70)
                .k(Math.pow(10, 4))
                .gamma(100)
                .algorithm(algorithm)
                .dt(0.01)
                .tf(5)
                .build();

        oscillator.run();
    }
}
