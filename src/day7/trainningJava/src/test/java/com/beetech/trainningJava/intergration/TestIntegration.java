package com.beetech.trainningJava.intergration;

import com.beetech.trainningJava.model.PageModel;
import com.beetech.trainningJava.model.ProductInforModel;
import com.beetech.trainningJava.service.IProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureWebMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@SpringBootTest
@AutoConfigureWebMvc
public class TestIntegration {


    @Autowired
    private IProductService productService;
    @Autowired
    private WebApplicationContext context;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
    }

    @Test
    void testGetListIntegration() throws Exception {
        // given
        PageModel<ProductInforModel> pageModel = productService.findAllModel(0, 5, "name");

        // when
        ResultActions resultActions = mockMvc.perform(get("/user/product/list"));

        // then
        resultActions.andExpectAll(
                MockMvcResultMatchers.status().isOk(),
                MockMvcResultMatchers.view().name("product/list"),
                MockMvcResultMatchers.model().attributeExists("page"),
                MockMvcResultMatchers.model().size(1),
                MockMvcResultMatchers.content().contentType("text/html;charset=UTF-8")
        ).andDo(print());
        //
        MvcResult res = resultActions.andReturn();
        PageModel<ProductInforModel> pageModel1 = (PageModel<ProductInforModel>) res.getModelAndView().getModel().get("page");
        assertEquals(pageModel1.getTotalPages(), pageModel.getTotalPages());
        assertEquals(pageModel1.getPageNumber(), pageModel.getPageNumber());
        assertEquals(pageModel1.getItems().size(), pageModel.getItems().size());
    }

    @Test
    @Transactional
    void testGetInformationIntegration() throws Exception {
        //given
        ProductInforModel productInforModel = productService.getProductInforModel(1);

        //when
        ResultActions resultActions = mockMvc.perform(get("/user/product/information").param("id", "1"));

        //then
        resultActions.andExpectAll(
                        MockMvcResultMatchers.status().isOk(),
                        MockMvcResultMatchers.view().name("product/information"),
                        MockMvcResultMatchers.model().attributeExists("product"),
                        MockMvcResultMatchers.model().size(1),
                        MockMvcResultMatchers.content().contentType("text/html;charset=UTF-8")
                ).andDo(print());
        //
        MvcResult mvcResult = resultActions.andReturn();
        ProductInforModel productInforModel1 = (ProductInforModel) mvcResult.getModelAndView().getModel().get("product");
        assertEquals(productInforModel1.getId(), productInforModel.getId());
        assertEquals(productInforModel1.getName(), productInforModel.getName());
        assertEquals(productInforModel1.getPrice(), productInforModel.getPrice());
        assertEquals(productInforModel1.getQuantity(), productInforModel.getQuantity());
    }
}
