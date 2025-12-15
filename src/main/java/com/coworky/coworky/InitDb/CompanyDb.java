package com.coworky.coworky.InitDb;

import com.coworky.coworky.domain.Company;
import com.coworky.coworky.domain.ConnectStatus;
import jakarta.annotation.PostConstruct;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class CompanyDb {

    private final CompanyInitService companyInitService;

    @PostConstruct
    public void init(){
        companyInitService.dbCompany();
    }

    @Component
    @Transactional
    @RequiredArgsConstructor
    static class CompanyInitService{
        private final EntityManager em;

        public void dbCompany(){

            Company company = new Company();
            company.setName("testCompany");
            company.setStatus(ConnectStatus.CONNECTED);

            em.persist(company);

        }
    }

}
