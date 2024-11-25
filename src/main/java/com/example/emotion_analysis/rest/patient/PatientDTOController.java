package com.example.emotion_analysis.rest.patient;

import com.example.emotion_analysis.dto.PatientDTO;
import com.example.emotion_analysis.entity.Patient;
import com.example.emotion_analysis.service.patient.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/patients")
public class PatientDTOController {

    @Autowired
    private PatientService patientService;

    // GET all patients
    @GetMapping
    public List<PatientDTO> getAllPatients() {
        List<Patient> patients = patientService.findAllPatients();
        return patients.stream()
                .map(patient -> new PatientDTO(patient.getId(), patient.getFirstName(), patient.getLastName(), patient.getGender()))
                .collect(Collectors.toList());
    }

    // GET patient by ID
    @GetMapping("/{patientId}")
    public PatientDTO getPatientById(@PathVariable int patientId) {
        Patient patient = patientService.findById(patientId);
        if (patient == null) {
            throw new RuntimeException("Patient not found");
        }
        return new PatientDTO(patient.getId(), patient.getFirstName(), patient.getLastName(), patient.getGender());
    }

    // GET patients by city
    @GetMapping("/city/{city}")
    public List<PatientDTO> getPatientsByCity(@PathVariable String city) {
        List<Patient> allPatients = patientService.findAllPatients();
        List<Patient> patientsByCity = allPatients.stream()
                .filter(patient -> patient.getLocation().getCity().equalsIgnoreCase(city))
                .collect(Collectors.toList());

        if (patientsByCity.isEmpty()) {
            throw new RuntimeException("No patients found in the specified city");
        }

        return patientsByCity.stream()
                .map(patient -> new PatientDTO(patient.getId(), patient.getFirstName(), patient.getLastName(), patient.getGender()))
                .collect(Collectors.toList());
    }

    // GET patients by sentiment
    @GetMapping("/sentiment/{description}")
    public List<PatientDTO> getPatientsBySentiment(@PathVariable String description) {
        List<Patient> patients = patientService.findPatientsBySentiment(description);

        if (patients.isEmpty()) {
            throw new RuntimeException("No patients found with sentiment: " + description);
        }

        return patients.stream()
                .map(patient -> new PatientDTO(patient.getId(), patient.getFirstName(), patient.getLastName(), patient.getGender()))
                .collect(Collectors.toList());
    }

    // GET patients by last name
    @GetMapping("/lastname/{lastname}")
    public List<PatientDTO> getPatientsByLastname(@PathVariable String lastname) {
        List<Patient> patients = patientService.findPatientByLastName(lastname);

        if (patients.isEmpty()) {
            throw new RuntimeException("No patients found with last name: " + lastname);
        }

        return patients.stream()
                .map(patient -> new PatientDTO(patient.getId(), patient.getFirstName(), patient.getLastName(), patient.getGender()))
                .collect(Collectors.toList());
    }

    // GET patients by gender
    @GetMapping("/gender/{typeOfGender}")
    public List<PatientDTO> getPatientsByGender(@PathVariable String typeOfGender) {
        List<Patient> patients = patientService.findPatientsByGender(typeOfGender);

        if (patients.isEmpty()) {
            throw new RuntimeException("No patients found with gender: " + typeOfGender);
        }

        return patients.stream()
                .map(patient -> new PatientDTO(patient.getId(), patient.getFirstName(), patient.getLastName(), patient.getGender()))
                .collect(Collectors.toList());
    }

    // DELETE patient by ID
    @DeleteMapping("/delete/{patientId}")
    public String deletePatientById(@PathVariable int patientId) {
        Patient thePatient = patientService.findById(patientId);
        if (thePatient == null) {
            throw new RuntimeException("Patient with id " + patientId + " not found");
        }
        return patientService.delete(patientId);
    }
}

