package com.study.springboot.security;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class SecurityIntegrationTests {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ApplicationContext applicationContext;

    @Test
    @DisplayName("SecurityFilterChain bean named 'filterChain' is present (Spring Security is enabled)")
    void securityFilterChainBeanPresent() {
        SecurityFilterChain chain = applicationContext.getBean("filterChain", SecurityFilterChain.class);
        assertThat(chain)
                .as("'filterChain' from SecurityConfig should be registered")
                .isNotNull();
    }

    @Test
    @DisplayName("Home page is accessible without authentication due to permitAll")
    void homeAccessibleWithoutAuth() throws Exception {
        mockMvc.perform(get("/"))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Form login is disabled, so /login should not be provided (404)")
    void loginEndpointDisabled() throws Exception {
        mockMvc.perform(get("/login"))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("Static resource is accessible without authentication")
    void staticResourceAccessible() throws Exception {
        mockMvc.perform(get("/js/jquery-3.7.1.min.js"))
                .andExpect(status().isOk());
    }
}
