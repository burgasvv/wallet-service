package org.burgas.walletservice.controller;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static java.nio.charset.StandardCharsets.UTF_8;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.http.MediaType.TEXT_PLAIN;

@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(value = MethodOrderer.OrderAnnotation.class)
class OperationControllerTest {

    private static final Logger log = LoggerFactory.getLogger(OperationControllerTest.class);
    @Autowired
    MockMvc mockMvc;

    private String getId() throws Exception {
        MvcResult mvcResult = this.mockMvc
                .perform(MockMvcRequestBuilders.post("/api/v1/wallets/create"))
                .andReturn();
        String content = mvcResult.getResponse().getContentAsString();
        content = content.replaceAll("\\{", "");
        content = content.replaceAll("}", "");
        String[] strings = content.split(",");
        String id = "";
        for (String temp : strings) {
            if (temp.startsWith("\"id\"")) {
                id = temp.split(":")[1];
                id = id.replaceAll("\"", "");
            }
        }
        return id;
    }

    @Test
    @Order(value = 1)
    void getOperationsByWallet() throws Exception {
        String id = getId();
        this.mockMvc
                .perform(
                        MockMvcRequestBuilders.get("/api/v1/operations/by-wallet")
                                .param("walletId", id)
                )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(APPLICATION_JSON))
                .andDo(result -> result.getResponse().setCharacterEncoding("UTF-8"))
                .andDo(result -> log.info(result.getResponse().getContentAsString()))
                .andReturn();
    }

    @Test
    @Order(value = 2)
    void performOperation() throws Exception {
        String id = getId();
        String operation = "{\"walletId\":\"" + id + "\", \"amount\":1000, \"operationType\":\"DEPOSIT\"}";
        this.mockMvc
                .perform(
                        MockMvcRequestBuilders.post("/api/v1/operations/perform")
                                .contentType(APPLICATION_JSON)
                                .content(operation)
                )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(new MediaType(TEXT_PLAIN, UTF_8)))
                .andDo(result -> result.getResponse().setCharacterEncoding("UTF-8"))
                .andDo(result -> log.info(result.getResponse().getContentAsString()))
                .andReturn();
    }
}