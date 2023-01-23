package web.app.restfulquiz.service;

import web.app.restfulquiz.domain.entity.Quiz;
import web.app.restfulquiz.domain.entity.QuizQues;
import web.app.restfulquiz.domain.entity.QuizRecord;


import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

public interface QuizService {

    Optional<QuizQues> getQuizByCategory(Integer category_id);

    Optional<Quiz> getQuizById(int quiz_id);

    Integer createQuizRecord(int quiz_id, int user_id, int score);

    Optional<QuizRecord> getQuizRecordById(int record_id);

    List<QuizRecord> getAllQuizRecordsByUser(int user_id);

    List<Quiz> getAllQuizzes();

    List<Quiz> getQuizzesByCategory(Integer category_id);

    // -----Async-----
    CompletableFuture<Optional<Quiz>> getQuizByIdAsync(int quiz_id);

}
