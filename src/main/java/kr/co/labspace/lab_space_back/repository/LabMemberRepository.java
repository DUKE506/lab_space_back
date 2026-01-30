package kr.co.labspace.lab_space_back.repository;

import kr.co.labspace.lab_space_back.entity.LabMember;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LabMemberRepository extends JpaRepository<LabMember, Long> {
}
