package web.app.restfulquiz.dao;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import web.app.restfulquiz.domain.entity.*;
import web.app.restfulquiz.domain.request.QuizSubmitRequest;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public class QuizDao {

    private SessionFactory sessionFactory;

    @Autowired
    public QuizDao(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public Optional<QuizQues> getQuizByCategory(Integer category_id) {
        Session session = sessionFactory.getCurrentSession();
        Criteria criteria = session.createCriteria(Quiz.class);
        criteria.add(Restrictions.eq("category_id", category_id));
        criteria.add(Restrictions.sqlRestriction("1=1 order by rand()"));
        criteria.setMaxResults(1);

        List<Quiz> quizzes = criteria.list();
        if(quizzes.size()==0) return Optional.empty();
        Quiz quiz = quizzes.get(0);

        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<Question> cr2 = builder.createQuery(Question.class);
        Root<Question> root = cr2.from(Question.class);
        cr2.select(root)
                .where(builder.equal(root.get("quiz_id"), quiz.getQuiz_id()));
        List<Question> questions = session.createQuery(cr2).getResultList();

        List<QuestionOps> questionQs = new ArrayList<>();
        if(questions.size()>0) {
            for(Question q: questions) {
                CriteriaQuery<Option> cr3 = builder.createQuery(Option.class);
                Root<Option> root3 = cr3.from(Option.class);
                cr3.select(root3)
                        .where(builder.equal(root.get("question_id"), q.getQuestion_id()));
                List<Option> options = session.createQuery(cr3).getResultList();
                QuestionOps questionOps = new QuestionOps(q, options);
                questionQs.add(questionOps);
            }
        }

        return Optional.ofNullable(new QuizQues(quiz, questionQs));
    }


    public Optional<Quiz> getQuizById(int quiz_id) {
        Session session = sessionFactory.getCurrentSession();
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<Quiz> criteria = builder.createQuery(Quiz.class);
        Root<Quiz> root = criteria.from(Quiz.class);
        criteria.select(root)
                .where(builder.equal(root.get("quiz_id"), quiz_id));

        Query<Quiz> query = session.createQuery(criteria);
        List<Quiz> quizzes = query.getResultList();
        return quizzes.size() == 0 ? Optional.empty() : Optional.ofNullable(quizzes.get(0));
    }


    public Integer createQuizRecord(int quiz_id, int user_id, int score) {
        Session session = sessionFactory.getCurrentSession();
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        QuizRecord quizRecord = QuizRecord.builder()
                .quiz_id(quiz_id)
                .user_id(user_id)
                .taken_date(timestamp.toString())
                .score(score)
                .build();
        Serializable id = session.save(quizRecord);
        return Integer.valueOf(id.toString());
    }


    public Optional<QuizRecord> getQuizRecordById(int record_id) {
        Session session = sessionFactory.getCurrentSession();
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<QuizRecord> criteria = builder.createQuery(QuizRecord.class);
        Root<QuizRecord> root = criteria.from(QuizRecord.class);
        criteria.select(root)
                .where(builder.equal(root.get("record_id"), record_id));

        Query<QuizRecord> query = session.createQuery(criteria);
        List<QuizRecord> quizRecords = query.getResultList();
        return quizRecords.size()==0? Optional.empty(): Optional.ofNullable(quizRecords.get(0));
    }


    public List<QuizRecord> getAllQuizRecordsByUser(int user_id) {
        Session session = sessionFactory.getCurrentSession();
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<QuizRecord> criteria = builder.createQuery(QuizRecord.class);
        Root<QuizRecord> root = criteria.from(QuizRecord.class);
        criteria.select(root)
                .where(builder.equal(root.get("user_id"), user_id));

        Query<QuizRecord> query = session.createQuery(criteria);
        List<QuizRecord> quizRecords = query.getResultList();
        return quizRecords;
    }


    public List<Quiz> getAllQuizzes() {
        Session session = sessionFactory.getCurrentSession();
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<Quiz> criteria = builder.createQuery(Quiz.class);
        Root<Quiz> root = criteria.from(Quiz.class);
        criteria.select(root);

        return session.createQuery(criteria).getResultList();
    }


    public List<Quiz> getQuizzesByCategory(Integer category_id) {
        Session session = sessionFactory.getCurrentSession();
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<Quiz> criteria = builder.createQuery(Quiz.class);
        Root<Quiz> root = criteria.from(Quiz.class);
        criteria.select(root)
                .where(builder.equal(root.get("category_id"), category_id));

        List<Quiz> quizzes = session.createQuery(criteria).getResultList();
        return quizzes;
    }

}
