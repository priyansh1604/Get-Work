package com.example.Freelance.Repository;

import com.example.Freelance.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepo extends JpaRepository<User,Long> {
    User findByEmail(String email);
    boolean existsByEmail(String email);
}
