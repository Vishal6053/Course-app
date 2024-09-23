package com.example.courseapp.controller;

import com.example.courseapp.dto.LoginRequest;
import com.example.courseapp.dto.RegistrationRequest;
import com.example.courseapp.dto.OtpVerificationRequest;  // Create this DTO
import com.example.courseapp.service.UserService;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody RegistrationRequest request) {
        try {
            String message = userService.registerUser(request);
            return ResponseEntity.ok(message);
        } catch (MessagingException e) {
            return ResponseEntity.status(500).body("Error sending OTP email.");
        }
    }


    @PostMapping("/verify-otp")
    public ResponseEntity<String> verifyOtp(@RequestBody OtpVerificationRequest request) {
        String message = userService.verifyOtp(request.getEmail(), request.getOtp());
        return ResponseEntity.ok(message);
    }
    @PostMapping("/login")
    public ResponseEntity<String> loginUser(@RequestBody LoginRequest request){
        String message = userService.loginUser(request.getEmail(), request.getPassword());
        return ResponseEntity.ok(message);
    }
}
