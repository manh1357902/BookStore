package com.example.bookstore.repositories;

import com.example.bookstore.models.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsernameAndIsActive(String username, Boolean isActive);
    Boolean existsByUsernameAndIsActive(String username, Boolean isActive);
}
