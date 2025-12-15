package com.coworky.coworky.repository;

import com.coworky.coworky.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import com.coworky.coworky.domain.Company;
import com.coworky.coworky.domain.User;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class UserRepository {

    private final EntityManager em;

    // 유저 아이디 + 회사 통해서 조회
    public Optional<User> findByLoginIdAndCompany(String loginId, Company company){
        return em.createQuery("select u from User u where u.loginId = :loginId and u.company = :company", User.class)
                .setParameter("loginId", loginId)
                .setParameter("company", company)
                .getResultList().stream().findFirst();
    }


    // 카카오 아이디로 조회
    public Optional<User> findByKakaoId(Long kakaoId){
        return em.createQuery("select u from User u where u.kakaoId = :kakaoId", User.class)
                .setParameter("kakaoId", kakaoId)
                .getResultList()
                .stream()
                .findFirst();
    }

//    public void save(User user){
//        em.persist(user);
//    }

}
