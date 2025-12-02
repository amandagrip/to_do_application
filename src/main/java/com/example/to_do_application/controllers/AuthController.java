package com.example.to_do_application.controllers;

import com.example.to_do_application.security.JwtUtil;
import com.example.to_do_application.controllers.LogInRequest;
import com.example.to_do_application.controllers.RegisterRequest;
import com.example.to_do_application.security.MyUserDetailsService;
import com.example.to_do_application.services.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import lombok.RequiredArgsConstructor;


@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;
    private final MyUserDetailsService myUserDetailsService;
    private final JwtUtil jwtUtil;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest request) {
        try {
            userService.registerUser(request);
            return ResponseEntity.ok("User registered successfully");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Registration failed: " + e.getMessage());
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LogInRequest request) {
        try {

            UserDetails userDetails = myUserDetailsService.loadUserByUsername(request.getUserName());

            String token = jwtUtil.generateToken(userDetails);


            return ResponseEntity.ok(token);

        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Login failed: " + e.getMessage());
        }
    }
}
