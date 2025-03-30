package com.example.emotion_analysis.rest.patient;

import com.example.emotion_analysis.dto.PatientDTO;
import com.example.emotion_analysis.entity.*;
import com.example.emotion_analysis.service.emotionAnalysis.EmotionAnalysisService;
import com.example.emotion_analysis.service.location.LocationService;
import com.example.emotion_analysis.service.patient.PatientService;
import com.example.emotion_analysis.service.psychologists.PsychologistService;
import com.example.emotion_analysis.service.sentiment.SentimentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/patients")
public class PatientDTOController {

//    @Autowired
//    private PatientService patientService;
//
//    @Autowired
//    private SentimentService sentimentService;
//
//    @Autowired
//    private LocationService locationService;
//
//    @Autowired
//    private EmotionAnalysisService emotionAnalysisService;
//
//    @Autowired
//    private PsychologistService psychologistService;
//
//    // GET all patients
//    @GetMapping
//    public List<PatientDTO> getAllPatients() {
//        List<Patient> patients = patientService.findAllPatients();
//        return patients.stream()
//                .map(patient -> new PatientDTO(patient.getId(), patient.getFirstName(), patient.getLastName(), patient.getGender()))
//                .collect(Collectors.toList());
//    }
//
//    // GET patient by ID
//    @GetMapping("/{patientId}")
//    public PatientDTO getPatientById(@PathVariable int patientId) {
//        Patient patient = patientService.findById(patientId);
//        if (patient == null) {
//            throw new RuntimeException("Patient not found");
//        }
//        return new PatientDTO(patient.getId(), patient.getFirstName(), patient.getLastName(), patient.getGender());
//    }
//
//    // GET patients by city
//    @GetMapping("/city/{city}")
//    public List<PatientDTO> getPatientsByCity(@PathVariable String city) {
//        List<Patient> allPatients = patientService.findAllPatients();
//        List<Patient> patientsByCity = allPatients.stream()
//                .filter(patient -> patient.getLocation().getCity().equalsIgnoreCase(city))
//                .collect(Collectors.toList());
//
//        if (patientsByCity.isEmpty()) {
//            throw new RuntimeException("No patients found in the specified city");
//        }
//
//        return patientsByCity.stream()
//                .map(patient -> new PatientDTO(patient.getId(), patient.getFirstName(), patient.getLastName(), patient.getGender()))
//                .collect(Collectors.toList());
//    }
//
//    // GET patients by sentiment
//    @GetMapping("/sentiment/{description}")
//    public List<PatientDTO> getPatientsBySentiment(@PathVariable String description) {
//        List<Patient> patients = patientService.findPatientsBySentiment(description);
//
//        if (patients.isEmpty()) {
//            throw new RuntimeException("No patients found with sentiment: " + description);
//        }
//
//        return patients.stream()
//                .map(patient -> new PatientDTO(patient.getId(), patient.getFirstName(), patient.getLastName(), patient.getGender()))
//                .collect(Collectors.toList());
//    }
//
//    // GET patients by last name
//    @GetMapping("/lastname/{lastname}")
//    public List<PatientDTO> getPatientsByLastname(@PathVariable String lastname) {
//        List<Patient> patients = patientService.findPatientByLastName(lastname);
//
//        if (patients.isEmpty()) {
//            throw new RuntimeException("No patients found with last name: " + lastname);
//        }
//
//        return patients.stream()
//                .map(patient -> new PatientDTO(patient.getId(), patient.getFirstName(), patient.getLastName(), patient.getGender()))
//                .collect(Collectors.toList());
//    }
//
//    // GET patients by gender
//    @GetMapping("/gender/{typeOfGender}")
//    public List<PatientDTO> getPatientsByGender(@PathVariable String typeOfGender) {
//        List<Patient> patients = patientService.findPatientsByGender(typeOfGender);
//
//        if (patients.isEmpty()) {
//            throw new RuntimeException("No patients found with gender: " + typeOfGender);
//        }
//
//        return patients.stream()
//                .map(patient -> new PatientDTO(patient.getId(), patient.getFirstName(), patient.getLastName(), patient.getGender()))
//                .collect(Collectors.toList());
//    }
//
//    // DELETE patient by ID
//    @DeleteMapping("/delete/{patientId}")
//    public String deletePatientById(@PathVariable int patientId) {
//        Patient thePatient = patientService.findById(patientId);
//        if (thePatient == null) {
//            throw new RuntimeException("Patient with id " + patientId + " not found");
//        }
//        return patientService.delete(patientId);
//    }
//    @PostMapping("/addPatient")
//    public ResponseEntity<?> addPatientByAdmin(@RequestParam("firstName") String firstName,
//                                               @RequestParam("lastName") String lastName,
//                                               @RequestParam("age") int age,
//                                               @RequestParam("gender") String gender,
//                                               @RequestParam("location") String location,
//                                               @RequestParam("comment") String comment) {
//        try {
//            // Find Location object by description
//            Location locationOfPatient = locationService.findByCity(location);
//            if (locationOfPatient == null) {
//                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid location: " + location);
//            }
//
//            // Analyze sentiment from the comment
//            String emotion = sentimentService.analyzeSentiment(comment);
//            Sentiment sentiment = sentimentService.findSentimentByDescription(emotion);
//            if (sentiment == null) {
//                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid sentiment for the given comment.");
//            }
//
//            // Create a new patient and associate sentiment
//            Set<Sentiment> sentiments = new HashSet<>();
//            sentiments.add(sentiment);
//            Patient patient = new Patient(firstName, lastName, age, gender, locationOfPatient, comment, LocalDateTime.now(), sentiments);
//
//            // Save the patient entity
//            patientService.save(patient);
//            return ResponseEntity.status(HttpStatus.CREATED).body(patient); // Return the created Patient entity
//
//        } catch (Exception ex) {
//            // Log the error and return a generic error response
//            ex.printStackTrace();
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while adding the patient.");
//        }
//    }

}

