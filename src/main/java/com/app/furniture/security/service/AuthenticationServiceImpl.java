package com.app.furniture.security.service;

import com.app.furniture.dto.LoginRequest;
import com.app.furniture.dto.LoginResponse;
import com.app.furniture.dto.RegistrationRequest;
import com.app.furniture.dto.ResetPasswordRequest;
import com.app.furniture.entity.ResetPasswordToken;
import com.app.furniture.entity.Role;
import com.app.furniture.entity.User;
import com.app.furniture.repository.ResetPasswordTokenRepo;
import com.app.furniture.repository.RoleRepo;
import com.app.furniture.security.util.JwtTokenUtil;
import com.app.furniture.service.UserService;
import jakarta.mail.MessagingException;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {

    private final RoleRepo roleRepo;
    private final UserService userService;
    private final EmailService emailService;
    private final ResetPasswordTokenRepo passwordTokenRepo;
    private final JwtTokenUtil jwtTokenUtil;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    @Value("${application.security.jwt.expiration}")
    private long jwtExpirationInMs;

    @Override
    public void register(RegistrationRequest request) throws BadRequestException {
        if (userService.findByEmail(request.email()).isPresent()) {
            throw new DuplicateKeyException("Email already used. Please try another one.");
        }
        if (!request.password().equals(request.confirmPassword())) {
            throw new BadRequestException("Passwords do not match.");
        }
        Role userRole = roleRepo.findByName("ROLE_USER")
                .orElseThrow(() -> new IllegalStateException("ROLE USER was not initialized"));
        String encodedPassword = passwordEncoder.encode(request.password());
        User savedUser = User.builder()
                .email(request.email())
                .password(encodedPassword)
                .confirmPassword(encodedPassword)
                .firstName(request.firstName())
                .lastName(request.lastName())
                .phone(request.phone())
                .roles(List.of(userRole))
                .build();
        userService.addUser(savedUser);
    }

    @Override
    public LoginResponse login(LoginRequest request) {
        userService.findByEmail(request.email()).orElseThrow(() -> new
                UsernameNotFoundException("Your account was not found."));
        Authentication authentication = authenticationManager.authenticate(new
                UsernamePasswordAuthenticationToken(request.email(), request.password()));
        User user = (User) authentication.getPrincipal();
        Map<String, Object> claims = new HashMap<>();
        claims.put("fullName", user.fullName());
        String jwtToken = jwtTokenUtil.generateToken(claims, user, jwtExpirationInMs);
        return new LoginResponse(jwtToken);
    }

    @Override
    public void forgottenPassword(String email) throws MessagingException {
        User user = userService.findByEmail(email).orElseThrow(() -> new
                UsernameNotFoundException("User not found"));
        emailService.sendEmailValidation(user);
    }

    @Override
    public Map<String, String> verifyPasswordResetToken(String code) throws MessagingException {
        ResetPasswordToken passwordToken = passwordTokenRepo.findByToken(code).orElseThrow(() -> new
                EntityNotFoundException("Invalid token"));
        if (LocalDateTime.now().isAfter(passwordToken.getExpiresAt()) || passwordToken.getValidatedAt() != null) {
            emailService.sendEmailValidation(passwordToken.getUser());
            throw new MessagingException("Expired token. A new token has been sent to your account.");
        }
        passwordToken.setValidatedAt(LocalDateTime.now());
        passwordTokenRepo.save(passwordToken);
        Map<String, Object> claims = new HashMap<>();
        claims.put("fullName", passwordToken.getUser().fullName());
        String token = jwtTokenUtil.generateToken(claims, passwordToken.getUser(), 300000);
        return new HashMap<>(Map.of("token", token));
    }

    @Override
    public void resetPassword(ResetPasswordRequest request) throws BadRequestException {
        if (!request.newPassword().equals(request.confirmPassword())) {
            throw new BadRequestException("Passwords do not match.");
        }
        Integer userId = (Integer) jwtTokenUtil.extractClaim(request.token(), claims -> claims.get("userId"));
        User user = userService.findById(userId).orElseThrow(() -> new
                UsernameNotFoundException("User not found"));
        if (jwtTokenUtil.isTokenValid(request.token(), user)) {
            String encodedPassword = passwordEncoder.encode(request.newPassword());
            user.setPassword(encodedPassword);
            userService.updateUser(user);
            return;
        }
        throw new BadRequestException("Invalid token.");
    }


}
