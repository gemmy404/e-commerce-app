package com.app.furniture.repository;

import com.app.furniture.entity.ResetPasswordToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ResetPasswordTokenRepo extends JpaRepository<ResetPasswordToken, Integer> {

    Optional<ResetPasswordToken> findByToken(String token);

}
