package daangnmarket.daangn.project.apiTest;


import com.fasterxml.jackson.databind.ObjectMapper;
import daangnmarket.daangn.project.config.SecurityConfig;
import daangnmarket.daangn.project.controller.ProductController;
import daangnmarket.daangn.project.domain.product.ProductCategory;
import daangnmarket.daangn.project.dto.ProductDTO;
import daangnmarket.daangn.project.service.ProductService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;


@ExtendWith(MockitoExtension.class)
@WebMvcTest(controllers = ProductController.class,
        excludeFilters = { @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = SecurityConfig.class) })
@MockBean(JpaMetamodelMappingContext.class)
@AutoConfigureMockMvc(addFilters = false)
public class ProductTest {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    MockMvc mvc;

    @MockBean
    private ProductService productService;

    @Test
    public void 중고장터_게시물_생성() throws Exception {
        // given
        String content = objectMapper.writeValueAsString(
                new ProductDTO.SaveDto("josunghyeon", "냉장고 팔아요",
                        "아이템 설명입니다.", 1000000, ProductCategory.CLOTHES, null)
        );

        MockHttpServletRequestBuilder builder =
                post("/products")
                        .param("writer", "sunghyeon")
                        .param("title", "냉장고 팔아요")
                        .param("description", "최신 냉장고!")
                        .param("price", String.valueOf(1000000))
                        .contentType(MediaType.APPLICATION_JSON);

        MvcResult result = mvc.perform(builder).andReturn();
        String message = result.getResolvedException().getMessage();
        System.out.println("message = " + message);

        assertThat(HttpStatus.BAD_REQUEST);
    }
}
