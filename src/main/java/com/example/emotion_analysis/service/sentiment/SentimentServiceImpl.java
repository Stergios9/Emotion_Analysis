package com.example.emotion_analysis.service.sentiment;
import com.example.emotion_analysis.dao.SentimentRepository;
import com.example.emotion_analysis.entity.Sentiment;
import com.example.emotion_analysis.service.phrasalVerbs.PhrasalVerbService;
import com.example.emotion_analysis.utils.SentimentWords;
import com.example.emotion_analysis.utils.NegationWords;
import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.pipeline.*;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Service("sentimentServiceImpl")
public class SentimentServiceImpl implements SentimentService {

    @Autowired
    private PhrasalVerbService phrasalVerbService;

    @Autowired
    private SentimentRepository sentimentRepository;

    @Transactional
    @Override
    public String analyzeSentiment(String comment) {
        AtomicInteger joyfulCount = new AtomicInteger(0);
        AtomicInteger fearfulCount = new AtomicInteger(0);
        AtomicInteger anxiousCount = new AtomicInteger(0);
        AtomicInteger angryCount = new AtomicInteger(0);
        AtomicInteger sadnessCount = new AtomicInteger(0);

        // Δημιουργία αντικειμένου Properties
        Properties props = new Properties();
        props.setProperty("annotators", "tokenize,ssplit,pos,lemma");
        StanfordCoreNLP pipeline = new StanfordCoreNLP(props);
        CoreDocument document = new CoreDocument(comment);
        pipeline.annotate(document);


        int totalWords = 0;
        int numberOf_sentimentWords = 0;
        boolean negateNext = false;
        List<String> sentimentWords = new ArrayList<>();

        int numberOfSentence = 0;
        for (CoreSentence sentence : document.sentences()) {
            numberOfSentence++;
            List<CoreLabel> tokens = sentence.tokens();
            System.out.println("\nSentence(" + numberOfSentence + "): ");

            for (int i = 0; i < tokens.size(); i++) {
                String token = tokens.get(i).lemma().toLowerCase();
                List<String> splitTokens = splitToken(token);

                for (String splitToken : splitTokens) {
                    String word = splitToken.toLowerCase();
                    String pos = tokens.get(i).get(CoreAnnotations.PartOfSpeechAnnotation.class);
                    System.out.println("\nElement(" + (i) + "): " + word);

                    // Check if the token is punctuation or a number
                    if (word.matches("\\p{Punct}") || word.matches("\\d+")) {
                        System.out.println("Word is the punctuation: '" + pos+"'");
                        negateNext = false;
                        continue;
                    }

                    // Count total words (excluding punctuation and numbers)
                    totalWords++;

                    // Check for negation word and handle negation logic
                    if (NegationWords.NEGATION_WORDS.contains(word)) {
                        System.out.println("\nWord is negated. Word == " + word);
                        negateNext = true;

                        // Check next three tokens for verbs/adverbs/gerunds or sentiment words
                        for (int j = i + 1; j <= i+3; j++) {
                            String nextWord = tokens.get(j).lemma().toLowerCase();
                            String nextPos = tokens.get(j).get(CoreAnnotations.PartOfSpeechAnnotation.class);

                            // Check for sentence-ending punctuation to stop negation scope
                            if (nextWord.matches("[.!?]")) {
                                break;
                            }

                            // Check if one the next three words express one of these emotions. If yes, then increase the joy emotion
                            if (SentimentWords.SADNESS_WORDS.contains(nextWord) ||
                                    SentimentWords.ANXIOUS_WORDS.contains(nextWord) ||
                                    SentimentWords.ANGRY_WORDS.contains(nextWord) ||
                                    SentimentWords.FEARING_WORDS.contains(nextWord)) {

                                joyfulCount.incrementAndGet();
                                sentimentWords.add("not " + nextWord);
                                numberOf_sentimentWords++;
                                i = j;
                                break;

                            }// Check if one the next three words express joy. If yes, then increase the joy anger
                            else if (SentimentWords.JOYFUL_WORDS.contains(nextWord)) {
                                sadnessCount.incrementAndGet();
                                sentimentWords.add("not " + nextWord);
                                numberOf_sentimentWords++;
                                i = j;
                                break;
                            }

                        }
                        negateNext = false;  // Reset negation flag after applying
                    }

                    // Handle Phrasal Verbs
                    boolean phrasalVerbFound = false;

                    for (int j = 1; j <= 3 && (i + j) < tokens.size(); j++) {
                        String nextWord = tokens.get(i + j).lemma().toLowerCase();
                        String phrasalVerb = word + " " + nextWord;

                        int result = phrasalVerbService.processPhrasalVerb(phrasalVerb, sentimentWords, negateNext);
                        if (result != 0){
                            System.out.println("\nPhrasalVerb: " + phrasalVerb);
                            checkEmotion(result,negateNext,angryCount,joyfulCount,fearfulCount, anxiousCount, sadnessCount);
                            numberOf_sentimentWords++;
                            i += j;
                            phrasalVerbFound = true;
                            break;
                        }
                    }
                    if (phrasalVerbFound) {
                        continue;
                    }
                    // Apply sentiment logic
                    if (SentimentWords.JOYFUL_WORDS.contains(word)) {
                        if (negateNext) {
                            sadnessCount.incrementAndGet();
                            negateNext = false;
                            sentimentWords.add("not " + word);
                        } else {
                            joyfulCount.incrementAndGet();
                            sentimentWords.add(word);
                        }
                        numberOf_sentimentWords++;
                    } else if (SentimentWords.SADNESS_WORDS.contains(word)) {
                        if (negateNext) {
                            joyfulCount.incrementAndGet();
                            negateNext = false;
                            sentimentWords.add("not " + word);
                        } else {
                            sadnessCount.incrementAndGet();
                            sentimentWords.add(word);
                        }
                        numberOf_sentimentWords++;
                    } else if (SentimentWords.ANXIOUS_WORDS.contains(word)) {
                        if (negateNext) {
                            joyfulCount.incrementAndGet();
                            negateNext = false;
                            sentimentWords.add("not " + word);
                        } else {
                            anxiousCount.incrementAndGet();
                            sentimentWords.add(word);
                        }
                        numberOf_sentimentWords++;
                    } else if (SentimentWords.ANGRY_WORDS.contains(word)) {
                        if (negateNext) {
                            joyfulCount.incrementAndGet();
                            negateNext = false;
                            sentimentWords.add("not " + word);
                        } else {
                            angryCount.incrementAndGet();
                            sentimentWords.add(word);
                        }
                        numberOf_sentimentWords++;
                    } else if (SentimentWords.FEARING_WORDS.contains(word)) {
                        if (negateNext) {
                            joyfulCount.incrementAndGet();
                            negateNext = false;
                            sentimentWords.add("not " + word);
                        } else {
                            fearfulCount.incrementAndGet();
                            sentimentWords.add(word);
                        }
                        numberOf_sentimentWords++;
                    }
                }
            }
        }

        // PRINT THE RESULTS OF NPL PROCESSING
        printStatistcs(totalWords, numberOf_sentimentWords, sentimentWords);

        // Calculate scores
        Map<String, Double> emotionScores = calculateScores(totalWords, joyfulCount, fearfulCount, anxiousCount, sadnessCount, angryCount, 0);

        // Generate composite sentiment by sorting the keys (emotions) alphabetically
        String compositeSentiment = compositeSentiment(emotionScores);

        //System.out.println("\ncompositeSentiment: ******** " + compositeSentiment + " ******** \n\n");
        return compositeSentiment;
    }

