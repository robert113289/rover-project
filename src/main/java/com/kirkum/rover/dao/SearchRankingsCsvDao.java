package com.kirkum.rover.dao;

import com.kirkum.rover.model.Sitter;
import com.kirkum.rover.service.csv.CsvWriter;

import java.util.List;
import java.util.stream.Collectors;

public class SearchRankingsCsvDao {
    private final String fileName;

    public SearchRankingsCsvDao(String fileName) {
        this.fileName = fileName;
    }

    public void save(List<Sitter> sitters) {
        String header = "email,name,profile_score,ratings_score,search_score";

        List<String> rows = sitters.
                stream()
                .map(sitter -> toCsvString(sitter))
                .collect(Collectors.toList());

        CsvWriter.writeToCsv(fileName, header, rows);
    }

    private static String toCsvString(Sitter sitter) {
        return String.join(",",
                sitter.getEmail(),
                sitter.getName(),
                String.format("%.2f", sitter.getProfileScore()),
                String.format("%.2f", sitter.getRatingsScore()),
                String.format("%.2f", sitter.getSearchScore())
        );
    }
}
