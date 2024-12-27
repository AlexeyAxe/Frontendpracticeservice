package com.aston.frontendpracticeservice.domain.mapper;

import com.aston.frontendpracticeservice.domain.dto.request.UserRequestDto;
import com.aston.frontendpracticeservice.domain.dto.response.UserAllInfoResponseDto;
import com.aston.frontendpracticeservice.domain.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mapping(target = "requisites", ignore = true)
    @Mapping(target = "id", ignore = true)
    User userRequestDtoToUser(UserRequestDto userRequestDto);

    UserAllInfoResponseDto userToUserAllInfoResponseDto(User user);

    @Mapping(target = "requisites", ignore = true)
    @Mapping(target = "id", ignore = true)
    void updateDtoToUser(@MappingTarget User existingUser, UserRequestDto userRequestDto);
}
