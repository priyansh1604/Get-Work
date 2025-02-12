package com.example.Freelance.Controller;

import com.example.Freelance.DTO.UserLoginDTO;
import com.example.Freelance.DTO.UserRegisterDTO;
import com.example.Freelance.Entity.User;
import com.example.Freelance.Service.UserService;
import com.example.Freelance.Utils.JwtUtil;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthContoller {
    @Autowired
    private UserService userService;

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/register")
    public ResponseEntity<String> register(@Valid @RequestBody UserRegisterDTO registrationDTO) {
        userService.registerUser(registrationDTO);
        return ResponseEntity.ok("User registered successfully");
    }

    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> login(@Valid @RequestBody UserLoginDTO loginDTO) {
        User user = userService.authenticate(loginDTO.getEmail(), loginDTO.getPassword());
        if (user != null) {
            String token = jwtUtil.generateToken(user.getEmail());

            // Create a map for the response
            Map<String, String> response = new HashMap<>();
            response.put("message", "login success");
            response.put("token", token);

            return ResponseEntity.ok(response);
        } else {
            // Optionally handle login failure here
            Map<String, String> response = new HashMap<>();
            response.put("message", "Invalid email or password");
            response.put("token", "");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
        }
    }

}
