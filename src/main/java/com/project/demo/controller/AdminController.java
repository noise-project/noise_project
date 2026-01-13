package com.project.demo.controller;

import com.project.demo.api.dto.PendingUserResponse;
import com.project.demo.domain.user.Status;
import com.project.demo.domain.user.User;
import com.project.demo.repository.UserRepository;
import com.project.demo.service.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class AdminController {

    private final UserRepository userRepository;
    private final AdminService adminService;

    // 승인 대기 유저 목록 조회
    @GetMapping("/users/pending")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<PendingUserResponse>> pendingUsers(){
        return ResponseEntity.ok(adminService.getPendingUsers());
    }

    // 유저 승인
    @PostMapping("/users/approved/{userId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> approvedUser(@PathVariable Long userId) {
        adminService.approvedUser(userId);

        return ResponseEntity.ok("승인 완료");
    }

    // 유저 거절
    @PostMapping("/users/reject/{userId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> rejectUser(@PathVariable Long userId) {
        adminService.rejectUser(userId);

        return ResponseEntity.ok("승인 거절");
    }
}
