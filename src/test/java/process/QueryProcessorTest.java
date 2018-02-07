package process;

import csv.CSVFactory;
import csv.CSVReader;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class QueryProcessorTest {
    private QueryProcessor queryProcessor;

    @Before
    public void SetUp() {
        queryProcessor = new QueryProcessor("src/test/resources");
    }

    @Test
    public void sortDepartments_shouldOrderDepartmentAlphabetically() {
        List<String> humanResource = new ArrayList<>();
        humanResource.add("Human Resource Test Department");
        List<String> accounting = new ArrayList<>();
        accounting.add("Accounting Test Department");
        List<String> sales = new ArrayList<>();
        sales.add("Sales Test Department");
        List<List<String>> departments = new ArrayList<>();
        departments.add(humanResource);
        departments.add(accounting);
        departments.add(sales);
        assertThat(queryProcessor.sortDepartments(departments))
                .startsWith("Accounting Test Department")
                .contains("Human Resource Test Department")
                .endsWith("Sales Test Department");
    }

    @Test
    public void sortDepartments_shouldReturnEmptyListForEmptyDepartmentList() {
        List<List<String>> departments = new ArrayList<>();
        assertThat(queryProcessor.sortDepartments(departments)).isEmpty();
    }

    @Test
    public void sortAgeOfEmployees_shouldOrderAgeOfEmployees() {
        List<String> employees = Arrays.asList("Bijendra", "Scott", "Andrew");
        List<String> scottAge = new ArrayList<>();
        scottAge.add("Scott");
        scottAge.add("32");
        List<String> bijendraAge = new ArrayList<>();
        bijendraAge.add("Bijendra");
        bijendraAge.add("30");
        List<String> andrewAge = new ArrayList<>();
        andrewAge.add("Andrew");
        andrewAge.add("29");
        List<List<String>> ages = new ArrayList<>();
        ages.add(scottAge);
        ages.add(bijendraAge);
        ages.add(andrewAge);

        assertThat(queryProcessor.sortAgeOfEmployees(ages, employees))
        .startsWith(29)
        .contains(30)
        .endsWith(32);
    }

    @Test
    public void sortAgeOfEmployees_shouldFilterEmployeesWhoseAgeInfoIsNotAvailable() {
        List<String> employees = Arrays.asList("Bijendra", "Scott", "Andrew");
        List<String> scottAge = new ArrayList<>();
        scottAge.add("Scott");
        scottAge.add("32");
        List<String> flintoffAge = new ArrayList<>();
        flintoffAge.add("Flintoff");
        flintoffAge.add("30");
        List<String> andrewAge = new ArrayList<>();
        andrewAge.add("Andrew");
        andrewAge.add("31");
        List<List<String>> ages = new ArrayList<>();
        ages.add(scottAge);
        ages.add(flintoffAge);
        ages.add(andrewAge);

        assertThat(queryProcessor.sortAgeOfEmployees(ages, employees))
        .startsWith(31)
        .endsWith(32);
    }

    @Test
    public void sortAgeOfEmployees_shouldFilterEmployeesWhoseAgeInfoIsEmpty() {
        List<String> employees = Arrays.asList("Bijendra", "Scott", "Andrew");
        List<String> scottAge = new ArrayList<>();
        scottAge.add("Scott");
        scottAge.add("32");
        List<String> bijendraAge = new ArrayList<>();
        bijendraAge.add("Bijendra");
        bijendraAge.add("30");
        List<String> andrewAge = new ArrayList<>();
        andrewAge.add("Andrew");
        andrewAge.add("");
        List<List<String>> ages = new ArrayList<>();
        ages.add(scottAge);
        ages.add(bijendraAge);
        ages.add(andrewAge);

        assertThat(queryProcessor.sortAgeOfEmployees(ages, employees))
                .startsWith(30)
                .endsWith(32);
    }

    @Test
    public void sortAgeOfEmployees_shouldFilterEmployeesWhoseAgeIsNotPresentInEmployeeAgeList() {
        List<String> employees = Arrays.asList("Bijendra", "Scott", "Andrew");
        List<String> scottAge = new ArrayList<>();
        scottAge.add("Scott");
        scottAge.add("35");
        List<String> bijendraAge = new ArrayList<>();
        bijendraAge.add("Bijendra");
        bijendraAge.add("37");
        List<String> andrewAge = new ArrayList<>();
        andrewAge.add("Andrew");
        List<List<String>> ages = new ArrayList<>();
        ages.add(scottAge);
        ages.add(bijendraAge);
        ages.add(andrewAge);

        assertThat(queryProcessor.sortAgeOfEmployees(ages, employees))
                .startsWith(35)
                .endsWith(37);
    }

    @Test
    public void getEmployeeNames_shouldListNameOfEmployeesForAGivenDepartment() {
        List<String> sortedDepartments = Arrays.asList("Accounting", "Human Resource", "Sales");
        List<String> scottInfo = new ArrayList<>();
        scottInfo.add("2");
        scottInfo.add("Scott");
        scottInfo.add("m");
        scottInfo.add("2750");
        List<String> DanielInfo = new ArrayList<>();
        DanielInfo.add("2");
        DanielInfo.add("Daniel");
        DanielInfo.add("m");
        DanielInfo.add("2750");
        List<String> andrewInfo = new ArrayList<>();
        andrewInfo.add("1");
        andrewInfo.add("Andrew");
        andrewInfo.add("m");
        andrewInfo.add("3750");
        List<String> flintoffInfo = new ArrayList<>();
        flintoffInfo.add("3");
        flintoffInfo.add("Flintoff");
        flintoffInfo.add("m");
        flintoffInfo.add("2800");

        List<List<String>> employees = new ArrayList<>();
        employees.add(scottInfo);
        employees.add(DanielInfo);
        employees.add(andrewInfo);
        employees.add(flintoffInfo);
        assertThat(queryProcessor.getEmployeeNames(employees,sortedDepartments, "Human Resource"))
                .startsWith("Scott")
                .endsWith("Daniel");
        assertThat(queryProcessor.getEmployeeNames(employees,sortedDepartments, "Sales"))
                .startsWith("Flintoff");
        assertThat(queryProcessor.getEmployeeNames(employees,sortedDepartments, "Accounting"))
                .startsWith("Andrew");
    }

    @Test
    public void getEmployeeNames_shouldReturnEmptyListIfAGivenDepartmentIsNotPresent() {
        List<String> sortedDepartments = new ArrayList<>();
        List<String> scottInfo = new ArrayList<>();
        scottInfo.add("2");
        scottInfo.add("Scott");
        scottInfo.add("m");
        scottInfo.add("2750");
        List<String> DanielInfo = new ArrayList<>();
        DanielInfo.add("2");
        DanielInfo.add("Daniel");
        DanielInfo.add("m");
        DanielInfo.add("2750");
        List<String> andrewInfo = new ArrayList<>();
        andrewInfo.add("1");
        andrewInfo.add("Andrew");
        andrewInfo.add("m");
        andrewInfo.add("3750");
        List<String> flintoffInfo = new ArrayList<>();
        flintoffInfo.add("3");
        flintoffInfo.add("Flintoff");
        flintoffInfo.add("m");
        flintoffInfo.add("2800");

        List<List<String>> employees = new ArrayList<>();
        employees.add(scottInfo);
        employees.add(DanielInfo);
        employees.add(andrewInfo);
        employees.add(flintoffInfo);
        assertThat(queryProcessor.getEmployeeNames(employees,sortedDepartments, "Human Resource"))
                .isEmpty();

    }

    @Test
    public void getEmployeeNames_shouldFilterNamesIfInvalidRowIsPresentInEmployeeList() {
        List<String> sortedDepartments = Arrays.asList("Accounting", "Human Resource", "Sales");
        List<String> scottInfo = new ArrayList<>();
        scottInfo.add("2");
        scottInfo.add("Scott");
        scottInfo.add("m");
        scottInfo.add("2750");
        List<String> DanielInfo = new ArrayList<>();
        DanielInfo.add("2");
        DanielInfo.add("m");
        DanielInfo.add("2750");
        List<String> andrewInfo = new ArrayList<>();
        andrewInfo.add("1");
        andrewInfo.add("");
        andrewInfo.add("m");
        andrewInfo.add("3750");
        List<String> flintoffInfo = new ArrayList<>();
        flintoffInfo.add("3");
        flintoffInfo.add("Flintoff");
        flintoffInfo.add("m");
        flintoffInfo.add("2800");

        List<List<String>> employees = new ArrayList<>();
        employees.add(scottInfo);
        employees.add(DanielInfo);
        employees.add(andrewInfo);
        employees.add(flintoffInfo);
        assertThat(queryProcessor.getEmployeeNames(employees,sortedDepartments, "Human Resource"))
                .startsWith("Scott")
                .endsWith("Scott");
        assertThat(queryProcessor.getEmployeeNames(employees,sortedDepartments, "Sales"))
                .startsWith("Flintoff");
        assertThat(queryProcessor.getEmployeeNames(employees,sortedDepartments, "Accounting"))
                .isEmpty();
    }

    @Test
    public void sortDepartmentWiseIncome_shouldSortIncomeOfEmployeeForAGivenDepartment() {
        List<String> sortedDepartments = Arrays.asList("Accounting", "Human Resource", "Sales");
        List<String> scottInfo = new ArrayList<>();
        scottInfo.add("2");
        scottInfo.add("Scott");
        scottInfo.add("m");
        scottInfo.add("2758.00");
        List<String> DanielInfo = new ArrayList<>();
        DanielInfo.add("2");
        DanielInfo.add("Daniel");
        DanielInfo.add("m");
        DanielInfo.add("2755.00");
        List<String> andrewInfo = new ArrayList<>();
        andrewInfo.add("2");
        andrewInfo.add("Andrew");
        andrewInfo.add("m");
        andrewInfo.add("3750.00");

        List<List<String>> employees = new ArrayList<>();
        employees.add(scottInfo);
        employees.add(DanielInfo);
        employees.add(andrewInfo);
        assertThat(queryProcessor.sortDepartmentWiseIncome(sortedDepartments, "Human Resource", employees))
                .startsWith(2755.00)
                .contains(2758.00)
                .endsWith(3750.00);
    }

    @Test
    public void sortDepartmentWiseIncome_shouldFilterIncomeOfEmployeeForInvalidRowInEmployeeList() {
        List<String> sortedDepartments = Arrays.asList("Accounting", "Human Resource", "Sales");
        List<String> scottInfo = new ArrayList<>();
        scottInfo.add("2");
        scottInfo.add("Scott");
        scottInfo.add("m");
        scottInfo.add("2758.00");
        List<String> DanielInfo = new ArrayList<>();
        DanielInfo.add("2");
        DanielInfo.add("Daniel");
        DanielInfo.add("m");
        //Income is missing
        List<String> andrewInfo = new ArrayList<>();
        andrewInfo.add("2");
        andrewInfo.add("Andrew");
        andrewInfo.add("m");
        andrewInfo.add("3750.00");

        List<List<String>> employees = new ArrayList<>();
        employees.add(scottInfo);
        employees.add(DanielInfo);
        employees.add(andrewInfo);
        assertThat(queryProcessor.sortDepartmentWiseIncome(sortedDepartments, "Human Resource", employees))
                .startsWith(2758.00)
                .endsWith(3750.00);
    }

    @Test
    public void getEmployeeAgeAndIncome_shouldGetListOfAgeAndIncomeOfAnEmployee() {
        List<String> scottInfo = new ArrayList<>();
        scottInfo.add("2");
        scottInfo.add("Scott");
        scottInfo.add("m");
        scottInfo.add("2758.00");
        List<String> DanielInfo = new ArrayList<>();
        DanielInfo.add("2");
        DanielInfo.add("Daniel");
        DanielInfo.add("m");
        DanielInfo.add("3075.00");
        List<String> andrewInfo = new ArrayList<>();
        andrewInfo.add("2");
        andrewInfo.add("Andrew");
        andrewInfo.add("m");
        andrewInfo.add("3750.00");

        List<List<String>> employees = new ArrayList<>();
        employees.add(scottInfo);
        employees.add(DanielInfo);
        employees.add(andrewInfo);

        List<String> scottAge = new ArrayList<>();
        scottAge.add("Scott");
        scottAge.add("35");
        List<String> danielAge = new ArrayList<>();
        danielAge.add("Daniel");
        danielAge.add("37");
        List<String> andrewAge = new ArrayList<>();
        andrewAge.add("Andrew");
        andrewAge.add("40");
        List<List<String>> ages = new ArrayList<>();
        ages.add(scottAge);
        ages.add(danielAge);
        ages.add(andrewAge);

        assertThat(queryProcessor.getEmployeeAgeAndIncome(employees, ages))
                .contains(Arrays.asList("35", "2758.00"))
                .contains(Arrays.asList("37", "3075.00"))
                .contains(Arrays.asList("40", "3750.00"));

    }

    @Test
    public void average_shouldCalculateAverage() {
        double[] values = {4, 1, 6, 19};
        assertThat(queryProcessor.average(values))
                .isEqualTo(7.5);
    }

    @Test
    public void median_shouldCalculateMedian() {
        double[] values = {1, 4, 6, 19};
        assertThat(queryProcessor.median(values))
                .isEqualTo(5.0);
    }

    @Test
    public void percentile_shouldCalculatePercentile() {
        double[] values = {1, 4, 6, 19};
        assertThat(queryProcessor.percentile(values, 95.0))
                .isEqualTo(16.4);
    }

    @Test
    public void fetchDepartmentWiseMedianIncome_shouldWriteReportToCSV() {
        List<String> testDepartment = Arrays.asList("Accounting");
        List<List<String>> departments = new ArrayList<>();
        departments.add(testDepartment);
        List<String> testEmployee = new ArrayList<>();
        testEmployee.add("1");
        testEmployee.add("Test");
        testEmployee.add("m");
        testEmployee.add("2758.00");
        List<List<String>> employees = new ArrayList<>();
        employees.add(testEmployee);
        queryProcessor.fetchDepartmentWiseMedianIncome(
                "test-income-by-department.csv",
                departments,
                employees);
        CSVReader csvReader = new CSVFactory("src/test/resources").createReader("test-income-by-department.csv");
        assertThat(csvReader.readRecords())
                .contains(Arrays.asList("Accounting","2758.0"));

    }

    @Test
    public void fetchDepartmentWise95PercentileIncome_shouldWriteReportToCSV() {
        List<String> testDepartment = Arrays.asList("Accounting");
        List<List<String>> departments = new ArrayList<>();
        departments.add(testDepartment);
        List<String> testEmployee1 = new ArrayList<>();
        testEmployee1.add("1");
        testEmployee1.add("Test1");
        testEmployee1.add("m");
        testEmployee1.add("2758.00");
        List<String> testEmployee2 = new ArrayList<>();
        testEmployee2.add("1");
        testEmployee2.add("Test2");
        testEmployee2.add("m");
        testEmployee2.add("3000.00");
        List<List<String>> employees = new ArrayList<>();
        employees.add(testEmployee1);
        employees.add(testEmployee2);
        queryProcessor.fetchDepartmentWise95PercentileIncome(
                "test-income-95-by-department.csv",
                departments,
                employees);
        CSVReader csvReader = new CSVFactory("src/test/resources").createReader("test-income-95-by-department.csv");
        assertThat(csvReader.readRecords())
                .contains(Arrays.asList("Accounting","2975.8"));
    }

    @Test
    public void fetchDepartmentWiseMedianAge_shouldWriteReportToCSV() {
        List<String> testDepartment = Arrays.asList("Accounting");
        List<List<String>> departments = new ArrayList<>();
        departments.add(testDepartment);
        List<String> testEmployee1 = new ArrayList<>();
        testEmployee1.add("1");
        testEmployee1.add("Test1");
        testEmployee1.add("m");
        testEmployee1.add("2758.00");
        List<String> testEmployee2 = new ArrayList<>();
        testEmployee2.add("1");
        testEmployee2.add("Test2");
        testEmployee2.add("m");
        testEmployee2.add("3000.00");
        List<List<String>> employees = new ArrayList<>();
        employees.add(testEmployee1);
        employees.add(testEmployee2);

        List<String> test1Age = new ArrayList<>();
        test1Age.add("Test1");
        test1Age.add("35");
        List<String> test2Age = new ArrayList<>();
        test2Age.add("Test2");
        test2Age.add("37");
        List<List<String>> ages = new ArrayList<>();
        ages.add(test1Age);
        ages.add(test2Age);

        queryProcessor.fetchDepartmentWiseMedianAge(
                "test-employee-age-by-department.csv",
                departments,
                employees,
                ages);
        CSVReader csvReader = new CSVFactory("src/test/resources").createReader("test-employee-age-by-department.csv");
        assertThat(csvReader.readRecords())
                .contains(Arrays.asList("Accounting","36.0"));
    }

    @Test
    public void fetchIncomeAverageByAgeRange_shouldWriteReportToCSV() {
        List<String> testEmployee1 = new ArrayList<>();
        testEmployee1.add("1");
        testEmployee1.add("Test1");
        testEmployee1.add("m");
        testEmployee1.add("2758.00");
        List<String> testEmployee2 = new ArrayList<>();
        testEmployee2.add("1");
        testEmployee2.add("Test2");
        testEmployee2.add("m");
        testEmployee2.add("3000.00");
        List<List<String>> employees = new ArrayList<>();
        employees.add(testEmployee1);
        employees.add(testEmployee2);

        List<String> test1Age = new ArrayList<>();
        test1Age.add("Test1");
        test1Age.add("35");
        List<String> test2Age = new ArrayList<>();
        test2Age.add("Test2");
        test2Age.add("47");
        List<List<String>> ages = new ArrayList<>();
        ages.add(test1Age);
        ages.add(test2Age);

        queryProcessor.fetchIncomeAverageByAgeRange(
                "test-income-average-by-age-range.csv",
                employees,
                ages);
        CSVReader csvReader = new CSVFactory("src/test/resources").createReader("test-income-average-by-age-range.csv");
        assertThat(csvReader.readRecords())
                .contains(Arrays.asList("35-45", "2758.0"))
                .contains(Arrays.asList("45-55", "3000.0"));
    }
}
