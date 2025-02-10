package com.example.emotion_analysis.rest;

import com.example.emotion_analysis.entity.*;
import com.example.emotion_analysis.service.emotionAnalysis.EmotionAnalysisServiceImpl;
import com.example.emotion_analysis.service.location.LocationServiceImpl;
import com.example.emotion_analysis.service.patient.PatientService;
import com.example.emotion_analysis.service.psychologists.PsychologistServiceImpl;
import com.example.emotion_analysis.service.sentiment.SentimentService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


@CrossOrigin(origins = "http://192.168.1.203:8080/")
@Controller
public class SentimentAnalysisRestController {

    @Autowired
    private PatientService patientService;

    @Autowired
    private SentimentService sentimentService;

    @Autowired
    private EmotionAnalysisServiceImpl emotionAnalysisService;

    @Autowired
    private LocationServiceImpl locationService;

    @Autowired
    private PsychologistServiceImpl psychologistService;


    @PostMapping("/medicalDiagnosis")
    public String submitPatient(@RequestParam("firstName") String firstName,
                                @RequestParam("lastName") String lastName,
                                @RequestParam("age") int age,
                                @RequestParam("gender") String gender,
                                @RequestParam("location") String location,
                                @RequestParam("comment") String comment,
                                Model model, HttpSession session) {

        // ✅ Ensure user exists in session
        User user = (User) session.getAttribute("user");
        String form = "";

        if (user == null) {
            user = new User(); // Create new user if session expired
            session.setAttribute("user", user);
            form = "loginForm";
        }else{
            form = "new2";
        }

        // ✅ Add user to model (prevents Thymeleaf errors)
        model.addAttribute("user", user);

        // ✅ Ensure role is always available
        String role = (user.getRole() != null) ? user.getRole() : "USER";
        model.addAttribute("role", role);

        try {
            // Find Location object by description
            Location locationOfPatient = locationService.findByCity(location);

            // Find emotion from database by the inserted comment
            String emotion = sentimentService.analyzeSentiment(comment);
            Sentiment sentiment = sentimentService.findSentimentByDescription(emotion);

            emotion = emotion.replace("_", " - ");
            if (sentiment == null) {
                model.addAttribute("error", "Sentiment not found for description: " + emotion);
                return "error"; // Return an error page
            }

            int sentimentId = sentiment.getId();

            // Create and save patient with sentiment
            Set<Sentiment> sentiments = new HashSet<>();
            sentiments.add(sentiment);

            Patient patient = new Patient(firstName, lastName, age, gender, locationOfPatient, comment, LocalDateTime.now(), sentiments);
            patientService.save(patient);

            // Find diagnosis & relaxation techniques
            String medicalDiagnosis = emotionAnalysisService.getDiagnosisBySentimentId(sentimentId);
            String relaxationTechniques = emotionAnalysisService.getRelaxationTechniqueBySentimentId(sentimentId);

            // Find psychologists based on location
            List<Psychologist> allPsychologists = psychologistService.findPsychologistsByLocation(locationOfPatient);

            // ✅ Add attributes to model
            model.addAttribute("firstName", firstName);
            model.addAttribute("lastName", lastName);
            model.addAttribute("emotion", emotion);
            model.addAttribute("diagnosis", medicalDiagnosis);
            model.addAttribute("techniques", relaxationTechniques);
            model.addAttribute("professionals", allPsychologists);

            System.out.println("******** All attributes added ********");

            // Redirect based on role
            if ("ADMIN".equals(role)) {
                List<Patient> allPatients = patientService.findAllPatientsOrderByLastnameAsc();
                model.addAttribute("successMessage", "Patient saved successfully!!");
                model.addAttribute("patients", allPatients);
                return "patients/editPatient";
            }

            return form; // Success page

        } catch (Exception e) {
            // Log error
            e.printStackTrace();
            model.addAttribute("error", "An unexpected error occurred. Please try again.");
            return "loginForm"; // Redirect to login on error
        }
    }

}