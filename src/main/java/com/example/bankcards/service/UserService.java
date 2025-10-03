package com.example.bankcards.service;


import com.example.bankcards.dto.UserRequestDTO;
import com.example.bankcards.dto.UserResponseDTO;
import com.example.bankcards.entity.User;
import com.example.bankcards.exception.ClientNotFoundException;
import com.example.bankcards.exception.EmailAlreadyExistsException;
import com.example.bankcards.exception.EmailNotFoundException;
import com.example.bankcards.repository.UserRepository;
import com.example.bankcards.util.UserMapper;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleService roleService;

    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, RoleService roleService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.roleService = roleService;
    }

    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    @Transactional // TODO why
    public UserDetails loadUserByUsername(String email) throws EmailNotFoundException {
        User user = findByEmail(email).orElseThrow(() -> new EmailNotFoundException("Пользователь с таким email не найден: " + email));

        return new org.springframework.security.core.userdetails.User(
                user.getEmail(),
                user.getPassword(),
                user.getRoles().stream().map(role -> new SimpleGrantedAuthority(role.getRole())).toList()
        );
    }

    public UserResponseDTO createUser(UserRequestDTO userRequestDTO) {
        if (userRepository.existsByEmail(userRequestDTO.getEmail())) {
            throw new EmailAlreadyExistsException("Такой email: " + userRequestDTO.getEmail() + " уже существует");
        }

        User newUser = UserMapper.toModel(userRequestDTO);
        newUser.setPassword(passwordEncoder.encode(userRequestDTO.getPassword()));
        newUser.setRoles(List.of(roleService.findByName("ROLE_USER").get()));

        newUser = userRepository.save(newUser);

        UserResponseDTO userResponseDTO = UserMapper.toDTO(newUser);

        return userResponseDTO;
    }

    public UserResponseDTO updateUser(UUID id, UserRequestDTO userRequestDTO) {
        User user = userRepository.findById(id).orElseThrow(() -> new ClientNotFoundException("Пользователь с таким id не найден: " + id));

        user = UserMapper.toUpdatedUser(user, userRequestDTO);

        if (userRequestDTO.getPassport() != null) {
            user.setEmail(passwordEncoder.encode(userRequestDTO.getPassword()));
        }

        User updatedUser = userRepository.save(user);

        return UserMapper.toDTO(updatedUser);
    }

    public void deleteUser(UUID id) {
        userRepository.findById(id).orElseThrow(() -> new ClientNotFoundException("Пользователь с таким id не найден: " + id));
        userRepository.deleteById(id);
    }

    public User findById(UUID id) {
        User user = userRepository.findById(id).orElseThrow(() -> new ClientNotFoundException("Пользователь с таким id не найден: " + id));
        return user;
    }

}
