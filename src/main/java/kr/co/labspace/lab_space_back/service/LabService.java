package kr.co.labspace.lab_space_back.service;

import kr.co.labspace.lab_space_back.entity.FileCategory;
import kr.co.labspace.lab_space_back.entity.Lab;
import kr.co.labspace.lab_space_back.repository.LabRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
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


    public boolean createLab (String name , List<MultipartFile> files){
      log.info("---------ENTERED LAB SERVICE---------");
      log.info("Lab Name : " + name);

//      1. 연구실생성
        Lab newLab = new Lab();
        newLab.setName(name);

//        Lab lab = labRepository.save(newLab);

//        2. 파일저장
//        2-1. 파일 저장 폴더 생성
        files.forEach(file -> {
            boolean res = saveFile(file, FileCategory.LAB);
        });
//        2-2. 파일 저장 경로 지정

//        2-3. 파일 저장 명칭 생성
      return true;
    }

    private boolean saveFile(MultipartFile file, FileCategory fileCategory){
        // 1.저장할 파일명 생성
        UUID uuid = UUID.randomUUID();
        LocalDateTime createDateTime = LocalDateTime.now();
        String fileName = uuid.toString()+"_" + createDateTime.toString();

        log.info("---------ENTERED SAVE FILE FUNC---------");
        log.info("Original FileName : " + file.getOriginalFilename());
        log.info("FileName : " + fileName);

        // 2. 이미지 경로
        // 2-1. image경로가 존재하는가
//        Path path = Paths.get(BaseFilePath)

        // 2-2. category경로가 존재하는가




        return true;
    }
}
