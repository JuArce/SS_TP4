package ar.edu.itba.ss.venusMission.utils;

import ar.edu.itba.ss.venusMission.interfaces.Exporter;
import ar.edu.itba.ss.venusMission.models.SolarSystem;
import com.opencsv.CSVWriter;

import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class VelocityExporter implements Exporter {

    private static final String baseFilename = "src/main/resources/venusMission/output/velocity/";

    private final String filename;
    private final LocalDateTime date;
    private CSVWriter csvWriterAppender;

    public VelocityExporter(String filename, LocalDateTime date) {
        this.filename = filename;
        this.date = date;
    }

    @Override
    public void open() {
        try {
            CSVWriter writer = new CSVWriter(new FileWriter(baseFilename + filename));
            writer.writeNext(new String[]{"Date", "Velocity"});
            writer.close();

            this.csvWriterAppender = new CSVWriter(new FileWriter(baseFilename + filename, true), CSVWriter.DEFAULT_SEPARATOR, CSVWriter.NO_QUOTE_CHARACTER, CSVWriter.DEFAULT_ESCAPE_CHARACTER, CSVWriter.DEFAULT_LINE_END);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void export(SolarSystem solarSystem) {
        try {
            final LocalDateTime date = this.date.plusSeconds((long) solarSystem.getT());
            final Pair velocity = solarSystem.getSpaceship().getVelocity();
            final double velocityModule = Math.sqrt(Math.pow(velocity.getX(), 2) + Math.pow(velocity.getY(), 2));
            csvWriterAppender.writeNext(new String[]{date.toString(), velocityModule + ""});
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
