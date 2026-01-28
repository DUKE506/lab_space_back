package kr.co.labspace.lab_space_back.service;

import jakarta.transaction.Transactional;
import kr.co.labspace.lab_space_back.entity.ApprovalStatus;
import kr.co.labspace.lab_space_back.entity.File;
import kr.co.labspace.lab_space_back.entity.FileCategory;
import kr.co.labspace.lab_space_back.entity.Lab;
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
import java.util.List;
import java.util.Optional;
import java.util.UUID;


@Slf4j
@Service
public class LabService {
    @Value("${file.upload-dir}")
    private String BaseFilePath;

    private final LabRepository labRepository;
    private final FileRepository fileRepository;

    //의존성 주입
    public LabService (LabRepository labRepository, FileRepository fileRepository){
        this.labRepository = labRepository;
        this.fileRepository = fileRepository;
    }

    //관리자 전체 조회
    public List<Lab> getLabs(){
        return labRepository.findAll();
    }

    //관리자 연구실 승인
    public Optional<Lab> approveLab(Long id){
        return labRepository.findById(id)
                .map(lab -> {
                    lab.setApprovalStatus(ApprovalStatus.APPROVED);
                    return labRepository.save(lab);
                });
    }
    //관리자 연구실 거부
    public Optional<Lab> rejectLab(Long id){
        return labRepository.findById(id)
                .map(lab -> {
                    lab.setApprovalStatus(ApprovalStatus.REJECTED);
                    return labRepository.save(lab);
                });
    }

    /*
    * 승인된 연구실 조회
    * */
    public List<Lab> getLabsByApproved(){
        return labRepository.findAllByApprovalStatus(ApprovalStatus.APPROVED);
    }

    @Transactional // Lab 저장 실패 시 전체 롤백
    public Lab createLab (String name , List<MultipartFile> files){
      log.info("----------ENTERED LAB SERVICE----------");
      log.info("Lab Name : " + name);

//      1. 연구실생성
        Lab newLab = Lab.builder()
                .name(name)
                .approvalStatus(ApprovalStatus.PENDING)
                .build();
        Lab savedLab = labRepository.save(newLab);

        //파일 없는지 체크
        if(files == null || files.isEmpty()){
            log.info("Not Exist File");
            return savedLab;
        }

        files.forEach(file -> {
            // 2. 파일저장
            // 파일 없는경우 제외 (saveFile메서드에서 확장자추출, 이름 등 여러가지 정보에 접근할 때 에러 발생할 수 있음)
            if(!file.isEmpty()){
                //        2-1. 파일 저장 메서드
                saveFile(file, FileCategory.LAB_CERTIFICATION, savedLab.getId());
            }



        });


        log.info("LAB Created Successfully");
      return savedLab;
    }

    //파일 저장 메서드
    private File saveFile(MultipartFile file, FileCategory fileCategory, Long referenceId){
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
