package kr.co.labspace.lab_space_back.repository;

import kr.co.labspace.lab_space_back.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    // 이메일로 사용자 찾기
    Optional<User> findByEmail(String email);
}
