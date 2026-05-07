package edu.sjsu.cmpe172.starterdemo.controller;

import edu.sjsu.cmpe172.starterdemo.model.CreditPackage;
import edu.sjsu.cmpe172.starterdemo.service.CreditPackageService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/credits")
public class CreditPackageController {

    private final CreditPackageService service;

    public CreditPackageController(CreditPackageService service) {
        this.service = service;
    }

    @GetMapping
    public List<CreditPackage> getPackages() {
        return service.getAllPackages();
    }

    @PostMapping
    public CreditPackage createPackage(@RequestBody CreditPackage creditPackage) {
        return service.addCreditPackage(creditPackage);
    }

    @PutMapping("/{packageId}")
    public String updatePackage(@PathVariable Long packageId,
                                @RequestBody CreditPackage creditPackage) {
        return service.updateCreditPackage(
                packageId,
                creditPackage.getPackageCost(),
                creditPackage.getClassesPerPackage()
        );
    }

    @DeleteMapping("/{packageId}")
    public String deletePackage(@PathVariable Long packageId) {
        return service.deleteCreditPackage(packageId);
    }
}