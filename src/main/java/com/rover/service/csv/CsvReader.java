package com.rover.service.csv;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.stream.Collectors;

public class CsvReader {
    public static List<List<String>> readAll(String fileName) {
        try (BufferedReader reader = getBufferedReader(fileName)) {
            return reader.lines()
                    .skip(1) // Skip header
                    .map(line -> List.of(line.split(",")))
                    .collect(Collectors.toList());
        } catch (Exception e) {
            throw new RuntimeException("Error reading CSV file " + fileName, e);
        }
    }

    static BufferedReader getBufferedReader(String fileName) {
        ClassLoader classLoader = CsvReader.class.getClassLoader();
        InputStream inputStream = classLoader.getResourceAsStream(fileName);
        if (inputStream == null) {
            throw new IllegalArgumentException("File not found! " + fileName);
        }
        return new BufferedReader(new InputStreamReader(inputStream));
    }
}
