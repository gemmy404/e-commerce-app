package com.app.furniture.controller;

import com.app.furniture.dto.LoginRequest;
import com.app.furniture.dto.LoginResponse;
import com.app.furniture.dto.RegistrationRequest;
import com.app.furniture.dto.ResetPasswordRequest;
import com.app.furniture.security.service.AuthenticationService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.mail.MessagingException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.BadRequestException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
@Tag(name = "Authentication")
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody RegistrationRequest request) throws BadRequestException {
        authenticationService.register(request);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@Valid @RequestBody LoginRequest request) {
        return ResponseEntity.ok(authenticationService.login(request));
    }

    @PostMapping("/forgotten-password")
    public ResponseEntity<?> forgottenPassword(@RequestParam String email) throws MessagingException {
        authenticationService.forgottenPassword(email);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/verify-token")
    public ResponseEntity<Map<String, String>> verifyPasswordResetToken(@RequestParam String token) throws MessagingException {
        return new ResponseEntity<>(authenticationService
                .verifyPasswordResetToken(token), HttpStatus.OK);
    }

    @PutMapping("/reset-password")
    public ResponseEntity<?> resetPassword(@Valid @RequestBody ResetPasswordRequest request) throws BadRequestException {
        authenticationService.resetPassword(request);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
