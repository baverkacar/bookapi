package com.spring.bookapi.model.api;

import lombok.*;

import javax.validation.constraints.NotEmpty;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserUpdatePasswordRequest {

    @NotEmpty
    private String username;

    @NotEmpty
    private String oldPassword;

    @NotEmpty
    private String newPassword;
}
