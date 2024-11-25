package com.example.emotion_analysis.service.patient;

import com.example.emotion_analysis.entity.Patient;

import java.util.List;

public interface PatientService {
   //Patient: 1) findAll 2) findByName, 3) findByEmotion

   List<Patient> findAllPatients();

   Patient findById(int id);

   Patient save(Patient patient);

   String delete(int id);

   List<Patient> findPatientsBySentiment(String sentimentDescription);

   List<Patient> findPatientByLastName(String lastName);

   List<Patient> findPatientsByGender(String gender);

   void updatePatientLastName(int id, String newLastName);

   void  updatePatientFirstName(int id, String newFirstName);

   List<Object[]> findMostFrequentSentiment();

   List<Object[]> getMostCommonSentimentByGender();

   List<Object[]> getSentimentPercentageByGender();

}
