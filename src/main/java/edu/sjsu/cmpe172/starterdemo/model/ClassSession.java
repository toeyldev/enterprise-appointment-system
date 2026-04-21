package edu.sjsu.cmpe172.starterdemo.model;

public class ClassSession {

    private Long classId;
    private String classDate;
    private String classTime;
    private String instructorName;
    private int classCapacity;
    private int availableSpots; // Derived attribute
    private int waitlistCount;

    public ClassSession() {
    }

    public ClassSession(Long classId, String classDate, String classTime, String instructorName, int classCapacity) {
        this.classId = classId;
        this.classDate = classDate;
        this.classTime = classTime;
        this.instructorName = instructorName;
        this.classCapacity = classCapacity;
    }

    public Long getClassId() {
        return classId;
    }

    public void setClassId(Long classId) {
        this.classId = classId;
    }

    public String getClassDate() {
        return classDate;
    }

    public void setClassDate(String classDate) {
        this.classDate = classDate;
    }

    public String getClassTime() {
        return classTime;
    }

    public void setClassTime(String classTime) {
        this.classTime = classTime;
    }

    public String getInstructorName() {
        return instructorName;
    }

    public void setInstructorName(String instructorName) {
        this.instructorName = instructorName;
    }

    public int getClassCapacity() {
        return classCapacity;
    }

    public void setClassCapacity(int classCapacity) {
        this.classCapacity = classCapacity;
    }

    public int getAvailableSpots() {
        return availableSpots;
    }

    public void setAvailableSpots(int availableSpots) {
        this.availableSpots = availableSpots;
    }

    public int getWaitlistCount() {
        return waitlistCount;
    }

    public void setWaitlistCount(int waitlistCount) {
        this.waitlistCount = waitlistCount;
    }
}
