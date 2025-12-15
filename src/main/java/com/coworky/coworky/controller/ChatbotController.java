package com.coworky.coworky.controller;

import com.coworky.coworky.domain.CType;
import com.coworky.coworky.domain.Chatbot;
import com.coworky.coworky.domain.ChatbotStatus;
import com.coworky.coworky.domain.User;
import com.coworky.coworky.dto.ChatbotResponseDto;
import com.coworky.coworky.service.ChatbotService;
import com.coworky.coworky.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.coworky.coworky.domain.CType.valueOf;
import static com.coworky.coworky.domain.ChatbotStatus.EXIST;
import static java.util.Locale.ROOT;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class ChatbotController {

    private final ChatbotService chatbotService;
    private final UserService userService;

    private CType getType(String type) {
        try {
            return valueOf(type.toUpperCase(ROOT));
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("invalid chatbot type: " + type);
        }
    }

    // 목록 조회 및 검색/페이지네이션 (GET /api/{type}s)
    // 기존의 단순 리스트 조회 메소드를 이 강력한 메소드로 대체
    @GetMapping("/{type}s")
    public ResponseEntity<Page<ChatbotResponseDto>> getItems(
            @PathVariable String type,
            @RequestParam(required = false, defaultValue = "") String query, // 검색어
            @RequestParam(defaultValue = "0") int page,                      // 페이지 번호
            @RequestParam(defaultValue = "20") int size                     // 페이지 크기
    ) {
        CType ctype = getType(type);

        // Service 계층의 searchChatbots 호출
        Page<Chatbot> pageResult = chatbotService.searchChatbots(
                ctype,
                EXIST,
                query,
                page,
                size
        );

        // Entity Page를 DTO Page로 변환
        Page<ChatbotResponseDto> responseDtos = pageResult.map(ChatbotResponseDto::fromEntity);

        return ResponseEntity.ok(responseDtos);
    }

    // 단일 항목 상세 조회 (Read - for Edit Modal data fetch)
    // GET /api/{type}s/{id}
    @GetMapping("/{type}s/{id}")
    public ResponseEntity<ChatbotResponseDto> getItem(
            @PathVariable String type,
            @PathVariable Long id) {

        getType(type); // 유효성 검사

        // Service에서 Chatbot 엔티티 조회
        Chatbot item = chatbotService.findItemById(id);

        // 엔티티를 DTO로 변환하여 응답
        ChatbotResponseDto responseDto = ChatbotResponseDto.fromEntity(item);

        return ResponseEntity.ok(responseDto);
    }

    // 생성
    @PostMapping("/{type}s")
    public ResponseEntity<ChatbotResponseDto> createItem(
            @PathVariable String type,
            @RequestBody Chatbot item) {

        CType ctype = getType(type);

        User user = userService.findById(1L);

        Chatbot createItem = chatbotService.createItem(ctype, item, user);

        ChatbotResponseDto responseDto = ChatbotResponseDto.fromEntity(createItem);

        return ResponseEntity.ok(responseDto);
    }

    // 수정
    @PutMapping("/{type}s/{id}")
    public ResponseEntity<ChatbotResponseDto> updateItem(
            @PathVariable String type,
            @PathVariable Long id,
            @RequestBody Chatbot updatedItem
    ) {

        // 유효성 검사
        getType(type);

        // Service에서 수정 처리
        Chatbot result = chatbotService.updateItem(id, updatedItem);

        ChatbotResponseDto responseDto = ChatbotResponseDto.fromEntity(result);

        return ResponseEntity.ok(responseDto);
    }

    // 삭제
    @DeleteMapping("/{type}s/{id}")
    public ResponseEntity<Void> deleteItem(
            @PathVariable String type,
            @PathVariable Long id) {

        // 유효성 검사
        getType(type);

        // Service에서 삭제 처리
        chatbotService.deleteItem(id);

        // 204 No Content 반환
        return ResponseEntity.noContent().build();
    }

}
