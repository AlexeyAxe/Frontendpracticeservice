package com.aston.frontendpracticeservice.repository;

import com.aston.frontendpracticeservice.domain.dto.response.UserBankInfoResponseDto;
import com.aston.frontendpracticeservice.domain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {

    Optional<User> findByLogin(String login);

    @Query("""
 SELECT new com.aston.frontendpracticeservice.domain.dto.response.UserBankInfoResponseDto(u.firstName, r.currentAccount, r.kbk)
 FROM User u
 JOIN u.requisites r
 WHERE u.id = :id
 """)
    Optional<UserBankInfoResponseDto> findBankInfoById(UUID id);
}
