package com.aston.frontendpracticeservice.service;

import com.aston.frontendpracticeservice.domain.entity.User;
import com.aston.frontendpracticeservice.domain.dto.UserBankInfoDto;
import com.aston.frontendpracticeservice.exception.UserNotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@Testcontainers
@ActiveProfiles("test")
class UserServiceIntegrationTest {

    @Container
    public static PostgreSQLContainer<?> postgreSQLContainer = new PostgreSQLContainer<>("postgres:14")
            .withDatabaseName("testdb")
            .withUsername("test")
            .withPassword("test");

    @Autowired
    private UserService userService;

    @DynamicPropertySource
    static void configureProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgreSQLContainer::getJdbcUrl);
        registry.add("spring.datasource.username", postgreSQLContainer::getUsername);
        registry.add("spring.datasource.password", postgreSQLContainer::getPassword);
    }

    @Sql(scripts = "classpath:insert-test-data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Test
    void shouldReturnUserWhenUserExists() {
        User user = userService.findByLogin("login");
        assertThat(user).isNotNull();
        assertThat(user.getLogin()).isEqualTo("login");
        assertThat(user.getSnils()).isEqualTo("11122233345");
        assertThat(user.getLastName()).isEqualTo("Петров");
    }

    @Test
    void shouldThrowExceptionWhenUserNotFound() {
        assertThrows(UserNotFoundException.class, () -> userService.findByLogin("notfound"));
    }

//    @Sql(scripts = "classpath:insert-test-data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Test
    void shouldReturnUserBankInfoWhenUserExists() {
        UUID userId = UUID.fromString("b5ca20bc-841e-4e58-ad53-bbb165e2329e");
        UserBankInfoDto userBankInfoDto = userService.getUserBankInfo(userId);
        assertThat(userBankInfoDto).isNotNull();
        assertThat(userBankInfoDto.getFirstName()).isEqualTo("Петр");
        assertThat(userBankInfoDto.getCurrentAccount()).isEqualTo("40702810000000000001");
        assertThat(userBankInfoDto.getKbk()).isEqualTo("18210802020016000130");
    }

    @Test
    void shouldThrowExceptionWhenUserBankInfoNotFound() {
        UUID nonExistUserId = UUID.randomUUID();
        assertThrows(UserNotFoundException.class, () -> userService.getUserBankInfo(nonExistUserId));
    }
}
