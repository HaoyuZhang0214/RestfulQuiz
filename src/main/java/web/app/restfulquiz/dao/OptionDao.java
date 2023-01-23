package web.app.restfulquiz.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import web.app.restfulquiz.domain.entity.Option;
import web.app.restfulquiz.domain.entity.Question;
import web.app.restfulquiz.domain.entity.QuestionRecord;
import web.app.restfulquiz.domain.request.QuestionRequest;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.CriteriaUpdate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public class OptionDao {

    @Autowired
    private SessionFactory sessionFactory;

    public Optional<Option> getOptionById(int option_id) {
        Session session = sessionFactory.getCurrentSession();
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<Option> criteria = builder.createQuery(Option.class);
        Root<Option> root = criteria.from(Option.class);
        criteria.select(root)
                .where(builder.equal(root.get("option_id"), option_id));

        Query<Option> query = session.createQuery(criteria);
        List<Option> options = query.getResultList();
        return options.size() == 0 ? Optional.empty() : Optional.ofNullable(options.get(0));
    }


    public List<Option> getOptionsByQuestion(int question_id) {
        Session session = sessionFactory.getCurrentSession();
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<Option> criteria = builder.createQuery(Option.class);
        Root<Option> root = criteria.from(Option.class);
        criteria.select(root)
                .where(builder.equal(root.get("question_id"), question_id));

        Query<Option> query = session.createQuery(criteria);
        List<Option> options = query.getResultList();
        return new ArrayList<>(options);
    }


    public void updateOption(int option_id, String content, boolean is_solution) {
        Session session = sessionFactory.getCurrentSession();
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaUpdate<Option> criteria = builder.createCriteriaUpdate(Option.class);
        Root<Option> root = criteria.from(Option.class);

        criteria.set("content", content)
                .set("is_solution", is_solution)
                .where(builder.equal(root.get("option_id"), option_id));
        session.createQuery(criteria).executeUpdate();
    }


    public void createOption(QuestionRequest request, int question_id) {
        Session session = sessionFactory.getCurrentSession();
        if(request.getOptions().size()>0) {
            for(Option op: request.getOptions()) {
                session.save(Option.builder()
                        .question_id(question_id)
                        .content(op.getContent())
                        .is_solution(op.is_solution())
                        .build());
            }
        }
    }

}
