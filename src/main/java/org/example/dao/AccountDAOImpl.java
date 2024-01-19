package org.example.dao;

import org.example.model.Account;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class AccountDAOImpl implements AccountDAO {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    @Transactional
    public void saveAccount(Account account) {
        Session session = sessionFactory.getCurrentSession();
        session.save(account);
    }

    @Override
    @Transactional
    public Account getAccountById(Long accountId) {
        Session session = sessionFactory.getCurrentSession();
        return session.get(Account.class, accountId);
    }

    @Override
    @Transactional
    public void updateAccount(Account account) {
        Session session = sessionFactory.getCurrentSession();
        session.update(account);
    }

    @Override
    @Transactional
    public void deleteAccount(Account account) {
        Session session = sessionFactory.getCurrentSession();
        session.delete(account);
    }

}
