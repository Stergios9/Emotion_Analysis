package com.example.emotion_analysis.entity;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "calendar")
public class CalendarNote {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "note_date")
    private LocalDate noteDate;

    @Column(name = "time")
    private String time;

    @Column(name = "email")
    private String email;

    @Column(name = "name")
    private String name;

    @Column(name = "last_name")
    private String lastName;

    public CalendarNote(LocalDate noteDate, String time, String email, String name, String LastName) {
        this.noteDate = noteDate;
        this.time = time;
        this.email = email;
        this.name = name;
        this.lastName = LastName;
    }

    public CalendarNote() {}

    // Getters and setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalDate getNoteDate() {
        return noteDate;
    }

    public void setNoteDate(LocalDate noteDate) {
        this.noteDate = noteDate;
    }

    public String getNoteContent() {
        return time;
    }

    public void setNoteContent(String time) {
        this.time = time;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    @Override
    public String toString() {
        return "CalendarNote{" +
                "id=" + id +
                ", noteDate=" + noteDate +
                ", noteContent='" + time + '\'' +
                ", email='" + email + '\'' +
                ", name='" + name + '\'' +
                ", lastName='" + lastName + '\'' +
                '}';
    }
}