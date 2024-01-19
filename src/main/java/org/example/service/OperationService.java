package org.example.service;

import org.example.model.Operation;

public interface OperationService {
    void createOperation(Operation operation);
    Operation getOperationById(Long operationId);
    void updateOperation(Operation operation);
    void deleteOperation(Long operationId);
}
