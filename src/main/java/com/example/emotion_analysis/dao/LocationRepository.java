package com.example.emotion_analysis.dao;

import com.example.emotion_analysis.entity.Location;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface LocationRepository extends JpaRepository<Location, Integer> {

    Location findLocationByCity(String city);

    Location findLocationById(int id);

    Location save(Location location);

    List<Location> findAll();

    @Query("SELECT DISTINCT l FROM Location l JOIN l.psychologists p WHERE p.specialization = :specialization")
    List<Location> findLocationsBySpecialization(@Param("specialization") String specialization);
}
