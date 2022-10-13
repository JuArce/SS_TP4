package ar.edu.itba.ss.oscillator;

import ar.edu.itba.ss.oscillator.algorithms.Verlet;
import ar.edu.itba.ss.oscillator.interfaces.Exporter;
import ar.edu.itba.ss.oscillator.interfaces.OscillatorAlgorithm;
import ar.edu.itba.ss.oscillator.models.Oscillator;
import ar.edu.itba.ss.oscillator.utils.CsvExporter;

public class App {
    public static void main(String[] args) {
        Exporter exporter = new CsvExporter("oscillator.csv");
        exporter.open();
        final Oscillator oscillator = new Oscillator.Builder()
                .amplitude(1)
                .position(1)
                .mass(70)
                .k(Math.pow(10, 4))
                .gamma(100)
                .dt(0.01)
                .tf(5)
                .exporter(exporter)
                .build();

        final OscillatorAlgorithm algorithm = new Verlet(oscillator);
        oscillator.setAlgorithm(algorithm);
        oscillator.run();
        exporter.close();
    }
}
