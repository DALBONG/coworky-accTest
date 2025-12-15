package com.coworky.coworky.InitDb;

import com.coworky.coworky.domain.Company;
import com.coworky.coworky.domain.Role;
import com.coworky.coworky.domain.User;

import jakarta.annotation.PostConstruct;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;


@Component
@RequiredArgsConstructor
public class UserDb {

    private final UserInitService userInitService;

    @PostConstruct
    public void init(){
        userInitService.dbUser();
    }

    @Component
    @Transactional
    @RequiredArgsConstructor
    static class UserInitService {
        private final EntityManager em;

        public void dbUser(){

            Company company = em.find(Company.class, 1L);

            User user1 = User.createUser(
                    company,
                    "testUser",
                    "1234",
                    "테스트유저",
                    Role.USER
            );

            em.persist(user1);

            User user2 = User.createUser(
                    company,
                    "test2",
                    "1234",
                    "테스투",
                    Role.USER
            );

            em.persist(user2);

            User admin1 = User.createAdminUser(
                    company,
                    "admin1",
                    "1234",
                    "관리자1",
                    Role.ADMIN
            );

            em.persist(admin1);


        }
    }

}
