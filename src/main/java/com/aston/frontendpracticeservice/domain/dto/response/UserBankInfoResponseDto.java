package com.aston.frontendpracticeservice.domain.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserBankInfoResponseDto {

    private String firstName;

    private String currentAccount;

    private String kbk;
}