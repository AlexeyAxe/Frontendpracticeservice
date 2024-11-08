package com.aston.frontendpracticeservice.service;

import com.aston.frontendpracticeservice.domain.dto.request.UserRequestDto;
import com.aston.frontendpracticeservice.domain.dto.response.UserAllInfoResponseDto;
import com.aston.frontendpracticeservice.domain.dto.response.UserBankInfoResponseDto;
import com.aston.frontendpracticeservice.domain.entity.User;
import com.aston.frontendpracticeservice.domain.mapper.UserMapper;
import com.aston.frontendpracticeservice.exception.UserNotFoundException;
import com.aston.frontendpracticeservice.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@CacheConfig(cacheNames = "users")
public class UserService {

    private final UserRepository userRepository;

    private final UserMapper userMapper;

    @Cacheable(key = "#login")
    @Transactional
    public User findByLogin(String login) {
        return userRepository.findByLogin(login)
                .orElseThrow(() -> new UserNotFoundException("User with login = " + login + " not found"));
    }

    @Cacheable(value = "shortBankInfo", key = "#id")
    @Transactional
    public UserBankInfoResponseDto getUserBankInfo(UUID id) {
        return userRepository.findBankInfoById(id)
                .orElseThrow(() -> new UserNotFoundException("User with ID = " + id + " not found"));
    }

    @CachePut(key = "#userRequestDto.getLogin()")
    @Transactional
    public UserAllInfoResponseDto createUser(UserRequestDto userRequestDto) {
        User user = userMapper.userRequestDtoToUser(userRequestDto);
        User userSaved = userRepository.save(user);
        return userMapper.userToUserAllInfoResponseDto(userSaved);
    }

    @Cacheable(key = "#id")
    @Transactional
    public UserAllInfoResponseDto getAllInfo(UUID id) {
        User existingUser = findUserById(id);
        return userMapper.userToUserAllInfoResponseDto(existingUser);
    }

    @CachePut(key = "#id")
    @Transactional
    public UserAllInfoResponseDto updateInfoUser(UUID id, UserRequestDto userRequestDto) {
        User existingUser = findUserById(id);
        userMapper.updateDtoToUser(existingUser, userRequestDto);
        User userSaved = userRepository.save(existingUser);
        return userMapper.userToUserAllInfoResponseDto(userSaved);
    }

    @CacheEvict(key = "#id")
    @Transactional
    public void deleteUser(UUID id) {
        User existingUser = findUserById(id);
        userRepository.delete(existingUser);
    }

    private User findUserById(UUID id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User with ID = " + id + " not found"));
    }
}
