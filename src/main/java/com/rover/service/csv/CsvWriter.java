package com.rover.service.csv;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class CsvWriter {
    public static void writeToCsv(String fileName, String header, List<String> rows) {
        try (FileWriter writer = new FileWriter(fileName)) {
            writer.append(header).append("\n");
            for (String row : rows) {
                writer.append(row).append("\n");
            }
        } catch (IOException e) {
            throw new RuntimeException("unable to write " + fileName, e);
        }
    }
}


