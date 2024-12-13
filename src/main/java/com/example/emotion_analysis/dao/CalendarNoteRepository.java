package com.example.emotion_analysis.dao;


import com.example.emotion_analysis.entity.CalendarNote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface CalendarNoteRepository extends JpaRepository<CalendarNote, Integer> {

    List<CalendarNote> findCalendarNoteByNoteDate(LocalDate date);

    CalendarNote save(CalendarNote note);

    List<CalendarNote> findByNoteDate(LocalDate date);

    boolean existsByName(String name);

    @Modifying
    @Query("DELETE FROM CalendarNote cn WHERE cn.name = :name")
    void deleteByName(@Param("name") String name);



}