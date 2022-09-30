package ar.edu.itba.ss.oscillator.models;

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
    @Getter @Setter
    private double mass;
    @Getter @Setter
    private double k;
    @Getter @Setter
    private double gamma;
    @Getter @Setter
    private OscillatorAlgorithm algorithm;
    @Getter @Setter
    private double dt;
    @Getter @Setter
    private double tf;
    @Getter @Setter
    private double dt2;

    public Oscillator(double amplitude, double position, double velocity, double mass, double k, double gamma, OscillatorAlgorithm algorithm, double dt, double tf) {
        this.amplitude = amplitude;
        this.position = position;
        this.velocity = velocity;
        this.acceleration = (-k * position - gamma * velocity) / mass;
        this.mass = mass;
        this.k = k;
        this.gamma = gamma;
        this.algorithm = algorithm;
        this.dt = dt;
        this.tf = tf;
        this.dt2 = 10 * dt;
    }

    public void run() {
        double t = 0;
        while (t < tf) {
            algorithm.run(this, dt);
            t += dt;
            // TODO if (t/dt2 == (int) t/dt2) print
        }
    }

    // Implement builder pattern
    public static class Builder {
        private double amplitude;
        private double position;
        private double velocity;
        private double mass;
        private double k;
        private double gamma;
        private OscillatorAlgorithm algorithm;
        private double dt;
        private double tf;

        public Builder amplitude(double amplitude) {
            this.amplitude = amplitude;
            return this;
        }

        public Builder position(double position) {
            this.position = position;
            return this;
        }

        public Builder velocity(double velocity) {
            this.velocity = velocity;
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

        public Oscillator build() {
            return new Oscillator(amplitude, position, velocity, mass, k, gamma, algorithm, dt, tf);
        }
    }
}
