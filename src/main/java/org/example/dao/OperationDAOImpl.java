package org.example.dao;

import org.example.model.Operation;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class OperationDAOImpl implements OperationDAO {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    @Transactional
    public void saveOperation(Operation operation) {
        Session session = sessionFactory.getCurrentSession();
        session.save(operation);
    }

    @Override
    @Transactional
    public Operation getOperationById(Long operationId) {
        Session session = sessionFactory.getCurrentSession();
        return session.get(Operation.class, operationId);
    }

    @Override
    @Transactional
    public void updateOperation(Operation operation) {
        Session session = sessionFactory.getCurrentSession();
        session.update(operation);
    }

    @Override
    @Transactional
    public void deleteOperation(Operation operation) {
        Session session = sessionFactory.getCurrentSession();
        session.delete(operation);
    }

}
