package kr.co.labspace.lab_space_back.controller;

import kr.co.labspace.lab_space_back.entity.Lab;
import kr.co.labspace.lab_space_back.service.LabService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/labs")
public class LabController {
    private LabService labService;

    public LabController ( LabService labService){
        this.labService = labService;
    }

    @PostMapping("/create")
    public ResponseEntity<Lab> createLab(@RequestPart("name")String name, @RequestPart("files")List<MultipartFile> files){
        System.out.println("========== Lab Add Request ==========");
        System.out.println("Name: " + name);
        System.out.println("Files count: " + files.size());

        files.forEach(file -> {
            System.out.println("File name: " + file.getOriginalFilename());
            System.out.println("File size: " + file.getSize());
        });

        Lab lab= labService.createLab(name, files);


        return ResponseEntity.ok(lab);
    }
}
