package com.rover.dao;

import com.rover.TestData;
import com.rover.model.ReviewCsvRow;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ReviewCsvDaoTest {
    private static final String TEST_FILE = "test_reviews.csv";
    private static final String TEST_FILE_INVALID = "test_reviews_invalid.csv";


    @Test
    void whenCsvFileIsValid_thenFindAllReturnsCorrectData() {
        List<ReviewCsvRow> expectedReviews = TestData.csvRows();

        ReviewCsvDao reviewCsvDao = new ReviewCsvDao(TEST_FILE);
        List<ReviewCsvRow> reviews = reviewCsvDao.findAll();

        assertThat(reviews).isEqualTo(expectedReviews);
    }

    @Test
    void whenCsvFileHasInvalidData_thenThrowsIllegalArgumentException() {
        ReviewCsvDao reviewCsvDao = new ReviewCsvDao(TEST_FILE_INVALID);
        Exception exception = assertThrows(IllegalArgumentException.class,
                () -> reviewCsvDao.findAll());

        String expectedMessage = "Invalid CSV row data:";
        String actualMessage = exception.getMessage();

        assertThat(actualMessage).contains(expectedMessage);
    }
}
