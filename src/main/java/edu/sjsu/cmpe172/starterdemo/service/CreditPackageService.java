package edu.sjsu.cmpe172.starterdemo.service;

import edu.sjsu.cmpe172.starterdemo.model.CreditPackage;
import edu.sjsu.cmpe172.starterdemo.repository.ClassCreditRepository;
import edu.sjsu.cmpe172.starterdemo.repository.CreditPackageRepository;
import edu.sjsu.cmpe172.starterdemo.repository.CreditTransactionRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CreditPackageService {

    private final CreditPackageRepository repo;
    private final ClassCreditRepository classCreditRepository;
    private final CreditTransactionRepository transactionRepository;

    public CreditPackageService(
            CreditPackageRepository repo,
            ClassCreditRepository classCreditRepository,
            CreditTransactionRepository transactionRepository) {

        this.repo = repo;
        this.classCreditRepository = classCreditRepository;
        this.transactionRepository = transactionRepository;
    }

    public List<CreditPackage> getAllPackages() {
        return repo.findAll();
    }

    // create a new credit package
    public CreditPackage addCreditPackage(CreditPackage creditPackage) {
        return repo.save(creditPackage);
    }

    // handle customer purchasing credits
    @Transactional
    public String buyCredits(Long customerUserId, Long packageId) {

        CreditPackage creditPackage = repo.findById(packageId);

        if (creditPackage == null) {
            return "Package not found.";
        }

        int credits = creditPackage.getClassesPerPackage();

        classCreditRepository.addCredits(customerUserId, credits);

        transactionRepository.insertTransaction(
                customerUserId,
                packageId,
                credits
        );

        return credits + " credits purchased successfully.";
    }

    // update existing credit package info
    @Transactional
    public String updateCreditPackage(Long packageId, double packageCost, int classesPerPackage) {
        int rowsUpdated = repo.updatePackage(packageId, packageCost, classesPerPackage);

        if (rowsUpdated == 0) {
            return "Credit package not found.";
        }

        return "Credit package updated successfully.";
    }

    // delete credit package from system
    @Transactional
    public String deleteCreditPackage(Long packageId) {
        int rowsDeleted = repo.deletePackage(packageId);

        if (rowsDeleted == 0) {
            return "Credit package not found.";
        }

        return "Credit package removed successfully.";
    }
}