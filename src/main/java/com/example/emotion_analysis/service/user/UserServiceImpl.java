package com.example.emotion_analysis.service.user;

import com.example.emotion_analysis.dao.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import com.example.emotion_analysis.entity.User;import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;


    @Override
    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }
}
