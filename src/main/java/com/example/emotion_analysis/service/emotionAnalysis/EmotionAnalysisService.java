package com.example.emotion_analysis.service.emotionAnalysis;

import com.example.emotion_analysis.entity.EmotionAnalysis;

public interface EmotionAnalysisService {

    String getDiagnosisBySentimentId(int sentimentId);

    String getRelaxationTechniqueBySentimentId(int sentimentId);

    EmotionAnalysis getEmotionAnalysisBySentimentId(int sentimentId);
}
