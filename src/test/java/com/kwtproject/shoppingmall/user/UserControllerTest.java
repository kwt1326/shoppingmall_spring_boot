package com.kwtproject.shoppingmall.user;

import javax.transaction.Transactional;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kwtproject.shoppingmall.dto.user.RequestSignIn;
import com.kwtproject.shoppingmall.dto.user.RequestSignUp;

import com.kwtproject.shoppingmall.utils.authentication.process.RestAuthenticationRequestFilter;

import org.junit.Before;
import org.junit.jupiter.api.*;
import org.junit.runner.RunWith;

import org.springframework.boot.json.JacksonJsonParser;
import org.springframework.core.env.Environment;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.security.test.web.servlet.
        setup.SecurityMockMvcConfigurers.springSecurity;

@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Transactional
public class UserControllerTest {
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    private WebApplicationContext context;

    @Autowired
    private MockMvc mvc;

    @Autowired
    private Environment environment;

    @Before
    public void setUp() {
        mvc = MockMvcBuilders.webAppContextSetup(context)
                .apply(springSecurity())
                .build();
    }

    @Test
    public void signUp() throws Exception {
        final RequestSignUp signUpObj = RequestSignUp.builder()
                .username("testuser")
                .password("1234")
                .email("kwt1326@naver.com")
                .name("kimwontae")
                .build();

        ResultActions actions = mvc.perform(MockMvcRequestBuilders
                .post("/user/auth/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(signUpObj))
        )
                .andExpect(MockMvcResultMatchers.status().isOk());

        String resultString = actions.andReturn().getResponse().getContentAsString();

        JacksonJsonParser jsonParser = new JacksonJsonParser();
        String token = jsonParser.parseMap(resultString).get("jwt").toString();

        System.out.println(token);
    }

//    @Test
//    @WithMockUser(username = "testuser", password = "1234")
//    public void signIn() throws Exception {
//        final RequestSignIn signInObj = new RequestSignIn("testuser", "1234");
//
//        ResultActions actions = mvc.perform(MockMvcRequestBuilders
//                .post("/user/auth/login")
//                .contentType(MediaType.APPLICATION_JSON)
//                .content(objectMapper.writeValueAsString(signInObj))
//        )
//                .andExpect(MockMvcResultMatchers.status().isOk());
//
//        String resultString = actions.andReturn().getResponse().getContentAsString();
//
//        JacksonJsonParser jsonParser = new JacksonJsonParser();
//        String token = jsonParser.parseMap(resultString).get("access_token").toString();
//    }
}
