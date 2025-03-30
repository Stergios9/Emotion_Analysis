package com.example.emotion_analysis.service.calendar;

import com.example.emotion_analysis.entity.CalendarNote;

import java.time.LocalDate;
import java.util.List;

public interface CalendarNoteService {

    CalendarNote saveNote(LocalDate date, String content, String name,String lastName, String email);

    CalendarNote saveNote(CalendarNote calendarNote);

    List<CalendarNote> getNotesByDate(LocalDate date);

    boolean deleteNoteByName(String name);

    List<CalendarNote> findAllAppointments();

    CalendarNote findById(int id);

    void deleteNoteById(int id);

}