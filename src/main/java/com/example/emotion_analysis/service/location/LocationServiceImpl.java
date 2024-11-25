package com.example.emotion_analysis.service.location;

import com.example.emotion_analysis.dao.LocationRepository;
import com.example.emotion_analysis.entity.Location;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LocationServiceImpl implements LocationService {

    private LocationRepository locationRepository;

    public LocationServiceImpl(LocationRepository locationRepository) {
        this.locationRepository = locationRepository;
    }

    @Override
    public Location findByCity(String city) {
        return locationRepository.findLocationByCity(city);
    }

    @Override
    public Location findById(int id) {
        return locationRepository.findLocationById(id);
    }

    @Override
    @Transactional
    public Location save(Location location) {
        return locationRepository.save(location);
    }

    @Override
    public List<Location> findAllLocations() {
        return locationRepository.findAll();
    }

    @Override
    public List<Location> findLocationsBySpecialization(String specialization) {
        return locationRepository.findLocationsBySpecialization(specialization);
    }
}
