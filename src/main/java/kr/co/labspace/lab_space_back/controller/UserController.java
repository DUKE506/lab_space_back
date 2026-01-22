package kr.co.labspace.lab_space_back.controller;

import kr.co.labspace.lab_space_back.dto.user.AdditionalUserDto;
import kr.co.labspace.lab_space_back.entity.User;
import kr.co.labspace.lab_space_back.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping
    public List<User> getUsers(){
        return userService.getAllUsers();
    }

    @PostMapping("/register")
    public ResponseEntity<User> additionalUser(@RequestBody AdditionalUserDto additionalUserDto) {
        User user = userService.registerAdditionalProfile(additionalUserDto);
        return ResponseEntity.ok(user);
    }
}
