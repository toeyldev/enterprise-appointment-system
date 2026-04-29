package edu.sjsu.cmpe172.starterdemo.controller;

import edu.sjsu.cmpe172.starterdemo.model.WaitlistEntry;
import edu.sjsu.cmpe172.starterdemo.service.WaitlistService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/waitlist")
public class WaitlistController {

    private final WaitlistService waitlistService;

    public WaitlistController(WaitlistService waitlistService) {
        this.waitlistService = waitlistService;
    }

    @GetMapping("/customer/{customerUserId}")
    public List<WaitlistEntry> getCustomerWaitlist(@PathVariable Long customerUserId) {
        return waitlistService.getCustomerWaitlist(customerUserId);
    }

    @PostMapping("/leave")
    public String leaveWaitlist(@RequestBody Map<String, Long> body) {
        Long waitlistId = body.get("waitlistId");
        Long customerUserId = body.get("customerUserId");

        return waitlistService.leaveWaitlist(waitlistId, customerUserId);
    }
}