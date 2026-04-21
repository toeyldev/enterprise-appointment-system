package edu.sjsu.cmpe172.starterdemo.model;

public class NotificationRequest {
    private Long customerUserId;
    private Long classId;
    private String customerEmail;
    private String message;

    public NotificationRequest() {
    }

    public NotificationRequest(Long customerUserId, Long classId, String customerEmail, String message) {
        this.customerUserId = customerUserId;
        this.classId = classId;
        this.customerEmail = customerEmail;
        this.message = message;
    }

    public Long getCustomerUserId() {
        return customerUserId;
    }

    public void setCustomerUserId(Long customerUserId) {
        this.customerUserId = customerUserId;
    }

    public Long getClassId() {
        return classId;
    }

    public void setClassId(Long classId) {
        this.classId = classId;
    }

    public String getCustomerEmail() {
        return customerEmail;
    }

    public void setCustomerEmail(String customerEmail) {
        this.customerEmail = customerEmail;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}