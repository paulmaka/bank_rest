package com.example.bankcards.controller;


import com.example.bankcards.dto.UserRequestDTO;
import com.example.bankcards.dto.UserResponseDTO;
import com.example.bankcards.dto.validators.CreateUserValidationGroup;
import com.example.bankcards.service.UserService;
import jakarta.validation.groups.Default;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    /*
    Созданием нового пользователя банка занимается ADMIN
     */
    @PostMapping
    public ResponseEntity<UserResponseDTO> createNewUser(
            @Validated({CreateUserValidationGroup.class})
            @RequestBody UserRequestDTO userRequestDTO) {
        UserResponseDTO userResponseDTO = userService.createUser(userRequestDTO);

        return ResponseEntity.ok(userResponseDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserResponseDTO> updateUser(
            @Validated({Default.class})
            @PathVariable String id, @RequestBody UserRequestDTO userRequestDTO) {
        UUID uuid = UUID.fromString(id);

        UserResponseDTO userResponseDTO = userService.updateUser(uuid, userRequestDTO);

        return ResponseEntity.ok(userResponseDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable String id) {
        UUID uuid = UUID.fromString(id);
        userService.deleteUser(uuid);

        return ResponseEntity.noContent().build();
    }

}
