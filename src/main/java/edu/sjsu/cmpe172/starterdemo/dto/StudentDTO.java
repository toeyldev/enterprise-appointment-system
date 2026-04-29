package edu.sjsu.cmpe172.starterdemo.dto;

public class StudentDTO {

    private String firstName;
    private String lastName;

    public StudentDTO(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }
}