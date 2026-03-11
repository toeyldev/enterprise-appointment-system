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
}