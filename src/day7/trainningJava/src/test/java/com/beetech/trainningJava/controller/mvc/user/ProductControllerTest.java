package com.beetech.trainningJava.controller.mvc.user;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

//@ExtendWith(MockitoExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
class ProductControllerTest {
    @Autowired
    MockMvc mockMvc;

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void listProduct() throws Exception {
        // when
        ResultActions res = mockMvc.perform(get("/user/product/list"));

        // then
        res.andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("product/list"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("page"))
                .andExpect(MockMvcResultMatchers.model().size(1))
                .andExpect(MockMvcResultMatchers.content().contentType("text/html;charset=UTF-8"));
    }

    @Test
    void getInformation() throws Exception {
        // when
        ResultActions res = mockMvc.perform(get("/user/product/information").param("id", "1"));

        // then
        res.andExpectAll(
                MockMvcResultMatchers.status().isOk(),
                MockMvcResultMatchers.view().name("product/information"),
                MockMvcResultMatchers.model().attributeExists("product"),
                MockMvcResultMatchers.model().size(1),
                MockMvcResultMatchers.content().contentType("text/html;charset=UTF-8")
        );
    }
}