package web.app.restfulquiz.controller;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import web.app.restfulquiz.domain.common.ResponseStatus;
import web.app.restfulquiz.domain.entity.*;
import web.app.restfulquiz.domain.response.AllQuizzesResponse;
import web.app.restfulquiz.exception.QuizResultNotFoundException;
import web.app.restfulquiz.exception.UserNotFoundException;
import web.app.restfulquiz.service.impl.OptionServiceImpl;
import web.app.restfulquiz.service.impl.QuestionServiceImpl;
import web.app.restfulquiz.service.impl.QuizServiceImpl;
import web.app.restfulquiz.service.impl.UserServiceImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)

public class QuizControllerTest {

    @Mock
    private QuizServiceImpl quizService;

    @Mock
    private UserServiceImpl userService;

    @Mock
    private QuestionServiceImpl questionService;

    @Mock
    private OptionServiceImpl optionService;

    @InjectMocks
    private QuizController quizController;


    @Test
    void getQuizResultTest_success() throws QuizResultNotFoundException {
        Optional<QuizRecord> quizRecord = Optional.ofNullable(QuizRecord.builder()
                .record_id(10)
                .quiz_id(1)
                .user_id(1)
                .taken_date("2023-01-08 18:43:06")
                .score(40)
                .build());
        Optional<Quiz> quiz = Optional.ofNullable(Quiz.builder()
                .quiz_id(1)
                .category_id(1)
                .name("Java")
                .build());
        Optional<User> user = Optional.ofNullable(User.builder()
                .user_id(1)
                .username("alanz")
                .password("123456")
                .firstname("Alan")
                .lastname("Zhang")
                .address("Boston")
                .email("haoyuz0214@gmail.com")
                .phone("8573089078")
                .status(false)
                .is_admin(false)
                .build());
        Optional<Question> question1 = Optional.ofNullable(Question.builder()
                .question_id(1)
                .quiz_id(1)
                .content("Question1")
                .status(true)
                .build());
        Optional<Question> question2 = Optional.ofNullable(Question.builder()
                .question_id(2)
                .quiz_id(1)
                .content("Which is not primitive data type in Java?")
                .status(false)
                .build());
        Optional<Question> question3 = Optional.ofNullable(Question.builder()
                .question_id(3)
                .quiz_id(1)
                .content("Whose answer is yes about static method?")
                .status(false)
                .build());
        Optional<Question> question4 = Optional.ofNullable(Question.builder()
                .question_id(4)
                .quiz_id(1)
                .content("Can final method be overriden?")
                .status(false)
                .build());
        Optional<Question> question5 = Optional.ofNullable(Question.builder()
                .question_id(5)
                .quiz_id(1)
                .content("Is static a non-access modifier?")
                .status(false)
                .build());
        List<Question> questions = new ArrayList<>();
        questions.add(question1.get());
        questions.add(question2.get());
        questions.add(question3.get());
        questions.add(question4.get());
        questions.add(question5.get());

        List<QuestionRecord> questionRecords = new ArrayList<>();
        questionRecords.add(QuestionRecord.builder()
                .qr_id(27)
                .record_id(10)
                .question_id(1)
                .option_id(1)
                .build());

        List<QuestionDone> questionDones = new ArrayList<>();
        for(Question q: questions) {
            QuestionOps questionOps = new QuestionOps(q, null);
            QuestionDone questionDone = new QuestionDone(questionOps, 1);
            questionDone.setOptions(new ArrayList<>());
            questionDones.add(questionDone);
            break;
        }
        QuizResult expected = QuizResult.builder()
                .name("Java")
                .fullname("Alan Zhang")
                .time("2023-01-08 18:43:06")
                .questions(questionDones)
                .score(40)
                .build();

        Mockito.when(quizService.getQuizRecordById(10)).thenReturn(quizRecord);
        Mockito.when(quizService.getQuizById(1)).thenReturn(quiz);
        Mockito.when(questionService.getQuestionRecordsByRecord(10)).thenReturn(questionRecords);
        Mockito.when(userService.getUserById(1)).thenReturn(user);
        Mockito.when(questionService.getQuestionById(1)).thenReturn(question1);
//        Mockito.when(questionService.getQuestionById(2)).thenReturn(question2);
//        Mockito.when(questionService.getQuestionById(3)).thenReturn(question3);
//        Mockito.when(questionService.getQuestionById(4)).thenReturn(question4);
//        Mockito.when(questionService.getQuestionById(5)).thenReturn(question5);
//        Mockito.when(optionService.getOptionsByQuestion(1)).thenReturn(null);
//        Mockito.when(optionService.getOptionsByQuestion(2)).thenReturn(null);
//        Mockito.when(optionService.getOptionsByQuestion(3)).thenReturn(null);
//        Mockito.when(optionService.getOptionsByQuestion(4)).thenReturn(null);
//        Mockito.when(optionService.getOptionsByQuestion(5)).thenReturn(null);

//        System.out.println(expected);
//        System.out.println(quizController.getQuizResult(10));
        assertEquals(expected, quizController.getQuizResult(10));

    }



    @Test
    void getQuizResultTest_failed() {
        Mockito.when(quizService.getQuizRecordById(-1)).thenReturn(Optional.empty());
        assertThrows(QuizResultNotFoundException.class, () -> quizController.getQuizResult(-1));
    }


    @Test
    void getAllQuizzesByUserTest() {
        Optional<User> user = Optional.ofNullable(User.builder()
                .user_id(3)
                .username("user3")
                .password("pass3")
                .firstname("firstname3")
                .lastname("lastname3")
                .address("Boston")
                .email("haoyuz0214@gmail.com")
                .phone("8573089078")
                .status(false)
                .is_admin(false)
                .build());
        List<QuizRecord> quizRecords = new ArrayList<>();
        quizRecords.add(QuizRecord.builder()
                .record_id(3)
                .quiz_id(3)
                .user_id(3)
                .taken_date("2023-01-03 06:37:49")
                .score(60)
                .build());
        Optional<Quiz> quiz = Optional.ofNullable(Quiz.builder()
                .quiz_id(3)
                .category_id(3)
                .name("American Literature")
                .build());
        List<Quiz> quizzes = new ArrayList<>();
        quizzes.add(quiz.get());

//        Mockito.when(userService.getUserByUsername("user3")).thenReturn(user);
//        Mockito.when(quizService.getAllQuizRecordsByUser(3)).thenReturn(quizRecords);
//        Mockito.when(quizService.getQuizById(3)).thenReturn(quiz);

        AllQuizzesResponse expected = AllQuizzesResponse.builder()
                .status(ResponseStatus.builder()
                        .success(true)
                        .message("Get all quizzes for user")
                        .build())
                .quizzes(quizzes)
                .build();

        assertEquals(AllQuizzesResponse.builder()
                .status(ResponseStatus.builder()
                        .success(false)
                        .message("Not logged in")
                        .build())
                .build(), quizController.getAllQuizzesByUser());
    }

}
