package web.app.restfulquiz.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import web.app.restfulquiz.domain.entity.*;
import web.app.restfulquiz.domain.request.QuestionRequest;
import web.app.restfulquiz.domain.request.QuestionUpdateRequest;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.CriteriaUpdate;
import javax.persistence.criteria.Root;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public class QuestionDao {

    @Autowired
    private SessionFactory sessionFactory;

    public List<Question> getQuestionsByQuiz(int quiz_id) {
        Session session = sessionFactory.getCurrentSession();
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<Question> criteria = builder.createQuery(Question.class);
        Root<Question> root = criteria.from(Question.class);
        criteria.select(root)
                .where(builder.equal(root.get("quiz_id"), quiz_id), builder.equal(root.get("status"), true));

        Query<Question> query = session.createQuery(criteria);
        List<Question> questions = query.getResultList();
        return new ArrayList<>(questions);
    }


    public void createQuestionRecord(int record_id, int question_id, int option_id) {
        Session session = sessionFactory.getCurrentSession();
        QuestionRecord questionRecord = QuestionRecord.builder()
                .record_id(record_id)
                .question_id(question_id)
                .option_id(option_id)
                .build();
        session.save(questionRecord);
    }


    public List<QuestionRecord> getQuestionRecordsByRecord(int record_id) {
        Session session = sessionFactory.getCurrentSession();
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<QuestionRecord> criteria = builder.createQuery(QuestionRecord.class);
        Root<QuestionRecord> root = criteria.from(QuestionRecord.class);
        criteria.select(root)
                .where(builder.equal(root.get("record_id"), record_id));

        List<QuestionRecord> questionRecords = session.createQuery(criteria).getResultList();
        return questionRecords;
    }


    public Optional<Question> getQuestionById(int question_id) {
        Session session = sessionFactory.getCurrentSession();
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<Question> criteria = builder.createQuery(Question.class);
        Root<Question> root = criteria.from(Question.class);
        criteria.select(root)
                .where(builder.equal(root.get("question_id"), question_id));

        Query<Question> query = session.createQuery(criteria);
        List<Question> questions = query.getResultList();
        return questions.size()==0? Optional.empty(): Optional.ofNullable(questions.get(0));
    }


    public Optional<Question> updateQuestion(QuestionUpdateRequest request) {
        Session session = sessionFactory.getCurrentSession();
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaUpdate<Question> criteria = builder.createCriteriaUpdate(Question.class);
        Root<Question> root = criteria.from(Question.class);

        criteria.set("content", request.getContent())
                .where(builder.equal(root.get("question_id"), request.getQuestion_id()));
        session.createQuery(criteria).executeUpdate();

        return getQuestionById(request.getQuestion_id());
    }


    public List<Question> getAllQuestions() {
        Session session = sessionFactory.getCurrentSession();
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<Question> criteria = builder.createQuery(Question.class);
        Root<Question> root = criteria.from(Question.class);
        criteria.select(root);

        return session.createQuery(criteria).getResultList();
    }


    public Optional<Question> createQuestion(QuestionRequest request) {
        Session session = sessionFactory.getCurrentSession();
        Question question = Question.builder()
                .quiz_id(request.getQuiz_id())
                .content(request.getContent())
                .status(true)
                .build();
        Serializable id = session.save(question);
        question.setQuestion_id(Integer.valueOf(id.toString()));
        return Optional.ofNullable(question);
    }

}
