package edu.sjsu.cmpe172.starterdemo.service;

import edu.sjsu.cmpe172.starterdemo.model.CreditPackage;
import edu.sjsu.cmpe172.starterdemo.repository.CreditPackageRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CreditPackageService {

    private final CreditPackageRepository repo;

    public CreditPackageService(CreditPackageRepository repo) { this.repo = repo; }

    public List<CreditPackage> getAllPackages() {
        return repo.findAll();
    }

    public CreditPackage addCreditPackage(CreditPackage creditPackage) {
        return repo.save(creditPackage);
    }
}
