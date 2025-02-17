package com.example.Freelance.Controller;

import com.example.Freelance.DTO.UpdateUserDTO;
import com.example.Freelance.DTO.UserLoginDTO;
import com.example.Freelance.DTO.UserRegisterDTO;
import com.example.Freelance.Entity.User;
import com.example.Freelance.Service.UserService;
import com.example.Freelance.Utils.ApiResponse;
import com.example.Freelance.Utils.JwtUtil;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthContoller {
    @Autowired
    private UserService userService;

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/register")
    public ResponseEntity<ApiResponse> register(@Valid @RequestBody UserRegisterDTO registrationDTO) {
        userService.registerUser(registrationDTO);
        ApiResponse response = new ApiResponse("User registered successfully");
        return ResponseEntity.ok(response);
    }

    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> login(@Valid @RequestBody UserLoginDTO loginDTO) {
        User user = userService.authenticate(loginDTO.getEmail(), loginDTO.getPassword());
        if (user != null) {
            String token = jwtUtil.generateToken(user);

            Map<String, String> response = new HashMap<>();
            response.put("message", "login success");
            response.put("token", token);

            return ResponseEntity.ok(response);
        } else {
            Map<String, String> response = new HashMap<>();
            response.put("message", "Invalid email or password");
            response.put("token", "");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
        }
    }
    @GetMapping("/admin")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> adminAccess() {
        return ResponseEntity.ok("Admin access granted");
    }

    @GetMapping("/get-all")
    @PreAuthorize("hasRole('ADMIN')")
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }
    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        User user = userService.getUserById(id).orElseThrow(() -> new RuntimeException("User not found"));
        return ResponseEntity.ok(user);
    }
    @PutMapping("/{id}")
    public ResponseEntity<String> updateUser(@RequestHeader ("Authorization") String token,@Valid @RequestBody UpdateUserDTO userDetails) {
        Long userId= jwtUtil.extractUserIdFromToken(token);
        System.out.println("userid"+userId);
        User updatedUser = userService.updateUser(userId, userDetails);
        return ResponseEntity.ok("User updated successfully!");
    }
    @DeleteMapping("delete-user/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }


}
