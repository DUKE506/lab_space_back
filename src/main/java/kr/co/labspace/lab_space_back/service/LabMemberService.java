package kr.co.labspace.lab_space_back.service;

import kr.co.labspace.lab_space_back.dto.lab_member.LabMemberDto;
import kr.co.labspace.lab_space_back.dto.lab_member.UserLabListDto;
import kr.co.labspace.lab_space_back.repository.LabMemberRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class LabMemberService {

    private final LabMemberRepository labMemberRepository;

    public LabMemberService(LabMemberRepository labMemberRepository){
        this.labMemberRepository= labMemberRepository;
    }

    //사용자 연구실 조회 (목록용)
    public List<UserLabListDto> findAllLabByUser (Long userId){
        return labMemberRepository.findLabsByUserId((userId));
    }

    //연구싫 멤버 조회
    public List<LabMemberDto> findAllLabMembersByLab (Long labId){
        List<LabMemberDto> members = labMemberRepository.findByLabId(labId);
        log.info(members.toString());
        return members;
    }




}
