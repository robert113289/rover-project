package com.rover.service.sitter;

import com.rover.dao.SitterDao;
import com.rover.model.Sitter;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class SitterService {
    private final SitterDao sitterDao;
    private final Comparator<Sitter> searchScoreThenName = Comparator
            .comparingDouble(Sitter::getSearchScore)
            .reversed()
            .thenComparing(Sitter::getName);

    public SitterService(SitterDao sitterDao) {
        this.sitterDao = sitterDao;
    }

    public List<Sitter> getAllSitters() {
        return sitterDao.findAllSitters();
    }

    public List<Sitter> getAllSittersOrderedBySearchScore() {
        return getAllSitters().stream().sorted(searchScoreThenName).collect(Collectors.toList());

    }


    public void save(List<Sitter> sitters) {
        sitters.forEach(sitterDao::save);
    }
}

