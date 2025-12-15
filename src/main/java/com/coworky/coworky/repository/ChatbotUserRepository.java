package com.coworky.coworky.repository;

import com.coworky.coworky.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatbotUserRepository extends JpaRepository<User, Long> {
}
