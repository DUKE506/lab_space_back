package kr.co.labspace.lab_space_back.service;

import kr.co.labspace.lab_space_back.entity.File;
import kr.co.labspace.lab_space_back.entity.FileCategory;
import kr.co.labspace.lab_space_back.repository.FileRepository;
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
import java.util.UUID;

@Service
@Slf4j
public class FileService {
    @Value("${file.upload-dir}")
    private String BaseFilePath;

    private final FileRepository fileRepository;

    //의존성 주입
    public FileService ( FileRepository fileRepository){
        this.fileRepository = fileRepository;
    }


    //파일 저장 메서드
    public File saveFile(MultipartFile file, FileCategory fileCategory, Long referenceId){
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

        try{
            Path uploadPath = Paths.get(BaseFilePath, fileCategory.name().toLowerCase());

            if(!Files.exists(uploadPath)){
                // 폴더 없으면 생성
                Files.createDirectories(uploadPath);
                log.info("폴더 생성: {}", uploadPath);
            }

            Path savePath = uploadPath.resolve(fileName);
            file.transferTo(savePath);
            log.info("파일 저장 완료: {}", savePath);

        }catch(IOException e){
            throw new RuntimeException("파일 저장 실패:"+ originalName,e);
        }

        // 3. File DB저장
        File fileEntity = File.builder()
                .fileCategory(fileCategory)
                .path(fileCategory.name().toLowerCase() + "/" + fileName)
                .fileName(fileName)
                .originalName(originalName)
                .fileSize(file.getSize())
                .contentType(file.getContentType())
                .referenceId(referenceId)
                .build();

        File savedFile = fileRepository.save(fileEntity);


        return savedFile;
    }
}
