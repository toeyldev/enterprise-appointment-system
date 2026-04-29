package edu.sjsu.cmpe172.starterdemo.service;

import edu.sjsu.cmpe172.starterdemo.repository.ClassCreditRepository;
import org.springframework.stereotype.Service;

@Service
public class ClassCreditService {

    private final ClassCreditRepository repo;

    public ClassCreditService(ClassCreditRepository repo) {
        this.repo = repo;
    }

    public int getBalance(Long customerUserId) {
        return repo.getBalance(customerUserId);
    }

    public void buyCredits(Long customerUserId, int credits) {
        repo.addCredits(customerUserId, credits);
    }
}