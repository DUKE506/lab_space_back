package kr.co.labspace.lab_space_back.service;

import jakarta.annotation.PostConstruct;
import kr.co.labspace.lab_space_back.entity.ApprovalStatus;
import kr.co.labspace.lab_space_back.entity.File;
import kr.co.labspace.lab_space_back.entity.FileCategory;
import kr.co.labspace.lab_space_back.entity.Lab;
import kr.co.labspace.lab_space_back.repository.FileRepository;
import kr.co.labspace.lab_space_back.repository.LabRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;


import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

@Service
public class LabService {

    private final LabRepository labRepository;
    private final FileRepository fileRepository;
    public LabService( LabRepository labRepository, FileRepository fileRepository){
        this.labRepository = labRepository;
        this.fileRepository = fileRepository;
    }

    @Value("${file.upload-dir}")
    private String uploadDir;

    @PostConstruct //서비스 초기화 시 실행
    public void init(){
        try{
            Path uploadPath = Paths.get(uploadDir);
            if(!Files.exists(uploadPath)){
                Files.createDirectories(uploadPath);
                System.out.println("업로드 폴더 생성 : "+ uploadPath);
            }

            //lab 폴더도 미리 생성
            Path labPath = Paths.get(uploadDir, "lab");
            if(!Files.exists(labPath)){
                Files.createDirectories(labPath);
                System.out.println("Lab 폴더 생성 : "+ labPath);
            }
        }catch (IOException e){
            throw new RuntimeException("폴더 초기화 실패",e);
        }
    }


    public Lab createLab (String name, List<MultipartFile> files){
        System.out.println("========== Lab 생성 시작 ==========");
        System.out.println("Lab 이름: " + name);
        System.out.println("파일 개수: " + files.size());

        //1. lab 엔티티 생성 및 저장
        Lab lab = new Lab();
        lab.setName(name);
        lab.setStatus(ApprovalStatus.PENDING);

        Lab savedLab = labRepository.save(lab);
        System.out.println("Lab 저장 완료 (ID: " + savedLab.getId() + ")");

        //2. 파일저장
        for (MultipartFile file : files) {
            if (!file.isEmpty()) {
                saveFile(file, FileCategory.LAB, savedLab.getId(), savedLab.getName());
            }
        }
        System.out.println("========== Lab 생성 완료 ==========");   System.out.println("========== Lab 생성 완료 ==========");
        return savedLab;
    }

    /*
    *
    * */
    private File saveFile(MultipartFile multipartFile, FileCategory category,
                            Long referenceId, String folderName){
        try{
            // 1. 카테고리별 폴더 경로
            String categoryFolder = category.name().toLowerCase();
            String safeFolderName = sanitizeFolderName(folderName);

            // uploadfiles/lab/AI연구소/
            Path fileFolderPath = Paths.get(uploadDir, categoryFolder, safeFolderName);

            if (!Files.exists(fileFolderPath)) {
                Files.createDirectories(fileFolderPath);
                System.out.println("폴더 생성: " + fileFolderPath.toAbsolutePath());
            }

            // 2. 파일명 생성
            String originalFilename = multipartFile.getOriginalFilename();
            String extension = "";
            if (originalFilename != null && originalFilename.contains(".")) {
                extension = originalFilename.substring(originalFilename.lastIndexOf("."));
            }

            String savedFilename = System.currentTimeMillis() + "_"
                    + UUID.randomUUID().toString().substring(0, 8)
                    + extension;

            // 3. 파일 저장
            Path filePath = fileFolderPath.resolve(savedFilename);
            multipartFile.transferTo(filePath.toFile());

            System.out.println("파일 저장 완료: " + filePath.toAbsolutePath());

            // 4. File 엔티티 생성 및 저장
            File file = new File();
            file.setFilePath(categoryFolder + "/" + safeFolderName + "/" + savedFilename);
            file.setOriginalFilename(originalFilename);
            file.setSavedFilename(savedFilename);
            file.setFileSize(multipartFile.getSize());
            file.setContentType(multipartFile.getContentType());
            file.setCategory(category);
            file.setReferenceId(referenceId);

            return fileRepository.save(file);

        }catch (IOException e){
            throw new RuntimeException("파일 저장 실패: " + multipartFile.getOriginalFilename(), e);
        }
    }

    /**
     * 폴더명에서 위험한 문자 제거
     * 슬래시, 백슬래시, 콜론 등 파일시스템에서 문제가 될 수 있는 문자를 언더스코어로 변경
     */
    private String sanitizeFolderName(String folderName) {
        return folderName.replaceAll("[/\\\\:*?\"<>|]", "_");
    }

}
