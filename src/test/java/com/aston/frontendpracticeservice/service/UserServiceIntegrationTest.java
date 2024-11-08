package com.aston.frontendpracticeservice.service;

import com.aston.frontendpracticeservice.domain.dto.request.UserRequestDto;
import com.aston.frontendpracticeservice.domain.dto.response.UserAllInfoResponseDto;
import com.aston.frontendpracticeservice.domain.dto.response.UserBankInfoResponseDto;
import com.aston.frontendpracticeservice.domain.entity.User;
import com.aston.frontendpracticeservice.exception.UserNotFoundException;
import com.aston.frontendpracticeservice.security.Role;
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

import java.time.LocalDate;
import java.util.Set;
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

    @Test
    void shouldReturnUserBankInfoWhenUserExists() {
        UUID userId = UUID.fromString("b5ca20bc-841e-4e58-ad53-bbb165e2329e");
        UserBankInfoResponseDto userBankInfoResponseDto = userService.getUserBankInfo(userId);
        assertThat(userBankInfoResponseDto).isNotNull();
        assertThat(userBankInfoResponseDto.getFirstName()).isEqualTo("Петр");
        assertThat(userBankInfoResponseDto.getCurrentAccount()).isEqualTo("40702810000000000001");
        assertThat(userBankInfoResponseDto.getKbk()).isEqualTo("18210802020016000130");
    }

    @Test
    void shouldThrowExceptionWhenUserBankInfoNotFound() {
        UUID nonExistUserId = UUID.randomUUID();
        assertThrows(UserNotFoundException.class, () -> userService.getUserBankInfo(nonExistUserId));
    }

    @Test
    @Sql(scripts = "classpath:insert-test-data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    void shouldReturnUserAllInfoWhenUserExists() {

        UUID userId = UUID.fromString("b5ca20bc-841e-4e58-ad53-bbb165e2329e");

        UserAllInfoResponseDto userAllInfoResponseDto = userService.getAllInfo(userId);

        assertThat(userAllInfoResponseDto).isNotNull();
        assertThat(userAllInfoResponseDto.getFirstName()).isEqualTo("Иван");
        assertThat(userAllInfoResponseDto.getBirthday()).isEqualTo("1985-02-01");
        assertThat(userAllInfoResponseDto.getLogin()).isEqualTo("login");
        assertThat(userAllInfoResponseDto.getSnils()).isEqualTo("22233344455");
    }

    @Test
    @Sql(scripts = "classpath:insert-test-data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    void shouldCreateNewUser() {
        UserRequestDto userRequestDto = UserRequestDto.builder()
                .firstName("Никита")
                .lastName("Иванов")
                .birthday(LocalDate.of(1985, 12, 1))
                .snils("11122233341")
                .login("nikita")
                .password("password")
                .passportNumber("4234444100")
                .roles(Set.of(Role.ADMIN, Role.USER))
                .build();

        UserAllInfoResponseDto createdUser = userService.createUser(userRequestDto);
        assertThat(createdUser).isNotNull();
        assertThat(createdUser.getLogin()).isEqualTo("nikita");
        assertThat(createdUser.getFirstName()).isEqualTo("Никита");
        assertThat(createdUser.getRoles()).containsExactlyInAnyOrder(Role.ADMIN, Role.USER);
    }

    @Sql(scripts = "classpath:insert-test-data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Test
    void shouldUpdateUserInfo() {
        UUID userId = UUID.fromString("b5ca20bc-841e-4e58-ad53-bbb165e2329e");
        UserRequestDto userRequestDto = UserRequestDto.builder()
                .firstName("Иван")
                .lastName("Иванов")
                .birthday(LocalDate.of(1985, 2, 1))
                .snils("22233344455")
                .login("login")
                .password("newpassword")
                .passportNumber("1234567890")
                .roles(Set.of(Role.ADMIN))
                .build();

        UserAllInfoResponseDto updatedUser = userService.updateInfoUser(userId, userRequestDto);
        assertThat(updatedUser).isNotNull();
        assertThat(updatedUser.getFirstName()).isEqualTo("Иван");
        assertThat(updatedUser.getLastName()).isEqualTo("Иванов");
        assertThat(updatedUser.getBirthday()).isEqualTo("1985-02-01");
        assertThat(updatedUser.getRoles()).containsExactly(Role.ADMIN);
    }

    @Test
    @Sql(scripts = "classpath:insert-test-data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    void shouldDeleteUser() {
        UUID userId = UUID.fromString("b5ca20bc-841e-4e58-ad53-bbb165e2329e");
        userService.deleteUser(userId);
        assertThrows(UserNotFoundException.class, () -> userService.getAllInfo(userId));
    }
}