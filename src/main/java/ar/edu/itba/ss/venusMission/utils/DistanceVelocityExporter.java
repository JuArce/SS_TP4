package ar.edu.itba.ss.venusMission.utils;

import ar.edu.itba.ss.venusMission.interfaces.Exporter;
import ar.edu.itba.ss.venusMission.models.SolarSystem;
import com.opencsv.CSVWriter;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class DistanceVelocityExporter implements Exporter {
    private static final String baseFilename = "src/main/resources/venusMission/output/";

    private final String filename;
    private final List<Double> velocities;
    private int i;
    private CSVWriter csvWriterAppender;

    public DistanceVelocityExporter(String filename, List<Double> velocities) {
        this.filename = filename;
        this.velocities = velocities;
        this.i = 0;
    }

    @Override
    public void open() {
        try {
            CSVWriter writer = new CSVWriter(new FileWriter(baseFilename + filename));
            writer.writeNext(new String[]{"Vo", "Distance"});
            writer.close();

            this.csvWriterAppender = new CSVWriter(new FileWriter(baseFilename + filename, true), CSVWriter.DEFAULT_SEPARATOR, CSVWriter.NO_QUOTE_CHARACTER, CSVWriter.DEFAULT_ESCAPE_CHARACTER, CSVWriter.DEFAULT_LINE_END);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void export(SolarSystem solarSystem) {
        try {
            final double distance = solarSystem.getSpaceshipDistances().stream().min(Double::compareTo).get();
            csvWriterAppender.writeNext(new String[]{this.velocities.get(i) + "", distance + ""});
            i++;
        } catch (Exception e) {
            e.printStackTrace(); //TODO: handle exception
        }
    }

    @Override
    public void close() {
        try {
            csvWriterAppender.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
