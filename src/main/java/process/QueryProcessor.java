package process;

import csv.CSVFactory;
import csv.CSVWriter;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class QueryProcessor {
    private String filepath = "src/main/java/outdata";
    private Integer minAge;
    private Integer maxAge;

    public QueryProcessor(String filepath) {
        this.filepath = filepath;
    }

    public void fetchDepartmentWiseMedianIncome(String writeToFile, List<List<String>> departments,
                                                List<List<String>> employees) {
        CSVWriter csvWriter = new CSVFactory(filepath).createWriter(writeToFile);
        List<String> sortedDepartments  = sortDepartments(departments);
        List<String> departmentWiseMedianIncome = sortedDepartments.stream()
                .map(department -> {
                    double[] sortedIncome = sortDepartmentWiseIncome(sortedDepartments, department, employees);
                    String medianIncome = sortedIncome.length != 0 ?
                            Double.toString(median(sortedIncome)) : "No information available";
                    return department + "," + medianIncome;
                })
                .collect(Collectors.toList());
        csvWriter.writeRecords(departmentWiseMedianIncome);
    }

    public void fetchDepartmentWise95PercentileIncome(String writeToFile, List<List<String>> departments,
                                                      List<List<String>> employees){
        CSVWriter csvWriter = new CSVFactory(filepath).createWriter(writeToFile);
        List<String> sortedDepartments  = sortDepartments(departments);
        List<String> deptWise95PercentileIncome = sortedDepartments.stream()
                .map(department -> {
                    double[] sortedIncome = sortDepartmentWiseIncome(sortedDepartments, department, employees);
                    String percentileIncome = sortedIncome.length != 0 ?
                            Double.toString(percentile(sortedIncome, 95.0)) : "No information available";
                    return department + "," + percentileIncome;
                })
                .collect(Collectors.toList());
        csvWriter.writeRecords(deptWise95PercentileIncome);
    }

    public void fetchDepartmentWiseMedianAge(String writeToFile, List<List<String>> departments,
                                             List<List<String>> employees, List<List<String>> ages) {
        CSVWriter csvWriter = new CSVFactory(filepath).createWriter(writeToFile);
        List<String> sortedDepartments  = sortDepartments(departments);
        List<String> employeeAgeByDepartment = sortedDepartments.stream()
                .map(department -> {
                    List<String> employeeNames = getEmployeeNames(employees, sortedDepartments, department);
                    int[] sortedAgeOfEmployees = sortAgeOfEmployees(ages, employeeNames);
                    String medianAge = sortedAgeOfEmployees.length != 0 ?
                            Double.toString(median(sortedAgeOfEmployees)) : "No information available";
                    return department + "," + medianAge;
                })
                .collect(Collectors.toList());
        csvWriter.writeRecords(employeeAgeByDepartment);
    }

    public void fetchIncomeAverageByAgeRange(String writeToFile, List<List<String>> employees, List<List<String>> ages) {
        CSVWriter csvWriter = new CSVFactory(filepath).createWriter(writeToFile);
        minAge = getMinAge(ages);
        maxAge = getMaxAge(ages);
        double delta = Math.ceil((maxAge - minAge)/10.0);
        List<Integer> rangeList = IntStream.range(0, (int)delta).boxed().collect(Collectors.toList());
        List<String> incomeAverageByAgeRange = rangeList.stream()
                .map( x -> {
                    String range = Integer.toString(minAge) + "-" + Integer.toString(minAge + 10);
                    List<List<String>> employeeAgeAndIncome = getEmployeeAgeAndIncome(employees, ages);
                    double[] incomeByAgeRange = employeeAgeAndIncome.stream()
                            .filter(ai -> Integer.parseInt(ai.get(0)) >= minAge &&
                                    Integer.parseInt(ai.get(0)) < minAge + 10)
                            .mapToDouble(y -> Double.parseDouble(y.get(1)))
                            .toArray();
                    minAge += 10;
                    String averageAge = incomeByAgeRange.length != 0 ?
                            Double.toString(average(incomeByAgeRange)) : "No information available";
                    return range + "," + averageAge;
                })
                .collect(Collectors.toList());
        resetMinAge(ages);
        csvWriter.writeRecords(incomeAverageByAgeRange);
    }

    public void resetMinAge(List<List<String>> ages) {
        minAge = getMinAge(ages);
    }

    public int[] sortAgeOfEmployees(List<List<String>> ages, List<String> employeeByDepartment) {
        return employeeByDepartment.stream()
                .map(e -> ages.stream()
                        .filter(a -> a.size() == 2 &&
                                a.contains(e) &&
                                !a.get(1).isEmpty())
                        .map(fa -> fa.get(1))
                        .collect(Collectors.toList()))
                .flatMap(List::stream)
                .mapToInt(Integer::parseInt)
                .sorted()
                .toArray();
    }

    public List<String> getEmployeeNames(List<List<String>> employees, List<String> sortedDepartments, String department) {
        return employees.stream()
                .filter(e -> e.size() == 4 &&
                        !e.get(1).isEmpty() &&
                        e.contains(Integer.toString(sortedDepartments.indexOf(department) + 1)))
                .map(fe->fe.get(1))
                .collect(Collectors.toList());
    }

    public double[] sortDepartmentWiseIncome(List<String> departments, String department, List<List<String>> employees) {
        return employees.stream()
                .filter(e -> e.size() == 4 &&
                        !e.get(3).isEmpty() &&
                        e.contains(Integer.toString(departments.indexOf(department) + 1)))
                .mapToDouble(fe->Double.parseDouble(fe.get(3)))
                .sorted()
                .toArray();
    }

    public List<String> sortDepartments(List<List<String>> departments) {
        return departments.stream()
                .flatMap(List::stream)
                .sorted()
                .collect(Collectors.toList());
    }

    public List<List<String>> getEmployeeAgeAndIncome(List<List<String>> employees, List<List<String>> ages) {
        return employees.stream()
                .map(e -> ages.stream()
                        .filter(a -> e.size() == 4 &&
                                a.size() == 2 &&
                                a.contains(e.get(1)) &&
                                !a.get(1).isEmpty() &&
                                !e.get(3).isEmpty())
                        .map(fa -> fa.get(1)+ "," + e.get(3))
                        .map(x -> Arrays.asList(x.split(",")))
                        .collect(Collectors.toList()))
                .flatMap(List::stream)
                .collect(Collectors.toList());
    }

    public double average(double[] incomeByAgeRange) {
        if( incomeByAgeRange.length == 0 )
            throw new IllegalArgumentException("Data array is empty");
        return Arrays.stream(incomeByAgeRange).sum()/incomeByAgeRange.length;
    }

    public Double median(double[] sortedValues) {
        if( sortedValues.length == 0 )
            throw new IllegalArgumentException("Data array is empty");
        double medianIndex = (sortedValues.length + 1);
        if( medianIndex % 2 != 0) {
            double ceilIndex =  Math.ceil((float) medianIndex / 2);
            double floorIndex =  Math.floor((float) medianIndex / 2);
            return (sortedValues[(int)ceilIndex - 1] + sortedValues[(int)floorIndex - 1])/2;
        } else {
            return sortedValues[(sortedValues.length + 1)/2 - 1];
        }
    }

    public Double median(int[] sortedValues) {
        if( sortedValues.length == 0 )
            throw new IllegalArgumentException("Data array is empty");
        double medianIndex = (sortedValues.length + 1);
        if( medianIndex % 2 != 0) {
            double ceilIndex =  Math.ceil((float) medianIndex / 2);
            double floorIndex =  Math.floor((float) medianIndex / 2);
            return (sortedValues[(int)ceilIndex - 1] + sortedValues[(int)floorIndex - 1])/2.0;
        } else {
            return (double)sortedValues[(sortedValues.length + 1)/2 - 1];
        }
    }

    public Double percentile(double[] values, Double percentileVal) {
        int N = values.length;
        if( N == 0 )
            throw new IllegalArgumentException("Data array is empty");
        else if(N == 1)
            return values[0];
        else {
            double rank = (percentileVal / 100.0) * (N);
            double delta = rank - Math.floor(rank);
            double ceilRank = Math.ceil(rank);
            double floorRank = Math.floor(rank);
            return delta * (values[(int) ceilRank - 1] - values[(int) floorRank - 1])
                    + values[(int) floorRank - 1];
        }
    }

    public Integer getMinAge(List<List<String>> ages) {
        return ages.stream()
                .filter(x -> x.size() == 2 && !x.isEmpty())
                .mapToInt(y -> Integer.parseInt(y.get(1)))
                .sorted()
                .toArray()[0];
    }

    public Integer getMaxAge(List<List<String>> ages) {
        int[] sortedAge = ages.stream()
                .filter(x -> x.size() == 2 && !x.isEmpty())
                .mapToInt(y -> Integer.parseInt(y.get(1)))
                .sorted()
                .toArray();
        return sortedAge[sortedAge.length - 1];
    }
}
