package kr.co.labspace.lab_space_back.repository;

import kr.co.labspace.lab_space_back.entity.Authcode;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.Optional;

public interface AuthCodeRepository extends JpaRepository<Authcode, Long> {
    Optional<Authcode> findByCode(String code);

    //만료된 코드 삭제
    void deleteByExpiresAtBefore(LocalDateTime dateTime);
}
