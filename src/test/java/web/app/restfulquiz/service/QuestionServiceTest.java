package web.app.restfulquiz.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import web.app.restfulquiz.dao.QuestionDao;
import web.app.restfulquiz.domain.entity.Question;
import web.app.restfulquiz.domain.entity.QuestionRecord;
import web.app.restfulquiz.domain.request.QuestionUpdateRequest;
import web.app.restfulquiz.service.impl.QuestionServiceImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class QuestionServiceTest {

    @Mock
    private QuestionDao questionDao;

    @InjectMocks
    private QuestionServiceImpl questionService;


    @Test
    void getQuestionRecordsByRecordTest() {
        List<QuestionRecord> questionRecords = new ArrayList<>();
        questionRecords.add(QuestionRecord.builder()
                .qr_id(1)
                .record_id(1)
                .question_id(1)
                .option_id(3)
                .build());
        questionRecords.add(QuestionRecord.builder()
                .qr_id(2)
                .record_id(1)
                .question_id(2)
                .option_id(5)
                .build());
        questionRecords.add(QuestionRecord.builder()
                .qr_id(3)
                .record_id(1)
                .question_id(3)
                .option_id(10)
                .build());
        questionRecords.add(QuestionRecord.builder()
                .qr_id(4)
                .record_id(1)
                .question_id(4)
                .option_id(14)
                .build());
        questionRecords.add(QuestionRecord.builder()
                .qr_id(5)
                .record_id(1)
                .question_id(5)
                .option_id(15)
                .build());


        Mockito.when(questionDao.getQuestionRecordsByRecord(1)).thenReturn(questionRecords);
        assertEquals(questionRecords, questionService.getQuestionRecordsByRecord(1));
    }

    @Test
    void getQuestionByIdTest() {
        Optional<Question> expected = Optional.ofNullable(Question.builder()
                .question_id(1)
                .quiz_id(1)
                .content("Question1")
                .status(true)
                .build());
        Mockito.when(questionDao.getQuestionById(1)).thenReturn(expected);
        assertEquals(expected, questionService.getQuestionById(1));
    }


    @Test
    void updateQuestionTest() {
        QuestionUpdateRequest request = QuestionUpdateRequest.builder()
                .question_id(10)
                .content("Question")
                .options(new ArrayList<>())
                .build();

        Optional<Question> expected = Optional.ofNullable(Question.builder()
                .question_id(10)
                .quiz_id(2)
                .content("Question")
                .status(false)
                .build());

        Mockito.when(questionDao.updateQuestion(request)).thenReturn(expected);
        assertEquals(expected, questionService.updateQuestion(request));
    }


    @Test
    void getAllQuestionsTest() {
        List<Question> expected = new ArrayList<>();
        expected.add(Question.builder()
                .question_id(1)
                .quiz_id(1)
                .content("Question1")
                .status(true)
                .build());

        Mockito.when(questionDao.getAllQuestions()).thenReturn(expected);
        assertEquals(expected, questionService.getAllQuestions());
    }


}
