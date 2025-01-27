package com.example.emotion_analysis.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "patient")
public class Patient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "age")
    private int age;

    @Column(name = "gender")
    private String gender;

    @Column(name = "comment")
    private String comment;

    @Column(name = "registration_day")
    private LocalDateTime registration_day;

    // Change to ManyToOne relationship
    @ManyToOne(cascade = CascadeType.MERGE)  // Adjust cascade type as needed
    @JoinColumn(name = "location_id")  // This column in Patient table references Location
    private Location location;

    @ManyToMany(cascade = CascadeType.MERGE) // Use ManyToMany here
    @JoinTable(
            name = "patient_sentiment", // Join table name
            joinColumns = @JoinColumn(name = "patient_id"), // Foreign key for Patient
            inverseJoinColumns = @JoinColumn(name = "sentiment_id") // Foreign key for Sentiment
    )
    private Set<Sentiment> sentiments = new HashSet<>(); // Initialize with an empty Set

    // New OneToOne relationship with User
    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "user_id", referencedColumnName = "id")  // Foreign key reference to User
    private User user;

    public Patient() {}

    public Patient(String firstName, String lastName, int age, String gender, Location location, String comment, LocalDateTime registration_day, Set<Sentiment> sentiments) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
        this.gender = gender;
        this.location = location;
        this.comment = comment;
        this.registration_day = registration_day;
        this.sentiments = sentiments;
        this.user = new User();
        this.user.setId(1);
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

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public int getAge() { return age; }

    public void setAge(int age) { this.age = age; }



    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public LocalDateTime getRegistrationDay() {
        return registration_day;
    }

    public void setRegistrationDay(LocalDateTime registration_day) {
        this.registration_day = registration_day;
    }

    public Set<Sentiment> getSentiments() {
        return sentiments;
    }

    public void setSentiments(Set<Sentiment> sentiments) {
        this.sentiments = sentiments;
    }
    // Getter and Setter for User
    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }

    public String getFormattedRegistrationDay() {
        return registration_day != null ? registration_day.toLocalDate().toString() : "";
    }


}
