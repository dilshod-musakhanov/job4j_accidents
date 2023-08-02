package ru.job4j.accidents.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import ru.job4j.accidents.Main;

@SpringBootTest(classes = Main.class)
@AutoConfigureMockMvc
class LoginControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Test
    @WithMockUser
    public void shouldReturnLoginPageWithNoErrorMessage() throws Exception {
        mockMvc.perform(get("/users/login"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("user/login"))
                .andExpect(model().attributeDoesNotExist("errorMessage"));
    }

    @Test
    @WithMockUser
    public void shouldReturnLoginPageWithErrorMessage() throws Exception {
        mockMvc.perform(get("/users/login").param("error", "true"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("user/login"))
                .andExpect(model().attribute("errorMessage", "Username or Password is incorrect !!"));
    }

    @Test
    @WithMockUser
    public void shouldShowLoginPageWithLogoutMessage() throws Exception {
        mockMvc.perform(get("/users/login").param("logout", "true"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("user/login"))
                .andExpect(model().attribute("errorMessage", "You have been successfully logged out !!"));
    }

}