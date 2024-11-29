package com.rover.service.csv;

import com.rover.TestData;
import com.rover.dao.ReviewCsvDao;
import com.rover.dao.SearchRankingsCsvDao;
import com.rover.model.ReviewCsvRow;
import com.rover.model.Sitter;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CsvServiceTest {

    @Mock
    private ReviewCsvDao reviewCsvDao;

    @Mock
    private SearchRankingsCsvDao searchRankingsCsvDao;

    @InjectMocks
    private CsvService csvService;


    @Test
    void whenExtractReviews_thenReturnsAllReviews() {
        List<ReviewCsvRow> mockReviews = TestData.csvRows();

        when(reviewCsvDao.findAll()).thenReturn(mockReviews);

        List<ReviewCsvRow> reviews = csvService.extractReviews();

        assertThat(reviews).isEqualTo(mockReviews);
        verify(reviewCsvDao, times(1)).findAll();
    }

    @Test
    void whenExportSittersToCsv_thenWritesSearchRankingCsv() {
        csvService.exportSearchRankingsToCsv(List.of(new Sitter("Sitter One", "email")));

        verify(searchRankingsCsvDao, times(1)).save(any());
    }
}
