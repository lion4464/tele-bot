package com.example.telegrambot.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class UserRequest {
    @NotBlank(message = "username_is_required")
    private String username;
    @NotBlank(message = "password_is_required")
    private String password;

    private String fullName;
    @NotNull(message = "status_is_required")
    private DataStatusEnum status;
}
