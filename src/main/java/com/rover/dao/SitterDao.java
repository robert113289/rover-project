package com.rover.dao;

import com.rover.model.Sitter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SitterDao {
    private final Map<String, Sitter> sitterMap;

    public SitterDao() {
        this.sitterMap = new HashMap<>();
    }

    public List<Sitter> findAllSitters() {
        return new ArrayList<>(sitterMap.values());
    }

    public void save(Sitter sitter) {
        this.sitterMap.put(sitter.getEmail(), sitter);
    }

}