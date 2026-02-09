package kr.co.labspace.lab_space_back.controller;

import kr.co.labspace.lab_space_back.dto.lab_member.UserLabListDto;
import kr.co.labspace.lab_space_back.dto.user.AdditionalUserDto;
import kr.co.labspace.lab_space_back.entity.User;
import kr.co.labspace.lab_space_back.service.LabMemberService;
import kr.co.labspace.lab_space_back.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {
    @Autowired
    private UserService userService;

    private LabMemberService labMemberService;

    public UserController(LabMemberService labMemberService){
        this.labMemberService = labMemberService;
    }

    @GetMapping
    public List<User> getUsers(){
        return userService.getAllUsers();
    }

    @GetMapping("/myprofile")
    public void findMyProfile (){

    }



    /*
    * 사용자 추가정보 입력 컨트롤러
    * */
    @PostMapping("/register")
    public ResponseEntity<User> additionalUser(@RequestBody AdditionalUserDto additionalUserDto) {
        User user = userService.registerAdditionalProfile(additionalUserDto);
        return ResponseEntity.ok(user);
    }

    /**
     * 사용자 연구실 조회
     */
    @GetMapping("/findLabs")
    public ResponseEntity<List<UserLabListDto>> findLabsByUserId(
            @AuthenticationPrincipal User user
    ){
        return ResponseEntity.ok(labMemberService.findAllLabByUser(user.getId()));
    }
}
