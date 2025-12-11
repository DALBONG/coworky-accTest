package com.coworky.coworky.controller;

import com.coworky.coworky.domain.Accommodation;
import com.coworky.coworky.service.AccommodationService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;
import tools.jackson.databind.ObjectMapper;


import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@WebMvcTest(AccommodationController.class)
class AccommodationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private AccommodationService accService;

    @Autowired
    private ObjectMapper objMapper;

    @Test
    @DisplayName("GET /api/accommodations")
    public void 호출성공() throws Exception {
        //given
        List<AccommodationRe>

        //when

        //then
    }





}