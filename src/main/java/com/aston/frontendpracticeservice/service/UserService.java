package com.aston.frontendpracticeservice.service;

import com.aston.frontendpracticeservice.domain.dto.UserBankInfoDto;
import com.aston.frontendpracticeservice.domain.entity.User;
import com.aston.frontendpracticeservice.exception.UserNotFoundException;
import com.aston.frontendpracticeservice.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public User findByLogin(String login) {
        return userRepository.findByLogin(login)
                .orElseThrow(() -> new UserNotFoundException("User not found"));
    }

    public UserBankInfoDto getUserBankInfo(UUID id) {
        return userRepository.findBankInfoById(id)
                .orElseThrow(() -> new UserNotFoundException("User with ID = " + id +  " not found"));
    }
}
