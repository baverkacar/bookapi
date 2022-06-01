package com.spring.bookapi.repository;

import com.spring.bookapi.model.domain.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UserRepository extends MongoRepository<User, Integer> {

    @Query("{username:'?0'}")
    public User getUserByUsername(@Param("username") String username);

    @Query("{email:'?0'}")
    public User getUserByEmail(@Param("email") String email);

    public List<User> findAll();    

    public User findTopByOrderByIdDesc();
}
