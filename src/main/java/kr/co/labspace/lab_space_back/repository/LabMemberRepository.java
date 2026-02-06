package kr.co.labspace.lab_space_back.repository;

import kr.co.labspace.lab_space_back.dto.lab_member.LabMemberDto;
import kr.co.labspace.lab_space_back.dto.lab_member.UserLabListDto;
import kr.co.labspace.lab_space_back.entity.LabMember;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LabMemberRepository extends JpaRepository<LabMember, Long> {

    //로그인 사용자의 연구실 조회
    @Query("SELECT new kr.co.labspace.lab_space_back.dto.lab_member.UserLabListDto(b.id, b.name) FROM LabMember a JOIN a.lab b  WHERE a.user.id = :userId")
    List<UserLabListDto> findLabsByUserId (@Param("userId") Long userId);

    @Query("SELECT new kr.co.labspace.lab_space_back.dto.lab_member.LabMemberDto(lm.id, u.name, lm.approvalStatus, lm.labRole) FROM LabMember lm JOIN lm.user u  WHERE lm.lab.id = :labId")
    List<LabMemberDto> findByLabId(Long labId);
}
