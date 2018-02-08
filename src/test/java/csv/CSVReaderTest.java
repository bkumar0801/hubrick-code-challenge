package csv;

import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class CSVReaderTest {
    private String filepath = "src/test/resources";

    @Test
    public void shouldReadsHeader() {
        CSVReader CSVReader = new CSVFactory(filepath).createReader("sample.csv");
        List<String> header = CSVReader.readHeader();
        assertThat(header)
                .contains("username")
                .contains("visited")
                .hasSize(2);
    }

    @Test
    public void shouldReadsRecords() {
        CSVReader CSVReader = new CSVFactory(filepath).createReader("sample.csv");
        List<List<String>> records = CSVReader.readRecords();
        assertThat(records)
                .contains(Arrays.asList("username", "visited"))
                .contains(Arrays.asList("minirobo", "10"))
                .contains(Arrays.asList("testrobo", "40"))
                .hasSize(3);
    }
}
