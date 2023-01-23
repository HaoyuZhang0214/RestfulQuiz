package web.app.restfulquiz.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import web.app.restfulquiz.domain.entity.*;
import web.app.restfulquiz.domain.request.RegisterRequest;

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
public class UserDao {

    private SessionFactory sessionFactory;

    @Autowired
    public UserDao(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public Optional<UserPerm> loadUserByUsername(String username) {
        Session session = sessionFactory.getCurrentSession();
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<User> cr1 = builder.createQuery(User.class);
        Root<User> root1 = cr1.from(User.class);
        cr1.select(root1)
                .where(builder.equal(root1.get("username"), username));
        List<User> users = session.createQuery(cr1).getResultList();
        if(users.size()==0) return Optional.empty();
        User user = users.get(0);

        CriteriaQuery<UserPermission> cr2 = builder.createQuery(UserPermission.class);
        Root<UserPermission> root2 = cr2.from(UserPermission.class);
        cr2.select(root2)
                .where(builder.equal(root2.get("user_id"), user.getUser_id()));
        List<UserPermission> ups = session.createQuery(cr2).getResultList();

        List<Permission> permissions = new ArrayList<>();
        if(ups.size() > 0) {
            for(UserPermission up: ups) {
                CriteriaQuery<Permission> cr3 = builder.createQuery(Permission.class);
                Root<Permission> root3 = cr3.from(Permission.class);
                cr3.select(root3)
                        .where(builder.equal(root3.get("permission_id"), up.getPermission_id()));
                List<Permission> perms = session.createQuery(cr3).getResultList();
                if(perms.size() > 0) permissions.add(perms.get(0));
            }
        }
        return Optional.ofNullable(new UserPerm(user, permissions));
    }


    public Optional<User> createUser(RegisterRequest request) {
        Session session = sessionFactory.getCurrentSession();

        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<User> cr = builder.createQuery(User.class);
        Root<User> root = cr.from(User.class);
        cr.select(root)
                .where(builder.equal(root.get("username"), request.getUsername()));
        List<User> users = session.createQuery(cr).getResultList();
        if(users.size()>0) return Optional.empty();

        User user = User.builder()
                .username(request.getUsername())
                .password(request.getPassword())
                .firstname(request.getFirstname())
                .lastname(request.getLastname())
                .status(true)
                .is_admin(false)
                .build();

        Serializable user_id = session.save(user);
        user.setUser_id(Integer.valueOf(user_id.toString()));
        return Optional.ofNullable(user);
    }


    public Optional<User> getUserById(int user_id) {
        Session session = sessionFactory.getCurrentSession();
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<User> criteria = builder.createQuery(User.class);
        Root<User> root = criteria.from(User.class);
        criteria.select(root);
        criteria.where(builder.equal(root.get("user_id"), user_id));

        Query<User> query = session.createQuery(criteria);
        List<User> users = query.getResultList();
        return users.size() == 0 ? Optional.empty() : Optional.ofNullable(users.get(0));
    }


    public Optional<User> getUserByUsername(String username) {
        Session session = sessionFactory.getCurrentSession();
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<User> criteria = builder.createQuery(User.class);
        Root<User> root = criteria.from(User.class);
        criteria.select(root);
        criteria.where(builder.equal(root.get("username"), username));

        Query<User> query = session.createQuery(criteria);
        List<User> users = query.getResultList();
        return users.size() == 0 ? Optional.empty() : Optional.ofNullable(users.get(0));
    }


    public List<User> getAllUsersExceptAdmin() {
        Session session = sessionFactory.getCurrentSession();
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<User> criteria = builder.createQuery(User.class);
        Root<User> root = criteria.from(User.class);
        criteria.select(root)
                .where(builder.equal(root.get("is_admin"), false));

        Query<User> query = session.createQuery(criteria);
        List<User> users = query.getResultList();
        return users;
    }


    public Optional<User> updateUserStatus(int user_id) {
        Session session = sessionFactory.getCurrentSession();
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaUpdate<User> criteria = builder.createCriteriaUpdate(User.class);
        Root<User> root = criteria.from(User.class);

        Optional<User> user = getUserById(user_id);
        boolean status = true;
        if(user.isPresent()) {
            status = user.get().isStatus()? false: true;
        } else
            return Optional.empty();
        criteria.set("status", status)
                .where(builder.equal(root.get("user_id"), user_id));
        session.createQuery(criteria).executeUpdate();
        user.get().setStatus(status);
        return user;
    }




}
