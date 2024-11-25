package com.example.emotion_analysis.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "emotion_analysis")
public class EmotionAnalysis {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @OneToOne
    @JoinColumn(name = "sentiment_id") // Foreign key to the Sentiment table
    private Sentiment sentiment; // This establishes a relationship

    @Column(name = "diagnosis", columnDefinition = "TEXT") // or "CLOB" for even larger text
    private String diagnosis;

    @Column(name = "relaxation_technique", columnDefinition = "TEXT")
    private String relaxationTechnique;

    public EmotionAnalysis() {}

    public EmotionAnalysis(Sentiment sentiment, String diagnosis, String relaxationTechnique) {
        this.sentiment = sentiment;
        this.diagnosis = diagnosis;
        this.relaxationTechnique = relaxationTechnique;
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Sentiment getSentiment() {
        return sentiment;
    }

    public void setSentiment(Sentiment sentiment) {
        this.sentiment = sentiment;
    }

    public String getDiagnosis() {
        return diagnosis;
    }

    public void setDiagnosis(String diagnosis) {
        this.diagnosis = diagnosis;
    }

    public String getRelaxationTechnique() {
        return relaxationTechnique;
    }

    public void setRelaxationTechnique(String relaxationTechnique) {
        this.relaxationTechnique = relaxationTechnique;
    }
}
