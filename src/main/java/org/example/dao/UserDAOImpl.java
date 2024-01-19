package org.example.dao;

import org.example.model.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class UserDAOImpl implements UserDAO {

    private final SessionFactory sessionFactory;

    @Autowired
    public UserDAOImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    @Transactional
    public void saveUser(User user) {
        Session session = sessionFactory.getCurrentSession();
        session.save(user);
    }

    @Override
    @Transactional
    public User getUserById(Long userId) {
        Session session = sessionFactory.getCurrentSession();
        return session.get(User.class, userId);
    }

    @Override
    @Transactional
    public User createUser(User user) {
        Session session = sessionFactory.getCurrentSession();
        session.save(user);
        return user;
    }

    @Override
    @Transactional
    public User updateUser(User user) {
        Session session = sessionFactory.getCurrentSession();
        session.update(user);
        return user;
    }

    @Override
    @Transactional
    public void deleteUser(User user) {
        Session session = sessionFactory.getCurrentSession();
        session.delete(user);
    }
}
