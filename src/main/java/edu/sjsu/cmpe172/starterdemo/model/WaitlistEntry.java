package edu.sjsu.cmpe172.starterdemo.model;

public class WaitlistEntry {

    private Long waitlistId;
    private Long customerUserId;
    private Long classId;
    private String joinedAt;
    private String status;

    public WaitlistEntry() {
    }

    public WaitlistEntry(Long waitlistId, Long customerUserId, Long classId, String joinedAt, String status) {
        this.waitlistId = waitlistId;
        this.customerUserId = customerUserId;
        this.classId = classId;
        this.joinedAt = joinedAt;
        this.status = status;
    }

    public Long getWaitlistId() {
        return waitlistId;
    }

    public void setWaitlistId(Long waitlistId) {
        this.waitlistId = waitlistId;
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

    public String getJoinedAt() {
        return joinedAt;
    }

    public void setJoinedAt(String joinedAt) {
        this.joinedAt = joinedAt;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}