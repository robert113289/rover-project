package com.rover.dao;

import com.rover.model.Sitter;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class SearchRankingsCsvDaoTest {
    private static final String TEST_FILE = "test_sitters_output.csv";
    SearchRankingsCsvDao searchRankingsCsvDao = new SearchRankingsCsvDao(TEST_FILE);

    @AfterEach
    void cleanUp() throws IOException {
        Files.deleteIfExists(Path.of(TEST_FILE));
    }

    @Test
    void whenExportSittersToCsv_thenWritesSearchRankingCsv() throws IOException {
        Sitter sitterOne = new Sitter("Sitter One", "sitter1@example.com");
        sitterOne.setProfileScore(4.5);
        sitterOne.setRatingsScore(4.8);
        sitterOne.setSearchScore(4.9);

        Sitter sitterTwo = new Sitter("Sitter Two", "sitter2@example.com");
        sitterTwo.setProfileScore(4.0);
        sitterTwo.setRatingsScore(4.3);
        sitterTwo.setSearchScore(4.2);

        List<Sitter> sitters = Arrays.asList(sitterOne,
                sitterTwo);

        searchRankingsCsvDao.save(sitters);
        String expectedHeader = "email,name,profile_score,ratings_score,search_score";
        List<String> expectedRows = Arrays.asList("sitter1@example.com,Sitter One,4.50,4.80,4.90",
                "sitter2@example.com,Sitter Two,4.00,4.30,4.20");
        List<String> lines = Files.readAllLines(Path.of(TEST_FILE));
        assertThat(lines).isNotNull();
        assertThat(lines).hasSize(3);
        assertThat(lines.get(0)).isEqualTo(expectedHeader);
        assertThat(lines.get(1)).isEqualTo(expectedRows.get(0));
        assertThat(lines.get(2)).isEqualTo(expectedRows.get(1));
    }

}