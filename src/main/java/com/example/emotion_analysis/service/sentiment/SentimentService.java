package com.example.emotion_analysis.service.sentiment;


import com.example.emotion_analysis.entity.Sentiment;

import java.util.List;

public interface SentimentService {
    List<String> findDescriptions();

    String analyzeSentiment(String comment);

    int findIdBySentiment(String description);

    Sentiment findSentimentByDescription(String description);

}
