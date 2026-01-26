package kr.co.labspace.lab_space_back.config;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Component
@Slf4j
public class FileStorageConfig {

    @Value("${file.upload-dir}")
    private String fileBasePath;

    @PostConstruct
    public void initFile() {

        try{
            log.info("----------File Config----------");
            // Create Image Folder
            Path uploadPath = Paths.get(fileBasePath);

            // 업로드 폴더가 존재하지 않으면 생성
            if(!Files.exists(uploadPath)){
                Files.createDirectories(uploadPath);
                log.info("Create Images Folder!");
                return;
            }

            log.info("Already Exist Images Folder!");

        }catch (IOException e){
            throw new RuntimeException("폴더 초기화 실패", e);
        }

    }
}
