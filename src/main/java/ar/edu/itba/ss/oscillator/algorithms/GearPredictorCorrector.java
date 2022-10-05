package ar.edu.itba.ss.oscillator.algorithms;

import ar.edu.itba.ss.oscillator.interfaces.OscillatorAlgorithm;
import ar.edu.itba.ss.oscillator.models.Oscillator;

public class GearPredictorCorrector implements OscillatorAlgorithm {

    private double r3;
    private double r4;
    private double r5;

    private double rp0;
    private double rp1;
    private double rp2;
    private double rp3;
    private double rp4;
    private double rp5;

    private final double alpha0 = 3/16.0;
    private final double alpha1 = 251/360.0;
    private final double alpha2 = 1;
    private final double alpha3 = 11/18.0;
    private final double alpha4 = 1/6.0;
    private final double alpha5 = 1/60.0;

    public GearPredictorCorrector(Oscillator oscillator){
        this.r3 = -(oscillator.getK() / oscillator.getMass()) * oscillator.getVelocity();
        this.r4 = Math.pow((oscillator.getK() / oscillator.getMass()), 2) * oscillator.getAcceleration();
        this.r5 = Math.pow((oscillator.getK() / oscillator.getMass()), 2) * this.r3;
        this.rp0 = 0;
        this.rp1 = 0;
        this.rp2 = 0;
        this.rp3 = 0;
        this.rp4 = 0;
        this.rp5 = 0;
    }

    //factorial function
    private int factorial(int n){
        if(n == 0){
            return 1;
        }
        return n * factorial(n-1);
    }

    @Override
    public void run(Oscillator oscillator) {
        //GearPredictorCorrector's algorithm
        rp0 = oscillator.getPosition() + oscillator.getVelocity() * oscillator.getDt()
                + (1.0/2.0) * Math.pow(oscillator.getDt(), 2) * oscillator.getAcceleration()
                + (1.0/6.0) * Math.pow(oscillator.getDt(), 3) * r3
                + (1.0/24.0) * Math.pow(oscillator.getDt(), 4) * r4
                + (1.0/120.0) * Math.pow(oscillator.getDt(), 5) * r5;
        rp1 = oscillator.getVelocity()
                + oscillator.getAcceleration() * oscillator.getDt()
                + (1.0/2.0) * Math.pow(oscillator.getDt(), 2) * r3
                + (1.0/6.0) * Math.pow(oscillator.getDt(), 3) * r4
                + (1.0/24.0) * Math.pow(oscillator.getDt(), 4) * r5;
        rp2 = oscillator.getAcceleration()
                + r3 * oscillator.getDt()
                + (1.0/2.0) * Math.pow(oscillator.getDt(), 2) * r4
                + (1.0/6.0) * Math.pow(oscillator.getDt(), 3) * r5;
        rp3 = r3
                + r4 * oscillator.getDt()
                + (1.0/2.0) * Math.pow(oscillator.getDt(), 2) * r5;
        rp4 = r4 + r5 * oscillator.getDt();
        rp5 = r5;

        double a = (- oscillator.getK() * rp0 - oscillator.getGamma() * rp1)/ oscillator.getMass();
        double dR2 = ( a - rp2 ) * Math.pow(oscillator.getDt(), 2) / 2;

        rp0 = rp0 + dR2 * alpha0 * factorial(0) / Math.pow(oscillator.getDt(), 0);
        rp1 = rp1 + dR2 * alpha1 * factorial(1) / Math.pow(oscillator.getDt(), 1);
        rp2 = rp2 + dR2 * alpha2 * factorial(2) / Math.pow(oscillator.getDt(), 2);
        rp3 = rp3 + dR2 * alpha3 * factorial(3) / Math.pow(oscillator.getDt(), 3);
        rp4 = rp4 + dR2 * alpha4 * factorial(4) / Math.pow(oscillator.getDt(), 4);
        rp5 = rp5 + dR2 * alpha5 * factorial(5) / Math.pow(oscillator.getDt(), 5);

        oscillator.setPosition(rp0);
        oscillator.setVelocity(rp1);
        oscillator.setAcceleration(rp2);
        r3 = rp3;
        r4 = rp4;
        r5 = rp5;

    }
    
}
