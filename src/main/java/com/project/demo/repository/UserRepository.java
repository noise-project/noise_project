package com.project.demo.repository;

import com.project.demo.domain.user.Status;
import com.project.demo.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);

    Optional<User> findByRefreshToken(String refreshToken);

    boolean existsByEmail(String email);

    List<User> findAllByStatus(Status status);
}
