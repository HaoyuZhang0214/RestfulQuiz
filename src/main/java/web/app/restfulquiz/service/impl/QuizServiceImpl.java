package web.app.restfulquiz.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import web.app.restfulquiz.dao.QuizDao;
import web.app.restfulquiz.domain.entity.*;
import web.app.restfulquiz.domain.request.QuizSubmitRequest;
import web.app.restfulquiz.service.QuizService;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

@Service
public class QuizServiceImpl implements QuizService {

    private QuizDao quizDao;

    @Autowired
    public QuizServiceImpl(QuizDao quizDao) {
        this.quizDao = quizDao;
    }

    @Override
    public Optional<QuizQues> getQuizByCategory(Integer category_id) {
        return quizDao.getQuizByCategory(category_id);
    }

    @Override
    @Cacheable("quizzes")
    public Optional<Quiz> getQuizById(int quiz_id) {
        return quizDao.getQuizById(quiz_id);
    }

    @Override
    public Integer createQuizRecord(int quiz_id, int user_id, int score) {
        return quizDao.createQuizRecord(quiz_id, user_id, score);
    }

    @Override
    @Cacheable("quizRecords")
    public Optional<QuizRecord> getQuizRecordById(int record_id) {
        return quizDao.getQuizRecordById(record_id);
    }

    @Override
    public List<QuizRecord> getAllQuizRecordsByUser(int user_id) {
        return quizDao.getAllQuizRecordsByUser(user_id);
    }

    @Override
    public List<Quiz> getAllQuizzes() {
        return quizDao.getAllQuizzes();
    }

    @Override
    public List<Quiz> getQuizzesByCategory(Integer category_id) {
        return quizDao.getQuizzesByCategory(category_id);
    }


    // -----Async-----
    @Async("taskExecutor")
    public CompletableFuture<Optional<Quiz>> getQuizByIdAsync(int quiz_id) {
        Optional<Quiz> quiz = quizDao.getQuizById(quiz_id);
        return CompletableFuture.completedFuture(quiz);
    }
}
