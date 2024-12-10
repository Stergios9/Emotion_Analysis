package com.example.emotion_analysis.service.calendar;

import com.example.emotion_analysis.entity.CalendarNote;

import java.time.LocalDate;

public interface CalendarNoteService {

    CalendarNote saveOrUpdateNote(LocalDate date, String content);

    CalendarNote getNoteByDate(LocalDate date);
}
