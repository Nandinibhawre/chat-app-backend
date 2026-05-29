package com.spring.chatApp.Repository;


import com.spring.chatApp.Model.User;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository
        extends MongoRepository<User, String>
{

    Optional<User> findByEmail(String email);
    Optional<User> findByResetToken(String resetToken);
    List<User> findByUsernameContainingIgnoreCase(String username);
}