package edu.sjsu.cmpe172.starterdemo.service;

import edu.sjsu.cmpe172.starterdemo.model.WaitlistEntry;
import edu.sjsu.cmpe172.starterdemo.repository.WaitlistRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WaitlistService {

    private final WaitlistRepository waitlistRepository;

    public WaitlistService(WaitlistRepository waitlistRepository) {
        this.waitlistRepository = waitlistRepository;
    }

    public List<WaitlistEntry> getCustomerWaitlist(Long customerUserId) {
        return waitlistRepository.findByCustomerUserId(customerUserId);
    }

    public String leaveWaitlist(Long waitlistId, Long customerUserId) {
        int rowsUpdated = waitlistRepository.leaveWaitlist(waitlistId, customerUserId);

        if (rowsUpdated == 0) {
            return "Unable to leave waitlist. Entry not found or already removed.";
        }

        return "You have left the waitlist.";
    }
}