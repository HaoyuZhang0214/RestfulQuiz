package web.app.restfulquiz.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import web.app.restfulquiz.dao.QuestionDao;
import web.app.restfulquiz.domain.entity.Question;
import web.app.restfulquiz.domain.entity.QuestionRecord;
import web.app.restfulquiz.domain.request.QuestionRequest;
import web.app.restfulquiz.domain.request.QuestionUpdateRequest;
import web.app.restfulquiz.service.QuestionService;

import java.util.List;
import java.util.Optional;

@Service
public class QuestionServiceImpl implements QuestionService {

    private QuestionDao questionDao;

    @Autowired
    public QuestionServiceImpl(QuestionDao questionDao) {
        this.questionDao = questionDao;
    }

    @Override
    public List<Question> getQuestionsByQuiz(int quiz_id) {
        return questionDao.getQuestionsByQuiz(quiz_id);
    }

    @Override
    public void createQuestionRecord(int record_id, int question_id, int option_id) {
        questionDao.createQuestionRecord(record_id, question_id, option_id);
    }

    @Override
    public List<QuestionRecord> getQuestionRecordsByRecord(int record_id) {
        return questionDao.getQuestionRecordsByRecord(record_id);
    }

    @Override
    @Cacheable("questions")
    public Optional<Question> getQuestionById(int question_id) {
        return questionDao.getQuestionById(question_id);
    }

    @Override
    public Optional<Question> updateQuestion(QuestionUpdateRequest request) {
        return questionDao.updateQuestion(request);
    }

    @Override
    public List<Question> getAllQuestions() {
        return questionDao.getAllQuestions();
    }

    @Override
    public Optional<Question> createQuestion(QuestionRequest request) {
        return questionDao.createQuestion(request);
    }


}
