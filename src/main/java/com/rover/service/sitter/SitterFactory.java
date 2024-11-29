package com.rover.service.sitter;

import com.rover.model.Review;
import com.rover.model.ReviewCsvRow;
import com.rover.model.Sitter;
import com.rover.service.ScoreCalculator;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SitterFactory {
    private final Map<String, Sitter> sitterMap = new HashMap<>();
    private final ScoreCalculator scoringStrategy = new ScoreCalculator();

    public List<Sitter> createSittersWithScores(List<ReviewCsvRow> reviews) {
        for (ReviewCsvRow row : reviews) {
            String sitterName = row.sitter();
            String sitterEmail = row.sitterEmail();

            Sitter sitter = getOrCreateSitter(sitterEmail, sitterName);

            Review review = new Review(
                    row.rating(),
                    sitter
            );

            sitter.addReview(review);
        }

        for (Sitter sitter : sitterMap.values()) {
            double profileScore = scoringStrategy.calculateProfileScore(sitter.getName());
            double ratingsScore = scoringStrategy.calculateRatingsScore(sitter.getReviews());
            double searchScore = scoringStrategy.calculateSearchScore(profileScore, ratingsScore,
                    sitter.getReviews().size());

            sitter.setProfileScore(profileScore);
            sitter.setRatingsScore(ratingsScore);
            sitter.setSearchScore(searchScore);
        }

        return sitterMap.values().stream().toList();
    }

    private Sitter getOrCreateSitter(String sitterEmail, String sitterName) {
        return sitterMap.computeIfAbsent(sitterEmail, email -> new Sitter(sitterName, email));
    }
}
