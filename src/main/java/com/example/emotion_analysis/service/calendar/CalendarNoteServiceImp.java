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
    public CalendarNote saveNote(LocalDate date, String content, String name, String email) {
        // Create a new note each time regardless of whether a note for that date already exists.
        CalendarNote newNote = new CalendarNote();
        newNote.setNoteDate(date);
        newNote.setNoteContent(content);  // Set note content
        newNote.setName(name);            // Set name
        newNote.setEmail(email);          // Set email
        return calendarNoteRepository.save(newNote);  // Save the new note
    }

}
