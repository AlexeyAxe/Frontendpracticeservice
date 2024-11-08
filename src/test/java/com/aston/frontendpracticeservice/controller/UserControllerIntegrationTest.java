package com.aston.frontendpracticeservice.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.UUID;

import static org.hamcrest.Matchers.hasItems;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Testcontainers
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class UserControllerIntegrationTest {

    @Container
    public static PostgreSQLContainer<?> postgreSQLContainer = new PostgreSQLContainer<>("postgres:14")
            .withDatabaseName("testdb")
            .withUsername("test")
            .withPassword("test");

    @Autowired
    private MockMvc mockMvc;

    @DynamicPropertySource
    static void configureProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgreSQLContainer::getJdbcUrl);
        registry.add("spring.datasource.username", postgreSQLContainer::getUsername);
        registry.add("spring.datasource.password", postgreSQLContainer::getPassword);
    }

    @Test
    void shouldReturnUserBankInfoWhenUserExists() throws Exception {
        UUID userId = UUID.fromString("4856b221-110b-4e7a-a4fc-f308e469c4d9");
        mockMvc.perform(get("/users/short-bank-info/" + userId))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.firstName").value("Влад"))
                .andExpect(jsonPath("$.currentAccount").value("40702810000000000000"))
                .andExpect(jsonPath("$.kbk").value("18210802020016000131"));
    }

    @Test
    void shouldReturnNotFoundForNonExistentUser() throws Exception {
        UUID nonExistUserId = UUID.randomUUID();
        mockMvc.perform(get("/users/short-bank-info/" + nonExistUserId))
                .andExpect(status().isNotFound());
    }

    @Test
    @Sql(scripts = "classpath:insert-test-data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    void shouldReturnUserAllInfoWhenUserExists() throws Exception {
        UUID userId = UUID.fromString("b5ca20bc-841e-4e58-ad53-bbb165e2329e");
        mockMvc.perform(get("/users/all-info/" + userId))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.firstName").value("Петр"))
                .andExpect(jsonPath("$.snils").value("11122233345"))
                .andExpect(jsonPath("$.login").value("login"))
                .andExpect(jsonPath("$.birthday").value("1990-12-12"));
    }

    @Test
    void shouldCreateNewUser() throws Exception {
        String newUser = """
                {
                    "firstName": "Никита",
                    "lastName": "Иванов",
                    "birthday": "1982-12-12",
                    "snils": "11122233341",
                    "login": "ivanov",
                    "password": "password",
                    "passportNumber": "4234444100",
                    "roles": [
                        "ADMIN",
                        "USER"
                    ]
                }""";
        mockMvc.perform(post("/users/new-user")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(newUser))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName").value("Никита"))
                .andExpect(jsonPath("$.lastName").value("Иванов"))
                .andExpect(jsonPath("$.birthday").value("1982-12-12"))
                .andExpect(jsonPath("$.snils").value("11122233341"))
                .andExpect(jsonPath("$.login").value("ivanov"))
                .andExpect(jsonPath("$.roles").isArray())
                .andExpect(jsonPath("$.roles", hasSize(2)))
                .andExpect(jsonPath("$.roles").value(hasItems("ADMIN", "USER")));
    }

    @Test
    void shouldUpdateUserInfo() throws Exception {
        UUID userId = UUID.fromString("4856b221-110b-4e7a-a4fc-f308e469c4d9");
        String updateUser = """
                {
                    "firstName": "Влад",
                    "lastName": "Григорьев",
                    "birthday": "1982-12-12",
                    "snils": "11122233341",
                    "login": "nikita",
                    "password": "password",
                    "passportNumber": "4234444100",
                    "roles": [
                        "ADMIN",
                        "USER"
                    ]
                }""";
        mockMvc.perform(patch("/users/" + userId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(updateUser))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName").value("Влад"))
                .andExpect(jsonPath("$.lastName").value("Григорьев"));
    }

    @Test
    void shouldDeleteUser() throws Exception {
        UUID userId = UUID.fromString("b5ca20bc-841e-4e58-ad53-bbb165e2329e");
        mockMvc.perform(delete("/users/" + userId))
                .andExpect(status().isOk())
                .andExpect(content().string("User with ID = " + userId + " delete successfully"));
    }
}