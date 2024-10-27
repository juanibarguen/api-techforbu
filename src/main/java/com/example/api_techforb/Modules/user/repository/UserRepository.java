package com.example.api_techforb.Modules.user.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.api_techforb.Modules.user.model.User;

public interface UserRepository extends JpaRepository<User,Integer>{

    Optional<User> findByMail(String mail);
    boolean existsByMail(String mail);
    boolean existsByUsername(String username);
}
