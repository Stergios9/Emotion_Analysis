package com.example.emotion_analysis.rest.calendar;

import com.example.emotion_analysis.entity.User;
import com.example.emotion_analysis.service.calendar.CalendarNoteService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping()
public class CalendarNoteController {

    @Autowired
    private CalendarNoteService calendarNoteService;

    @GetMapping("/calendar")
    public String calendar(HttpSession session, Model model) {
        User user = (User) session.getAttribute("user"); // Retrieve the user from the session
        if (user != null) {
            String role = user.getRole(); // Get the role (e.g., "PATIENT" or "DOCTOR")
            model.addAttribute("role", role); // Pass the role to the template
            return "calendar"; // Thymeleaf will look for calendar.html in templates
        }
        model.addAttribute("error", "User not logged in. Please login to access this resource.");
        return "loginForm"; // If no user is logged in, redirect to login

    }
    @GetMapping("/calendar2")
    public String calendar2(HttpSession session, Model model) {
        User user = (User) session.getAttribute("user");
        if (user != null) {
            String role = user.getRole();
            // Use .equalsIgnoreCase() for a case-insensitive comparison
            if ("ADMIN".equalsIgnoreCase(role)) {
                model.addAttribute("role", role);
                return "calendar2";
            } else {
                model.addAttribute("error", "User must have role ADMIN");
                return "loginForm";
            }
        }
        model.addAttribute("error", "User not logged in. Please login to access this resource.");
        return "loginForm";
    }
    @GetMapping("/appoitmentsAdmin")
    public String appoitmentsAdmin(HttpSession session, Model model) {
        User user = (User) session.getAttribute("user");
        if (user != null) {
            String role = user.getRole();
            // Use .equalsIgnoreCase() for a case-insensitive comparison
            if ("ADMIN".equalsIgnoreCase(role)) {
                model.addAttribute("role", role);
                return "appoitmentsAdmin";
            } else {
                model.addAttribute("error", "User must have role ADMIN");
                return "loginForm";
            }
        }
        model.addAttribute("error", "User not logged in. Please login to access this resource.");
        return "loginForm";
    }


}