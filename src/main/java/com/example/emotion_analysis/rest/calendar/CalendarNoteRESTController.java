package com.example.emotion_analysis.rest.calendar;

import com.example.emotion_analysis.entity.CalendarNote;
import com.example.emotion_analysis.service.calendar.CalendarNoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/calendar")
public class CalendarNoteRESTController {

    @Autowired
    private CalendarNoteService calendarNoteService;

    // Endpoint to add or update a note
    @PostMapping("/add-note")
    public void addNote(@RequestParam String date, @RequestParam String content) {
        LocalDate noteDate = LocalDate.parse(date);
        calendarNoteService.saveOrUpdateNote(noteDate, content);
    }

    // Endpoint to get a note by date
    @GetMapping("/get-note")
    public String getNote(@RequestParam String date) {
        LocalDate noteDate = LocalDate.parse(date);
        CalendarNote note = calendarNoteService.getNoteByDate(noteDate);
        return note != null ? note.getNoteContent() : "No notes for this day.";
    }
}