package com.example.emotion_analysis.rest.statistics;


import com.example.emotion_analysis.entity.User;
import jakarta.servlet.http.HttpSession;
import org.springframework.ui.Model;
import com.example.emotion_analysis.service.patient.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("/stats")
public class StatisticsController {

    @Autowired
    private PatientService patientService;

    @GetMapping("/")
    public String statistics(HttpSession session, Model model) {
        User user = (User) session.getAttribute("user"); // Retrieve the user from the session

        if (user != null) {
            String role = user.getRole(); // Get the role (e.g., "PATIENT" or "DOCTOR")
            model.addAttribute("role", role); // Pass the role to the template
            return "statistics"; // Return the welcome page
        }
        model.addAttribute("error", "User not logged in. Please login to access this resource.");
        return "loginForm"; // If no user is logged in, redirect to login
    }

    // ************************** Finds the most frequent sentiments *****************************  //
    // ************************************** Histogram ******************************************  //

    @GetMapping("/sentiment/dominant")
    public String getMostFrequentSentiment(Model model) {
        List<Object[]> results = patientService.findMostFrequentSentiment();
        model.addAttribute("results", results);
        return "mostFrequentSentiment"; // Refers to mostFrequentSentiment.html
    }

    // ****************************************************************************************** //

    // ************************* Counts each sentiment per gender ******************************  //

    @GetMapping("/sentiment/gender")
    public String mostCommonSentimentByGender(Model model) {
        // Fetch the sentiment data
        List<Object[]> sentimentData = patientService.getMostCommonSentimentByGender();

        // Adding the data to the model so that it can be accessed in the view
        model.addAttribute("sentimentData", sentimentData);

        // Return the view name (Thymeleaf template)
        return "sentimentByGender";  // View name, e.g. sentimentByGender.html
    }

    // ****************************************************************************************** //

    // *******************  Finds for each sentiment the percentage by gender *******************  //

    @GetMapping("/sentiment/percentage")
    public String sentimentPercentageByGender(Model model) {
        // Fetch the sentiment percentage data
        List<Object[]> sentimentData = patientService.getSentimentPercentageByGender();

        // Add data to the model
        model.addAttribute("sentimentData", sentimentData);

        // Return the view name
        return "sentimentPercentage"; // Corresponding HTML template
    }

    @GetMapping("/sentiment/percentage/json")
    @ResponseBody
    public List<Object[]> sentimentPercentageByGender() {
        return patientService.getSentimentPercentageByGender();
    }



}
