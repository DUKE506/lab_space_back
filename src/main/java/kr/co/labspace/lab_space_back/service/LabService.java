package kr.co.labspace.lab_space_back.service;

import kr.co.labspace.lab_space_back.entity.ApprovalStatus;
import kr.co.labspace.lab_space_back.entity.FileCategory;
import kr.co.labspace.lab_space_back.entity.Lab;
import kr.co.labspace.lab_space_back.repository.LabRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.UUID;

@Service
@Slf4j
public class LabService {
    @Value("${file.upload-dir}")
    private String BaseFilePath;

    private final LabRepository labRepository;

    //의존성 주입
    public LabService (LabRepository labRepository){
        this.labRepository = labRepository;
    }


    public Lab createLab (String name , List<MultipartFile> files){
      log.info("----------ENTERED LAB SERVICE----------");
      log.info("Lab Name : " + name);

//      1. 연구실생성
        Lab newLab = new Lab();
        newLab.setName(name);
        newLab.setApprovalStatus(ApprovalStatus.PENDING);


        Lab lab = labRepository.save(newLab);


//        2. 파일저장
//        2-1. 파일 저장 폴더 생성
        files.forEach(file -> {
            boolean res = saveFile(file, FileCategory.LAB);
        });


        log.info("LAB Created Successfully");
      return lab;
    }

    //파일 저장 메서드
    private boolean saveFile(MultipartFile file, FileCategory fileCategory){
        // 1.저장할 파일명 생성
        UUID uuid = UUID.randomUUID();
        String createDateTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmssSSSS"));
        String originalName = file.getOriginalFilename();
        String fileExtension = originalName.substring(originalName.lastIndexOf("."));
        String fileName = uuid.toString()+"_" + createDateTime.toString()+fileExtension;


        log.info("----------ENTERED SAVE FILE FUNC----------");
        log.info("Original FileName : " + originalName);
        log.info("FileName : " + fileName);
        log.info("FileExtension : " + fileExtension);


        // 2. 이미지 경로
        // 2-1. image/category경로가 존재하는가
        Path uploadPath = Paths.get(BaseFilePath+"/"+fileCategory);
        try{
            if(!Files.exists(uploadPath)){
                //없으면 생성
                Files.createDirectories(uploadPath);
                log.info("Create" + fileCategory + "Folder!");
            }else {
                //이미 존재하면 로그만
                log.info("Already Exist" + fileCategory + "Folder!");
            }
        }catch(IOException e){
            throw new RuntimeException("Create" + fileCategory + "Images Folder Failed...", e);
        }

        // 3. 파일 저장
        try{
            Path savePath = uploadPath.resolve(fileName);
            file.transferTo(savePath);
            log.info("File save Success!");
        }catch (IOException e) {
            throw new RuntimeException("File Save Failed...",e);
        }



        return true;
    }
}
