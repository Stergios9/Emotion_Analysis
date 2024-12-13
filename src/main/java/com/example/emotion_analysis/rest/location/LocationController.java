package com.example.emotion_analysis.rest.location;


import com.example.emotion_analysis.entity.User;
import com.example.emotion_analysis.service.psychologists.PsychologistService;
import com.example.emotion_analysis.service.user.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.ui.Model;
import com.example.emotion_analysis.entity.Location;
import com.example.emotion_analysis.service.location.LocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/locations")
public class LocationController {

    @Autowired
    private LocationService locationService;

    @Autowired
    private UserService userService;

    @Autowired
    private PsychologistService psychologistService;

    @GetMapping
    public String getAllLocations(HttpSession session,Model model) {
        User user = (User) session.getAttribute("user");

        if(user != null) {
            List<Location> allLocations = locationService.findAllLocations();
            model.addAttribute("locations", allLocations);
            return "locations";
        }
        model.addAttribute("error", "Please login");
        return "loginForm";
    }

    // ****** GET all Locations where exists a Psychologist with a specific speciality ******
    @GetMapping("/psychologists/{specialization}")
    public String getLocationsBySpecialization(@PathVariable("specialization") String specialization,HttpSession session,Model model) {

        User user = (User) session.getAttribute("user");
        if(user != null) {
            String role = user.getRole();
            model.addAttribute("role", role);
            List<Location> locationsBySpecialization = locationService.findLocationsBySpecialization(specialization);

            // Check if the list of locations is empty
            if (locationsBySpecialization.isEmpty()) {
                model.addAttribute("error", "There are no psychologists with this specialization");
                return "error";
            }
            // Add the list of locations to the model
            model.addAttribute("locations", locationsBySpecialization);
            return "locations"; // Return the view to display the locations
        }
        model.addAttribute("error", "User not logged in. Please login to access this resource.");
        return "loginForm";
    }
}
//    @GetMapping("/psychologists/{specialization}")
//    public String getLocationsBySpecialization(@PathVariable("specialization") String specialization,Model model) {
//
//        List<Location> locationsBySpecialization = locationService.findLocationsBySpecialization(specialization);
//        // Check if the list of locations is empty
//        if (locationsBySpecialization.isEmpty()) {
//            model.addAttribute("error", "There are no Locations");
//            return "welcome"; // Return error view if no locations found
//        }
//        // Add the list of locations to the model
//        model.addAttribute("locations", locationsBySpecialization);
//        return "locations"; // Return the view to display the locations
//    }


