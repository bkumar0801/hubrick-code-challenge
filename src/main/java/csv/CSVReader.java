package csv;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.io.UncheckedIOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class CSVReader {

    private static final String SEPARATOR = ",";

    private final Reader source;

    public CSVReader(Reader source) {
        this.source = source;
    }

    public List<String> readHeader() {
        try (BufferedReader reader = new BufferedReader(source)) {
            return reader.lines()
                    .findFirst()
                    .map(line -> Arrays.asList(line.split(SEPARATOR)))
                    .get();
        } catch (IOException e) {
            throw new UncheckedIOException("Problem reading a line: ",e);
        }
    }

    public List<List<String>> readRecords() {
        try (BufferedReader reader = new BufferedReader(source)) {
            return reader.lines()
                    .map(line -> Arrays.asList(line.split(SEPARATOR)))
                    .collect(Collectors.toList());
        } catch (IOException e) {
            throw new UncheckedIOException("Problem reading a line: ",e);
        }
    }
}