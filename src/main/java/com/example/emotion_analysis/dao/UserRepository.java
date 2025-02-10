package com.example.emotion_analysis.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.emotion_analysis.entity.User;

import java.util.List;

public interface UserRepository  extends JpaRepository<User, Integer> {

    User findByUsername(String username);
    User findByUsernameAndPassword(String username, String password);

    User save(User user);

    User findUserByUsernameAndPassword(String username, String password);

    List<User> findAll();

    void deleteById(Integer integer);
}