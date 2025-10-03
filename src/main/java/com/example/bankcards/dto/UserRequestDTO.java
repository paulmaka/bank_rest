package com.example.bankcards.dto;


import com.example.bankcards.dto.validators.CreateUserValidationGroup;
import jakarta.validation.constraints.NotBlank;

//TODO сделать валидацию и сделать отдельное DTO для update, то же самое для Card
public class UserRequestDTO {

    @NotBlank(groups = CreateUserValidationGroup.class, message = "Имя пользователя не должно быть пустым.")
    private String username;
    @NotBlank(groups = CreateUserValidationGroup.class, message = "Поле паспорта не должно быть пустым.")
    private String passport;
    @NotBlank(groups = CreateUserValidationGroup.class, message = "Почта не должна быть пустой.")
    private String email;
    @NotBlank(groups = CreateUserValidationGroup.class, message = "Пароль не должен быть пустым.")
    private String password;

    public UserRequestDTO() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassport() {
        return passport;
    }

    public void setPassport(String passport) {
        this.passport = passport;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
