package com.codegym.airbnb.repositories;

import com.codegym.airbnb.model.UserModel;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface UserRepository extends CrudRepository<UserModel, Long> {

    Optional<UserModel> findByNameAndPassword(String name, String password);

    Optional<UserModel> findByEmail(String email);

    Optional<UserModel> findByUsername(String username);

    boolean existsByUsername(String username);

}
