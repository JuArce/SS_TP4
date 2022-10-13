package ar.edu.itba.ss.spaceMemento.utils;

import ar.edu.itba.ss.spaceMemento.interfaces.Exporter;
import ar.edu.itba.ss.spaceMemento.models.SolarSystem;
import com.opencsv.CSVWriter;

import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class DistanceExporter implements Exporter {

    private static final String baseFilename = "src/main/resources/";

    private final String fullPath;
    private LocalDateTime date;
    private final int minutesOffset;
    private CSVWriter csvWriterAppender;


    public DistanceExporter(String path, String filename, LocalDate date) {
        this(path, filename, date, 24 * 60);
    }

    public DistanceExporter(String path, String filename, LocalDate date, int minutesOffset) {
        this.fullPath = baseFilename + path + filename;
        this.date = date.atStartOfDay();
        this.minutesOffset = minutesOffset;
    }

    @Override
    public void open() {
        try {
            CSVWriter writer = new CSVWriter(new FileWriter(fullPath));
            writer.writeNext(new String[]{"Date", "Distance"});
            writer.close();

            this.csvWriterAppender = new CSVWriter(new FileWriter(fullPath, true), CSVWriter.DEFAULT_SEPARATOR, CSVWriter.NO_QUOTE_CHARACTER, CSVWriter.DEFAULT_ESCAPE_CHARACTER, CSVWriter.DEFAULT_LINE_END);
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
