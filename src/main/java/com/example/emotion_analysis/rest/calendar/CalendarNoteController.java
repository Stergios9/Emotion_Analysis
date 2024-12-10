package com.example.emotion_analysis.rest.calendar;

import com.example.emotion_analysis.service.calendar.CalendarNoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@Controller
@RequestMapping()
public class CalendarNoteController {

    @Autowired
    private CalendarNoteService calendarNoteService;

    @GetMapping("/calendar")
    public String calendar() {
        return "calendar"; // Thymeleaf will look for calendar.html in templates
    }

    @GetMapping("/calendar2")
    public String calendar2() {
        return "calendar2"; // Thymeleaf will look for calendar.html in templates
    }



}