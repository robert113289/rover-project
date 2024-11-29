package com.rover.service.sitter;

import com.rover.dao.SitterDao;
import com.rover.model.Sitter;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class SitterServiceTest {

    @Mock
    private SitterDao sitterDao;

    @InjectMocks
    private SitterService sitterService;

    @Test
    void whenGetAllSitters_thenReturnsAllSitters() {
        Sitter sitter1 = new Sitter("Alice", "alice@example.com");
        Sitter sitter2 = new Sitter("Bob", "bob@example.com");
        when(sitterDao.findAllSitters()).thenReturn(Arrays.asList(sitter1, sitter2));

        List<Sitter> sitters = sitterService.getAllSitters();

        assertThat(sitters).containsExactly(sitter1, sitter2);
    }

    @Test
    void whenGetAllSittersOrderedBySearchScore_thenReturnsSittersOrderedBySearchScoreAndThenName() {
        Sitter sitter1 = new Sitter("Alice", "alice@example.com");
        sitter1.setSearchScore(4.5);
        Sitter sitter2 = new Sitter("Bob", "bob@example.com");
        sitter2.setSearchScore(3.5);
        Sitter sitter3 = new Sitter("Charlie", "charlie@example.com");
        sitter3.setSearchScore(4.5);

        when(sitterDao.findAllSitters()).thenReturn(Arrays.asList(sitter1, sitter2, sitter3));

        List<Sitter> sitters = sitterService.getAllSittersOrderedBySearchScore();

        assertThat(sitters).containsExactly(sitter1, sitter3, sitter2);
    }

    @Test
    void whenSave_thenSavesAllSitters() {
        Sitter sitter1 = new Sitter("Alice", "alice@example.com");
        Sitter sitter2 = new Sitter("Bob", "bob@example.com");
        List<Sitter> sitters = Arrays.asList(sitter1, sitter2);

        sitterService.save(sitters);

        verify(sitterDao, times(1)).save(sitter1);
        verify(sitterDao, times(1)).save(sitter2);
    }
}