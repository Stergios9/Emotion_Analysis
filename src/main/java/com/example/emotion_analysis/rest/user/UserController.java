package com.example.emotion_analysis.rest.user;


import com.example.emotion_analysis.dao.UserRepository;
import com.example.emotion_analysis.entity.*;
import com.example.emotion_analysis.service.user.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Controller
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private UserRepository userRepository;

    @PostMapping("/saveUser")
    public String saveUser(@ModelAttribute("user") User newUser, Model model, HttpSession session) {

        newUser.setRole("USER");
        userService.saveUser(newUser);

        System.out.println("\n\nUser saved successfully\n\n");
        System.out.println("\nrole: " + newUser.getRole());

        session.setAttribute("user", newUser);
        model.addAttribute("user", newUser);

        return "loginForm";
    }

    @GetMapping("/addUser")
    public String addUser(HttpSession session,Model model) {

        User user = (User) session.getAttribute("user"); // Retrieve the user from the session
        System.out.println("\n\n\ni am in AddUser\n\n");


        if (user != null) {
            String userRole = user.getRole();
            if (userRole.equals("ADMIN")) {
                User newUser = new User();
                model.addAttribute("newUser", newUser);
//                model.addAttribute("role", userRole);
//                model.addAttribute("role", "USER");

                return "users/newUser-form";
            }
        }
        model.addAttribute("error", "User not logged in. Please login to access this resource.");
        return "loginForm";
    }

    @PostMapping("/add")
    public String addUser(@RequestParam("username") String username,
                          @RequestParam("password") String password,
                          Model model, HttpSession session) {

        // Retrieve user from session
        User user = (User) session.getAttribute("user");
        String role = user.getRole();
        System.out.println("\n\n\nrole: "+role);

        if (role.equals("ADMIN")) {
            // Set the location for the psychologist

            User newUser = new User(role,username,password);
            // Save the new psychologist
            userService.saveUser(newUser);
            System.out.println("\n\nnew User saved\n\n");


            List<User> listOfUsers = userRepository.findAll();
            model.addAttribute("allUsers", listOfUsers);

            model.addAttribute("role", role);
            model.addAttribute("successMessage","User saved successfully!!");

            return "users/editUsers";
        }

        model.addAttribute("error", "User not logged in. Please log in to access this resource.");
        return "loginForm";
    }


    @GetMapping("/list")
    public String getAllPatients(HttpSession session,Model model) {
        System.out.println("\n\ni am in usercontroller\n\n");

        User user = (User) session.getAttribute("user"); // Retrieve the user from the session

        if (user != null) {

            String role = user.getRole();
            model.addAttribute("role", role);
            List<User> allUsers = userRepository.findAll();

            model.addAttribute("allUsers", allUsers);

            if (role.equals("ADMIN")) {
                return "users/editUsers"; // Το όνομα της σελίδας που θα εμφανίσει τη λίστα
            }
            else if (role.equals("USER")) {
                return "users/users";
            }
        }
        model.addAttribute("error", "User not logged in. Please login to access this resource.");
        return "loginForm";
    }

    @GetMapping("/showFormForUpdate")
    public String showFormForUpdate(@RequestParam("userId") int theId, Model model, HttpSession session) {

        User user = (User) session.getAttribute("user");
        String role = user.getRole();
        if (role.equals("ADMIN")) {
            User theUser = userRepository.findById(theId).get();

            model.addAttribute("userForUpdate", theUser);
            model.addAttribute("role", "role");

            System.out.println("\n\nin showFormForUpdateUser\n\n");

            return "users/updateUser-form";
        }
        model.addAttribute("error", "User not logged in. Please login to access this resource.");
        return "loginForm";
    }

    @PostMapping("/updateUsers")
    public String updateUser(@RequestParam("id") int id,
                             @RequestParam("role") String role,
                             @RequestParam("username") String username,
                             @RequestParam("password") String password,
                             RedirectAttributes redirectAttributes, HttpSession session){

        User existingUser = userRepository.findById(id).get();

        existingUser.setRole(role);
        existingUser.setUsername(username);
        existingUser.setPassword(password);
        userService.saveUser(existingUser);

        List<User> allUsers = userService.findAll();
        redirectAttributes.addFlashAttribute("successMessage", "User updated successfully!!");

        return "redirect:/users/list"; // Redirect to the patient list or appropriate page
    }

    @GetMapping("/deleteUser")
    public String deleteUser(@RequestParam("userId") int theId,  RedirectAttributes redirectAttributes) {

        userService.deleteUserById(theId);
        redirectAttributes.addFlashAttribute("successMessage", "User deleted successfully!!");
        return "redirect:/users/list";

    }



}

