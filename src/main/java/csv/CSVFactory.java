package csv;

import java.io.IOException;
import java.io.Reader;
import java.io.UncheckedIOException;
import java.io.Writer;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class CSVFactory {
    private String filepath;

    public CSVFactory(String filepath) {
        this.filepath = filepath;
    }

    public CSVReader createReader(String filename) {
        try {
            Path path = Paths.get(filepath, filename);
            Reader reader = Files.newBufferedReader(
                    path, Charset.forName("UTF-8"));
            return new CSVReader(reader);
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    public CSVWriter createWriter(String filename) {
        try {
            Path path = Paths.get(filepath, filename);
            Writer writer = Files.newBufferedWriter(path, Charset.forName("UTF-8"));
            return new CSVWriter(writer);
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }
}