package kr.co.labspace.lab_space_back.controller;

import kr.co.labspace.lab_space_back.dto.lab.CreateRequestDto;
import kr.co.labspace.lab_space_back.service.LabService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
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

    @PostMapping("/create")
    public boolean CreateLab (
            @RequestPart("name") String name,
            @RequestPart(value = "files",required = false) List<MultipartFile> files
            ){
        log.info("=========ENTERED LAB CREATE CONTROLLER=========");
        log.info("DTO : "+name);

        //파일 null-safe 처리
        List<MultipartFile> uploadFiles = Optional.ofNullable(files)
                        .orElse(Collections.emptyList()); // 빈배열 반환

        log.info("Files count : ", uploadFiles.size());
        uploadFiles.forEach((file) -> log.info("file name : " + file.getOriginalFilename()));

        boolean res = labService.createLab(name, files);


        return true;
    }
}
