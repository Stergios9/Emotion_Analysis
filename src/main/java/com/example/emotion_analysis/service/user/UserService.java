package com.example.emotion_analysis.service.user;

import com.example.emotion_analysis.entity.User;
public interface UserService {

    User findByUsername(String username);

    User findByUsernameAndPassword(String username, String password);
}
