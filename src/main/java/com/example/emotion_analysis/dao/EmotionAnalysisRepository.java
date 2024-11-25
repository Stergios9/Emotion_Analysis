package com.example.emotion_analysis.dao;

import com.example.emotion_analysis.entity.EmotionAnalysis;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
public interface EmotionAnalysisRepository extends JpaRepository<EmotionAnalysis, Integer> {

    // Custom query to find diagnosis by sentimentId
    @Query("SELECT ea.diagnosis FROM EmotionAnalysis ea WHERE ea.sentiment.id = :sentimentId")
    String findDiagnosisBySentimentId(@Param("sentimentId") int sentimentId);

    // Custom query to find relaxation technique by sentimentId
    @Query("SELECT ea.relaxationTechnique FROM EmotionAnalysis ea WHERE ea.sentiment.id = :sentimentId")
    String findRelaxationTechniqueBySentimentId(@Param("sentimentId") int sentimentId);

    // Retrieve both diagnosis and relaxation technique
    @Query("SELECT ea FROM EmotionAnalysis ea WHERE ea.sentiment.id = :sentimentId")
    EmotionAnalysis findBySentimentId(@Param("sentimentId") int sentimentId);
}

