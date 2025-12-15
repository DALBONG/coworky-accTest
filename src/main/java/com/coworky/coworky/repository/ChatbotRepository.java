package com.coworky.coworky.repository;

import com.coworky.coworky.domain.CType;
import com.coworky.coworky.domain.Chatbot;
import com.coworky.coworky.domain.ChatbotStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ChatbotRepository extends JpaRepository<Chatbot,Long> {

    @Query("select c from Chatbot c " +
           "left join fetch c.user " +
           "where c.ctype = :ctype " +
            " and c.chatbotStatus = :status " +
            " and (lower(c.title) like %:query% or lower(c.content) like %:query% or lower(c.keywords) like %:query%)"
    )
    // 튜닝 전 (슬로우 쿼리 유발 쿼리)
    List<Chatbot> findActiveItemsByQuery(
            @Param("ctype") CType cType,
            @Param("status")ChatbotStatus status,
            @Param("query") String query
            );

    // 튜닝 후 : P99 지연을 해결하는 최종 쿼리
    @Query(value =
            "select c.*, " +
            "u.user_id as user_user_id, " +
            "u.name as user_name, " +
            "u.role as user_role, " +
            "u.company_id as user_company_id " +
            "from TB_Chatbot c " +
            "left join TB_User u on c.user_id = u.user_id " +
            "where c.ctype = :ctype " +
            "  and c.status = :status " +
            // Full-Text Search 적용: Like 대신 MATCH...AGAINST 사용
            "  and MATCH(c.title, c.content, c.keywords) AGAINST (:query IN BOOLEAN MODE) " +
            "order by c.created_date DESC",

            // JPQL이 아닌 SQL 문법을 사용했기 때문에 true 설정
            nativeQuery = true)

    // 반환 타입: Page<Chatbot>으로 변경하여 Pageable을 통해 LIMIT/OFFSET 자동 적용
    Page<Chatbot> findChatbotsByFullTextSearchTuned(
            @Param("ctype") String cType,
            @Param("status") String status,
            @Param("query") String query,
            Pageable pageable
    );

    @Query(value = "select c.*, " +
                   "u.user_id as user_user_id, " +
                   "u.name as user_name, " +
                   "u.role as user_role, " +
                   "u.company_id as user_company_id " +
                   "from TB_Chatbot c " +
                   "left join TB_User u on c.user_id = u.user_id " +
                   "where c.ctype = :ctype " +
                   "  and c.status = :status " +
                   "order by c.created_date DESC",
                   nativeQuery = true)
    Page<Chatbot> findChatbotsByCTypeAndStatus(
            @Param("ctype") String cType,
            @Param("status") String status,
            Pageable pageable
    );



    // List<Chatbot> findByCTypeAndChatbotStatus(CType ctype, ChatbotStatus status);
}
