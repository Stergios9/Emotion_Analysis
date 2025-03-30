package com.example.emotion_analysis.utils;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class SentimentWords {


    public static final Set<String> ANXIOUS_WORDS = new HashSet<>(Arrays.asList(
            "anxious", "stress", "worried", "nervous", "tense", "uneasy",
            "restless", "jittery", "agitated", "fretful","worry", "anxiety",
            "panicky", "fraught", "distraught", "alarmed", "distressed","exhaust","exhausted","exhausting","crippling"
    ));
    public static final Set<String> FEARING_WORDS = new HashSet<>(Arrays.asList(
            "fear", "fright", "terror", "horror", "alarm", "panic", "dread", "trepidation", "apprehension",
            "unease", "consternation", "concern", "phobia", "dread",  "terrify", "alarm", "scare",
            "panic", "startle", "apprehend", "agonize", "shudder", "tremble", "quake", "fret", "cower", "afraid",
            "frightened", "scared", "fearful", "terrified", "alarmed", "panicked", "apprehensive", "frightening",
            "timid", "timorous", "shaken", "startled", "dreading", "jittery", "fearfully", "frighteningly", "terrifyingly",
            "alarmingly", "apprehensively", "uneasily", "timidly", "timorously", "anxiously", "fearing", "dreading",
            "terrifying", "alarming", "scaring", "panicking", "startling", "worrying", "agonizing", "shuddering","overwhelmed",
            "trembling", "quaking", "fretting", "cowering", "guilty", "troubled", "panicked", "insecure", "pessimistic"

    ));
    public static final Set<String> ANGRY_WORDS = new HashSet<>(Arrays.asList(
            "angry", "furious", "mad", "irate", "annoyed", "enraged", "incensed", "irritated", "infuriated", "livid",
            "outraged", "aggravated", "resentful", "offended", "exasperated", "wrathful", "anger", "rage", "fury",
            "wrath", "outrage", "ire", "madness", "indignation", "resentment", "exasperation", "enrage", "infuriate",
            "madden", "provoke", "incense", "annoy", "irritate", "vex", "exasperate", "aggravate", "fume", "resent",
            "seethe", "boil", "bristle", "explode", "get mad", "see red","fuming", "indignant", "displeased", "choleric",
            "irascible", "raging", "seething", "ballistic", "angrily", "furiously", "wrathfully", "outrageously", "irately",
            "madly", "fuming", "indignantly", "irritatedly", "exasperatedly", "aggravatedly", "annoyedly", "angering",
            "enraging", "infuriating", "maddening", "provoking", "incensing", "annoying", "irritating",  "vexing",
            "exasperating", "aggravating", "fuming", "resenting", "seething", "boiling", "bristling", "exploding",
            "blowing up", "hot-tempered", "blow up", "flare up", "vindictive", "vengeful", "hateful", "hostile"
    ));

    public static final Set<String> JOYFUL_WORDS = new HashSet<>(Arrays.asList(
            "well", "good", "perfect","excited", "enthusiastic", "grateful", "hopeful", "optimistic", "fortunate",
            "loving", "proud", "confident", "amused", "wonderful", "excellent", "great", "fantastic", "charmed", "sunny",
            // Nouns
            "joy", "happiness", "delight", "elation", "euphoria", "bliss", "jubilation", "glee", "exultation", "cheerfulness",
            "mirth", "rejoicing", "contentment", "satisfaction", "pleasure", "exhilaration", "radiance", "exuberance",
            "felicity", "joyfulness", "gladness", "gratification","fun",

            // Verbs
            "rejoice", "delight", "exult", "celebrate", "cheer", "enjoy", "revel", "beam", "smile", "gladden", "elate",
            "exhilarate", "radiate", "bliss out",

            // Adjectives
            "joyful", "happy", "delighted", "elated", "euphoric", "blissful", "jubilant", "gleeful", "exultant", "cheerful",
            "mirthful", "content", "satisfied", "pleased", "exhilarated", "radiant", "exuberant", "felicitous", "glad",
            "gratified",

            // Adverbs
            "joyfully", "happily", "delightfully", "elatedly", "euphorically", "blissfully", "jubilantly", "gleefully",
            "exultantly", "cheerfully", "mirthfully", "contentedly", "satisfyingly", "pleasurably", "exhilaratingly",
            "radiantly", "exuberantly", "gladly", "gratefully",

            // Gerunds
            "rejoicing", "delighting", "exulting", "celebrating", "cheering", "enjoying", "reveling", "beaming", "smiling",
            "gladdening", "elating", "exhilarating", "radiating", "blissing out"

    ));

    public static final Set<String> SADNESS_WORDS = new HashSet<>(Arrays.asList(
            "bad", "frustrated", "disappointed", "jealous", "envious", "lonely", "upset",  "ashamed", "bitter",
            "dismal", "glum", "morose", "melancholy","unhappy",

            // Nouns
            "sadness", "sorrow", "grief", "melancholy", "misery", "despair", "gloom", "heartache", "woe", "depression",
            "unhappiness", "suffering", "anguish", "despondency", "dismay", "dolefulness", "downheartedness",
            "forlornness", "hopelessness", "mourning", "regret", "remorse", "woefulness",

            // Verbs
            "sadden", "grieve", "mourn", "lament", "sorrow", "weep", "wail", "bemoan", "bewail", "brood", "pine", "regret",
            "agonize", "despair", "dole",

            // Adjectives
            "sad", "sorrowful", "grief-stricken", "melancholic", "miserable", "despondent", "gloomy", "heartbroken",
            "woeful", "depressed", "unhappy", "suffering", "anguished", "dismal", "downcast", "downhearted", "forlorn",
            "hopeless", "mournful", "regretful", "remorseful", "tearful", "wistful", "bereaved",

            // Adverbs
            "sadly", "sorrowfully", "grievously", "mournfully", "woefully", "depressingly", "unhappily", "dismally",
            "forlornly", "despondently", "anguishfully", "regretfully", "remorsefully", "wistfully",

            // Gerunds
            "saddening", "grieving", "mourning", "lamenting", "sorrowing", "weeping", "wailing", "bemoaning", "bewailing",
            "brooding", "pining", "regretting", "agonizing", "despairing", "doleful","frustrating",

            // phrasal verbs
            " feel horrible", "feel terrible", " feel poor", " feel negative", " been hurt", "mope around", "fall apart",
            "break down", "choke up", "let down", "tear up", "sink into", "cry over", "mourn over", "feel down", "drag on",
            "weep over", "give up"


    ));
    public static boolean isSentimentWord(String word) {
        return ANXIOUS_WORDS.contains(word) ||
                FEARING_WORDS.contains(word) ||
                ANGRY_WORDS.contains(word) ||
                JOYFUL_WORDS.contains(word) ||
                SADNESS_WORDS.contains(word);
    }
}
