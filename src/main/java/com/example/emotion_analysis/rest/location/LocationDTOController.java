package com.example.emotion_analysis.rest.location;

import com.example.emotion_analysis.dto.LocationDTO;
import com.example.emotion_analysis.entity.Location;
import com.example.emotion_analysis.service.location.LocationService;
import com.example.emotion_analysis.service.psychologists.PsychologistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;
@RestController
@RequestMapping("/api/locations")
public class LocationDTOController {

    @Autowired
    private LocationService locationService;

    @Autowired
    private PsychologistService psychologistService;

    // GET all locations
    @GetMapping
    public List<LocationDTO> getAllLocations() {
        List<Location> locations = locationService.findAllLocations();
        return locations.stream()
                .map(location -> new LocationDTO(location.getId(), location.getCity()))
                .collect(Collectors.toList());
    }

    // GET all locations where there is a psychologist with a specific specialization
    @GetMapping("/psychologists/{specialization}")
    public List<LocationDTO> getLocationsBySpecialization(@PathVariable String specialization) {
        // Fetch all locations with psychologists having the given specialization
        List<Location> locationsBySpecialization = locationService.findLocationsBySpecialization(specialization);

        // Check if any locations are found; if not, throw an exception
        if (locationsBySpecialization.isEmpty()) {
            throw new RuntimeException("No locations found with psychologists specialized in: " + specialization);
        }

        // Map the locations to DTOs and return
        return locationsBySpecialization.stream()
                .map(location -> new LocationDTO(location.getId(), location.getCity()))
                .collect(Collectors.toList());
    }
}

