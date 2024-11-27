package com.kirkum.rover.dao;

import com.kirkum.rover.model.ReviewCsvRow;
import com.kirkum.rover.service.csv.CsvReader;

import java.util.List;
import java.util.stream.Collectors;


public class ReviewCsvDao {
    private final String fileName;

    public ReviewCsvDao(String fileName) {
        this.fileName = fileName;
    }

    public List<ReviewCsvRow> findAll() {
        List<List<String>> rows = CsvReader.readAll(fileName);
        return rows.stream().map(row -> toPojo(row)).collect(Collectors.toList());
    }

    private ReviewCsvRow toPojo(List<String> row) {
        try {
            return new ReviewCsvRow(Integer.parseInt(row.get(0)), row.get(1), row.get(2), row.get(3),
                    row.get(4), row.get(5), row.get(6), row.get(7), row.get(8), row.get(9),
                    row.get(10), row.get(11), row.get(12), Integer.parseInt(row.get(13)));
        } catch (IndexOutOfBoundsException | NumberFormatException e) {
            throw new IllegalArgumentException("Invalid CSV row data: " + row, e);
        }
    }

}
