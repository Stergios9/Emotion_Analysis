package com.example.emotion_analysis.rest.calendar;

import com.example.emotion_analysis.entity.*;
import com.example.emotion_analysis.service.calendar.CalendarNoteService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Controller
@RequestMapping("/appointments")
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

    @GetMapping("/list")
    public String getAllAppointments(HttpSession session,Model model) {
        User user = (User) session.getAttribute("user"); // Retrieve the user from the session
        if (user != null) {
            String role = user.getRole();
            model.addAttribute("role", role);
            List<CalendarNote> allAppointments = calendarNoteService.findAllAppointments();
            System.out.println("\n\nAppointments");
            for (CalendarNote appointment : allAppointments) {
                System.out.println("Name: " + appointment.getName()+"\nLastname: "+appointment.getLastName()+"\n" +
                        "Date: "+appointment.getNoteDate()+"\nTime: "+appointment.getEmail()+"\nEmail: "+appointment.getEmail());
                System.out.println();
            }

            model.addAttribute("appointments", allAppointments);

            return "appointments/editAppointments"; // Το όνομα της σελίδας που θα εμφανίσει τη λίστα

        }
        model.addAttribute("error", "User not logged in. Please login to access this resource.");
        return "loginForm";
    }

    @GetMapping("/addAppointment")
    public String addAppointment(HttpSession session,Model model) {
        User user = (User) session.getAttribute("user"); //
        System.out.println("\n\n\ni am in addPsychologist\n\n");

        if (user != null) {
            String userRole = user.getRole();
            if (userRole.equals("ADMIN")) {
                model.addAttribute("appointment", new CalendarNote());
                model.addAttribute("role", "userRole");
                return "appointments/newAppointment";
            }
        }
        model.addAttribute("error", "User not logged in. Please login to access this resource.");
        return "loginForm";
    }

    @PostMapping("/add")
    public String addAppointment(@ModelAttribute("appointment") CalendarNote theAppointment,
                                 HttpSession session,
                                 RedirectAttributes redirectAttributes) {

        // Retrieve user from session
        User user = (User) session.getAttribute("user");
        String role = user.getRole();

        if (role.equals("ADMIN")) {

            calendarNoteService.saveNote(theAppointment);

            System.out.println("\n\nAppointment saved!!\n\n");


            List<CalendarNote> allCalendarNotes = calendarNoteService.findAllAppointments();

            redirectAttributes.addFlashAttribute("appointments", allCalendarNotes);

            redirectAttributes.addFlashAttribute("role", user.getRole());
            redirectAttributes.addFlashAttribute("successMessage","Appointment saved successfully!!");

            return "redirect:/appointments/list";
        }

        redirectAttributes.addFlashAttribute("error", "User not logged in. Please log in to access this resource.");
        return "redirect:/";
    }

    @GetMapping("/update-form")
    public String showFormForUpdate(@RequestParam("appointmentId") int theId, Model model, HttpSession session) {

        User user = (User) session.getAttribute("user");
        String role = user.getRole();

        if (role.equals("ADMIN")) {
            CalendarNote theCalendarNote = calendarNoteService.findById(theId);

            model.addAttribute("appointment", theCalendarNote);
            model.addAttribute("role", "role");
            return "appointments/updateAppointment-form";
        }
        model.addAttribute("error", "User not logged in. Please login to access this resource.");
        return "loginForm";
    }

    @PostMapping("/updateAppointment")
    public String saveUpdatedAppointment(@ModelAttribute("appointment") CalendarNote updatedAppointment,
                                         RedirectAttributes redirectAttributes, HttpSession session){

        User user = (User) session.getAttribute("user");
        if (user.getRole().equals("ADMIN")) {
            if (updatedAppointment.getNoteDate() != null) {
                calendarNoteService.saveNote(updatedAppointment);
                List<CalendarNote> allCalendarNotes = calendarNoteService.findAllAppointments();
                redirectAttributes.addFlashAttribute("successMessage", "Appointment updated successfully!!");

                return "redirect:/appointments/list"; // Redirect to the patient list or appropriate page
            }
            redirectAttributes.addFlashAttribute("error", "Appointment missing fields");
            return "loginForm";

        }
        redirectAttributes.addFlashAttribute("error", "Not have the rights to Update. Please, sign in as Admin");
        return "loginForm";

    }
    @GetMapping("/delete-form")
    public String deleteAppointment(@RequestParam("appointmentId") int theId,  RedirectAttributes redirectAttributes) {

        calendarNoteService.deleteNoteById(theId);
        redirectAttributes.addFlashAttribute("successMessage", "Appointment deleted successfully!!");
        return "redirect:/appointments/list";

    }

}