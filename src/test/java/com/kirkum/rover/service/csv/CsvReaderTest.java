package com.kirkum.rover.service.csv;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class CsvReaderTest {

    @Test
    void whenCsvFileIsValid_thenReadAllReturnsCorrectDataAndSkipsHeader() throws Exception {
        String fileName = "test.csv";
        List<List<String>> result = CsvReader.readAll(fileName);
        assertThat(result).isNotNull();
        assertThat(result).hasSize(2);
        assertThat(result.get(0)).containsExactly("value1", "value2");
        assertThat(result.get(1)).containsExactly("value3", "value4");
    }

    @Test
    void whenCsvFileNotFound_thenThrowsRuntimeExceptionWithCause() {
        String fileName = "nonexistent.csv";
        Exception exception = assertThrows(RuntimeException.class, () -> {
            CsvReader.readAll(fileName);
        });

        String expectedMessage = "Error reading CSV file";
        String actualMessage = exception.getMessage();
        assertThat(actualMessage).contains(expectedMessage);
        Throwable cause = exception.getCause();
        assertThat(cause).isInstanceOf(IllegalArgumentException.class);
        assertThat(cause.getMessage()).contains("File not found! " + fileName);
    }

}
