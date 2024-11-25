package com.example.emotion_analysis.service.location;

import com.example.emotion_analysis.entity.Location;

import java.util.List;

public interface LocationService {


    Location findByCity(String city);

    Location findById(int id);

    Location save(Location location);

    List<Location> findAllLocations();

    List<Location> findLocationsBySpecialization(String specialization);
}
