package main;

import process.QueryProcessor;
import store.CSVStore;

public class ReportGenerator {

    public static void main(String [] args) {
        CSVStore csvStore = new CSVStore();
        csvStore.add("ages", "ages.csv");
        csvStore.add("departments", "departments.csv");
        csvStore.add("employees", "employees.csv");

        QueryProcessor queryProcessor = new QueryProcessor("src/main/java/outdata");
        queryProcessor.fetchDepartmentWiseMedianIncome(
                "income-by-department.csv",
                csvStore.get("departments"),
                csvStore.get("employees"));
        queryProcessor.fetchDepartmentWise95PercentileIncome(
                "income-95-by-department.csv",
                csvStore.get("departments"),
                csvStore.get("employees"));
        queryProcessor.fetchDepartmentWiseMedianAge(
                "employee-age-by-department.csv",
                csvStore.get("departments"),
                csvStore.get("employees"),
                csvStore.get("ages"));
        queryProcessor.fetchIncomeAverageByAgeRange(
                "income-average-by-age-range.csv",
                csvStore.get("employees"),
                csvStore.get("ages"));
        System.out.println("Reports are successfully generated at: src/main/java/outdata");
        System.out.println("Note: all invalid inputs are discarded!!");
    }
}
