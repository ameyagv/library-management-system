package com.epam.lms.mapper;

import com.epam.lms.dto.UserDto;
import com.epam.lms.entity.User;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserDto userToUserDto(User user);

    User userDtoToUser(UserDto userDto);

    List<UserDto> mapUsersToUserDtos(List<User> users);

    List<User> mapUserDtosToUsers(List<UserDto> userDtos);
}
