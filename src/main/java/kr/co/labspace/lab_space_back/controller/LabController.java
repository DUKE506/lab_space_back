package kr.co.labspace.lab_space_back.controller;

import kr.co.labspace.lab_space_back.dto.lab.CreateRequestDto;
import kr.co.labspace.lab_space_back.entity.Lab;
import kr.co.labspace.lab_space_back.entity.User;
import kr.co.labspace.lab_space_back.service.LabService;
import lombok.extern.slf4j.Slf4j;
import org.apache.coyote.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/labs")
@Slf4j
public class LabController {
    //서비스 의존성 주입
    private final LabService labService;

    public  LabController(LabService labService){
        this.labService = labService;
    }

    @GetMapping("/findAll")
    public ResponseEntity<List<Lab>> getLabs(){
        log.info("==========승인된 연구실 전체 조회 컨트롤러==========");
        List<Lab> labs = labService.getLabsByApproved();
        return ResponseEntity.ok(labs);
    }



    @PostMapping("/create")
    public ResponseEntity<Lab> createLab (
            @RequestPart("name") String name,
            @RequestPart(value = "files",required = false) List<MultipartFile> files,
            @AuthenticationPrincipal User requestUser
            ){
        log.info("==========ENTERED LAB CREATE CONTROLLER==========");
        log.info("요청자 : "+ requestUser.toString());

        //파일 null-safe 처리
        List<MultipartFile> uploadFiles = Optional.ofNullable(files)
                        .orElse(Collections.emptyList()); // 빈배열 반환

        log.info("Files count : "+ uploadFiles.size());
        uploadFiles.forEach((file) -> log.info("File name : " + file.getOriginalFilename()));

        Lab res = labService.createLab(name, files, requestUser);


        return ResponseEntity.ok(res);
    }

    // ==============관리자==============

    @GetMapping("/admin/findAll")
    public ResponseEntity<List<Lab>> getAll(){
        log.info("==========관리자 연구실 전체 조회 컨트롤러==========");
        List<Lab> labs = labService.getLabs();
        return ResponseEntity.ok(labs);
    }

    @PatchMapping("/admin/approve/{id}")
    public ResponseEntity<Lab> approveLab(@PathVariable Long id){
        return labService.approveLab(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PatchMapping("/admin/reject/{id}")
    public ResponseEntity<Lab> rejectLab(@PathVariable Long id){
        return labService.rejectLab(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }



}
