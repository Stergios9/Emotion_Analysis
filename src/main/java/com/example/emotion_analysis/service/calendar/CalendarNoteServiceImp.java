package com.example.emotion_analysis.service.calendar;

import com.example.emotion_analysis.dao.CalendarNoteRepository;
import com.example.emotion_analysis.entity.CalendarNote;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;


@Service
public class CalendarNoteServiceImp implements CalendarNoteService {

    @Autowired
    private CalendarNoteRepository calendarNoteRepository;

    @Override
    public CalendarNote saveOrUpdateNote(LocalDate date, String content) {
        CalendarNote existingNote = calendarNoteRepository.findByNoteDate(date);

        if (existingNote != null) {
            // Update the existing note
            existingNote.setNoteContent(content);
            return calendarNoteRepository.save(existingNote);  // Save the updated note
        } else {
            // No existing note for the date, create a new one
            CalendarNote newNote = new CalendarNote();
            newNote.setNoteDate(date);
            newNote.setNoteContent(content);
            return calendarNoteRepository.save(newNote);  // Save the new note

        }
    }

        @Override
        public CalendarNote getNoteByDate (LocalDate date){
            return calendarNoteRepository.findByNoteDate(date);
        }

}
