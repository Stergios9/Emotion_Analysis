package com.example.emotion_analysis.rest.patient;

import com.example.emotion_analysis.dao.PatientRepository;
import com.example.emotion_analysis.entity.Patient;
import com.example.emotion_analysis.service.patient.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/patients")
public class PatientController {

    @Autowired
    private PatientService patientService;
    @Autowired
    private PatientRepository patientRepository;


    // *************************************** GET METHODS **************************************** //

    @GetMapping
    public String getAllPatients(Model model) {
        List<Patient> allPatients = patientService.findAllPatients();
        model.addAttribute("patients", allPatients); // Προσθήκη της λίστας στο μοντέλο
        return "patients"; // Το όνομα της σελίδας που θα εμφανίσει τη λίστα
    }

    @GetMapping("/id/{patientId}")
    public String getPatientById(@PathVariable("patientId") int patientId, Model model) {

        if (patientId <= 0) {
            throw new RuntimeException("\n\n\n*** Give a valid patient id ***\n\n\n");
        }
        Patient tempPatient = patientService.findById(patientId);
        if (tempPatient == null) {
            model.addAttribute("error", "The patient does not exist");
            return "error";
        }
        model.addAttribute("patient", tempPatient);
        return "patient";
    }

    @GetMapping("/city/{city}")
    public String getPatientByCity(@PathVariable("city") String city, Model model) {
        List<Patient> patientsByCity = new ArrayList<>();
        List<Patient> allPatients = patientService.findAllPatients();

        for (Patient patient : allPatients) {
            if (patient.getLocation().getCity().equalsIgnoreCase(city)) {
                patientsByCity.add(patient);
            }
        }
        if (patientsByCity.isEmpty()) {
            model.addAttribute("error", "No patients found in the specified city.");
            return "error";
        }
        model.addAttribute("patients", patientsByCity);  // Use "patients" to handle a list
        return "patients";  // The name of the page that displays the list of patients by city
    }

    @GetMapping("/sentiment/{description}")
    public String getPatientsBySentiment(@PathVariable("description") String description, Model model) {
        List<Patient> patients = patientService.findPatientsBySentiment(description);

        if (patients.isEmpty()) {
            model.addAttribute("message", "\n\n*****No patients found with " + description + " sentiment.*****\n\n\n");;
            return "error";
        }
        model.addAttribute("patients", patients);
        return "patients"; // Ensure this view displays the list of patients.
    }

    @GetMapping("/lastname/{lastname}")
    public String getPatientsByLastname(@PathVariable("lastname") String lastname, Model model) {
        List<Patient>   patientsWithSameLastName = patientService.findPatientByLastName(lastname);
        if (patientsWithSameLastName.isEmpty()) {
            model.addAttribute("message", "Patients with last name '"+lastname+"' does not exist");
        }
        model.addAttribute("patients", patientsWithSameLastName);
        return "patients";
    }

    @GetMapping("/gender/{typeOfGender}")
    public String getPatientsByGender(@PathVariable("typeOfGender") String typeOfGender, Model model) {
        List<Patient> patientsByGender = patientService.findPatientsByGender(typeOfGender);
        if (patientsByGender.isEmpty()) {
            model.addAttribute("message", "No patients found with " + typeOfGender + " gender.");
        }
        model.addAttribute("patients", patientsByGender);
        return "patients";
    }

    // *********************************************************************************************** //
    // *************************************** DELETE METHODS **************************************** //

    @DeleteMapping("/delete/id/{patientId}")
    @ResponseBody
    public String deletePatientById(@PathVariable int patientId) {
        Patient thePatient = patientService.findById(patientId);
        if (thePatient == null) {
            throw new RuntimeException("Patient with id '" + patientId+"' not found");
        }
        return patientService.delete(patientId);
    }

    @DeleteMapping("/delete/lastname/{lastname}")
    @ResponseBody
    public String deletePatientByLastName(@PathVariable String lastname) {
        List<Patient> patients = patientService.findPatientByLastName(lastname);
        if (patients.isEmpty()) {
            throw new RuntimeException("No patients found with last name '" + lastname + "'.");
        }
        for (Patient patient : patients) {
            patientService.delete(patient.getId());
        }
        return "Deleted " + patients.size() + " patient(s) with last name '" + lastname + "'.";
    }

    // *********************************************************************************************** //

    // *************************************** UPDATE METHODS **************************************** //

    @PutMapping("/update-lastname/{id}")
    public ResponseEntity<String> updatePatientLastName(
            @PathVariable int id,
            @RequestParam String lastname) {
        try {
            patientService.updatePatientLastName(id, lastname);
            return ResponseEntity.ok("Last name updated successfully for patient with ID: " + id);
        } catch (RuntimeException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
        }
    }

    @PutMapping("/update-firstname/{id}")
    public ResponseEntity<String> updatePatientFirstName(
            @PathVariable int id,
            @RequestParam String firstname) {
        try {
            patientService.updatePatientFirstName(id, firstname);
            return ResponseEntity.ok("First name updated successfully for patient with ID: " + id);
        } catch (RuntimeException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
        }
    }

    // *********************************************************************************************** //
}
