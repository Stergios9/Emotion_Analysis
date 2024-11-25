package com.example.emotion_analysis.dao;

import com.example.emotion_analysis.entity.Patient;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PatientRepository extends JpaRepository<Patient, Integer> {

    List<Patient> findAll();

    Patient save(Patient patient);

    void deleteById(Integer integer);

    Patient findById(int id);


    @Query("SELECT p FROM Patient p JOIN p.sentiments s WHERE s.description = :description")
    List<Patient> findBySentimentDescription(@Param("description") String description);

    @Query("SELECT p FROM Patient p WHERE p.lastName = :lastName")
    List<Patient> findByLastName(@Param("lastName") String lastName);

    @Query("SELECT p FROM Patient p WHERE p.gender = :gender")
    List<Patient> findByGender(@Param("gender") String gender);

    @Modifying
    @Transactional // Ensures the update is wrapped in a transaction
    @Query("UPDATE Patient p SET p.lastName = :lastName WHERE p.id = :id")
    int updateLastNameById(@Param("id") int id, @Param("lastName") String lastName);

    @Modifying
    @Transactional // Ensures the update is wrapped in a transaction
    @Query("UPDATE Patient p SET p.firstName = :firstName WHERE p.id = :id")
    int updateFirstNameById(@Param("id") int id, @Param("firstName") String firstName);

    // ******************************************************************************************* //
    // ************************** Finds the most frequent sentiments *****************************  //

    @Query(value = "SELECT s.description, COUNT(s.id) AS frequency " +
            "FROM Patient p " +
            "JOIN patient_sentiment ps ON p.id = ps.patient_id " +
            "JOIN Sentiment s ON ps.sentiment_id = s.id " +
            "GROUP BY s.description " +
            "ORDER BY frequency DESC " +
            "LIMIT 5",
            nativeQuery = true)
    List<Object[]> findTopFiveMostFrequentSentiments();


    // ***************************************************************************************** //
    // ************************* Counts each sentiment per gender ******************************  //

    @Query("SELECT p.gender, s.description AS sentiment, COUNT(s) AS sentimentCount " +
            "FROM Patient p " +
            "JOIN p.sentiments s " +
            "GROUP BY p.gender, s.description " +
            "ORDER BY p.gender ASC, sentimentCount DESC")
    List<Object[]> findMostCommonSentimentByGender();

    // ****************************************************************************************** //
    // ************************* Finds for each sentiment the percentage by gender ************** //

    @Query(value = "SELECT p.gender, " +
            "s.description AS sentiment, " +
            "ROUND(COUNT(s.id) * 100.0 / (SELECT COUNT(ps2.sentiment_id) " +
            "FROM patient_sentiment ps2 " +
            "JOIN Patient p2 ON ps2.patient_id = p2.id " +
            "WHERE p2.gender = p.gender), 0) AS percentage " + // Rounds to nearest whole number
            "FROM Patient p " +
            "JOIN patient_sentiment ps ON p.id = ps.patient_id " +
            "JOIN Sentiment s ON ps.sentiment_id = s.id " +
            "GROUP BY p.gender, s.description " +
            "ORDER BY p.gender, percentage DESC",
            nativeQuery = true)
    List<Object[]> findSentimentPercentageByGender();




}




