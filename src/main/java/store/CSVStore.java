package store;

import csv.CSVFactory;
import csv.CSVReader;

import java.util.HashMap;
import java.util.List;

public class CSVStore {
    private HashMap<String, List<List<String>> > csvStore = new HashMap<>();

    private String filepath = "src/main/java/data";

    public CSVStore() {}

    public void add(String key, String filename) {
        CSVReader csvReader = new CSVFactory(filepath).createReader(filename);
        csvStore.put(key, csvReader.readRecords());
    }

    public List<List<String>> get(String key) {
        return csvStore.get(key);
    }
}
