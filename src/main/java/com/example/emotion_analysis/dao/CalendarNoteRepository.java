package com.example.emotion_analysis.dao;


import com.example.emotion_analysis.entity.CalendarNote;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;

public interface CalendarNoteRepository extends JpaRepository<CalendarNote, Integer> {
    CalendarNote findByNoteDate(LocalDate noteDate); // Retrieve note by date
}