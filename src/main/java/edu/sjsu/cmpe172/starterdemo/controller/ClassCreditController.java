package edu.sjsu.cmpe172.starterdemo.controller;

import edu.sjsu.cmpe172.starterdemo.service.ClassCreditService;
import edu.sjsu.cmpe172.starterdemo.service.CreditPackageService;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/credits")
public class ClassCreditController {

    private final ClassCreditService service;
    private final CreditPackageService creditPackageService;

    public ClassCreditController(ClassCreditService service,
                                 CreditPackageService creditPackageService) {
        this.service = service;
        this.creditPackageService = creditPackageService;
    }

    @GetMapping("/{customerUserId}")
    public Map<String, Integer> getBalance(@PathVariable Long customerUserId) {
        return Map.of("balance", service.getBalance(customerUserId));
    }

    @PostMapping("/buy")
    public String buy(@RequestBody Map<String, Long> body) {
        Long customerUserId = body.get("customerUserId");
        Long packageId = body.get("packageId");

        return creditPackageService.buyCredits(customerUserId, packageId);
    }
}