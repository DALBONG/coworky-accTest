package com.coworky.coworky.service;


import com.coworky.coworky.domain.Company;
import com.coworky.coworky.domain.Role;
import com.coworky.coworky.domain.User;
import com.coworky.coworky.dto.LoginResponseDto;
import com.coworky.coworky.jwt.JwtTokenProvider;
import com.coworky.coworky.repository.CompanyRepository;
import com.coworky.coworky.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final CompanyRepository companyRepository;
    private final JwtTokenProvider jwtTokenProvider;

    // 회사 유효성 확인 로직
    public Company validateCompany(Long companyId){

        // companyId로 회사 조회
        Company company = companyRepository.findOne(companyId);

        if(company == null){
            throw new IllegalArgumentException("존재하지 않는 회사입니다.");
        }

        return company;

    }

    // 유저 조회 로직
    public Optional<User> findUser(String loginId, Company company){
        return userRepository.findByLoginIdAndCompany(loginId, company);
    }

    // 비밀번호 검증 로직
    public void validatePassword(String rawPwd, String loginPwd){
        if(!rawPwd.equals(loginPwd)){
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }

    }

    // 권한(role) 검증 로직
    public void validateAdmin(User user, Role role){
        if(user.getRole() != role){
            throw new IllegalArgumentException("권한 없음");
        }
    }


    // 로그인 성공 시 후처리 로직
    @Transactional
    public LoginResponseDto handleLoginSuccess(User user){

        // Access Token 생성
        String accessToken = jwtTokenProvider.createAccessToken(
                user.getId(),
                user.getRole().name()
        );

        // Refresh Token 생성
        String  refreshToken = jwtTokenProvider.createRefreshToken(
                user.getId()
        );

        // Refresh Token Redis 저장


        // 응답 DTO로 반환
        return  new LoginResponseDto(
                user.getLoginId(),
                user.getRole().name(),
                accessToken,
                refreshToken
        );
    }

    @Transactional
    public LoginResponseDto login(String loginId, String rawPwd, Long companyId){

        // 회사 검증
        Company company = validateCompany(companyId);

        // 유저 조회
        User user = findUser(loginId, company).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 회원입니다."));

        // 비밀번호 검증
        validatePassword(rawPwd, user.getLoginPwd());

        // 권한 검증 로직 추가 기능 (필요시)


        // 로그인 성공 후 처리 (토큰 발급)
        return handleLoginSuccess(user);
    }

}
