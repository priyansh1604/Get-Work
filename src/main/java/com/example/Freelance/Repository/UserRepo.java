package com.example.Freelance.Repository;

import com.example.Freelance.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepo extends JpaRepository<User,Long> {
    User findByEmail(String email);
    boolean existsByEmail(String email);
}
