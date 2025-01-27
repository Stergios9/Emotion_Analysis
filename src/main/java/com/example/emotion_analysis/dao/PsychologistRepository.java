package com.example.emotion_analysis.dao;

import com.example.emotion_analysis.entity.Location;
import com.example.emotion_analysis.entity.Psychologist;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PsychologistRepository extends JpaRepository<Psychologist, Integer> {

    List<Psychologist> findByLocation(Location location);

    List<Psychologist> findAll();

    List<Psychologist> findAllByOrderByNameAsc();

    String deletePsychologistById(int id);

    Psychologist findPsychologistById(int id);

    Psychologist save(Psychologist psychologist);

    List<Psychologist> findBySpecialization(String specialization);

    @Query("SELECT p FROM Psychologist p WHERE p.name = :name")
    List<Psychologist> findPsychologistsByName(@Param("name") String name);

    @Modifying
    @Transactional
    @Query("UPDATE Psychologist p SET p.email = :email WHERE p.id = :id")
    int updateEmailById(@Param("id") int id, @Param("email") String email);


    @Query("SELECT p FROM Psychologist p WHERE p.location.city = :city")
    List<Psychologist> findPsychologistsByCity(@Param("city") String city);

}