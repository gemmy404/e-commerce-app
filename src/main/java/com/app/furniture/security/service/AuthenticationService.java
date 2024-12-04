package com.app.furniture.security.service;

import com.app.furniture.dto.LoginRequest;
import com.app.furniture.dto.LoginResponse;
import com.app.furniture.dto.RegistrationRequest;
import com.app.furniture.dto.ResetPasswordRequest;
import jakarta.mail.MessagingException;
import org.apache.coyote.BadRequestException;

import java.util.Map;

public interface AuthenticationService {

    void register(RegistrationRequest registrationRequest) throws BadRequestException;

    LoginResponse login(LoginRequest request);

    void forgottenPassword(String email) throws MessagingException;

    Map<String, String> verifyPasswordResetToken(String token) throws MessagingException;

    void resetPassword(ResetPasswordRequest request) throws BadRequestException;

}
