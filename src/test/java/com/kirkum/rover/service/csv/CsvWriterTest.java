package com.kirkum.rover.service.csv;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class CsvWriterTest {

    private static final String TEST_FILE = "test_output.csv";

    @AfterEach
    void cleanUp() throws IOException {
        Files.deleteIfExists(Path.of(TEST_FILE));
    }

    @Test
    void whenCsvFileIsValid_thenWriteToCsvWritesCorrectData() throws IOException {
        String header = "header1,header2";
        List<String> rows = List.of("value1,value2", "value3,value4");

        CsvWriter.writeToCsv(TEST_FILE, header, rows);

        List<String> lines = Files.readAllLines(Path.of(TEST_FILE));

        assertThat(lines).isNotNull();
        assertThat(lines).hasSize(3);
        assertThat(lines.get(0)).isEqualTo(header);
        assertThat(lines.get(1)).isEqualTo(rows.get(0));
        assertThat(lines.get(2)).isEqualTo(rows.get(1));
    }

    @Test
    void whenWritingToFile_thenFileContainsExpectedContent() throws IOException {
        String header = "column1,column2";
        List<String> rows = List.of("data1,data2", "data3,data4");

        CsvWriter.writeToCsv(TEST_FILE, header, rows);

        try (BufferedReader reader = new BufferedReader(new FileReader(TEST_FILE))) {
            assertThat(reader.readLine()).isEqualTo(header);
            assertThat(reader.readLine()).isEqualTo(rows.get(0));
            assertThat(reader.readLine()).isEqualTo(rows.get(1));
        }
    }

}
