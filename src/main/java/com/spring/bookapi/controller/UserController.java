package com.spring.bookapi.controller;


import com.spring.bookapi.model.api.GetUserResponse;
import com.spring.bookapi.model.api.UserCreateRequest;
import com.spring.bookapi.model.api.UserDeleteRequest;
import com.spring.bookapi.model.api.UserUpdatePasswordRequest;
import com.spring.bookapi.model.domain.User;
import com.spring.bookapi.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {
    private final UserService userService;

    @PostMapping("/create")
    public ResponseEntity<UserCreateRequest> createUser(@RequestBody @Valid UserCreateRequest userCreateRequest){

        return userService.createUser(userCreateRequest);
    }

    @DeleteMapping("/delete")
    public ResponseEntity deleteUser(@RequestBody @Valid UserDeleteRequest userDeleteRequest){
        return userService.deleteUserByGivenUsername(userDeleteRequest);
    }

    @PatchMapping("/update")
    public ResponseEntity<UserUpdatePasswordRequest> updateUserPassword(@RequestBody @Valid UserUpdatePasswordRequest userUpdatePasswordRequest) {
        return userService.updateUserPassword(userUpdatePasswordRequest);
    }

    @GetMapping("/get/{username}")
    public ResponseEntity<GetUserResponse> getUserByUsername(@PathVariable String username){
        return userService.getUserByUsername(username);
    }

    @GetMapping("/getusers")
    public ResponseEntity<List<User>> getUsers(){
        return userService.getUsers();
    }
}
