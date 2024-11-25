package com.example.emotion_analysis.dto;

public class LocationDTO {
    private int id;
    private String city;

    // Constructor
    public LocationDTO(int id, String city) {
        this.id = id;
        this.city = city;
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }
}
