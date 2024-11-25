package com.example.emotion_analysis.utils;

import java.util.Arrays;
import java.util.List;

public class SentimentPhrasalVerbs {

    public List<String> getAngerPhrasalVerbs() {
        return Arrays.asList("blow up", "lose it", "fly off the handle", "tear into", "chew out", "lash out", "go off", "flare up",
                "boil over", "storm off", "bite back", "act up", "snap at", "blow off steam","tell off ", "Piss off",
                //PHRASES
                "driving me crazy", "fed up with", "I’ve had enough","gets on my nerves"
        );
    }

    public List<String> getJoyPhrasalVerbs() {
        return Arrays.asList("light up", "cheer up", "liven up", "crack up", "perk up", "bask in",
                "glow with", "kick up", "beam at", "laugh off", "revel in");
    }

    public List<String> getFearPhrasalVerbs() {
        return Arrays.asList("back away", "chicken out","freeze up", "cower down", "freak out", "run away",
                "shrivel up", "draw back", "hold back", "cringe at", "recoil from", "clam up","back off","shake up","tense up");
    }

    public List<String> getAnxietyPhrasalVerbs() {
        return Arrays.asList("worry about", "stress out", "fret over", "chew over", "freak out", "lose sleep over", "dwell on",
                "bottle up", "sweat over", "agonize over", "pace around", "psych out", "heart race");
    }

    public List<String> getSadnessPhrasalVerbs() {
        return Arrays.asList("feel horrible", "feel terrible",  "feel awful","feel poor", "feel negative", "been hurt", "mope around", "fall apart",
                "break down", "choke up", "let down", "tear up", "sink into", "cry over", "mourn over", "feel down", "drag on",
                "weep over", "give up");
    }
}