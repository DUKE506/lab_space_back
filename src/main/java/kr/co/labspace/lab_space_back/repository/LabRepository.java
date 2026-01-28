package kr.co.labspace.lab_space_back.repository;

import kr.co.labspace.lab_space_back.entity.ApprovalStatus;
import kr.co.labspace.lab_space_back.entity.File;
import kr.co.labspace.lab_space_back.entity.Lab;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LabRepository extends JpaRepository<Lab,Long> {

    //연구실명으로 조회
    Optional<Lab> findByName(String name);

    //승인상태별 조회
    List<Lab> findAllByApprovalStatus(ApprovalStatus approvalStatus);
}
