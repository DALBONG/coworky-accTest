package com.coworky.coworky.repository;

import com.coworky.coworky.domain.Company;
import org.springframework.data.jpa.repository.JpaRepository;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
@RequiredArgsConstructor
public class CompanyRepository {

    private final EntityManager em;

    // 회사 단건 조회
    public Company findOne(Long id) {
        return em.find(Company.class, id);
    }


    public Company findByCompanyName(String name) {
        List<Company> result = em.createQuery(
                        "select c from Company c where c.name = :name", Company.class)
                .setParameter("name", name)
                .getResultList();

        return result.isEmpty() ? null : result.get(0);
    }


}
