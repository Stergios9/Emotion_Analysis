package com.example.emotion_analysis.entity;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")  // Explicitly mapping to the 'id' column
    private int id;

    @Column(name = "role", nullable = false)  // Map to 'role' column
    private String role;

    @Column(name = "username", nullable = false, unique = true)  // Map to 'username' column
    private String username;

    @Column(name = "password", nullable = false)  // Map to 'password' column
    private String password;

    // OneToOne relationship with Patient
    @OneToMany(mappedBy = "user", cascade = CascadeType.MERGE)
    private List<Patient> patients; // Use a List to hold the patients associated with this user

    // Constructors
    public User() {}

    public User(String role, String username, String password) {
        this.role = role;
        this.username = username;
        this.password = password;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<Patient> getPatients() {
        return patients;
    }
    public void setPatients(List<Patient> patients) {
        this.patients = patients;
    }

}