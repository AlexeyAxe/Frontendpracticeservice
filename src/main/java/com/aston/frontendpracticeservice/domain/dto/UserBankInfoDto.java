package com.aston.frontendpracticeservice.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserBankInfoDto {

    private String firstName;

    private String currentAccount;

    private String kbk;
}