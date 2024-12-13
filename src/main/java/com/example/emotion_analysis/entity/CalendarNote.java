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
    
    @Column(name = "note_content")
    private String noteContent;
    
    @Column(name = "email")
    private String email;

    @Column(name = "name")
    private String name;

    public CalendarNote(LocalDate noteDate, String noteContent, String email, String name) {
        this.noteDate = noteDate;
        this.noteContent = noteContent;
        this.email = email;
        this.name = name;
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
        return noteContent;
    }

    public void setNoteContent(String noteContent) {
        this.noteContent = noteContent;
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
}