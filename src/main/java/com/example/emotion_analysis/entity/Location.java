package com.example.emotion_analysis.entity;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "location")
public class Location {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "city")
    private String city;


    // One location can have many patients
    @OneToMany(mappedBy = "location", cascade = CascadeType.ALL)
    private List<Patient> patients; // Use a List to hold the patients associated with this location


    // One location can have many psychologists
    @OneToMany(mappedBy = "location", cascade = CascadeType.ALL)
    private List<Psychologist> psychologists; // Use a List to hold the psychologists associated with this location


    public Location() {}

    public Location(String city) {
        this.city = city;
    }

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

    public List<Patient> getPatients() {
        return patients;
    }
    public void setPatients(List<Patient> patients) {
        this.patients = patients;
    }

    public List<Psychologist> getPsychologists() {
        return psychologists;
    }
    public void setPsychologists(List<Psychologist> psychologists) {
        this.psychologists = psychologists;
    }

}
