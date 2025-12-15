package com.coworky.coworky.dto;

import com.coworky.coworky.domain.CType;
import com.coworky.coworky.domain.Chatbot;
import com.coworky.coworky.domain.User;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class ChatbotResponseDto {

    private Long id;
    private String title;
    private String content;
    private CType ctype;
    private LocalDateTime createdDate;

    private Long userId;
    private String userName;

    public static ChatbotResponseDto fromEntity(Chatbot chatbot) {
        // Chatbot 엔티티에서 필요한 필드를 직접 추출하여 DTO를 빌드
        // User 정보도 필요한 필드만 뽑아냅니다.
        User user = chatbot.getUser();

        return ChatbotResponseDto.builder()
                .id(chatbot.getId())
                .title(chatbot.getTitle())
                .content(chatbot.getContent())
                .ctype(chatbot.getCtype())
                .createdDate(chatbot.getCreatedDate())
                .userId(user != null ? user.getId() : null)
                .userName(user != null ? user.getName() : "unknown")
                .build();
    }
}
