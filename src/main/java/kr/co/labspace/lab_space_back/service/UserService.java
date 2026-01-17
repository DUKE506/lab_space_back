package kr.co.labspace.lab_space_back.service;

import kr.co.labspace.lab_space_back.entity.User;
import kr.co.labspace.lab_space_back.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class UserService {
    @Autowired
    private UserRepository userRepository;

    public List<User> getAllUsers(){
        return userRepository.findAll();
    }
}

