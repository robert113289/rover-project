package com.rover.service.sitter;

import com.rover.TestData;
import com.rover.model.ReviewCsvRow;
import com.rover.model.Sitter;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class SitterFactoryTest {

    @Test
    void whenCreateSittersWithScores_thenReturnsSittersWithCalculatedScores() {
        List<ReviewCsvRow> reviews = TestData.csvRows();

        SitterFactory sitterFactory = new SitterFactory();
        List<Sitter> sitters = sitterFactory.createSittersWithScores(reviews);

        assertThat(sitters).isNotNull();
        assertThat(sitters).hasSize(2);

        Sitter sitter1 = sitters.stream().filter(s -> s.getEmail().equals("sitterEmail1")).findFirst().orElse(null);
        Sitter sitter2 = sitters.stream().filter(s -> s.getEmail().equals("sitterEmail2")).findFirst().orElse(null);

        assertThat(sitter1).isNotNull();
        assertThat(sitter1.getName()).isEqualTo("sitter1");
        assertThat(sitter1.getReviews()).hasSize(1);
        assertThat(sitter1.getReviews().get(0).sitter()).isEqualTo(sitter1);
        assertThat(sitter1.getProfileScore()).isGreaterThan(0);
        assertThat(sitter1.getRatingsScore()).isGreaterThan(0);
        assertThat(sitter1.getSearchScore()).isGreaterThan(0);

        assertThat(sitter2).isNotNull();
        assertThat(sitter2.getName()).isEqualTo("sitter2");
        assertThat(sitter2.getReviews()).hasSize(1);
        assertThat(sitter2.getReviews().get(0).sitter()).isEqualTo(sitter2);
        assertThat(sitter2.getProfileScore()).isGreaterThan(0);
        assertThat(sitter2.getRatingsScore()).isGreaterThan(0);
        assertThat(sitter2.getSearchScore()).isGreaterThan(0);
    }


}
