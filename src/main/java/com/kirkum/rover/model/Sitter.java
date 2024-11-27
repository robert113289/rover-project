package com.kirkum.rover.model;

import java.util.ArrayList;
import java.util.List;

public class Sitter {
    private final String name;
    private final String email;
    private final List<Review> reviews;
    ;
    private double profileScore;
    private double ratingsScore;
    private double searchScore;

    public Sitter(String name, String email) {
        this.name = name;
        this.email = email;
        this.reviews = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public List<Review> getReviews() {
        return reviews;
    }

    public void addReview(Review review) {
        reviews.add(review);
    }

    public double getProfileScore() {
        return profileScore;
    }

    public void setProfileScore(double profileScore) {
        this.profileScore = profileScore;
    }

    public double getRatingsScore() {
        return ratingsScore;
    }

    public void setRatingsScore(double ratingsScore) {
        this.ratingsScore = ratingsScore;
    }

    public double getSearchScore() {
        return searchScore;
    }

    public void setSearchScore(double searchScore) {
        this.searchScore = searchScore;
    }
}
