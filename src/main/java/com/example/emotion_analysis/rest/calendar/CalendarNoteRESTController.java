package com.example.emotion_analysis.rest.calendar;

import com.example.emotion_analysis.dao.CalendarNoteRepository;
import com.example.emotion_analysis.entity.CalendarNote;
import com.example.emotion_analysis.service.calendar.CalendarNoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/calendar")
public class CalendarNoteRESTController {

    @Autowired
    private CalendarNoteRepository calendarNoteRepository;

    @Autowired
    private CalendarNoteService calendarNoteService;


    // Endpoint to add or update a note
    @PostMapping("/add-note")
    public void addNote(@RequestParam String date, @RequestParam String content,
                        @RequestParam String name,@RequestParam String email) {
        LocalDate noteDate = LocalDate.parse(date);
        calendarNoteService.saveNote(noteDate, content,name,email);
    }

    @GetMapping("/get-records")
    public List<CalendarNote> getRecords(@RequestParam String date) {
        LocalDate parsedDate = LocalDate.parse(date);
        System.out.println("\n\nReceived date: " + parsedDate);  // Log the received date
        return calendarNoteRepository.findByNoteDate(parsedDate);
    }

}