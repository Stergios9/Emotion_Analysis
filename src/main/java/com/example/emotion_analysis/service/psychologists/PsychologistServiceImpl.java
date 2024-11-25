package com.example.emotion_analysis.service.psychologists;

import com.example.emotion_analysis.dao.PsychologistRepository;
import com.example.emotion_analysis.entity.Location;

import com.example.emotion_analysis.entity.Psychologist;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@Transactional
public class PsychologistServiceImpl implements PsychologistService {

    @Autowired
    private PsychologistRepository psychologistRepository;

    @Override
    @Transactional
    public Psychologist addPsychologist(Psychologist psychologist) {
        return psychologistRepository.save(psychologist);
    }

    @Override
    public  List<Psychologist> findPsychologistBySpeciality(String speciality) {
        return psychologistRepository.findBySpecialization(speciality);
    }

    @Override
    public List<Psychologist> findPsychologistsByName(String lastName) {
        return psychologistRepository.findPsychologistsByName(lastName);
    }

    @Override
    public List<Psychologist> findPsychologistsByLocation(Location location) {
        return psychologistRepository.findByLocation(location);
    }

    @Override
    public List<Psychologist> findAllPsychologists() {
        List<Psychologist> allPsychologists = psychologistRepository.findAll();
        return allPsychologists;
    }

    @Override
    @Transactional
    public String delete(int id) {
        psychologistRepository.deletePsychologistById(id);
        return "Psychologist with id: "+ id +" deleted";
    }

    @Override
    public Psychologist findById(int id) {
        Psychologist result = psychologistRepository.findPsychologistById(id);
       if (result == null) {
           return null;
       }
        return result;
    }
    @Override
    @Transactional
    public void updatePsychologistEmail(int id, String newEmail) {
        int rowsUpdated = psychologistRepository.updateEmailById(id, newEmail);
        if (rowsUpdated == 0) {
            throw new RuntimeException("Psychologist with id " + id + " not found");
        }
    }

}
