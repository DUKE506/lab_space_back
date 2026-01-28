package kr.co.labspace.lab_space_back.repository;

import kr.co.labspace.lab_space_back.entity.ApprovalStatus;
import kr.co.labspace.lab_space_back.entity.File;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;



@Repository
public interface FileRepository extends JpaRepository<File,Long> {


}
