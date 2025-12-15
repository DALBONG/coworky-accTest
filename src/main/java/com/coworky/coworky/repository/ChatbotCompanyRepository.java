package com.coworky.coworky.repository;

import com.coworky.coworky.domain.Company;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatbotCompanyRepository extends JpaRepository<Company, Long> {
}
