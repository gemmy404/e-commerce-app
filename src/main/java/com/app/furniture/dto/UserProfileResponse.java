package com.app.furniture.dto;

public record UserProfileResponse(
        String fullName,
        String email,
        String phone,
        byte[] profilePicture
) {
}
