package com.example.emotion_analysis.rest.authentcation;

import jakarta.servlet.http.HttpSession;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class AuthAspect {

    private final HttpSession session;

    public AuthAspect(HttpSession session) {
        this.session = session;
    }

    @Before("execution(* com.example.emotion_analysis.rest.patient.PatientController.*(..)) || " +
            "execution(* com.example.emotion_analysis.rest..psychologist.PsychologistController.*(..)) || " +
            "execution(* com.example.emotion_analysis.rest..user.SentimentAnalysisRestController.*(..)) || " +
            "execution(* com.example.emotion_analysis.rest..calendar.CalendarNoteControler.*(..)) || " +
            "execution(* com.example.emotion_analysis.rest..user.LocationController.*(..)) || " +
            "execution(* com.example.emotion_analysis.rest.statistics.StatisticsController.*(..))")
    public void checkUserLogin() {
        Object user = session.getAttribute("user");
        if (user == null) {
            throw new RuntimeException("User not logged in. Please login to access this resource.");
        }
    }
}
