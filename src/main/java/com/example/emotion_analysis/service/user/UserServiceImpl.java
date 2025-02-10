package com.example.emotion_analysis.service.user;

import com.example.emotion_analysis.dao.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import com.example.emotion_analysis.entity.User;import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;


    @Override
    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public User findByUsernameAndPassword(String username, String password) {
        return userRepository.findByUsernameAndPassword(username, password);
    }

    @Override
    public User saveUser(User user) {
        return userRepository.save(user);
    }

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public void deleteUserById(Integer integer) {
        userRepository.deleteById(integer);
    }
}