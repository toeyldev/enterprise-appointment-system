package edu.sjsu.cmpe172.starterdemo.model;

public class CreditTransaction {

    private Long transactionId;
    private Long customerUserId;
    private Long packageId;
    private int creditsAdded;
    private String purchaseDateAndTime;

    public CreditTransaction(Long transactionId,
                             Long customerUserId,
                             Long packageId,
                             int creditsAdded,
                             String purchaseDateAndTime) {
        this.transactionId = transactionId;
        this.customerUserId = customerUserId;
        this.packageId = packageId;
        this.creditsAdded = creditsAdded;
        this.purchaseDateAndTime = purchaseDateAndTime;
    }

    public Long getTransactionId() {
        return transactionId;
    }

    public Long getCustomerUserId() {
        return customerUserId;
    }

    public Long getPackageId() {
        return packageId;
    }

    public int getCreditsAdded() {
        return creditsAdded;
    }

    public String getPurchaseDateAndTime() {
        return purchaseDateAndTime;
    }
}