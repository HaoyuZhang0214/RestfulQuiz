package web.app.restfulquiz.service;

import web.app.restfulquiz.domain.entity.Question;
import web.app.restfulquiz.domain.entity.QuestionRecord;
import web.app.restfulquiz.domain.request.QuestionRequest;
import web.app.restfulquiz.domain.request.QuestionUpdateRequest;

import java.util.List;
import java.util.Optional;

public interface QuestionService {

    List<Question> getQuestionsByQuiz(int quiz_id);

    void createQuestionRecord(int record_id, int question_id, int option_id);

    List<QuestionRecord> getQuestionRecordsByRecord(int record_id);

    Optional<Question> getQuestionById(int question_id);

    Optional<Question> updateQuestion(QuestionUpdateRequest request);

    List<Question> getAllQuestions();

    Optional<Question> createQuestion(QuestionRequest request);

}
