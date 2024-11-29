package com.rover.service.sitter;

import com.rover.model.ReviewCsvRow;
import com.rover.model.Sitter;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class SitterFactoryTest {

    @Test
    void whenCreateSittersWithScores_thenReturnsSittersWithCalculatedScores() {
        List<ReviewCsvRow> reviews = Arrays.asList(
                new ReviewCsvRow(5, "sitterImage1", "endDate1", "text1", "ownerImage1", "dogs1",
                        "sitter1", "owner1", "startDate1", "sitterPhoneNumber1", "sitterEmail1",
                        "ownerPhoneNumber1", "ownerEmail1", 10),
                new ReviewCsvRow(4, "sitterImage2", "endDate2", "text2", "ownerImage2", "dogs2",
                        "sitter2", "owner2", "startDate2", "sitterPhoneNumber2", "sitterEmail2",
                        "ownerPhoneNumber2", "ownerEmail2", 20)
        );

        SitterFactory sitterFactory = new SitterFactory();
        List<Sitter> sitters = sitterFactory.createSittersWithScores(reviews);

        assertThat(sitters).isNotNull();
        assertThat(sitters).hasSize(2);

        Sitter sitter1 = sitters.stream().filter(s -> s.getEmail().equals("sitterEmail1")).findFirst().orElse(null);
        Sitter sitter2 = sitters.stream().filter(s -> s.getEmail().equals("sitterEmail2")).findFirst().orElse(null);

        assertThat(sitter1).isNotNull();
        assertThat(sitter1.getName()).isEqualTo("sitter1");
        assertThat(sitter1.getProfileScore()).isGreaterThan(0);
        assertThat(sitter1.getRatingsScore()).isGreaterThan(0);
        assertThat(sitter1.getSearchScore()).isGreaterThan(0);

        assertThat(sitter2).isNotNull();
        assertThat(sitter2.getName()).isEqualTo("sitter2");
        assertThat(sitter2.getProfileScore()).isGreaterThan(0);
        assertThat(sitter2.getRatingsScore()).isGreaterThan(0);
        assertThat(sitter2.getSearchScore()).isGreaterThan(0);
    }


}
