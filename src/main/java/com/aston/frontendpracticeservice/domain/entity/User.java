package com.aston.frontendpracticeservice.domain.entity;

import com.aston.frontendpracticeservice.security.Role;
import jakarta.persistence.CollectionTable;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@Builder
@Entity
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @NotBlank(message = "Имя не должно быть пустым")
    @Pattern(regexp = "[А-ЯA-Z][а-яa-z]{1,29}",
            message = "Имя может содержать от 2 до 30 букв. Первая буква должна быть заглавной")
    private String firstName;

    @NotBlank(message = "Фамилия не должна быть пустой")
    @Pattern(regexp = "[А-ЯA-Z][а-яa-z]{1,29}",
            message = "Фамилия может содержать от 2 до 30 букв. Первая буква должна быть заглавной")
    private String lastName;

    @Past
    @NotNull(message = "Дата рождения не должна быть пустой")
    private LocalDate birthday;

    @Pattern(regexp = "\\d{11}", message = "СНИЛС состоит из 11 цифр.")
    private String snils;

    @NotBlank(message = "Логин не должен быть пустым")
    @Pattern(regexp = "[А-ЯA-Zа-яa-z0-9]{4,30}",
            message = "Логин может содержать от 4 до 30 букв или цифр.")
    private String login;

    @NotBlank(message = "Пароль не должен быть пустым")
    @Pattern(regexp = "[А-ЯA-Zа-яa-z0-9]{8,30}",
            message = "Пароль может содержать от 8 до 30 букв или цифр.")
    private String password;

    @Pattern(regexp = "\\d{10}", message = "Номер паспорта должен содержать 10 цифр.")
    private String passportNumber;

    @Enumerated(EnumType.STRING)
    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id"))
    private Set<Role> roles;

    @NotNull
    @OneToOne
    @JoinColumn(name = "requisites_id")
    private Requisites requisites;
}
