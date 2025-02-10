package com.example.emotion_analysis.service.user;

import com.example.emotion_analysis.entity.User;

import java.util.List;

public interface UserService {

    User findByUsername(String username);

    User findByUsernameAndPassword(String username, String password);

    User saveUser(User user);

    List<User> findAll();

    void deleteUserById(Integer integer);
}