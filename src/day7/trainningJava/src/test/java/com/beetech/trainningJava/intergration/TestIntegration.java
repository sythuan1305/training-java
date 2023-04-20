package com.beetech.trainningJava.intergration;

import com.beetech.trainningJava.entity.ProductEntity;
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

import java.math.BigDecimal;

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
        Integer pageNumber = 0;
        PageModel<ProductInforModel> pageModel = productService.findPageModelByProductInforModelIndex(pageNumber, 5, "name");

        // when
        ResultActions resultActions = mockMvc.perform(get("/user/product/list").param("pageNumber", pageNumber.toString()));

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
        PageModel<ProductInforModel> pageModelRes = (PageModel<ProductInforModel>) res.getModelAndView().getModel().get("page");
        assertEquals(pageModelRes.getTotalPages(), pageModel.getTotalPages());
        assertEquals(pageModelRes.getPageNumber(), pageModel.getPageNumber());
        assertEquals(pageModelRes.getItems().size(), pageModel.getItems().size());

        for (int i = 0; i < pageModelRes.getItems().size(); i++) {
            assertEquals(pageModelRes.getItems().get(i).getId(), pageModel.getItems().get(i).getId());
            assertEquals(pageModelRes.getItems().get(i).getName(), pageModel.getItems().get(i).getName());
            assertEquals(pageModelRes.getItems().get(i).getPrice(), pageModel.getItems().get(i).getPrice());
            assertEquals(pageModelRes.getItems().get(i).getQuantity(), pageModel.getItems().get(i).getQuantity());
        }
    }

    @Test
    @Transactional
    void testGetInformationIntegration() throws Exception {
        //given
        ProductEntity productEntity = productService.saveProductEntity(new ProductEntity("name123", BigDecimal.TEN, 10));
        ProductInforModel productInforModel = productService.getProductInforModelById(productEntity.getId());

        //when
        ResultActions resultActions = mockMvc.perform(get("/user/product/information").param("id", productInforModel.getId().toString()));

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
