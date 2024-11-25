package com.example.emotion_analysis.dto;

// In src/main/java/com/yourcompany/application/dto/PatientDTO.java
public class PatientDTO {
    private int id;
    private String firstName;
    private String lastName;
    private String gender;

    public PatientDTO(int id, String firstName, String lastName, String gender) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
    }

    public PatientDTO() {
    }
    // Getters and Setters

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLasttName() {
        return lastName;
    }

    public void setLasttName(String lasttName) {
        this.lastName = lasttName;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }


}
