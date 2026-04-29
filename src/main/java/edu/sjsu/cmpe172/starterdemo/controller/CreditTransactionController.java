package edu.sjsu.cmpe172.starterdemo.controller;

import edu.sjsu.cmpe172.starterdemo.model.CreditTransaction;
import edu.sjsu.cmpe172.starterdemo.service.CreditTransactionService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/credit-history")
public class CreditTransactionController {

    private final CreditTransactionService service;

    public CreditTransactionController(CreditTransactionService service) {
        this.service = service;
    }

    @GetMapping("/{customerUserId}")
    public List<CreditTransaction> history(@PathVariable Long customerUserId) {
        return service.getCustomerHistory(customerUserId);
    }
}