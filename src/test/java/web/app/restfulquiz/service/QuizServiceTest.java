package web.app.restfulquiz.service;


import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import web.app.restfulquiz.dao.QuizDao;
import web.app.restfulquiz.domain.entity.Quiz;
import web.app.restfulquiz.domain.entity.QuizRecord;
import web.app.restfulquiz.service.impl.QuizServiceImpl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
public class QuizServiceTest {

    @Mock
    private QuizDao quizDao;

    @InjectMocks
    private QuizServiceImpl quizService;


    @Test
    void getQuizByIdTest() {
        Optional<Quiz> expected = Optional.ofNullable(new Quiz(
                1,
                1,
                "Java"));
        Mockito.when(quizDao.getQuizById(1)).thenReturn(expected);
        assertEquals(expected, quizService.getQuizById(1));
    }


    @Test
    void getQuizRecordByIdTest() {
        Optional<QuizRecord> expected = Optional.ofNullable(new QuizRecord(
                10,
                1,
                1,
                "2023-01-08 18:43:06",
                40
        ));
        Mockito.when(quizDao.getQuizRecordById(10)).thenReturn(expected);
        assertEquals(expected, quizService.getQuizRecordById(10));
    }

    @Test
    void getAllQuizRecordsByUserTest() {
        List<QuizRecord> expected = new ArrayList<>();
        expected.add(QuizRecord.builder()
                .record_id(3)
                .quiz_id(3)
                .user_id(3)
                .taken_date("2023-01-03 06:37:49")
                .score(60)
                .build());

        Mockito.when(quizDao.getAllQuizRecordsByUser(3)).thenReturn(expected);
        assertEquals(expected, quizService.getAllQuizRecordsByUser(3));

    }

}
