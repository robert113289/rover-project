package com.kirkum.rover;

import com.kirkum.rover.dao.ReviewCsvDao;
import com.kirkum.rover.dao.SearchRankingsCsvDao;
import com.kirkum.rover.dao.SitterDao;
import com.kirkum.rover.model.ReviewCsvRow;
import com.kirkum.rover.model.Sitter;
import com.kirkum.rover.service.csv.CsvService;
import com.kirkum.rover.service.sitter.SitterFactory;
import com.kirkum.rover.service.sitter.SitterService;

import java.util.List;


public class Main {

    public static final String INPUT_FILE_NAME = "reviews.csv";
    public static String OUTPUT_FILE_NAME = "search_rankings.csv";

    public static void main(String[] args) {
        String outputFilePath = args.length > 0 ? args[0] : System.getenv().getOrDefault("OUTPUT_FILE_NAME",
                OUTPUT_FILE_NAME);

        // EXTRACT
        CsvService csvService = new CsvService(new ReviewCsvDao(INPUT_FILE_NAME),
                new SearchRankingsCsvDao(outputFilePath));
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

}