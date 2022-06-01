package com.spring.bookapi.model.domain;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.aggregation.ArrayOperators;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Encrypted;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;



@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class User {


    @Id
    private Integer id;

    @NotEmpty
    @Size(max = 100)
    @Indexed(unique = true)
    private String username;

    @NotEmpty
    @Indexed(unique = true)
    @Email(message = "Email should be valid")
    private String email;

    private String password;
}