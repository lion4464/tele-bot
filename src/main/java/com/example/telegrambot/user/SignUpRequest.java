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
public class SignUpRequest {
    @NotBlank(message = "username_is_required")
    private String username;
    @NotBlank(message = "password_is_required")
    private String password;
    @NotBlank(message = "full_name_is_required")
    private String fullName;
    @NotNull(message = "status_is_required")
    private DataStatusEnum status;

    @NotNull(message = "role_is_required")
    private RoleNameEnum role;
}
