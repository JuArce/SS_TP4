package ar.edu.itba.ss.oscillator.utils;

import ar.edu.itba.ss.oscillator.interfaces.Exporter;
import com.opencsv.CSVWriter;

import java.io.FileWriter;
import java.io.IOException;

public class CsvExporter implements Exporter {

    private static final String baseFilename = "src/main/resources/output/";

    private final String filename;
    private CSVWriter csvWriterAppender;

    public CsvExporter(String filename) {
        this.filename = filename;
    }

    @Override
    public void open() {
        try {
            CSVWriter writer = new CSVWriter(new FileWriter(baseFilename + filename));
            writer.writeNext(new String[]{"iteration", "t", "position"});
            writer.close();

            this.csvWriterAppender = new CSVWriter(new FileWriter(baseFilename + filename, true), CSVWriter.DEFAULT_SEPARATOR, CSVWriter.NO_QUOTE_CHARACTER, CSVWriter.DEFAULT_ESCAPE_CHARACTER, CSVWriter.DEFAULT_LINE_END);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void export(int i, double t, double position, double velocity) {
        try {
            csvWriterAppender.writeNext(new String[]{i + "", t + "", position + ""});
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
