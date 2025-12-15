package com.coworky.coworky.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

import static jakarta.persistence.EnumType.STRING;
import static jakarta.persistence.FetchType.LAZY;
import static jakarta.persistence.GenerationType.IDENTITY;

@Entity
@Table(name = "TB_User")
@Getter @Setter
public class User {

    @Id @GeneratedValue(strategy = IDENTITY)
    @Column(name = "user_id")
    private Long id;

    @Column(name = "login_id", length = 100)
    private String loginId;

    @Column(name = "login_pwd", length = 100)
    private String loginPwd;

    @Column(name = "name", length = 30)
    private String name;

    @Enumerated(STRING)
    @Column(name = "role", nullable = false)
    private Role role;      // USER, ADMIN

    // --- Company 연관관계 ---
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "company_id")
    private Company company;

    public User() {}
    public User(String name, Role role, Company company) {
        this.name = name;
        this.role = role;
        this.company = company;
    }
    @Column(name = "kakao_id", unique = true)
    private Long kakaoId;


    public static User createUser(Company company, String loginId, String rawPwd, String name, Role role) {
        User user = new User();
        user.setCompany(company);
        user.setLoginId(loginId);
        user.setLoginPwd(rawPwd);
        user.setName(name);
        user.setRole(role);
        return user;
    }

    public static User createKakaoUser(Company company, Long kakaoId, String email) {
        User user = new User();
        user.setCompany(company);              // 회사 필수
        user.setKakaoId(kakaoId);              // 카카오 고유 ID
        user.setLoginId("kakao_" + kakaoId);   // loginId 대체값
        user.setLoginPwd(null);                // 소셜 로그인은 비번 없음
        user.setName(email != null ? email : "카카오유저");             // 기본 이름 (필요하면 변경 가능)
        user.setRole(Role.USER);               // 기본 권한

        return user;
    }

    public static User createAdminUser(Company company, String loginId, String rawPwd, String name, Role role) {
        User user = new User();
        user.setCompany(null);
        user.setLoginId(loginId);
        user.setLoginPwd(rawPwd);
        user.setName(name);
        user.setRole(role);
        return user;
    }

}
