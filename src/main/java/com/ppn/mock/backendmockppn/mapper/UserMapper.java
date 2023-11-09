package com.ppn.mock.backendmockppn.mapper;

import com.ppn.mock.backendmockppn.dto.UserDto;
import com.ppn.mock.backendmockppn.entities.User;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    User userDtoToUser(UserDto userDto);

    UserDto userToUserDto(User user);
}
