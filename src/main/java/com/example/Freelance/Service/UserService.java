package com.example.Freelance.Service;

import com.example.Freelance.DTO.UpdateUserDTO;
import com.example.Freelance.DTO.UserRegisterDTO;
import com.example.Freelance.Entity.User;
import com.example.Freelance.Repository.UserRepo;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private UserRepo userRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    public User registerUser(UserRegisterDTO registrationDTO) {

        if (userRepository.findByEmail(registrationDTO.getEmail()) != null) {
            throw new IllegalArgumentException("Email is already registered");
        }

        User user = new User();
        user.setUsername(registrationDTO.getUsername());
        user.setEmail(registrationDTO.getEmail());
        user.setPassword(passwordEncoder.encode(registrationDTO.getPassword()));
        user.setRole(registrationDTO.getRole());

        return userRepository.save(user);
    }

    public User authenticate(String email, String password) {
        User user = userRepository.findByEmail(email);
        if (user != null && passwordEncoder.matches(password, user.getPassword())) {
            return user;
        }
        return null;
    }
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }
    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }

    public User updateUser(Long id, UpdateUserDTO updateUserDTO) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
        if (updateUserDTO.getUsername() != null) {
            if (updateUserDTO.getUsername().trim().isEmpty()) {
                throw new IllegalArgumentException("Username cannot be empty or blank");
            }
            user.setUsername(updateUserDTO.getUsername());
        }
       // boolean emailExists = userRepository.existsByEmail(userDetails.getEmail());
        if (updateUserDTO.getEmail() != null) {
            boolean emailExists = userRepository.existsByEmail(updateUserDTO.getEmail());
            if (emailExists && !user.getEmail().equals(updateUserDTO.getEmail())) {
                throw new IllegalArgumentException("Email is already registered");
            }
            user.setEmail(updateUserDTO.getEmail());
        }
        try {
            return userRepository.save(user);
        } catch (Exception e) {
            throw new RuntimeException("Error updating user: " + e.getMessage());
        }
    }
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }
}
