package edu.sjsu.cmpe172.starterdemo.controller;

import edu.sjsu.cmpe172.starterdemo.service.ClassCreditService;
import edu.sjsu.cmpe172.starterdemo.service.CreditPackageService;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/credits")
public class ClassCreditController {

    // handle customer credit balance
    private final ClassCreditService classCreditService;
    // handle purchasing credit packages
    private final CreditPackageService creditPackageService;

    public ClassCreditController(ClassCreditService service,
                                 CreditPackageService creditPackageService) {
        this.classCreditService = service;
        this.creditPackageService = creditPackageService;
    }

    // return customer's current class credit balance
    @GetMapping("/{customerUserId}")
    public Map<String, Integer> getBalance(@PathVariable Long customerUserId) {
        return Map.of("balance", classCreditService.getBalance(customerUserId));
    }

    // endpoint for buying class credits
    @PostMapping("/buy")
    public String buy(@RequestBody Map<String, Long> body) {
        Long customerUserId = body.get("customerUserId");
        Long packageId = body.get("packageId");

        return creditPackageService.buyCredits(customerUserId, packageId);
    }
}