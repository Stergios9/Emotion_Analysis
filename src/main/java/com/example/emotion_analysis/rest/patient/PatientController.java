package com.example.emotion_analysis.rest.patient;

import com.example.emotion_analysis.entity.Patient;
import com.example.emotion_analysis.entity.User;
import com.example.emotion_analysis.service.patient.PatientService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;


@Controller
@RequestMapping("/patients")
public class PatientController {

    @Autowired
    private PatientService patientService;

    // *************************************** GET METHODS **************************************** //

    @GetMapping
    public String getAllPatients(HttpSession session,Model model) {
        User user = (User) session.getAttribute("user"); // Retrieve the user from the session
        if (user != null) {
            String role = user.getRole();
            model.addAttribute("role", role);
            List<Patient> allPatients = patientService.findAllPatients();
            model.addAttribute("patients", allPatients); // Προσθήκη της λίστας στο μοντέλο
            return "patients"; // Το όνομα της σελίδας που θα εμφανίσει τη λίστα
        }
        model.addAttribute("error", "User not logged in. Please login to access this resource.");
        return "loginForm";
    }

    @GetMapping("/addPatient")
    public String addPatient(HttpSession session,Model model) {
        User user = (User) session.getAttribute("user"); // Retrieve the user from the session

        if (user != null) {

            String role = user.getRole();
            if (role.equals("admin")) {
                model.addAttribute("role", "admin");
                return "addPatient";
            }
        }
        model.addAttribute("error", "User not logged in. Please login to access this resource.");
        return "loginForm";
    }

    @GetMapping("/id/{patientId}")
    public String getPatientById(@PathVariable("patientId") int patientId, HttpSession session, Model model) {
        User user = (User) session.getAttribute("user"); // Retrieve the user from the session
        Patient tempPatient = patientService.findById(patientId);
        if (user != null) {
            if (patientId <= 0) {
                model.addAttribute("error", "Use only positive numbers for patient id");
                return "welcome";
            }
            if (tempPatient == null) {
                model.addAttribute("error", "The patient does not exist");
                return "welcome";
            }
            String role = user.getRole();
            model.addAttribute("role", role);
            model.addAttribute("patient", tempPatient);
            return "patient";
        }
        model.addAttribute("error", "User not logged in. Please login to access this resource.");
        return "loginForm";
    }

    @GetMapping("/city/{city}")
    public String getPatientByCity(@PathVariable("city") String city, HttpSession session, Model model) {
        User user = (User) session.getAttribute("user"); // Retrieve the user from the session
        if (user != null) {
            List<Patient> patientsByCity = new ArrayList<>();
            List<Patient> allPatients = patientService.findAllPatients();

            for (Patient patient : allPatients) {
                if (patient.getLocation().getCity().equalsIgnoreCase(city)) {
                    patientsByCity.add(patient);
                }
            }
            if (patientsByCity.isEmpty()) {
                model.addAttribute("error", "No patients found in this city.");
                return "welcome";
            }
            String role = user.getRole();
            model.addAttribute("role", role);
            model.addAttribute("patients", patientsByCity);  // Use "patients" to handle a list
            return "patients";  // The name of the page that displays the list of patients by city
        }
        model.addAttribute("error", "User not logged in. Please login to access this resource.");
        return "loginForm";
    }

    @GetMapping("/sentiment/{description}")
    public String getPatientsBySentiment(@PathVariable("description") String description, HttpSession session, Model model) {
        User user = (User) session.getAttribute("user"); // Retrieve the user from the session
        if (user != null) {
            List<Patient> patients = patientService.findPatientsBySentiment(description);

            if (patients.isEmpty()) {
                model.addAttribute("error", "*****No patients found with " + description + " sentiment.*****");;
                return "welcome";
            }
            model.addAttribute("patients", patients);
            return "patients"; // Ensure this view displays the list of patients.
        }
        model.addAttribute("error", "User not logged in. Please login to access this resource.");
        return "loginForm";
    }

    @GetMapping("/lastname/{lastname}")
    public String getPatientsByLastname(@PathVariable("lastname") String lastname, HttpSession session, Model model) {
        User user = (User) session.getAttribute("user"); // Retrieve the user from the session
        if (user != null) {
            List<Patient>   patientsWithSameLastName = patientService.findPatientByLastName(lastname);
            if (patientsWithSameLastName.isEmpty()) {
                model.addAttribute("error", "There are no any patients with lastname '"+lastname+"' ");
                return "welcome";
            }
            String role = user.getRole();
            model.addAttribute("role", role);
            model.addAttribute("patients", patientsWithSameLastName);
            return "patients";
        }
        model.addAttribute("error", "User not logged in. Please login to access this resource.");
        return "loginForm";
    }

    @GetMapping("/gender/{typeOfGender}")
    public String getPatientsByGender(@PathVariable("typeOfGender") String typeOfGender, HttpSession session, Model model) {
        User user = (User) session.getAttribute("user"); // Retrieve the user from the session
        if (user != null) {
            List<Patient> patientsByGender = patientService.findPatientsByGender(typeOfGender);
            if (patientsByGender.isEmpty()) {
                model.addAttribute("error", "No patients found with " + typeOfGender + " gender.");
                return "welcome";
            }
            String role = user.getRole();
            model.addAttribute("role", role);
            model.addAttribute("patients", patientsByGender);
            return "patients";
        }
        model.addAttribute("error", "User not logged in. Please login to access this resource.");
        return "loginForm";
    }

    // *********************************************************************************************** //
    // *************************************** DELETE METHODS **************************************** //

    @DeleteMapping("/delete/id/{patientId}")
    @ResponseBody
    public String deletePatientById(@PathVariable int patientId) {
        Patient thePatient = patientService.findById(patientId);
        System.out.println("\n\n\n Patient: " + thePatient+"\n\n");
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
