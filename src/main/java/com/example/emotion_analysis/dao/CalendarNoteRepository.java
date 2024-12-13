package com.example.emotion_analysis.dao;


import com.example.emotion_analysis.entity.CalendarNote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface CalendarNoteRepository extends JpaRepository<CalendarNote, Integer> {
    // If needed, you can cast or format the date to match exactly the format stored in your database
    @Query("SELECT c FROM CalendarNote c WHERE DATE(c.noteDate) = :noteDate")
    List<CalendarNote> findByNoteDate(@Param("noteDate") LocalDate noteDate);

}