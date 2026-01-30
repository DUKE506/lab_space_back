package kr.co.labspace.lab_space_back.service;

import jakarta.transaction.Transactional;
import kr.co.labspace.lab_space_back.entity.*;
import kr.co.labspace.lab_space_back.repository.FileRepository;
import kr.co.labspace.lab_space_back.repository.LabMemberRepository;
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
    private final LabMemberRepository labMemberRepository;
    private final FileService fileService;

    //의존성 주입
    public LabService (LabRepository labRepository,LabMemberRepository labMemberRepository,FileService fileService){
        this.labRepository = labRepository;
        this.labMemberRepository = labMemberRepository;
        this.fileService = fileService;
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
                    lab.getLabMembers().forEach(member -> member.setApprovalStatus(ApprovalStatus.APPROVED));
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


    /**
     * 연구실 생성 요청
     * @param name
     * @param files
     * @return
     */
    @Transactional // Lab 저장 실패 시 전체 롤백
    public Lab createLab (String name , List<MultipartFile> files, User requestUser){
      log.info("----------ENTERED LAB SERVICE----------");
      log.info("Lab Name : " + name);

//      1. 연구실생성
        Lab newLab = Lab.builder()
                .name(name)
                .approvalStatus(ApprovalStatus.PENDING)
                .build();
        Lab savedLab = labRepository.save(newLab);

        // 2. 요청자 lab_member 추가
        LabMember labMember = LabMember.builder()
                .labRole(LabRole.PROFESSOR)
                .approvalStatus(ApprovalStatus.PENDING) // 관리자가 승인할 떄 approve
                .lab(savedLab)
                .user(requestUser)
                .build();
        labMemberRepository.save(labMember);

        //파일 없는지 체크
        if(files == null || files.isEmpty()){
            log.info("Not Exist File");
            return savedLab;
        }

        // 3. 증빙파일 저장
        files.forEach(file -> {

            // 파일 없는경우 제외 (saveFile메서드에서 확장자추출, 이름 등 여러가지 정보에 접근할 때 에러 발생할 수 있음)
            if(!file.isEmpty()){
                //        2-1. 파일 저장 메서드
                fileService.saveFile(file, FileCategory.LAB_CERTIFICATION, savedLab.getId());
            }
        });



        log.info("LAB Created Successfully");
      return savedLab;
    }

}
