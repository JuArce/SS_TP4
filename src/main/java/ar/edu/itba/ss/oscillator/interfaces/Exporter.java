package ar.edu.itba.ss.oscillator.interfaces;

public interface Exporter {
    void open();

    void export(int i, double t, double position, double velocity);

    void close();
}
