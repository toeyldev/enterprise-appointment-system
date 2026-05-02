package edu.sjsu.cmpe172.starterdemo.service;

import edu.sjsu.cmpe172.starterdemo.model.NotificationRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class NotificationClient {

    private final RestTemplate restTemplate = new RestTemplate();

    public String sendReservationConfirmation(NotificationRequest request) {
        String url = "http://localhost:8080/mock-notifications/send";

        try {
            return restTemplate.postForObject(url, request, String.class);
        } catch (Exception e) {
            return "Notification failed: " + e.getMessage();
        }
    }

    public void sendNotification(Long customerUserId, Long classId, String email, String message) {
        NotificationRequest request = new NotificationRequest(
                customerUserId,
                classId,
                email,
                message
        );

        String url = "http://localhost:8080/mock-notifications/send";

        try {
            restTemplate.postForObject(url, request, String.class);
        } catch (Exception e) {
            System.out.println("Notification failed: " + e.getMessage());
        }
    }
}