package com.aston.frontendpracticeservice.domain.dto.response;

import com.aston.frontendpracticeservice.security.Role;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserAllInfoResponseDto {

    private String firstName;

    private String lastName;

    private LocalDate birthday;

    private String snils;

    private String login;

    private String password;

    private String passportNumber;

    private Set<Role> roles;
}
