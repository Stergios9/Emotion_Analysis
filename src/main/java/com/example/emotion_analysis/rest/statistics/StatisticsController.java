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
    public String getMostFrequentSentiment(HttpSession session,Model model) {
        User user = (User) session.getAttribute("user");
        if (user != null) {
            String role = user.getRole();
            List<Object[]> results = patientService.findMostFrequentSentiment();
            if ("ADMIN".equalsIgnoreCase(role)){
                model.addAttribute("role", role);
                model.addAttribute("results", results);
                return "mostFrequentSentiment"; // Refers to mostFrequentSentiment.html
            }
        }
        model.addAttribute("error", "User not logged in. Please login to access this resource.");
        return "loginForm";
    }

    // ****************************************************************************************** //

    // ************************* Counts each sentiment per gender ******************************  //

    @GetMapping("/sentiment/gender")
    public String mostCommonSentimentByGender(HttpSession session,Model model) {
        User user = (User) session.getAttribute("user");
        if (user != null) {
            List<Object[]> sentimentData = patientService.getMostCommonSentimentByGender();
            String role = user.getRole();
            if ("ADMIN".equalsIgnoreCase(role)){
                model.addAttribute("role", role);
                model.addAttribute("sentimentData", sentimentData);
                return "sentimentByGender";  // View name, e.g. sentimentByGender.html
            }
        }
        model.addAttribute("error", "User not logged in. Please login to access this resource.");
        return "loginForm";
    }

    // ****************************************************************************************** //

    // *******************  Finds for each sentiment the percentage by gender *******************  //

    @GetMapping("/sentiment/percentage")
    public String sentimentPercentageByGender(HttpSession session,Model model) {
        // Fetch the sentiment percentage data
        User user = (User) session.getAttribute("user");
        if (user != null) {
            List<Object[]> sentimentData = patientService.getSentimentPercentageByGender();
            String role = user.getRole();
            if ("ADMIN".equalsIgnoreCase(role)) {
                model.addAttribute("role", role);
                model.addAttribute("sentimentData", sentimentData);
                return "sentimentPercentage"; // Corresponding HTML template
            }
        }
        model.addAttribute("error", "User not logged in. Please login to access this resource.");
        return "loginForm";
    }


    @GetMapping("/sentiment/percentage/json")
    @ResponseBody
    public List<Object[]> sentimentPercentageByGender() {
        return patientService.getSentimentPercentageByGender();
    }

}
