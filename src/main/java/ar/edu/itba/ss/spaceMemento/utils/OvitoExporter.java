package ar.edu.itba.ss.spaceMemento.utils;

import ar.edu.itba.ss.spaceMemento.interfaces.Exporter;
import ar.edu.itba.ss.spaceMemento.models.CelestialBody;
import ar.edu.itba.ss.spaceMemento.models.SolarSystem;
import com.opencsv.CSVWriter;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class OvitoExporter implements Exporter {

    private static final String baseFilename = "src/main/resources/";

    private final String fullPath;
    private CSVWriter csvWriterAppender;

    public OvitoExporter(String path, String filename) {
        this.fullPath = baseFilename + path + filename;
    }

    @Override
    public void open() {
        try {
            CSVWriter writer = new CSVWriter(new FileWriter(fullPath));
            writer.close();

            this.csvWriterAppender = new CSVWriter(new FileWriter(fullPath, true), ' ', CSVWriter.NO_QUOTE_CHARACTER, CSVWriter.DEFAULT_ESCAPE_CHARACTER, CSVWriter.DEFAULT_LINE_END);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void export(SolarSystem solarSystem) {
        List<CelestialBody> bodies = solarSystem.getBodies();
        try {
            csvWriterAppender.writeNext(new String[]{bodies.size() + ""});
            csvWriterAppender.writeNext(new String[]{});
            for (CelestialBody body : bodies) {
                double radius = body.getRadius();
                if (body.getName().equals("Venus") || body.getName().equals("Earth") || body.getName().equals("Mars")) {
                    radius *= 500;
                } else {
                    radius *= 10;
                }
                csvWriterAppender.writeNext(new String[]{radius + "", body.getPosition().getX() + "", body.getPosition().getY() + "", body.getVelocity().getX() + "", body.getVelocity().getY() + ""});
            }
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
