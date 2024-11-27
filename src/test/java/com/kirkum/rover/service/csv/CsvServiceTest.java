package com.kirkum.rover.service.csv;

import com.kirkum.rover.dao.ReviewCsvDao;
import com.kirkum.rover.dao.SearchRankingsCsvDao;
import com.kirkum.rover.model.ReviewCsvRow;
import com.kirkum.rover.model.Sitter;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CsvServiceTest {

    private static final String TEST_FILE = "test_sitters_output.csv";

    @Mock
    private ReviewCsvDao reviewCsvDao;

    private CsvService csvService;

    @BeforeEach
    void setUp() {
        csvService = new CsvService(reviewCsvDao, new SearchRankingsCsvDao(TEST_FILE));
    }

    @AfterEach
    void cleanUp() throws IOException {
        Files.deleteIfExists(Path.of(TEST_FILE));
    }

    @Test
    void whenExtractReviews_thenReturnsAllReviews() {
        List<ReviewCsvRow> mockReviews = Arrays.asList(
                new ReviewCsvRow(5, "sitterImage1", "endDate1", "text1", "ownerImage1", "dogs1",
                        "sitter1", "owner1", "startDate1", "sitterPhoneNumber1", "sitterEmail1",
                        "ownerPhoneNumber1", "ownerEmail1", 10),
                new ReviewCsvRow(4, "sitterImage2", "endDate2", "text2", "ownerImage2", "dogs2",
                        "sitter2", "owner2", "startDate2", "sitterPhoneNumber2", "sitterEmail2",
                        "ownerPhoneNumber2", "ownerEmail2", 20)
        );

        when(reviewCsvDao.findAll()).thenReturn(mockReviews);

        List<ReviewCsvRow> reviews = csvService.extractReviews();

        assertThat(reviews).isEqualTo(mockReviews);
        verify(reviewCsvDao, times(1)).findAll();
    }

    @Test
    void whenExportSittersToCsv_thenWritesSearchRankingCsv() throws IOException {
        String fileName = TEST_FILE;
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

        csvService.exportSearchRankingsToCsv(sitters);
        String expectedHeader = "email,name,profile_score,ratings_score,search_score";
        List<String> expectedRows = Arrays.asList("sitter1@example.com,Sitter One,4.50,4.80,4.90",
                "sitter2@example.com,Sitter Two,4.00,4.30,4.20");
        List<String> lines = Files.readAllLines(Path.of(fileName));
        assertThat(lines).isNotNull();
        assertThat(lines).hasSize(3);
        assertThat(lines.get(0)).isEqualTo(expectedHeader);
        assertThat(lines.get(1)).isEqualTo(expectedRows.get(0));
        assertThat(lines.get(2)).isEqualTo(expectedRows.get(1));
    }
}
