package com.kirkum.rover.dao;

import com.kirkum.rover.model.ReviewCsvRow;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ReviewCsvDaoTest {

    private static final String TEST_FILE = "test_reviews.csv";
    private static final String TEST_FILE_INVALID = "test_reviews_invalid.csv";


    @Test
    void whenCsvFileIsValid_thenFindAllReturnsCorrectData() {
        List<ReviewCsvRow> expectedReviews = Arrays.asList(
                new ReviewCsvRow(5, "sitterImage1", "endDate1", "text1", "ownerImage1", "dogs1",
                        "sitter1", "owner1", "startDate1", "sitterPhoneNumber1", "sitterEmail1",
                        "ownerPhoneNumber1", "ownerEmail1", 10),
                new ReviewCsvRow(4, "sitterImage2", "endDate2", "text2", "ownerImage2", "dogs2",
                        "sitter2", "owner2", "startDate2", "sitterPhoneNumber2", "sitterEmail2",
                        "ownerPhoneNumber2", "ownerEmail2", 20)
        );

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
