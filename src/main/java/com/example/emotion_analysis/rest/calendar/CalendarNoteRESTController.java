package com.example.emotion_analysis.rest.calendar;

import com.example.emotion_analysis.dao.CalendarNoteRepository;
import com.example.emotion_analysis.entity.CalendarNote;
import com.example.emotion_analysis.service.calendar.CalendarNoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;


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
    public ResponseEntity<List<String>> getRecords(@RequestParam("date") String date) {
        LocalDate noteDate = LocalDate.parse(date);
        List<CalendarNote> notes = calendarNoteService.getNotesByDate(noteDate);

        // Debugging: print notes
        notes.forEach(note -> System.out.println("Note: " + note));

        // Format the response
        List<String> formattedRecords = notes.stream()
                .map(note -> "Name: " + note.getName() +
                        " - Time: " + note.getNoteContent() +
                        " - Email: " + note.getEmail())
                .collect(Collectors.toList());

        for(String noteRecord : formattedRecords) {
            System.out.println(noteRecord);
        }

        return ResponseEntity.ok(formattedRecords);  // Return the formatted list
    }

    @DeleteMapping("/delete-record")
    public ResponseEntity<String> deleteRecord(@RequestParam("name") String name) {
        boolean deleted = calendarNoteService.deleteNoteByName(name); // Calling service to delete the note
        if (deleted) {
            return ResponseEntity.ok("Record deleted successfully");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Record not found");
        }
    }



}