    private void checkEmotion(int result, boolean negateNext, AtomicInteger angryCount, AtomicInteger joyfulCount,
                              AtomicInteger fearfulCount, AtomicInteger anxiousCount, AtomicInteger sadnessCount) {

        if (negateNext) {
            result = invertSentiment(result);
            negateNext = false;
        }
        switch (result) {
            case 1 -> angryCount.incrementAndGet();
            case 2 -> joyfulCount.incrementAndGet();
            case 3 -> fearfulCount.incrementAndGet();
            case 4 -> anxiousCount.incrementAndGet();
            case 5 -> sadnessCount.incrementAndGet();
        }
    }

    // Reverse emotion when there is a negative meaning
    private int invertSentiment(int sentiment) {
        // Invert the sentiment score based on negation
        switch (sentiment) {
            case 1: // Angry
                return 2; // Joyful
            case 2: // Joyful
                return 5; // Angry
            case 3: // Fearful
                return 2; // Joyful
            case 4: // Anxious
                return 2; // Joyful
            case 5: // Sadness
                return 2; // Joyful
            default:
                return 0;
        }
    }

    private String compositeSentiment(Map<String, Double> emotionScores){
        String compositeSentiment = emotionScores.keySet().stream()
                .sorted()  // Sort emotions alphabetically
                .collect(Collectors.joining("_"));
        return compositeSentiment;
    }

