package com.example.emotion_analysis.dao;

import com.example.emotion_analysis.entity.Patient;
import com.example.emotion_analysis.entity.Sentiment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface SentimentRepository extends JpaRepository<Sentiment, Integer> {

    @Query("SELECT s.id FROM Sentiment s WHERE s.description = :description")
    int findIdByDescription(@Param("description") String description);

    Sentiment findByDescription(String description);

    @Query("SELECT d.description FROM Sentiment d")
    List<String> findAllDescriptions();
}

