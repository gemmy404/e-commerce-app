package com.app.furniture.service.impl;

import com.app.furniture.dto.UserProfileResponse;
import com.app.furniture.entity.User;
import com.app.furniture.repository.UserRepo;
import com.app.furniture.service.UserService;
import com.app.furniture.util.FileStorageUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepo userRepo;
    private final FileStorageUtil fileStorageUtil;

    @Override
    public void addUser(User user) {
        userRepo.save(user);
    }

    @Override
    public void updateUser(User user) {
        userRepo.findById(user.getId()).ifPresent(userRepo::save);
    }

    @Override
    public Optional<User> findById(Integer userId) {
        return userRepo.findById(userId);
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return userRepo.findByEmail(email);
    }

    @Override
    public UserProfileResponse getUserProfile(Authentication authentication) throws IOException {
        User user = (User) authentication.getPrincipal();
        User returnedUser = userRepo.findById(user.getId()).orElseThrow(() -> new
                UsernameNotFoundException("User not found"));
        byte[] profilePicture = FileStorageUtil.readFileToByteArray(returnedUser.getProfilePicture());
        return new UserProfileResponse(
                returnedUser.fullName(), returnedUser.getEmail(),
                returnedUser.getPhone(), profilePicture
        );
    }

    @Override
    public void uploadProfilePicture(MultipartFile file, Authentication connectedUser) throws IOException {
        User user = (User) connectedUser.getPrincipal();
        String profilePicture = fileStorageUtil.saveFile(file, "users-profile", user.getId());
        user.setProfilePicture(profilePicture);
        userRepo.save(user);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepo.findByEmail(username).orElseThrow(() ->
                new UsernameNotFoundException("User not found with email: " + username));
    }

}
