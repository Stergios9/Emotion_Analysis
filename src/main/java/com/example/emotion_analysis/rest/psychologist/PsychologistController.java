package com.example.emotion_analysis.rest.psychologist;

import com.example.emotion_analysis.entity.Location;
import com.example.emotion_analysis.entity.Patient;
import com.example.emotion_analysis.entity.Psychologist;
import com.example.emotion_analysis.entity.User;
import com.example.emotion_analysis.service.location.LocationServiceImpl;
import com.example.emotion_analysis.service.psychologists.PsychologistServiceImpl;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Controller
@RequestMapping("/psychologists")
public class PsychologistController {

    @Autowired
    private PsychologistServiceImpl psychologistService;

    @Autowired
    private LocationServiceImpl locationService;

    @Autowired
    @Value("${cities}")
    private List<String> allCities;

    // ************************************ DELETE METHODS ************************************ //

    @DeleteMapping("/delete/id/{psychologistId}")
    @ResponseBody
    public String deletePsychologisttById(@PathVariable int psychologistId) {
        Psychologist thePsychologist = psychologistService.findById(psychologistId);
        if (thePsychologist == null) {
            throw new RuntimeException("Patient with id '" + psychologistId+"' not found");
        }
        return psychologistService.delete(psychologistId);
    }

    @DeleteMapping("/delete/name/{name}")
    @ResponseBody
    public String deletePsychologistByLastName(@PathVariable String name) {
        List<Psychologist> psychologists = psychologistService.findPsychologistsByName(name);
        if (psychologists.isEmpty()) {
            throw new RuntimeException("No patients found with last name '" + name + "'.");
        }
        for (Psychologist psychologist : psychologists) {
            psychologistService.delete(psychologist.getId());
        }
        return "Deleted " + psychologists.size() + " patient(s) with last name '" + name + "'.";
    }

    // *********************************************************************************************** //

    // *************************************** GET METHODS ******************************************* //


    @GetMapping
    public String getAllPsychologists(HttpSession session, Model model) {
        User user = (User) session.getAttribute("user"); // Retrieve the user from the session

        if (user != null) {
            String role = user.getRole();
            model.addAttribute("role", role);
            List<Psychologist> allPsychologists = psychologistService.findAllPsychologists();
            model.addAttribute("psychologists", allPsychologists);
            return "psychologists";
        }
        model.addAttribute("error", "User not logged in. Please login to access this resource.");
        return "loginForm";
    }

    @GetMapping("/list")
    public String getAllPatients(HttpSession session,Model model) {
        User user = (User) session.getAttribute("user"); // Retrieve the user from the session
        if (user != null) {
            String role = user.getRole();
            model.addAttribute("role", role);
            List<Psychologist> allPsychologists = psychologistService.findAllPsychologistsByNameAsc();

            model.addAttribute("psychologists", allPsychologists);

            if (role.equals("ADMIN")) {
                return "psychologists/editPsychologist"; // Το όνομα της σελίδας που θα εμφανίσει τη λίστα
            }
            else if (role.equals("USER")) {
                return "psychologists";
            }
        }
        model.addAttribute("error", "User not logged in. Please login to access this resource.");
        return "loginForm";
    }

