package com.example.emotion_analysis.service.user;

import com.example.emotion_analysis.entity.User;

import java.util.List;
import java.util.Optional;

public interface UserService {

    User findByUsername(String username);

    User findByUsernameAndPassword(String username, String password);

    User saveUser(User user);

    List<User> findAll();

    void deleteUserById(Integer integer);

    Optional<User> findById(int theId);
}