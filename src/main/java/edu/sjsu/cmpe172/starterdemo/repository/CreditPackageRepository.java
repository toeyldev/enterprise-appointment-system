package edu.sjsu.cmpe172.starterdemo.repository;

import edu.sjsu.cmpe172.starterdemo.model.CreditPackage;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class CreditPackageRepository {

    private final Map<Long, CreditPackage> packages = new HashMap<>();
    private long nextId = 1L;

    public CreditPackageRepository() {
        // Mock data
        save(new CreditPackage(null, 444, 20));
        save(new CreditPackage(null, 277, 10));
        save(new CreditPackage(null, 31, 5));
        save(new CreditPackage(null, 33, 1));
    }

    public List<CreditPackage> findAll() {
        return new ArrayList<>(packages.values());
    }

    public CreditPackage save(CreditPackage creditPackage) {
        if (creditPackage.getPackageId() == null) {
            creditPackage.setPackageId(nextId++);
        }
        packages.put(creditPackage.getPackageId(), creditPackage);
        return creditPackage;
    }
}
