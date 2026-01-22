package kr.co.labspace.lab_space_back.service;

import jakarta.transaction.Transactional;
import kr.co.labspace.lab_space_back.dto.user.AdditionalUserDto;
import kr.co.labspace.lab_space_back.entity.User;
import kr.co.labspace.lab_space_back.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public List<User> getAllUsers(){
        return userRepository.findAll();
    }

    public User getUserByUserId(Long userId) {
        return userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
    }

    @Transactional
    public User registerAdditionalProfile (AdditionalUserDto additionalUserDto){
        User user = userRepository.findById(additionalUserDto.getId()).orElseThrow(() -> new RuntimeException("User not found"));

        user.setPhone(additionalUserDto.getPhone());
        user.setUniversity(additionalUserDto.getUniversity());
        user.setDepartment(additionalUserDto.getDepartment());
        user.setUserType(additionalUserDto.getUserType());
        user.setIsProfileCompleted(true);

        return userRepository.save(user);
    }

}

