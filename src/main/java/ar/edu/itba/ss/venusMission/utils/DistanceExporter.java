package ar.edu.itba.ss.venusMission.utils;

import ar.edu.itba.ss.venusMission.interfaces.Exporter;
import ar.edu.itba.ss.venusMission.models.CelestialBody;
import ar.edu.itba.ss.venusMission.models.SolarSystem;
import com.opencsv.CSVWriter;

import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public class DistanceExporter implements Exporter {

    private static final String baseFilename = "src/main/resources/venusMission/output/";

    private final String filename;
    private LocalDateTime date;
    private final int minutesOffset;
    private CSVWriter csvWriterAppender;


    public DistanceExporter(String filename, LocalDate date) {
        this.filename = filename;
        this.date = date.atStartOfDay();
        this.minutesOffset = 24 * 60;
    }

    public DistanceExporter(String filename, LocalDate date, int minutesOffset) {
        this.filename = filename;
        this.date = date.atStartOfDay();
        this.minutesOffset = minutesOffset;
    }

    @Override
    public void open() {
        try {
            CSVWriter writer = new CSVWriter(new FileWriter(baseFilename + filename));
            writer.writeNext(new String[]{"Date", "Distance"});
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
            csvWriterAppender.writeNext(new String[]{date.toString(), distance + ""});
            this.date = this.date.plusMinutes(minutesOffset);
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
