package com.coworky.coworky.service;

import com.coworky.coworky.domain.User;
import com.coworky.coworky.repository.ChatbotUserRepository;
import com.coworky.coworky.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final ChatbotUserRepository chatbotUserRepository;

    @Cacheable(value = "users", key = "#id")
    public User findById(Long id) {
        return chatbotUserRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));
    }
}
