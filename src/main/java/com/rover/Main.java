package com.rover;

import com.rover.dao.ReviewCsvDao;
import com.rover.dao.SearchRankingsCsvDao;
import com.rover.dao.SitterDao;
import com.rover.model.ReviewCsvRow;
import com.rover.model.Sitter;
import com.rover.service.csv.CsvService;
import com.rover.service.sitter.SitterFactory;
import com.rover.service.sitter.SitterService;

import java.util.List;


public class Main {

    public static final String INPUT_FILE_NAME = "reviews.csv";
    public static final String OUTPUT_FILE_NAME = "search_rankings.csv";

    public static void main(String[] args) {
        String outputFilePath = getOutputFilePath(args);

        // EXTRACT
        CsvService csvService = new CsvService(
                new ReviewCsvDao(INPUT_FILE_NAME),
                new SearchRankingsCsvDao(outputFilePath)
        );
        List<ReviewCsvRow> reviews = csvService.extractReviews();

        // TRANSFORM
        SitterFactory sitterFactory = new SitterFactory();
        List<Sitter> sitters = sitterFactory.createSittersWithScores(reviews);

        // LOAD
        SitterService sitterService = new SitterService(new SitterDao());
        sitterService.save(sitters);


        // Export sitters to CSV
        List<Sitter> sitterRankings = sitterService.getAllSittersOrderedBySearchScore();
        csvService.exportSearchRankingsToCsv(sitterRankings);


        System.out.println("Search Rankings have been exported to " + outputFilePath);
    }

    private static String getOutputFilePath(String[] args) {
        String outputFilePath = args.length > 0 ? args[0] : System.getenv().getOrDefault("OUTPUT_FILE_NAME",
                OUTPUT_FILE_NAME);
        return outputFilePath;
    }

}