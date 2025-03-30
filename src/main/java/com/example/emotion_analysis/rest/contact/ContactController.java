package com.example.emotion_analysis.rest.contact;

import com.example.emotion_analysis.entity.Contact;
import com.example.emotion_analysis.service.contact.ContactService;
import jakarta.servlet.http.HttpSession;
import org.springframework.ui.Model;
import  com.example.emotion_analysis.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.stereotype.Controller;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


@Controller
public class ContactController {

    @Autowired
    private ContactService contactService;


    @GetMapping("/contact")
    public String contactForm(HttpSession session, Model model) {
        User user = (User) session.getAttribute("user");

        if (user != null) {
            String role = user.getRole();
            model.addAttribute("contact", new Contact()); // Ensure contact object exists
            model.addAttribute("role", role);
            return "contact";
        }
        // ✅ Always add an error message when redirecting to login
        model.addAttribute("error", "User not logged in. Please log in first.");
        return "loginForm"; // Make sure this file is correct
    }

    @PostMapping("/contact/submit")
    public String submitContactForm(@ModelAttribute Contact contact, RedirectAttributes redirectAttributes, HttpSession session) {
        if (contactService == null) {
            System.out.println("contactService is null!");
        }

        User user = (User) session.getAttribute("user");

        if (user == null) {
            return "redirect:/"; // Redirect to login page if not logged in
        }

        contactService.save(contact);
        redirectAttributes.addFlashAttribute("successMessage", "Your message has been sent successfully!");
        return "redirect:/contact";
    }

}