package com.coworky.coworky.service;

import com.coworky.coworky.domain.Company;
import com.coworky.coworky.domain.User;
import com.coworky.coworky.dto.KakaoLoginResultDto;
import com.coworky.coworky.dto.KakaoTokenResponseDto;
import com.coworky.coworky.dto.KakaoUserResponseDto;
import com.coworky.coworky.dto.LoginResponseDto;
import com.coworky.coworky.jwt.JwtTokenProvider;
import com.coworky.coworky.repository.CompanyRepository;
import com.coworky.coworky.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class KakaoLoginService {

    private final KakaoApiClient kakaoApiClient;
    private final UserRepository userRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final CompanyRepository companyRepository;


    @Transactional(readOnly = false)
    public KakaoLoginResultDto kakaoLogin(String code, String redirectUrl) {
        System.out.println("카카오 로그인 서비스 들어옴, code = " + code);

        // 카카오 토큰 요청
        KakaoTokenResponseDto tokenResponse = kakaoApiClient.getKakaoToken(code, redirectUrl);
        String accessToken = tokenResponse.getAccess_token();

        // 카카오 사용자 정보 요청
        KakaoUserResponseDto userInfo = kakaoApiClient.geUserInfo(accessToken);

        Long kakaoId =  userInfo.getId();




        // 카카오 유저용 회사 조회
        Company socialCompany = companyRepository.findByCompanyName("testCompany");

        if(socialCompany == null){
            KakaoLoginResultDto error = new KakaoLoginResultDto();
            error.setStatus("ERROR");
            error.setMessage("소셜 로그인용 회사가 존재하지 않습니다.");
            return error;
        }

        // db에서 유저 조회

        User user = userRepository.findByKakaoId(kakaoId).orElse(null);

        if(user == null){
            KakaoLoginResultDto result = new  KakaoLoginResultDto();
            result.setStatus("NEED_LINK");
            result.setKakaoId(kakaoId);
            return result;
        }

        // jwt 생성
        String accessJwt = jwtTokenProvider.createAccessToken(user.getId(), user.getRole().name());
        String refreshJwt = jwtTokenProvider.createRefreshToken(user.getId());

        KakaoLoginResultDto success = new  KakaoLoginResultDto();
        success.setStatus("SUCCESS");
        success.setLoginId(user.getLoginId());
        success.setRole(user.getRole().name());
        success.setAccessToken(accessJwt);
        success.setRefreshToken(refreshJwt);
        return success;

    }

    public KakaoLoginResultDto linkKakaoAccount(Long kakaoId, String loginId, String rawPwd, Long companyId){

        // 사내 계정 조회
        Company company = companyRepository.findOne(companyId);

        if(company == null){
            KakaoLoginResultDto error = new KakaoLoginResultDto();
            error.setStatus("ERROR");
            error.setMessage("회사 정보를 찾을 수 없습니다.");
            return error;
        }

        // 로그인 아이디 + 회사로 실제 사용자 조회
        User user = userRepository.findByLoginIdAndCompany(loginId, company).orElse(null);

        if(user == null){
            KakaoLoginResultDto error = new KakaoLoginResultDto();
            error.setStatus("ERROR");
            error.setMessage("해당 사내 계정을 찾을 수 없습니다.");
            return error;
        }

        // 비밀번호 검증
        if(!user.getLoginPwd().equals(rawPwd)){
            KakaoLoginResultDto error = new KakaoLoginResultDto();
            error.setStatus("ERROR");
            error.setMessage("비밀번호가 일치하지 않습니다.");
            return error;
        }

        // 연결
        user.setKakaoId(kakaoId);

        // 연결 후 바로 로그인
        String accessJwt = jwtTokenProvider.createAccessToken(user.getId(), user.getRole().name());
        String refreshJwt = jwtTokenProvider.createRefreshToken(user.getId());

        KakaoLoginResultDto result = new KakaoLoginResultDto();
        result.setStatus("SUCCESS");
        result.setLoginId(user.getLoginId());
        result.setRole(user.getRole().name());
        result.setAccessToken(accessJwt);
        result.setRefreshToken(refreshJwt);

        return result;


    }

}
