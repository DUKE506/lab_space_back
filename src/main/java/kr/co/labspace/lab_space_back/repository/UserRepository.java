package kr.co.labspace.lab_space_back.repository;

import kr.co.labspace.lab_space_back.entity.AuthProvider;
import kr.co.labspace.lab_space_back.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    // 이메일로 사용자 찾기
    Optional<User> findByEmail(String email);

    // 카카오 아이디로 사용자 찾기
    Optional<User> findByProviderAndProviderId(AuthProvider provider, String kakaoId);

    Optional<User> findByEmailAndProvider(AuthProvider provider, String email);

    // ID로 사용자 찾기
    Optional<User> findByUserId (Long userId);

}
