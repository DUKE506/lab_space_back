package kr.co.labspace.lab_space_back.repository;

import kr.co.labspace.lab_space_back.entity.Lab;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LabRepository extends JpaRepository<Lab, Long> {

}
