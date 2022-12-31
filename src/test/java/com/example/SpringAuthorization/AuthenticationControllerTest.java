package com.example.SpringAuthorization;

import com.example.SpringAuthorization.dto.LoginRequestDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AuthenticationControllerTest {
    //@WithMockUser 사용하지 않음

    private MockMvc mvc;

    private String JWTToken;
    @Autowired
    private WebApplicationContext context;

    @Before
    public void setup() throws Exception {
        mvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity())
                .build();

        //given
        String username = "loose";
        String password = "1234";

        LoginRequestDto loginRequestDto = new LoginRequestDto();
        loginRequestDto.setUsername(username);
        loginRequestDto.setPassword(password);

        String url = "http://localhost:8080/login";

        //when
        MvcResult mvcResult = mvc.perform(post(url)
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(loginRequestDto)))
                .andExpect(status().isOk())
                .andReturn();

        JWTToken = mvcResult.getResponse().getHeader("Authorization");

    }

    @Test
    public void 시큐리티_공통설정에의해_인가된다() throws Exception {
        mvc.perform(get("/api/v1/admin")
                .header("Authorization", JWTToken))
                .andExpect(status().isOk());
    }
    @Test
    public void 시큐리티_공통설정에의해_인가되지않는다() throws Exception {
        mvc.perform(get("/api/v1/manage")
                .header("Authorization", JWTToken))
                .andExpect(status().isForbidden());
    }

    @Test
    public void 시큐리티_메소드_시큐리티에의해_인가된다() throws Exception {
        mvc.perform(get("/method1")
                .header("Authorization", JWTToken))
                .andExpect(status().isOk());
    }

    @Test
    public void 시큐리티_메소드_시큐리티에의해_인가되지않는다() throws Exception {
        mvc.perform(get("/method2")
                .header("Authorization", JWTToken))
                .andExpect(status().isForbidden());
    }

    @Test
    public void Enum을_사용하여_인가된다() throws Exception {
        mvc.perform(get("/methodAndEnum1")
                .header("Authorization", JWTToken))
                .andExpect(status().isOk());
    }

    @Test
    public void Enum을_사용하여_인가되지않는다() throws Exception {
        mvc.perform(get("/methodAndEnum2")
                .header("Authorization", JWTToken))
                .andExpect(status().isForbidden());
    }

    @Test
    public void customize한_어노테이션을_사용하여_인가된다() throws Exception {
        mvc.perform(get("/customizeAuthen1")
                .header("Authorization", JWTToken))
                .andExpect(status().isOk());
    }

    @Test
    public void customize한_어노테이션을_사용하여_인가되지않는다() throws Exception {
        mvc.perform(get("/customizeAuthen2")
                .header("Authorization", JWTToken))
                .andExpect(status().isForbidden());
    }
}
