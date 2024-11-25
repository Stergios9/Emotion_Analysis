package com.example.emotion_analysis.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.emotion_analysis.entity.User;

public interface UserRepository  extends JpaRepository<User, Integer> {
    User findByUsername(String username);
}
