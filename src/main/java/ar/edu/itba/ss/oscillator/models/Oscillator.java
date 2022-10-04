package ar.edu.itba.ss.oscillator.models;

import ar.edu.itba.ss.oscillator.algorithms.Analytic;
import ar.edu.itba.ss.oscillator.interfaces.Exporter;
import ar.edu.itba.ss.oscillator.interfaces.OscillatorAlgorithm;
import lombok.Getter;
import lombok.Setter;

public class Oscillator {

    @Getter
    private final double amplitude;
    @Getter @Setter
    private double position;
    @Getter @Setter
    private double velocity;
    @Getter @Setter
    private double acceleration;
    @Getter
    private final double mass;
    @Getter
    private final double k;
    @Getter
    private final double gamma;
    @Getter @Setter
    private OscillatorAlgorithm algorithm;
    @Getter @Setter
    private double t;
    @Getter
    private final double dt;
    private final double tf;
    private final Exporter exporter;

    public Oscillator(double amplitude, double position, double mass, double k, double gamma, OscillatorAlgorithm algorithm, double dt, double tf, Exporter exporter) {
        this.amplitude = amplitude;
        this.position = position;
        this.velocity = -amplitude * gamma / (2 * mass);
        this.acceleration = (-k * position - gamma * velocity) / mass;
        this.mass = mass;
        this.k = k;
        this.gamma = gamma;
        this.algorithm = algorithm;
        this.dt = dt;
        this.tf = tf;
        this.exporter = exporter;
    }

    public Oscillator(Builder builder) {
        this(builder.amplitude, builder.position, builder.mass, builder.k, builder.gamma, builder.algorithm, builder.dt, builder.tf, builder.exporter);
    }

    public void run() {
        this.t = 0;
        int i = 0;
        while (this.t < this.tf) {
            if (i % 1 == 0) {
                if (exporter != null) {
                    exporter.export(i, this.t, this.position, this.velocity);
                }
            }
            this.t += this.dt;
            algorithm.run(this);
            i++;
        }
    }

    // Implement builder pattern
    public static class Builder {
        private double amplitude;
        private double position;
        private double mass;
        private double k;
        private double gamma;
        private OscillatorAlgorithm algorithm = new Analytic();
        private double dt;
        private double tf;
        private Exporter exporter;

        public Builder amplitude(double amplitude) {
            this.amplitude = amplitude;
            return this;
        }

        public Builder position(double position) {
            this.position = position;
            return this;
        }

        public Builder mass(double mass) {
            this.mass = mass;
            return this;
        }

        public Builder k(double k) {
            this.k = k;
            return this;
        }

        public Builder gamma(double gamma) {
            this.gamma = gamma;
            return this;
        }

        public Builder algorithm(OscillatorAlgorithm algorithm) {
            this.algorithm = algorithm;
            return this;
        }

        public Builder dt(double dt) {
            this.dt = dt;
            return this;
        }

        public Builder tf(double tf) {
            this.tf = tf;
            return this;
        }

        public Builder exporter(Exporter exporter) {
            this.exporter = exporter;
            return this;
        }

        public Oscillator build() {
            return new Oscillator(this);
        }
    }
}
