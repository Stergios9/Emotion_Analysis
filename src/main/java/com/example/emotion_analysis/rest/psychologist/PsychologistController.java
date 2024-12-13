package com.example.emotion_analysis.rest.psychologist;

import com.example.emotion_analysis.entity.Location;
import com.example.emotion_analysis.entity.Psychologist;
import com.example.emotion_analysis.entity.User;
import com.example.emotion_analysis.service.location.LocationServiceImpl;
import com.example.emotion_analysis.service.psychologists.PsychologistServiceImpl;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
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

    @PersistenceContext
    private EntityManager entityManager;

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

    @PostMapping("/add")
    @ResponseBody
    public Psychologist addPsychologist(@RequestParam("name") String newName,
                                        @RequestParam("specialization") String newSpecialization,
                                        @RequestParam("phone") String newPhone,
                                        @RequestParam("email") String newEmail,
                                        @RequestParam("city") String newCity) {
        // Check if the location already exists
        Location location = locationService.findByCity(newCity);


        // If it doesn't exist, create and save a new Location
        if (location == null) {
            System.out.println("\n\n\n Location found: " + (location != null ? location.getCity() : "null"));
            location = new Location(newCity);
            location = locationService.save(location); // Persist the new Location to make it managed
        }
        locationService.save(location);

        // Directly use the location in a managed state
        // (no need for `merge` here if the location is already managed)
        // Save the Psychologist and associate it with the managed Location
        Psychologist newPsychologist = new Psychologist(newName, newSpecialization, newPhone, newEmail, location);

        // Save the Psychologist
        return psychologistService.addPsychologist(newPsychologist);
    }
}
