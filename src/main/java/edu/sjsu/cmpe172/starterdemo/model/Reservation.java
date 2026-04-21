package edu.sjsu.cmpe172.starterdemo.model;

public class Reservation {
    private Long reservationId;
    private Long customerUserId;
    private Long classId;
    private String status;
    private String reservedAt;
    private String canceledAt;

    public Reservation(Long reservationId, Long customerUserId, Long classId,
                       String status, String reservedAt, String canceledAt) {
        this.reservationId = reservationId;
        this.customerUserId = customerUserId;
        this.classId = classId;
        this.status = status;
        this.reservedAt = reservedAt;
        this.canceledAt = canceledAt;
    }

    public Long getReservationId() {
        return reservationId;
    }

    public void setReservationId(Long reservationId) {
        this.reservationId = reservationId;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getReservedAt() {
        return reservedAt;
    }

    public void setReservedAt(String reservedAt) {
        this.reservedAt = reservedAt;
    }

    public String getCanceledAt() {
        return canceledAt;
    }

    public void setCanceledAt(String canceledAt) {
        this.canceledAt = canceledAt;
    }
}
