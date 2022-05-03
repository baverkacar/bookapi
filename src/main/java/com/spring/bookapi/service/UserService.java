package com.spring.bookapi.service;


import com.spring.bookapi.model.api.GetUserResponse;
import com.spring.bookapi.model.api.UserCreateRequest;
import com.spring.bookapi.model.api.UserDeleteRequest;
import com.spring.bookapi.model.api.UserUpdatePasswordRequest;
import com.spring.bookapi.model.domain.User;
import com.spring.bookapi.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    Logger logger = LoggerFactory.getLogger(UserService.class);

    public ResponseEntity<UserCreateRequest> createUser(UserCreateRequest userCreateRequest){
        if (isUsernameExists(userCreateRequest.getUsername()) || isEmailExists(userCreateRequest.getEmail())){
            logger.error("User exists.");
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
        int biggestId;
        biggestId = userRepository.findTopByOrderByIdDesc() == null ? 0 : userRepository.findTopByOrderByIdDesc().getId();
        User user = User.builder()
                .id(biggestId + 1) // First we are finding the biggest id in database. Then increasing it with 1.
                .username(userCreateRequest.getUsername())
                .email(userCreateRequest.getEmail())
                .password(userCreateRequest.getPassword())
                .build();
        userRepository.save(user);
        logger.info("User created with id: " + user.getId());
        return new ResponseEntity(userCreateRequest, HttpStatus.OK);
    }
    public ResponseEntity deleteUserByGivenUsername(UserDeleteRequest userDeleteRequest) {
        User user = checkUserExistence(userDeleteRequest.getUsername(), userDeleteRequest.getPassword());
        if (user == null){
            logger.error("User could not be deleted");
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
        else {
            userRepository.delete(user);
            logger.info("User deleted successfully");
            return new ResponseEntity(HttpStatus.OK);
        }
    }

    public ResponseEntity<UserUpdatePasswordRequest> updateUserPassword(UserUpdatePasswordRequest userUpdatePasswordRequest){
        User user = checkUserExistence(userUpdatePasswordRequest.getUsername(), userUpdatePasswordRequest.getOldPassword());
        if (user == null){
            logger.error("User's password could not be changed");
            return new ResponseEntity(userUpdatePasswordRequest, HttpStatus.BAD_REQUEST);
        }
        else {
            user.setPassword(userUpdatePasswordRequest.getNewPassword());
            userRepository.save(user);
            logger.info("User's password changed");
            return new ResponseEntity(userUpdatePasswordRequest, HttpStatus.OK);
        }
    }

    public ResponseEntity<GetUserResponse> getUserByUsername(String username){
        User user = userRepository.getUserByUsername(username);
        if (user == null){
            logger.error("There is no user with given username");
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
        else{
            logger.info("getUser method worked successfully");
            GetUserResponse getUserResponse = GetUserResponse.builder()
                    .username(user.getUsername())
                    .email(user.getEmail())
                    .password(user.getPassword())
                    .build();
            return new ResponseEntity(getUserResponse, HttpStatus.OK);
        }
    }

    public ResponseEntity<List<User>> getUsers(){
        List<User> users = userRepository.findAll();
        if(users.size() == 0){
            logger.error("Database is empty");
            return new ResponseEntity(users, HttpStatus.NOT_FOUND);
        }
        logger.info("getUsers method worked successfully");
        return new ResponseEntity(users, HttpStatus.OK);
    }

    /* inner function */
    private User checkUserExistence(String username, String oldPassword){
        User user = userRepository.getUserByUsername(username);
        if (user == null || !user.getPassword().equals(oldPassword)){
            return null;
        }
        return user;
    }

    private boolean isUsernameExists(String username){
        User user = userRepository.getUserByUsername(username);
        return user != null ? true : false;
    }

    private boolean isEmailExists(String email){
        User user = userRepository.getUserByEmail(email);
        return user != null ? true : false;
    }
}
