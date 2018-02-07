package csv;

import org.junit.Test;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static junit.framework.TestCase.assertTrue;

public class CSVWriterTest {
    private String filepath = "src/test/resources";

    @Test
    public void shouldWriteRecords() {
        CSVWriter CSVWriter = new CSVFactory(filepath).createWriter("test-records.csv");
        List<String> fileContents = new ArrayList<>();
        fileContents.add("Human Resource,1");
        fileContents.add("Information Technology,1");
        fileContents.add("Accounts,1");
        CSVWriter.writeRecords(fileContents);
        File file = new File(filepath+"/test-records.csv");
        assertTrue(file.exists());
    }

    @Test
    public void shouldWriteContent() {
        CSVWriter CSVWriter = new CSVFactory(filepath).createWriter("test-content.csv");
        CSVWriter.writeContent("Accounts,1");
        File file = new File(filepath + "/test-content.csv");
        assertTrue(file.exists());
    }
}
