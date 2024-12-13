package com.example.emotion_analysis.rest.login;


import com.example.emotion_analysis.service.user.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.ui.Model;
import  com.example.emotion_analysis.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class LoginController {


    @Autowired
    private UserService userService;

    @GetMapping("/")
    public String loginForm(Model model) {
        return "loginForm";
    }

    @GetMapping("/about")
    public String aboutForm(HttpSession session, Model model) {
        User user = (User) session.getAttribute("user"); // Retrieve the logged-in user from the session
        if (user == null) {
            model.addAttribute("error", "User not logged in. Please login to access this resource.");
            return "loginForm"; // Redirect to login page if not logged in
        }
        String role = user.getRole(); // Get the role (e.g., "PATIENT" or "DOCTOR")
        model.addAttribute("role", role); // Pass the role to the template
        return "about"; // Show the about page if logged in
    }

    @GetMapping("/contact")
    public String contactForm(HttpSession session,Model model) {
        User user = (User) session.getAttribute("user");
        if (user != null) {
            String role = user.getRole(); // Get the role (e.g., "PATIENT" or "DOCTOR")
            model.addAttribute("role", role); // Pass the role to the template
            return "contact";
        }
        model.addAttribute("error", "User not logged in. Please login to access this resource.");
        return "loginForm";
    }

    @GetMapping("/welcome")
    public String welcomePage(HttpSession session, Model model) {
        User user = (User) session.getAttribute("user"); // Retrieve the user from the session

        if (user != null) {
            String role = user.getRole(); // Get the role (e.g., "PATIENT" or "DOCTOR")
            model.addAttribute("role", role); // Pass the role to the template
            return "welcome"; // Return the welcome page
        }
        model.addAttribute("error", "User not logged in. Please login to access this resource.");
        return "loginForm"; // If no user is logged in, redirect to login
    }


    @GetMapping("/stats")
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


    @GetMapping("/index")
    public String MedicalDiagnosis(HttpSession session, Model model) {
        User user = (User) session.getAttribute("user"); // Retrieve the user from the session

        if (user != null) {
            String role = user.getRole(); // Get the role (e.g., "PATIENT" or "DOCTOR")
            model.addAttribute("role", role); // Pass the role to the template
            return "index"; // Return the welcome page
        }
        model.addAttribute("error", "User not logged in. Please login to access this resource.");
        return "loginForm"; // If no user is logged in, redirect to login
    }

    @PostMapping("/welcome")
    public String login(@RequestParam String username,
                        @RequestParam String password,
                        HttpSession session, // Inject the session object
                        Model model) {
        User user = userService.findByUsernameAndPassword(username, password);

        if (user != null) {
            session.setAttribute("user", user); // Store the user in the session
            String role = user.getRole(); // Get the role (e.g., "PATIENT" or "DOCTOR")
            model.addAttribute("role", role); // Pass the role to the template
            return "welcome"; // Redirect to welcome.html
        }
        model.addAttribute("error", "Invalid username or password.");
        return "loginForm"; // Stay on loginForm.html with error message
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate(); // Invalidate the session to log the user out
        return "loginForm"; // Redirect to the login page
    }

}