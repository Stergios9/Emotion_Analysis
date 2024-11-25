package com.example.emotion_analysis.rest.psychologist;

import com.example.emotion_analysis.dto.PsychologistDTO;
import com.example.emotion_analysis.entity.Location;
import com.example.emotion_analysis.entity.Psychologist;
import com.example.emotion_analysis.service.location.LocationService;
import com.example.emotion_analysis.service.psychologists.PsychologistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/psychologists")
public class PsychologistDTOController {

    @Autowired
    private PsychologistService psychologistService;

    @Autowired
    private LocationService locationService;

    // GET all psychologists
    @GetMapping
    public List<PsychologistDTO> getAllPsychologists() {
        List<Psychologist> psychologists = psychologistService.findAllPsychologists();
        return psychologists.stream()
                .map(psychologist -> new PsychologistDTO(
                        psychologist.getId(),
                        psychologist.getName(),
                        psychologist.getSpecialization(),
                        psychologist.getPhone(),
                        psychologist.getEmail()))
                .collect(Collectors.toList());
    }

    // GET psychologists by specialization
    @GetMapping("/speciality/{specialization}")
    public List<PsychologistDTO> getPsychologistsBySpeciality(@PathVariable String specialization) {
        List<Psychologist> psychologists = psychologistService.findPsychologistBySpeciality(specialization);

        if (psychologists == null || psychologists.isEmpty()) {
            throw new RuntimeException("No psychologists found with specialization: " + specialization);
        }

        return psychologists.stream()
                .map(psychologist -> new PsychologistDTO(
                        psychologist.getId(),
                        psychologist.getName(),
                        psychologist.getSpecialization(),
                        psychologist.getPhone(),
                        psychologist.getEmail()))
                .collect(Collectors.toList());
    }

    // DELETE psychologist by ID
    @DeleteMapping("/{psychologistId}")
    public String deletePsychologist(@PathVariable int psychologistId) {
        Psychologist thePsychologist = psychologistService.findById(psychologistId);
        if (thePsychologist == null) {
            throw new RuntimeException("Psychologist with id " + psychologistId + " not found");
        }
        return psychologistService.delete(psychologistId);
    }

    // POST add psychologist
    @PostMapping("/add")
    public PsychologistDTO addPsychologist(
            @RequestParam("name") String newName,
            @RequestParam("specialization") String newSpecialization,
            @RequestParam("phone") String newPhone,
            @RequestParam("email") String newEmail,
            @RequestParam("city") String newCity) {

        // Check if the location already exists
        Location location = locationService.findByCity(newCity);

        // If it doesn't exist, create and save a new Location
        if (location == null) {
            location = new Location(newCity);
            location = locationService.save(location);
        }

        // Create a new psychologist associated with the location
        Psychologist newPsychologist = new Psychologist(newName, newSpecialization, newPhone, newEmail, location);

        // Save the psychologist
        Psychologist savedPsychologist = psychologistService.addPsychologist(newPsychologist);

        // Return the DTO for the created psychologist
        return new PsychologistDTO(
                savedPsychologist.getId(),
                savedPsychologist.getName(),
                savedPsychologist.getSpecialization(),
                savedPsychologist.getPhone(),
                savedPsychologist.getEmail());
    }
}

