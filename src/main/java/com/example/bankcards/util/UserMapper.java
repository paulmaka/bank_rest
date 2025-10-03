package com.example.bankcards.util;

import com.example.bankcards.dto.UserRequestDTO;
import com.example.bankcards.dto.UserResponseDTO;
import com.example.bankcards.entity.User;

public class UserMapper {

    public static User toModel(UserRequestDTO userRequestDTO) {
        User user = new User();

        user.setUsername(userRequestDTO.getUsername());
        user.setPassport(userRequestDTO.getPassport());
        user.setEmail(userRequestDTO.getEmail());

        return user;
    }

    public static UserResponseDTO toDTO(User user){
        UserResponseDTO userResponseDTO = new UserResponseDTO();

        userResponseDTO.setUsername(user.getUsername());
        userResponseDTO.setEmail(user.getEmail());
        userResponseDTO.setPassport(user.getPassport());

        return userResponseDTO;
    }

    public static User toUpdatedUser(User user, UserRequestDTO userRequestDTO) {
        if (userRequestDTO.getUsername() != null) {
            user.setUsername(userRequestDTO.getUsername());
        }
        if (userRequestDTO.getPassport() != null) {
            user.setPassport(userRequestDTO.getPassport());
        }
        if (userRequestDTO.getEmail() != null) {
            user.setEmail(userRequestDTO.getEmail());
        }
        return user;
    }
}
