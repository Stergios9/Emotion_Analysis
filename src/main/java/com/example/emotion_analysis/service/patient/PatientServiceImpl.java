package com.example.emotion_analysis.service.patient;

import com.example.emotion_analysis.dao.PatientRepository;
import com.example.emotion_analysis.entity.Patient;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PatientServiceImpl implements PatientService {

    private PatientRepository patientRepository;

    @Autowired
    public PatientServiceImpl(PatientRepository patientRepository) {
        this.patientRepository = patientRepository;
    }

    @Override
    public List<Patient> findAllPatients() {
        return patientRepository.findAll();
    }

    @Override
    public Patient findById(int theId) {
        Patient result = patientRepository.findById(theId);
       if (result == null) {
           return  null;
       }
        return result;
    }
    @Override
    public List<Patient> findPatientsBySentiment(String sentimentDescription) {
        return patientRepository.findBySentimentDescription(sentimentDescription);
    }

    @Override
    public List<Patient> findPatientByLastName(String lastName) {
        return patientRepository.findByLastName(lastName);
    }

    @Override
    public List<Patient> findPatientsByGender(String gender) {
        return patientRepository.findByGender(gender);
    }

    @Override
    @Transactional
    public Patient save(Patient patient) {
        return patientRepository.save(patient);
    }

    @Override
    @Transactional
    public String delete(int theId) {
        try {
            patientRepository.deleteById(theId);
            return "Patient with ID " + theId + " deleted successfully.";
        } catch (Exception e) {
            throw new RuntimeException("Failed to delete patient with ID " + theId, e);
        }
    }
    @Override
    @Transactional
    public void updatePatientLastName(int id, String newLastName) {
        int rowsUpdated = patientRepository.updateLastNameById(id, newLastName);
        if (rowsUpdated == 0) {
            throw new RuntimeException("Patient with id " + id + " not found");
        }
    }

    @Override
    @Transactional
    public void updatePatientFirstName(int id, String newFirstName) {
        int rowsUpdated = patientRepository.updateFirstNameById(id, newFirstName);
        if (rowsUpdated == 0) {
            throw new RuntimeException("Patient with id " + id + " not found");
        }
    }

    @Override
    public List<Object[]> findMostFrequentSentiment() {
        return patientRepository.findTopFiveMostFrequentSentiments();
    }



    @Override
    public List<Object[]> getMostCommonSentimentByGender() {
        return patientRepository.findMostCommonSentimentByGender();
    }

    @Override
    public List<Object[]> getSentimentPercentageByGender() {
        return patientRepository.findSentimentPercentageByGender();
    }

}
