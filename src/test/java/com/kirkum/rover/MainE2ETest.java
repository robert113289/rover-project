package com.kirkum.rover;

import com.kirkum.rover.service.csv.CsvReader;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class MainE2ETest {
    private static String outputFilePath = "src/test/resources/" + Main.OUTPUT_FILE_NAME;
    private static List<List<String>> csvData;

    @BeforeAll
    static void setup() {
        Main.main(new String[]{outputFilePath});
        csvData = CsvReader.readAll(Main.OUTPUT_FILE_NAME);
    }

    @Test
    void csvIsNotEmpty() {
        assertThat(csvData).isNotEmpty();
        for (List<String> row : csvData) {
            assertThat(row).hasSize(5); // email, name, profile_score, ratings_score, search_score
            assertThat(email(row)).isNotEmpty();
            assertThat(name(row)).isNotEmpty();
            assertThat(profileScore(row)).isGreaterThan(0);
            assertThat(ratingScore(row)).isGreaterThan(0);
            assertThat(searchScore(row)).isGreaterThan(0);
        }
    }

    @Test
    void rowsAreSortedBySearchScore() {
        // verify all rows are ordered by searchScore and then name
        for (int i = 0; i < csvData.size() - 1; i++) {
            List<String> row1 = csvData.get(i);
            List<String> row2 = csvData.get(i + 1);
            double searchScore1 = searchScore(row1);
            double searchScore2 = searchScore(row2);
            if (searchScore1 == searchScore2) {
                String name1 = name(row1);
                String name2 = name(row2);
                assertThat(name1).isLessThan(name2);
            } else {
                assertThat(searchScore1).isGreaterThanOrEqualTo(searchScore2);
            }
        }
    }

    @Test
    void firstRowIsLeilaniAndScoresAreAsExpected() {
        // Verify a specific row
        List<String> row = csvData.get(0);
        assertThat(row).hasSize(5); // email, name, profile_score, ratings_score, search_score
        assertThat(email(row)).isEqualTo("user7508@t-mobile.com"); // email
        assertThat(name(row)).isEqualTo("Leilani R."); // name
        assertThat(profileScore(row)).isEqualTo(1.15); // profile_score
        assertThat(ratingScore(row)).isEqualTo(3.50); // ratings_score
        assertThat(searchScore(row)).isEqualTo(3.50); // search_score
    }

    private static String email(List<String> row) {
        return row.get(0);
    }

    private static double ratingScore(List<String> row) {
        return Double.parseDouble(row.get(3));
    }

    private static double profileScore(List<String> row) {
        return Double.parseDouble(row.get(2));
    }

    private static double searchScore(List<String> row1) {
        return Double.parseDouble(row1.get(4));
    }

    private static String name(List<String> row) {
        return row.get(1);
    }
}