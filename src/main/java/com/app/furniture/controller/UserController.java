package com.app.furniture.controller;

import com.app.furniture.dto.UserProfileResponse;
import com.app.furniture.service.UserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
@Tag(name = "User")
public class UserController {

    private final UserService userService;

    @GetMapping("/profile")
    public ResponseEntity<UserProfileResponse> getUserProfile(Authentication authentication) throws IOException {
        return ResponseEntity.ok(userService.getUserProfile(authentication));
    }

    @PostMapping(value = "/profile-picture", consumes = "multipart/form-data")
    public ResponseEntity<?> uploadProfilePicture(@RequestParam("file") MultipartFile file,
                                                  Authentication connectedUser) throws IOException {
        userService.uploadProfilePicture(file, connectedUser);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
