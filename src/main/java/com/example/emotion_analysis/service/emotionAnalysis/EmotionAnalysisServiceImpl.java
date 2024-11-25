package com.example.emotion_analysis.service.emotionAnalysis;

import com.example.emotion_analysis.dao.EmotionAnalysisRepository;
import com.example.emotion_analysis.entity.EmotionAnalysis;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EmotionAnalysisServiceImpl implements EmotionAnalysisService {

    @Autowired
    private EmotionAnalysisRepository emotionAnalysisRepository;

    @Override
    public String getDiagnosisBySentimentId(int sentimentId) {
        return emotionAnalysisRepository.findDiagnosisBySentimentId(sentimentId);
    }
    @Override
    public String getRelaxationTechniqueBySentimentId(int sentimentId) {
        return emotionAnalysisRepository.findRelaxationTechniqueBySentimentId(sentimentId);
    }
    @Override
    public EmotionAnalysis getEmotionAnalysisBySentimentId(int sentimentId) {
        return emotionAnalysisRepository.findBySentimentId(sentimentId);
    }
}
