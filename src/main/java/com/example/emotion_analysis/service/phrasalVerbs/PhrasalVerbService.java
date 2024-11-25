package com.example.emotion_analysis.service.phrasalVerbs;

import java.util.List;

public interface PhrasalVerbService {
    int processPhrasalVerb(String phrasalVerb, List<String> sentimentWords, boolean negateNext);
}
