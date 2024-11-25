package com.example.emotion_analysis.rest.location;


import com.example.emotion_analysis.service.psychologists.PsychologistService;
import org.springframework.ui.Model;
import com.example.emotion_analysis.entity.Location;
import com.example.emotion_analysis.service.location.LocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/locations")
public class LocationController {

    @Autowired
    private LocationService locationService;

    @Autowired
    private PsychologistService psychologistService;

    @GetMapping
    public String getAllLocations(Model model) {
        List<Location> allLocations = locationService.findAllLocations();
        model.addAttribute("locations", allLocations); // Προσθήκη της λίστας στο μοντέλο
        return "locations";
    }

    // ****** GET all Locations where exists a Psychologist with a specific speciality ******
    @GetMapping("/psychologists/{specialization}")
    public String getLocationsBySpecialization(@PathVariable("specialization") String specialization, Model model) {
        // Fetch all locations that have at least one psychologist with the given specialization
        List<Location> locationsBySpecialization = locationService.findLocationsBySpecialization(specialization);

        // Check if the list of locations is empty
        if (locationsBySpecialization.isEmpty()) {
            return "error"; // Return error view if no locations found
        }

        // Add the list of locations to the model
        model.addAttribute("locations", locationsBySpecialization);
        return "locations"; // Return the view to display the locations
    }

}
