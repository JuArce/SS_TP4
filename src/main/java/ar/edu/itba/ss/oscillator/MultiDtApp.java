package ar.edu.itba.ss.oscillator;

import ar.edu.itba.ss.oscillator.algorithms.Beeman;
import ar.edu.itba.ss.oscillator.algorithms.Verlet;
import ar.edu.itba.ss.oscillator.interfaces.Exporter;
import ar.edu.itba.ss.oscillator.interfaces.OscillatorAlgorithm;
import ar.edu.itba.ss.oscillator.models.Oscillator;
import ar.edu.itba.ss.oscillator.utils.CsvExporter;

public class MultiDtApp {
    public static void main(String[] args) {
        final double[] dt = {1e-1, 1e-2, 1e-3, 1e-4, 1e-5, 1e-6};
        final double amplitude = 1;
        final double position = 1;
        final double mass = 70;
        final double k = Math.pow(10, 4);
        final double gamma = 100;
        final double tf = 5;
        for (double d : dt) {
            System.out.println("dt = " + d);
            /**
             * Analytic Solution
             */
            Exporter exporter = new CsvExporter("oscillator_analytic_" + d + ".csv");
            exporter.open();
            final Oscillator oscillator = new Oscillator.Builder()
                    .amplitude(amplitude)
                    .position(position)
                    .mass(mass)
                    .k(k)
                    .gamma(gamma)
                    .dt(d)
                    .tf(tf)
                    .exporter(exporter)
                    .build();
            oscillator.run();
            exporter.close();

            /**
             * Verlet Solution
             */
            exporter = new CsvExporter("oscillator_verlet_" + d + ".csv");
            exporter.open();
            final Oscillator oscillator2 = new Oscillator.Builder()
                    .amplitude(amplitude)
                    .position(position)
                    .mass(mass)
                    .k(k)
                    .gamma(gamma)
                    .dt(d)
                    .tf(tf)
                    .exporter(exporter)
                    .build();
            final OscillatorAlgorithm verlet = new Verlet(oscillator2);
            oscillator2.setAlgorithm(verlet);
            oscillator2.run();
            exporter.close();

            /**
             * Beeman Solution
             */
            exporter = new CsvExporter("oscillator_beeman_" + d + ".csv");
            exporter.open();
            final Oscillator oscillator3 = new Oscillator.Builder()
                    .amplitude(amplitude)
                    .position(position)
                    .mass(mass)
                    .k(k)
                    .gamma(gamma)
                    .dt(d)
                    .tf(tf)
                    .exporter(exporter)
                    .build();
            final OscillatorAlgorithm beeman = new Beeman(oscillator3);
            oscillator3.setAlgorithm(beeman);
            oscillator3.run();
            exporter.close();

            /**
             * Gear Predictor Corrector Solution
             */
            exporter = new CsvExporter("oscillator_gear_" + d + ".csv");
            exporter.open();
            final Oscillator oscillator4 = new Oscillator.Builder()
                    .amplitude(amplitude)
                    .position(position)
                    .mass(mass)
                    .k(k)
                    .gamma(gamma)
                    .dt(d)
                    .tf(tf)
                    .exporter(exporter)
                    .build();
            final OscillatorAlgorithm gear = new Beeman(oscillator4);
            oscillator4.setAlgorithm(gear);
            oscillator4.run();
            exporter.close();
        }
    }
}
