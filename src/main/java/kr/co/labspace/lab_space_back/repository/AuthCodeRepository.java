package kr.co.labspace.lab_space_back.repository;

import kr.co.labspace.lab_space_back.entity.AuthCode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public interface AuthCodeRepository extends JpaRepository<AuthCode, Long> {
    Optional<AuthCode> findByCode(String code);

    //만료된 코드 삭제
    void deleteByExpiresAtBefore(LocalDateTime dateTime);
}
