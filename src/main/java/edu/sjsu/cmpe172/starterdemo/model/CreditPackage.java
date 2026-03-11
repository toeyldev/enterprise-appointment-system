package edu.sjsu.cmpe172.starterdemo.model;

public class CreditPackage {

    private Long packageId;
    private double packageCost;
    private int classesPerPackage;

    public CreditPackage() {
    }

    public CreditPackage(Long packageId, double packageCost, int classesPerPackage) {
        this.packageId = packageId;
        this.packageCost = packageCost;
        this.classesPerPackage = classesPerPackage;
    }

    public Long getPackageId() {
        return packageId;
    }
    public void setPackageId(Long packageId) {
        this.packageId = packageId;
    }
    public double getPackageCost() {
        return packageCost;
    }

    public void setPackageCost(double packageCost) {
        this.packageCost = packageCost;
    }

    public int getClassesPerPackage() {
        return classesPerPackage;
    }

    public void setClassesPerPackage(int classesPerPackage) {
        this.classesPerPackage = classesPerPackage;
    }

    // Derived variable
    public double getCostPerClass() {
        if (classesPerPackage == 0) {
            return 0;
        }

        return packageCost / classesPerPackage;
    }
}
