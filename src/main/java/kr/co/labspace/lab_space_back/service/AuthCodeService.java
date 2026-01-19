package kr.co.labspace.lab_space_back.service;

import jakarta.transaction.Transactional;
import kr.co.labspace.lab_space_back.entity.Authcode;
import kr.co.labspace.lab_space_back.repository.AuthCodeRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class AuthCodeService {

    private final AuthCodeRepository authCodeRepository;
    public AuthCodeService(AuthCodeRepository authCodeRepository){
        this.authCodeRepository = authCodeRepository;
    }

    /*
    * 인증 코드 생성
    * */
    @Transactional
    public String generateCode(Long userId) {
        String code = UUID.randomUUID().toString();

        Authcode authcode = Authcode.builder()
                .code(code)
                .userId(userId)
                .expiresAt(LocalDateTime.now().plusMinutes(5))
                .used(false)
                .build();

        authCodeRepository.save(authcode);
        return code;
    }

    /*
    * 코드 검증 및 사용자 ID 반환
    * */
    public Long validateAdnConsume(String code){
        Authcode authCode = authCodeRepository.findByCode(code).orElse(null);

        if(authCode == null){
            return  null; // 없는 경우
        }
        if(!authCode.isValid()){
            return null; // 만료되었거나 이미 사용
        }

        //사용처리
        authCode.setUsed(true);
        authCodeRepository.save(authCode);

        return authCode.getUserId();
    }

    /*
    * 만료된 코드 정리
    * */
    public void cleanupExpiredCodes(){
        authCodeRepository.deleteByExpiresAtBefore(LocalDateTime.now());
    }



}
