package com.example.emotion_analysis.entity;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "calendar")
public class CalendarNote {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "note_Date")
    private LocalDate noteDate;

    @Column(name = "note_Content")
    private String noteContent;

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
}