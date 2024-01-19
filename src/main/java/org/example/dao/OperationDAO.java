package org.example.dao;

import org.example.model.Operation;

public interface OperationDAO {
    void saveOperation(Operation operation);
    Operation getOperationById(Long operationId);
    void updateOperation(Operation operation);
    void deleteOperation(Operation operation);

}
