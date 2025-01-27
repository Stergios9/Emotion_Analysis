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
                                Model model, HttpSession session){

        User user = (User) session.getAttribute("user");
        String role = user.getRole();

        // Find Location object by description
        Location locationOfPatient = locationService.findByCity(location);

        // Find emotion from database by the inserted comment --
        String emotion = sentimentService.analyzeSentiment(comment);

        Sentiment sentiment = sentimentService.findSentimentByDescription(emotion);  // Fetching existing Sentiment

        emotion = emotion.replace("_"," - ");
        if (sentiment == null) {
            System.out.println("Sentiment not found for description: " + emotion);
            return "error";  // or handle appropriately if sentiment is missing
        }
        else{
            System.out.println("\nSentiment description: " +emotion+ "\n");
        }
        int sentimentId = sentiment.getId();
        System.out.println("\nSentiment ID: " + sentimentId + "\n");

        // Create a new patient and add the sentiment
        Set<Sentiment> sentiments = new HashSet<>();
        sentiments.add(sentiment); // Add sentiment to the patient's sentiments
        // Create and save patient
        Patient patient = new Patient(firstName, lastName,age, gender, locationOfPatient, comment, LocalDateTime.now(), sentiments);
        patientService.save(patient);

        System.out.println("patient saved successfully\n");

        // Find diagnosi && relaxation techniques
        String medicalDiagnosis = emotionAnalysisService.getDiagnosisBySentimentId(sentimentId);
        String relaxationTechniques = emotionAnalysisService.getRelaxationTechniqueBySentimentId(sentimentId);
        System.out.println("\nDiagnosi: " + medicalDiagnosis+"\n\nRelaxation Techniques: " + relaxationTechniques + "\n");


        // Find psychologists based on location
        List<Psychologist> allPsychologists = psychologistService.findPsychologistsByLocation(locationOfPatient);
        for (Psychologist psychologists: allPsychologists){
            System.out.println(psychologists.toString() + "\n");
        }

        // Add data to model
        model.addAttribute("firstName", firstName);
        model.addAttribute("lastName", lastName);
        //model.addAttribute("gender", gender);
        //model.addAttribute("location",  locationOfPatient.getCity());
        //model.addAttribute("comment", comment);
        model.addAttribute("emotion", emotion);
        model.addAttribute("diagnosis", medicalDiagnosis);
        model.addAttribute("techniques", relaxationTechniques);
        model.addAttribute("professionals", allPsychologists);
        System.out.println("******** All attributes added ********");

        if (role.equals("ADMIN")){
            List<Patient> allPatients = patientService.findAllPatientsOrderByLastnameAsc();
            model.addAttribute("successMessage","Patient saved successfully!!");
            model.addAttribute("patients", allPatients);
            return "patients/editPatient";
        }

        return "new2";
    }
}