    private void printStatistcs(int totalWords,int numberOf_sentimentWords,List<String> sentimentWords){
        System.out.println("\n\n********** Statistics **********");
        System.out.println("\nTotalwords: " + totalWords);
        System.out.println("\nnumberOf_sentimentWords: " + numberOf_sentimentWords);
        System.out.println("\nsentimentWords: " + sentimentWords);
    }

    private Map<String, Double> calculateScores(int totalWords, AtomicInteger joyfulCount, AtomicInteger fearfulCount,
                                                AtomicInteger anxiousCount, AtomicInteger sadnessCount, AtomicInteger angryCount,
                                                int neutralCount) {
        // Avoid division by zero
        if (totalWords == 0) totalWords = 1;

        // Convert AtomicInteger to int and perform the division
        double joyScore = joyfulCount.get() / (double) totalWords;
        double fearScore = fearfulCount.get() / (double) totalWords;
        double anxiousScore = anxiousCount.get() / (double) totalWords;
        double sadnessScore = sadnessCount.get() / (double) totalWords;
        double angerScore = angryCount.get() / (double) totalWords;
        double neutralScore = neutralCount / (double) totalWords;

        System.out.println("\njoyScore: " + joyScore + "\n");
        System.out.println("\nfearScore: " + fearScore + "\n");
        System.out.println("\nanxiousScore: " + anxiousScore + "\n");
        System.out.println("\nsadnessScore: " + sadnessScore + "\n");
        System.out.println("\nangerScore: " + angerScore + "\n");
        System.out.println("\nneutralScore: " + neutralScore + "\n");

        // Create a Map to store the scores
        Map<String, Double> emotionScores = new HashMap<>();
        if (joyScore >= 0.01) {
            emotionScores.put("joy", joyScore);
        }
        if (fearScore >= 0.01) {
            emotionScores.put("fear", fearScore);
        }
        if (anxiousScore >= 0.01) {
            emotionScores.put("anxiety", anxiousScore);
        }
        if (sadnessScore >= 0.01) {
            emotionScores.put("sadness", sadnessScore);
        }
        if (angerScore >= 0.01) {
            emotionScores.put("anger", angerScore);
        }
        if (neutralScore >= 0.01) {
            emotionScores.put("neutral", neutralScore);
        }

        // If all scores are below 0.01, default to neutral
        if (joyScore < 0.01 && fearScore < 0.01 && anxiousScore < 0.01 && sadnessScore < 0.01 && angerScore < 0.01) {
            emotionScores.put("neutral", 1.0);
        }
        return emotionScores;
    }

    // Method to split tokens based on punctuation
    private List<String> splitToken(String token) {
        List<String> result = new ArrayList<>();
        String[] parts = token.split("(?<=\\p{Punct})|(?=\\p{Punct})");

        for (String part : parts) {
            if (!part.trim().isEmpty()) {
                result.add(part.trim());
            }
        }
        return result;
    }

    @Override
    public int findIdBySentiment(String sentimentDescription) {
        return sentimentRepository.findIdByDescription(sentimentDescription);
    }

    @Override
    public Sentiment findSentimentByDescription(String description) {
        return sentimentRepository.findByDescription(description);
    }

    @Override
    public List<String> findDescriptions() {
        return sentimentRepository.findAllDescriptions();
    }

}
