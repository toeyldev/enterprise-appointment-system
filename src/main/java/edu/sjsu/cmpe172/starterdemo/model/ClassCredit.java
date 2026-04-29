package edu.sjsu.cmpe172.starterdemo.model;

public class ClassCredit {

    private Long customerUserId;
    private int remainingCredit;

    public ClassCredit(Long customerUserId, int remainingCredit) {
        this.customerUserId = customerUserId;
        this.remainingCredit = remainingCredit;
    }

    public Long getCustomerUserId() {
        return customerUserId;
    }

    public int getRemainingCredit() {
        return remainingCredit;
    }
}