package com.app.furniture.service;

import com.app.furniture.dto.UserProfileResponse;
import com.app.furniture.entity.User;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

public interface UserService extends UserDetailsService {

    void addUser(User user);

    void updateUser(User user);

    Optional<User> findById(Integer userId);

    Optional<User> findByEmail(String email);

    UserProfileResponse getUserProfile(Authentication authentication) throws IOException;

    void uploadProfilePicture(MultipartFile file, Authentication connectedUser) throws IOException;
}
