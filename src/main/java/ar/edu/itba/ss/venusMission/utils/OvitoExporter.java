package ar.edu.itba.ss.venusMission.utils;

import ar.edu.itba.ss.venusMission.interfaces.Exporter;
import ar.edu.itba.ss.venusMission.models.CelestialBody;
import ar.edu.itba.ss.venusMission.models.SolarSystem;
import com.opencsv.CSVWriter;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class OvitoExporter implements Exporter {

    private static final String baseFilename = "src/main/resources/venusMission/output/ovito/";

    private final String filename;
    private CSVWriter csvWriterAppender;

    public OvitoExporter(String filename) {
        this.filename = filename;
    }

    @Override
    public void open() {
        try {
            CSVWriter writer = new CSVWriter(new FileWriter(baseFilename + filename));
            writer.close();

            this.csvWriterAppender = new CSVWriter(new FileWriter(baseFilename + filename, true), ' ', CSVWriter.NO_QUOTE_CHARACTER, CSVWriter.DEFAULT_ESCAPE_CHARACTER, CSVWriter.DEFAULT_LINE_END);
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
                if (body.getName().equals("Venus") || body.getName().equals("Earth")) {
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
