package com.rover.service;

import com.rover.model.Review;
import com.rover.model.Sitter;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class ScoreCalculatorTest {

    @Test
    void whenCalculateProfileScore_thenReturnsCalculationBasedOnName() {
        ScoreCalculator scoreCalculator = new ScoreCalculator();
        double score = scoreCalculator.calculateProfileScore("Leilani R.");
        assertThat(score).isEqualTo(1.15); // 6 distinct letters / 26 * 5 = 1.1538, rounded to 1.15
    }

    @Test
    void whenCalculateRatingsScore_thenReturnsAverageRating() {
        Sitter sitter = new Sitter("sitterName", "sitterEmail");
        List<Review> reviews = Arrays.asList(
                new Review(5, sitter),
                new Review(4, sitter),
                new Review(5, sitter)
        );

        ScoreCalculator scoreCalculator = new ScoreCalculator();
        double score = scoreCalculator.calculateRatingsScore(reviews);
        assertThat(score).isEqualTo(4.67); // Average of 5, 4, 5 is 4.6666, rounded to 4.67
    }


    @ParameterizedTest
    @CsvSource({
            // {profileScore, ratingsScore, numberOfStays, expectedScore}
            "2.5, 5.0, 0, 2.5",   // No stays, so search score is profile score
            "2.5, 5.0, 1, 2.75",
            "2.5, 5.0, 2, 3.0",
            "2.5, 5.0, 3, 3.25",
            "2.5, 5.0, 4, 3.5",
            "2.5, 5.0, 5, 3.75",
            "2.5, 5.0, 6, 4.0",
            "2.5, 5.0, 7, 4.25",
            "2.5, 5.0, 8, 4.5",
            "2.5, 5.0, 9, 4.75",
            "2.5, 5.0, 10, 5.0", // 10 or more stays, so search score is ratings score
            "2.5, 5.0, 11, 5.0", // 10 or more stays, so search score is ratings score
            "2.5, 5.0, 12, 5.0", // 10 or more stays, so search score is ratings score
    })
    void whenCalculateSearchScore_thenWeightRatingsScoreHigherAsSitterGetsMoreReviews(double profileScore, double ratingsScore, int numberOfStays, double expectedScore) {
        ScoreCalculator scoreCalculator = new ScoreCalculator();
        double searchScore = scoreCalculator.calculateSearchScore(profileScore, ratingsScore, numberOfStays);
        assertThat(searchScore).isEqualTo(expectedScore);
    }

}