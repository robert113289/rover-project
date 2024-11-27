package com.kirkum.rover.service;

import com.kirkum.rover.model.Review;

import java.util.List;

public class ScoreCalculator {

    /**
     * Calculates and returns the profile score as the number of distinct letters in the sitter's name divided by 26 and
     * then multiplied by 5.
     *
     * @param sitterName the name of the sitter
     * @return the profile score of the sitter
     */
    public double calculateProfileScore(String sitterName) {
        long distinctLetters = sitterName.toLowerCase().chars().filter(Character::isLetter).distinct().count();
        return roundToTwoDecimalPlaces((distinctLetters / 26.0) * 5.0);
    }

    /**
     * Calculates and returns the ratings score as the average rating of the sitter's reviews.
     *
     * @param reviews the reviews of a given sitter
     * @return the average review rating of the reviews
     */
    public double calculateRatingsScore(List<Review> reviews) {
        return roundToTwoDecimalPlaces(reviews.stream().mapToInt(Review::rating).average().orElse(0));
    }

    /**
     * Calculates and returns the search score as a weighted average of the Profile Score and Ratings Score.
     *
     * <p>The Search Score is:</p>
     * <ul>
     *   <li>The Profile Score if there are no stays.</li>
     *   <li>The Ratings Score if there are 10 or more stays.</li>
     *   <li>Otherwise it's the weighted average of the Profile Score and Ratings Score.</li>
     * </ul>
     * The idea is that as a sitter gets more reviews, we will weigh the Ratings Score more heavily.
     *
     * @param profileScore  the profile score of the sitter
     * @param ratingsScore  the ratings score of the sitter
     * @param numberOfStays the number of stays the sitter has had
     * @return the search score of the sitter
     */
    public double calculateSearchScore(double profileScore, double ratingsScore, int numberOfStays) {
        double searchScore;
        if (numberOfStays == 0) {
            searchScore = profileScore;
        } else if (numberOfStays >= 10) {
            searchScore = ratingsScore;
        } else {
            double weight = numberOfStays / 10.0;
            searchScore = (profileScore * (1 - weight)) + (ratingsScore * weight);
        }
        return roundToTwoDecimalPlaces(searchScore);
    }

    private double roundToTwoDecimalPlaces(double value) {
        //https://stackoverflow.com/questions/11701399/round-up-to-2-decimal-places-in-java
        return Math.round(value * 100.0) / 100.0;
    }
}