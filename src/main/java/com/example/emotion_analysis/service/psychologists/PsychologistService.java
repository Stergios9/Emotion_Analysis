package com.example.emotion_analysis.service.psychologists;

import com.example.emotion_analysis.entity.Location;
import com.example.emotion_analysis.entity.Psychologist;

import java.util.List;

public interface PsychologistService {
    List<Psychologist> findPsychologistsByLocation(Location location);

    List<Psychologist> findAllPsychologists();

    List<Psychologist> findAllPsychologistsByNameAsc();

    String delete(int id);

    Psychologist findById(int id);

    Psychologist addPsychologist(Psychologist psychologist);

    List<Psychologist> findPsychologistBySpeciality(String speciality);

    List<Psychologist> findPsychologistsByName(String lastName);

    void updatePsychologistEmail(int id, String email);

    List<Psychologist> findPsychologistsByLocation(String city);



}