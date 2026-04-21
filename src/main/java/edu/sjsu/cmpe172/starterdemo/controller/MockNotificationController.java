package edu.sjsu.cmpe172.starterdemo.controller;

import edu.sjsu.cmpe172.starterdemo.model.NotificationRequest;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/mock-notifications")
public class MockNotificationController {

    @PostMapping("/send")
    public String sendNotification(@RequestBody NotificationRequest request) {
        return "Mock notification sent to " + request.getCustomerEmail()
                + " for class " + request.getClassId();
    }
}