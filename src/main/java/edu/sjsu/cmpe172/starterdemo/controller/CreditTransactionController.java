package edu.sjsu.cmpe172.starterdemo.controller;

import edu.sjsu.cmpe172.starterdemo.model.CreditTransaction;
import edu.sjsu.cmpe172.starterdemo.service.CreditTransactionService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/credit-history")
public class CreditTransactionController {

    // handle retrieving customer transaction history
    private final CreditTransactionService creditTransactionService;

    public CreditTransactionController(CreditTransactionService service) {
        this.creditTransactionService = service;
    }

    // return all credit purchase history for a customer
    @GetMapping("/{customerUserId}")
    public List<CreditTransaction> history(@PathVariable Long customerUserId) {
        return creditTransactionService.getCustomerHistory(customerUserId);
    }
}