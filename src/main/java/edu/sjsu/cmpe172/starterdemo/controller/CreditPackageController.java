package edu.sjsu.cmpe172.starterdemo.controller;

import edu.sjsu.cmpe172.starterdemo.model.CreditPackage;
import edu.sjsu.cmpe172.starterdemo.service.CreditPackageService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/credits")
public class CreditPackageController {

    private final CreditPackageService creditPackageService;

    public CreditPackageController(CreditPackageService service) {
        this.creditPackageService = service;
    }

    // return all available credit packages
    @GetMapping
    public List<CreditPackage> getPackages() {
        return creditPackageService.getAllPackages();
    }

    // create a new credit package
    @PostMapping
    public CreditPackage createPackage(@RequestBody CreditPackage creditPackage) {
        return creditPackageService.addCreditPackage(creditPackage);
    }

    // update existing package details
    @PutMapping("/{packageId}")
    public String updatePackage(@PathVariable Long packageId,
                                @RequestBody CreditPackage creditPackage) {
        return creditPackageService.updateCreditPackage(
                packageId,
                creditPackage.getPackageCost(),
                creditPackage.getClassesPerPackage()
        );
    }

    // remove a credit package from the system
    @DeleteMapping("/{packageId}")
    public String deletePackage(@PathVariable Long packageId) {
        return creditPackageService.deleteCreditPackage(packageId);
    }
}