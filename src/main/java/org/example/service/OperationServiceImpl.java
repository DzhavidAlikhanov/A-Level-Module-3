package org.example.service;

import org.example.model.Operation;
import org.example.repository.OperationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OperationServiceImpl implements OperationService {

    private final OperationRepository operationRepository;

    @Autowired
    public OperationServiceImpl(OperationRepository operationRepository) {
        this.operationRepository = operationRepository;
    }

    @Override
    public void createOperation(Operation operation) {
        operationRepository.save(operation);
    }

    @Override
    public Operation getOperationById(Long operationId) {
        return operationRepository.findById(operationId).orElse(null);
    }

    @Override
    public void updateOperation(Operation operation) {
        operationRepository.save(operation);
    }

    @Override
    public void deleteOperation(Long operationId) {
        operationRepository.deleteById(operationId);
    }
}
