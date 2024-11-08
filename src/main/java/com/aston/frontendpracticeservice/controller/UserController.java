package com.aston.frontendpracticeservice.controller;

import com.aston.frontendpracticeservice.domain.dto.request.UserRequestDto;
import com.aston.frontendpracticeservice.domain.dto.response.UserAllInfoResponseDto;
import com.aston.frontendpracticeservice.domain.dto.response.UserBankInfoResponseDto;
import com.aston.frontendpracticeservice.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@Valid
@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    @GetMapping("/short-bank-info/{id}")
    public ResponseEntity<UserBankInfoResponseDto> getUserBankInfo(@PathVariable UUID id) {
        return ResponseEntity.ok(userService.getUserBankInfo(id));
    }

    @GetMapping("/all-info/{id}")
    public ResponseEntity<UserAllInfoResponseDto> getUserAllInfo(@PathVariable UUID id) {
        return ResponseEntity.ok(userService.getAllInfo(id));
    }

    @PostMapping("/new-user")
    public ResponseEntity<UserAllInfoResponseDto> createUser(@RequestBody @Valid UserRequestDto userRequestDto) {
        return ResponseEntity.ok(userService.createUser(userRequestDto));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<UserAllInfoResponseDto> updateInfoUser(@PathVariable UUID id,
                                                                 @RequestBody @Valid UserRequestDto userRequestDto) {
        UserAllInfoResponseDto userAllInfoResponseDto = userService.updateInfoUser(id, userRequestDto);
        return ResponseEntity.ok(userAllInfoResponseDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable UUID id) {
        userService.deleteUser(id);
        return ResponseEntity.ok("User with ID = " + id + " delete successfully");
    }
}