package com.codegym.airbnb.services;

import com.codegym.airbnb.model.UserModel;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.Optional;

public interface UserService extends UserDetailsService {

    Iterable<UserModel> findAll();

    Optional<UserModel> findById(Long id);

    Optional<UserModel> findByNameAndPassword(String name, String password);

    Optional<UserModel> findByEmail(String email);

    UserModel save(UserModel user);

    Optional<UserModel> findByUserName(String username);

    boolean existsByUsername(String username);
}
