package com.aston.frontendpracticeservice.controller;

import com.aston.frontendpracticeservice.domain.dto.UserBankInfoDto;
import com.aston.frontendpracticeservice.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    @GetMapping("/short-bank-info/{id}")
    public ResponseEntity<UserBankInfoDto> getUserBankInfo(@PathVariable UUID id){
        return ResponseEntity.ok(userService.getUserBankInfo(id));
    }
}