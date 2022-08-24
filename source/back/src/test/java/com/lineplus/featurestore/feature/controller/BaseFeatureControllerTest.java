package com.lineplus.featurestore.feature.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lineplus.featurestore.feature.domain.BaseFeature;
import com.lineplus.featurestore.feature.domain.BaseFeatureGroup;
import com.lineplus.featurestore.feature.domain.dto.response.BaseFeatureGroupResponseDto;
import com.lineplus.featurestore.feature.repository.custom.BaseFeatureCustomRepository;
import com.lineplus.featurestore.global.reponse.FeatureStoreResponse;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.content;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
class BaseFeatureControllerTest {

    @Autowired
    private MockMvc mockMvc; // mockMvc 생성
    @Autowired
    BaseFeatureCustomRepository baseFeatureCustomRepository;

    private ObjectMapper objectMapper = new ObjectMapper();



    BaseFeature baseFeature;

    @Test
    void getBaseFeatureGroup() throws Exception {
        MvcResult mvcResult =mockMvc.perform(get("/baseFeature/group")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.result.base_feature_group_list").isArray())
                .andDo(print())
                .andReturn();
   }

   @Test
    void getBaseFeatureLabel() throws Exception {
       MvcResult mvcResult =mockMvc.perform(get("/baseFeature/feature/45")
                       .contentType(MediaType.APPLICATION_JSON))
               .andExpect(status().isOk())
               .andExpect(MockMvcResultMatchers.jsonPath("$.result.attribute_list").isArray())
               .andDo(print())
               .andReturn();
   }
}