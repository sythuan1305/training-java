package com.beetech.trainningJava.controller.mvc.user;

import com.beetech.trainningJava.entity.ProductEntity;
import com.beetech.trainningJava.jwt.JwtTokenProvider;
import com.beetech.trainningJava.model.PageModel;
import com.beetech.trainningJava.model.ProductInforModel;
import com.beetech.trainningJava.service.IProductService;
import org.hamcrest.Matcher;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTestContextBootstrapper;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.MockBeans;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.test.context.BootstrapWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.web.servlet.ModelAndView;

import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.web.servlet.function.ServerResponse.status;

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