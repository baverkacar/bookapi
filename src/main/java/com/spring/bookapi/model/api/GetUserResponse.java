package com.spring.bookapi.model.api;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.index.Indexed;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GetUserResponse {

    @NotEmpty
    private String username;

    @NotEmpty
    @Indexed(unique = true)
    @Email
    private String email;

    @NotEmpty
    private String password;
}
