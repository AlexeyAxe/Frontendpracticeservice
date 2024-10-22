package com.aston.frontendpracticeservice.domain.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@Builder
@Entity
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Table(name = "requisites")
public class Requisites {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @NotBlank(message = "Расчетный счет не должен быть пустым")
    @Pattern(regexp = "^\\d{20}$", message = "Расчетный счет должен состоять из 20 цифр")
    private String currentAccount;

    @NotBlank(message = "БИК не должен быть пустым")
    @Pattern(regexp = "^\\d{9}$", message = "БИК должен состоять из 9 цифр")
    private String bic;

    @NotBlank(message = "Корреспондентский счет не должен быть пустым")
    @Pattern(regexp = "^\\d{20}$", message = "Корреспондентский счет должен состоять из 20 цифр")
    private String correspondentAccount;

    @NotBlank(message = "ИНН не должен быть пустым")
    @Pattern(regexp = "\\d{12}", message = "ИНН состоит из 12 цифр")
    private String inn;

    @NotBlank(message = "КПП не должен быть пустым")
    @Pattern(regexp = "\\d{9}$", message = "КПП должен состоять из 9 цифр")
    private String kpp;

    @NotBlank(message = "КБК не должен быть пустым")
    @Pattern(regexp = "\\d{20}$", message = "КБК должен состоять из 20 цифр")
    private String kbk;
}