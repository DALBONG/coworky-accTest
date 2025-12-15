package com.coworky.coworky.service;

import com.coworky.coworky.domain.CType;
import com.coworky.coworky.domain.Chatbot;
import com.coworky.coworky.domain.ChatbotStatus;
import com.coworky.coworky.domain.User;
import com.coworky.coworky.repository.ChatbotRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ChatbotService {

    private final ChatbotRepository chatbotRepository;

    @Cacheable(value = "chatbots", key = "'allChatbots'")
    public List<Chatbot> findAllChatbots() {
        System.out.println(">>> [DB Query] Chatbot 목록을 DB에서 조회합니다.");
        return chatbotRepository.findAll();
    }

    public Chatbot findItemById(Long id) {
        return chatbotRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Chatbot Item Not Found With Id: " + id));
    }

    @Transactional
    @CacheEvict(value = "chatbots",allEntries = true)
    public Chatbot createItem(CType ctype, Chatbot item, User user){
        item.setCtype(ctype);
        item.setChatbotStatus(ChatbotStatus.EXIST);
        item.setUser(user);
        return chatbotRepository.save(item);
    }

    @Transactional
    @CacheEvict(value = "chatbots", allEntries = true)
    public Chatbot updateItem(Long id, Chatbot updatedItem) {
        // 1. 기존 항목 조회 및 유효성 검사
        Chatbot existingItem = findItemById(id);

        // 2. 항목 내용 업데이트 (Dirty Checking)
        existingItem.setTitle(updatedItem.getTitle());
        existingItem.setContent(updatedItem.getContent());
        existingItem.setKeywords(updatedItem.getKeywords());

        // 3. 트랜잭션 종료 시 자동 저장 (return existingItem)
        return existingItem;
    }

    @Transactional
    @CacheEvict(value = "chatbots", allEntries = true)
    public void deleteItem(Long id){
        findItemById(id); // 삭제 전, 항목 존재 여부 확인
        chatbotRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public Page<Chatbot> searchChatbots(CType cType, ChatbotStatus status, String query, int page, int size){
        // 1. 페이지네이션 객체 생성 (page=0부터 시작, size=20 설정)
        // 정렬 조건도 Pageable에 포함하여 쿼리에 반영
        Pageable pageable = PageRequest.of(page, size);

        // 2. 검색어 정리 (null 또는 공백 제거)
        String cleanedQuery = (query == null || query.trim().isEmpty()) ? "" : query.trim();

        // 3. 검색어 유무에 따라 다른 Repository 메소드호출
        if(cleanedQuery.isEmpty()) {
            // 검색어가 없을 때 : MATCH 조건이 없는 쿼리 사용
            return chatbotRepository.findChatbotsByCTypeAndStatus(
                    cType.name(),
                    status.name(),
                    pageable
            );
        }else {
            // 검색어가 있을 때 : Full-Text Search 쿼리 사용
            return chatbotRepository.findChatbotsByFullTextSearchTuned(
                    cType.name(),
                    status.name(),
                    cleanedQuery,
                    pageable
            );
        }
    }
}
