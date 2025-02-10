package com.example.emotion_analysis.rest.login;

import com.example.emotion_analysis.entity.Patient;
import com.example.emotion_analysis.service.user.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.ui.Model;
import  com.example.emotion_analysis.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class LoginController {


    @Autowired
    private UserService userService;

    @Value("${cities}")
    private List<String> cities;

    @Value("${ages}")
    private List<Integer> ages;

    @Value("${genders}")
    private List<String> genders;


    @GetMapping("/")
    public String loginForm(Model model) {

        model.addAttribute("user", new User());
        System.out.println("\n\nin login \n\n");

        return "loginForm";
    }

//    @GetMapping("/registrate")
//    public String registrateNewUser(HttpSession session, Model model) {
//        User user = (User) session.getAttribute("user");
//        model.addAttribute("role", "USER");
//
//        Patient thePatient = new Patient();
//
//        model.addAttribute("patient", thePatient);
//        model.addAttribute("cities", cities);
//        model.addAttribute("ages", ages);
//        model.addAttribute("genders", genders);
//
//        System.out.println("\n\nin registrate\n\n");
//        return "patients/registration-form";
//    }


    @GetMapping("/signUp")
    public String signUp(HttpSession session, Model model) {

        User newUser = new User();
        model.addAttribute("user", newUser);
        model.addAttribute("role", "USER");
        System.out.println("\n\nin signUp method\n\n");
        return "patients/newUser";
    }

    @PostMapping("/saveNewUser")
    public String saveNewUser(@ModelAttribute("user") User theUser,
                              HttpSession session, Model model) {

        theUser.setRole("USER");

        System.out.println("\n\nin saveNewUser method\n\nusername: "+theUser.getUsername()+"\n\npassword: "+theUser.getPassword()+"\n\nrole: "+theUser.getRole()+"\n\n");
        // ✅ Save user and add it to the session
        userService.saveUser(theUser);
        System.out.println("User saved successfully\n\n");
        session.setAttribute("user", "USER");
        System.out.println("user saved IN SESSION\n\n");

        // ✅ Ensure `user` is added to the model when rendering `loginForm.html`
        model.addAttribute("user", theUser);
        model.addAttribute("role", theUser.getRole());
        model.addAttribute("username", theUser.getUsername());
        model.addAttribute("password", theUser.getPassword());

        System.out.println("\n\n redirecting to login\n\n");

        return "loginForm";
    }

    @PostMapping("/welcome")
    public String login(@RequestParam(required = false) String username,
                        @RequestParam(required = false) String password,
                        HttpSession session,
                        Model model) {

        model.addAttribute("user", new User()); // Ensure 'user' exists in model

        if (username == null || username.trim().isEmpty() ||
                password == null || password.trim().isEmpty()) {
            model.addAttribute("error", "Invalid username or password.");
            return "loginForm"; // Stay on login page with error message
        }

        User user = userService.findByUsernameAndPassword(username, password);
        if (user != null) {
            session.setAttribute("user", user);
            model.addAttribute("role", user.getRole());
            return "welcome"; // Redirect to welcome page
        }

        model.addAttribute("error", "Invalid username or password.");
        return "loginForm"; // Stay on login page with error message
    }

    @GetMapping("/logout")
    public String logout(HttpSession session, Model model) {
        session.invalidate(); // Invalidate the session to log the user ou
        model.addAttribute("user", new User());
        return "loginForm"; // Redirect to the login page
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


    @GetMapping("/index")
    public String MedicalDiagnosis(HttpSession session, Model model) {
        User user = (User) session.getAttribute("user"); // Retrieve the user from the session
        Patient thePatient = new Patient();

        if (user != null) {
            String role = user.getRole(); // Get the role (e.g., "PATIENT" or "DOCTOR")
            model.addAttribute("role", role); // Pass the role to the template
            model.addAttribute("cities", cities);
            model.addAttribute("ages", ages);
            model.addAttribute("patient", thePatient);
            return "index"; // Return the welcome page
        }
        model.addAttribute("error", "User not logged in. Please login to access this resource.");
        return "loginForm"; // If no user is logged in, redirect to login
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

}