package com.example.emotion_analysis.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "psychologist")
public class Psychologist {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "name")
    private String name;

    @Column(name = "specialization")
    private String specialization;

    @Column(name = "phone") // New phone column
    private String phone;  // Add the phone attribute

    @Column(name = "email")
    private String email;

    @ManyToOne(cascade = {CascadeType.MERGE})
    @JoinColumn(name = "location_id")
    private Location location;

    public Psychologist() {}

    public Psychologist(String name, String specialization, String phone, String email, Location location) {
        this.name = name;
        this.specialization = specialization;
        this.phone = phone;
        this.email = email;
        this.location = location;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSpecialization() {
        return specialization;
    }

    public void setSpecialization(String specialization) {
        this.specialization = specialization;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "Psychologist{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", specialization='" + specialization + '\'' +
                ", phone='" + phone + '\'' +
                ", email='" + email + '\'' +
                ", location=" + location.getCity() +
                '}';
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }
}