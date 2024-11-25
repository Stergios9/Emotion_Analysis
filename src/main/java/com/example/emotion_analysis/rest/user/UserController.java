package com.example.emotion_analysis.rest.user;


import com.example.emotion_analysis.entity.User;
import com.example.emotion_analysis.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> login(@RequestParam String username, @RequestParam String password) {
        Map<String, Object> response = new HashMap<>();

        User user = userService.findByUsername(username);

        if (user != null && user.getPassword().equals(password)) {
            response.put("success", true);
            response.put("role", user.getRole());  // Return user role (optional)
            response.put("message", "Login successful");
        } else {
            response.put("success", false);
            response.put("message", "Invalid credentials");
        }

        return ResponseEntity.ok(response);
    }
}
