package com.rover.model;

public record ReviewCsvRow(
        int rating,
        String sitterImage,
        String endDate,
        String text,
        String ownerImage,
        String dogs,
        String sitter,
        String owner,
        String startDate,
        String sitterPhoneNumber,
        String sitterEmail,
        String ownerPhoneNumber,
        String ownerEmail,
        int responseTimeMinutes
) {
}

