package com.rover.service.csv;

import com.rover.dao.ReviewCsvDao;
import com.rover.dao.SearchRankingsCsvDao;
import com.rover.model.ReviewCsvRow;
import com.rover.model.Sitter;

import java.util.List;

public class CsvService {
    private final ReviewCsvDao reviewCsvDao;
    private final SearchRankingsCsvDao searchRankingsCsvDao;

    public CsvService(ReviewCsvDao reviewCsvDao, SearchRankingsCsvDao searchRankingsCsvDao) {
        this.searchRankingsCsvDao = searchRankingsCsvDao;
        this.reviewCsvDao = reviewCsvDao;
    }

    public List<ReviewCsvRow> extractReviews() {
        return reviewCsvDao.findAll();
    }

    public void exportSearchRankingsToCsv(List<Sitter> sitters) {
        searchRankingsCsvDao.save(sitters);
    }
}

