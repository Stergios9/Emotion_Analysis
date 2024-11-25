package com.example.emotion_analysis.utils;


import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class NegationWords {
    public static final Set<String> NEGATION_WORDS = new HashSet<>(Arrays.asList(
            "not", "never", "no", "none", "hardly", "barely", "rarely", "seldom", "without", "neither"
    ));
}