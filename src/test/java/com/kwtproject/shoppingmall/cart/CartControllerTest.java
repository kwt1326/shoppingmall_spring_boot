package com.kwtproject.shoppingmall.cart;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kwtproject.shoppingmall.domain.ProductEntity;
import com.kwtproject.shoppingmall.dto.cart.RequestAddCartItem;
import com.kwtproject.shoppingmall.repository.product.IProductRepository;
import com.kwtproject.shoppingmall.repository.user.IUserRepository;
import com.kwtproject.shoppingmall.service.CartService;
import com.kwtproject.shoppingmall.testUtils.CreateTestEntity;

import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.env.Environment;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import javax.transaction.Transactional;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;

@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Transactional
public class CartControllerTest {
//    private final ObjectMapper objectMapper = new ObjectMapper();
//
//    @Autowired
//    private WebApplicationContext context;
//
//    @Autowired
//    private MockMvc mvc;
//
//    @Autowired
//    private Environment environment;
//
//    @Autowired
//    private CartService service;
//
//    @Autowired
//    private IProductRepository productRepository;
//
//    @Autowired
//    private IUserRepository userRepository;
//
//    @Before
//    public void setUp() {
//        mvc = MockMvcBuilders.webAppContextSetup(context)
//                .apply(springSecurity())
//                .build();
//    }

//    @Test
//    @WithMockUser(roles = "USER_COMMON")
//    public void addCartItem() throws Exception {
//        CreateTestEntity createUtil = new CreateTestEntity();
//
//        ProductEntity productEntity = createUtil.createProduct(productRepository, userRepository);
//
//        short quantity = 10;
//
//        final RequestAddCartItem cartItem = RequestAddCartItem.builder()
//                .quantity(quantity)
//                .productId(productEntity.getId())
//                .build();
//
//        ResultActions actions = mvc.perform(MockMvcRequestBuilders
//                .post("/cart")
//                .contentType(MediaType.APPLICATION_JSON)
//                .content(objectMapper.writeValueAsString(cartItem))
//        )
//                .andExpect(MockMvcResultMatchers.status().isOk());
//
//        String resultString = actions.andReturn().getResponse().getContentAsString();
//
//        System.out.println(resultString);
//    }
}
