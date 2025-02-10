package com.example.emotion_analysis.rest.patient;

import com.example.emotion_analysis.entity.*;
import com.example.emotion_analysis.service.location.LocationService;
import com.example.emotion_analysis.service.patient.PatientService;
import com.example.emotion_analysis.service.sentiment.SentimentService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


@Controller
@RequestMapping("/patients")
public class PatientController {

    @Autowired
    private PatientService patientService;

    @Autowired
    private LocationService locationService;

    @Autowired
    private SentimentService sentimentService;

    @Autowired
    @Value("${genders}")
    private List<String> genders;

    @Autowired
    @Value("${cities}")
    private List<String> allCities;

    // *************************************** GET METHODS **************************************** //


    @GetMapping("/list")
    public String getAllPatients(HttpSession session,Model model) {
        System.out.println("\n\ni am in patientController\n\n");

        User user = (User) session.getAttribute("user"); // Retrieve the user from the session
        if (user != null) {
            String role = user.getRole();
            model.addAttribute("role", role);
            List<Patient> allPatients = patientService.findAllPatientsOrderByLastnameAsc();

            model.addAttribute("patients", allPatients);

            if (role.equals("ADMIN")) {
                return "patients/editPatient"; // Το όνομα της σελίδας που θα εμφανίσει τη λίστα
            }
            else if (role.equals("USER")) {
                return "patients/patients";
            }
        }
        model.addAttribute("error", "User not logged in. Please login to access this resource.");
        return "loginForm";
    }

    @GetMapping("/addPatient")
    public String addPatient(HttpSession session,Model model) {
        User user = (User) session.getAttribute("user"); // Retrieve the user from the session

        if (user != null) {
            String userRole = user.getRole();
            if (userRole.equals("ADMIN")) {
                Patient thePatient = new Patient();
                model.addAttribute("patient", thePatient);
                model.addAttribute("genders", genders);
                model.addAttribute("cities", allCities);
                model.addAttribute("role", "userRole");

                return "patients/newPatient-form";
            }
        }
        model.addAttribute("error", "User not logged in. Please login to access this resource.");
        return "loginForm";
    }

    @GetMapping("/showFormForUpdate")
    public String showFormForUpdate(@RequestParam("patientId") int theId, Model model, HttpSession session) {

        User user = (User) session.getAttribute("user");
        String role = user.getRole();
        if (role.equals("ADMIN")) {
            Patient thePatient = patientService.findById(theId);

            thePatient.setLocation(null);
            model.addAttribute("patient", thePatient);
            model.addAttribute("genders", genders);
            model.addAttribute("cities", allCities);
            model.addAttribute("role", "role");

            System.out.println("\n\nin showFormForUpdate\n\n");

            return "patients/updatePatient-form";
        }
        model.addAttribute("error", "User not logged in. Please login to access this resource.");
        return "loginForm";
    }

    // *************************************** UPDATE METHOD **************************************** //
    @PostMapping("/updatePatient")
    public String savePatient(@RequestParam("id") int id,
                              @RequestParam("firstName") String firstName,
                              @RequestParam("lastName") String lastName,
                              @RequestParam("age") int age,
                              @RequestParam("gender") String gender,
                              @RequestParam("location") String location,
                              @RequestParam("comment") String comment,
                              RedirectAttributes redirectAttributes, HttpSession session){

        Patient existingPatient = patientService.findById(id);

        // Find Location object by description
        Location locationOfPatient = locationService.findByCity(location);

        existingPatient.setFirstName(firstName);
        existingPatient.setLastName(lastName);
        existingPatient.setAge(age);
        existingPatient.setGender(gender);
        existingPatient.setLocation(locationOfPatient);
        existingPatient.setComment(comment);

        String emotion = sentimentService.analyzeSentiment(comment);
        Sentiment sentiment = sentimentService.findSentimentByDescription(emotion);

        int sentimentId = sentiment.getId();

        // Create and save patient with sentiment
        Set<Sentiment> sentiments = new HashSet<>();
        sentiments.add(sentiment);

        existingPatient.setSentiments(sentiments);


        patientService.save(existingPatient);
        List<Patient> allPatients = patientService.findAllPatientsOrderByLastnameAsc();
        redirectAttributes.addFlashAttribute("successMessage", "Patient updated successfully!!");

        return "redirect:/patients/list"; // Redirect to the patient list or appropriate page
    }

    // *********************************************************************************************** //


    // *************************************** DELETE METHOD **************************************** //
    @GetMapping("/deletePatient")
    public String deletePatient(@RequestParam("patientId") int theId,RedirectAttributes redirectAttributes) {

        patientService.delete(theId);
        redirectAttributes.addFlashAttribute("successMessage", "Patient deleted successfully!!");
        return "redirect:/patients/list";

    }





// *************************************** DTO METHODS **************************************** //


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

}