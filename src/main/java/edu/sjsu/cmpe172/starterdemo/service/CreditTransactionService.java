package edu.sjsu.cmpe172.starterdemo.service;

import edu.sjsu.cmpe172.starterdemo.model.CreditTransaction;
import edu.sjsu.cmpe172.starterdemo.repository.CreditTransactionRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CreditTransactionService {

    private final CreditTransactionRepository repository;

    public CreditTransactionService(CreditTransactionRepository repository) {
        this.repository = repository;
    }

    public List<CreditTransaction> getCustomerHistory(Long customerUserId) {
        return repository.findByCustomerUserId(customerUserId);
    }
}