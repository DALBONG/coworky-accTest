package com.coworky.coworky.domain;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

import static jakarta.persistence.EnumType.STRING;
import static jakarta.persistence.FetchType.LAZY;
import static jakarta.persistence.GenerationType.IDENTITY;

@Entity
@Table(name = "TB_Chatbot", indexes = {
        // 1. 필터링 및 정렬 최적화를 위한 복합 인덱스
        @Index(name = "idx_filter_sort", columnList = "ctype, status, created_date DESC")
})
@Data
public class Chatbot {

    @Id @GeneratedValue(strategy = IDENTITY)
    @Column(name = "chatbot_id")
    private Long id;

    @Column(name = "title", nullable = false, length = 1000)
    private String title;

    @Column(name = "content",  nullable = false, length = 4000)
    private String content;

    @CreationTimestamp
    @Column(name = "created_date", nullable = false, updatable = false)
    private LocalDateTime createdDate;

    @UpdateTimestamp
    @Column(name = "updated_date", nullable = false)
    private LocalDateTime updatedDate;

    @Enumerated(STRING)
    @Column(name = "status", nullable = false, length = 7)
    private ChatbotStatus chatbotStatus; // [ EXIST, DELETED ]

    @Enumerated(STRING)
    @Column(name = "ctype", nullable = false)
    private CType ctype; // [ FAQ, NOTICE, RULE ]

    // 검색 성능 개선을 위한 keywords 필드 추가
    @Column(name = "keywords", length = 500)
    private String keywords;

    // --- User 연관관계 ---
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    public Chatbot() {}

    public Chatbot(String title,
                   String content,
                   ChatbotStatus chatbotStatus,
                   CType ctype,
                   User user) {
        this.title = title;
        this.content = content;
        this.chatbotStatus = chatbotStatus;
        this.ctype = ctype;
        this.user = user;
    }
}
