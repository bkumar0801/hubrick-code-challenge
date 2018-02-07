package csv;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.io.Writer;
import java.util.List;

public class CSVWriter {

    private final Writer source;

    public CSVWriter(Writer source) {
        this.source = source;
    }
    public void writeRecords(List<String> fileContent) {
        try (BufferedWriter writer = new BufferedWriter(source)) {
            fileContent.stream()
                    .forEach( s -> {
                        try {
                            writer.write(s + "\n");
                        } catch (IOException e) {
                            throw new UncheckedIOException("Problem writing a line: ",e);
                        }
                    });

        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    public void writeContent(String fileContent) {
        try (BufferedWriter writer = new BufferedWriter(source)) {
            try {
                writer.write(fileContent + "\n");
            } catch (IOException e) {
                throw new UncheckedIOException("Problem reading a line: ", e);
            }
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }
}