    @GetMapping("/psychologistForUpdate")
    public String showFormForUpdate(@RequestParam("psychologistId") int theId, Model model) {
        Psychologist thePsychologist = psychologistService.findById(theId);
        String city = thePsychologist.getLocation().getCity();
        model.addAttribute("patient", thePsychologist);
        model.addAttribute("city", city);

        return "psychologist/newPsychologist-form";
    }
    @GetMapping("/deletePsychologist")
    public String deletePsychologist(@RequestParam("psychologistId") int theId, Model model) {

        psychologistService.delete(theId);

        return "redirect:/psychologists/list";

    }
    @PostMapping("/add")
    public String addPsychologist(@RequestParam("name") String name,
                                  @RequestParam("specialization") String specialization,
                                  @RequestParam("phone") String phone,
                                  @RequestParam("email") String email,
                                  @RequestParam("location") String city,
                                  HttpSession session,
                                  Model model) {

        System.out.println("name: "+name+" specialization: "+specialization+" phone: "+phone+" email: "+email+" location: "+city);

        // Retrieve user from session
        User user = (User) session.getAttribute("user");
        String role = user.getRole();
        System.out.println("\n\n\nrole: "+role);


        if (role.equals("ADMIN")) {
            // Set the location for the psychologist
            Location location = locationService.findByCity(city);
            locationService.save(location);

            Psychologist newPsychologist = new Psychologist(name,specialization,phone,email,location);
            // Save the new psychologist
            psychologistService.addPsychologist(newPsychologist);
            System.out.println("\n\nnewPsychologist has saved\n\n");


            List<Psychologist> allPsychologists = psychologistService.findAllPsychologistsByNameAsc();

            model.addAttribute("psychologists", allPsychologists);

            model.addAttribute("role", user.getRole());
            model.addAttribute("successMessage","Patient saved successfully!!");

            return "psychologists/editPsychologist";
        }

        model.addAttribute("error", "User not logged in. Please log in to access this resource.");
        return "loginForm";
    }

    @GetMapping("/addPsychologist")
    public String addPatient(HttpSession session,Model model) {
        User user = (User) session.getAttribute("user"); // Retrieve the user from the session
        System.out.println("\n\n\ni am in addPsychologist\n\n");

        if (user != null) {
            String userRole = user.getRole();
            if (userRole.equals("ADMIN")) {
                Psychologist thePsycologist = new Psychologist();
                model.addAttribute("psychologist", thePsycologist);
                model.addAttribute("cities", allCities);
                model.addAttribute("role", "userRole");
                return "psychologists/newPsychologist-form";
            }
        }
        model.addAttribute("error", "User not logged in. Please login to access this resource.");
        return "loginForm";
    }

    @GetMapping("/city/{city}")
    public String getPsychologistsByCity(HttpSession session,Model model, @PathVariable String city) {
        User user = (User) session.getAttribute("user"); // Retrieve the user from the session
        if (user != null) {
            String role = user.getRole();
            model.addAttribute("role", role);
            List<Psychologist> psychologistsByCity = psychologistService.findPsychologistsByLocation(city);
            model.addAttribute("psychologists", psychologistsByCity);
            return "psychologists";
        }
        model.addAttribute("error", "User not logged in. Please login to access this resource.");
        return "loginForm";
    }

    @GetMapping("/speciality/{specialization}")
    public String psychologistsSpeciality(@PathVariable("specialization") String specialization,HttpSession session, Model model) {
        User user = (User) session.getAttribute("user"); // Retrieve the user from the session
        if (user != null) {
            List<Psychologist> psychologists = psychologistService.findPsychologistBySpeciality(specialization);
            if (psychologists == null) {
                model.addAttribute("message", "Psychologist with specialization " + specialization + " not found");
                return "error";
            }
            String role = user.getRole();
            model.addAttribute("role", role);
            model.addAttribute("psychologists", psychologists);
            return "psychologists";
        }
        model.addAttribute("error", "User not logged in. Please login to access this resource.");
        return "loginForm";
    }

    // ******************************************************************************************** //

    // *************************************** PUT METHODS **************************************** //

    @PutMapping("/update-email/{id}")
    public ResponseEntity<String> updatePsychologistEmail(
            @PathVariable int id,
            @RequestParam String email) {
        try {
            psychologistService.updatePsychologistEmail(id, email);
            return ResponseEntity.ok("Email updated successfully for psychologist with ID: " + id);
        } catch (RuntimeException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
        }
    }

    // ******************************************************************************************** //

    // *************************************** POST METHODS **************************************** //

}