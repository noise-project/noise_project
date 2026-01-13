package com.project.demo.service;

import com.project.demo.api.dto.PendingUserResponse;
import com.project.demo.domain.user.Status;
import com.project.demo.domain.user.User;
import com.project.demo.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class AdminService {

    private final UserRepository userRepository;

    //승인 대기 유저 목록 조회
    public List<PendingUserResponse> getPendingUsers() {

        return userRepository.findAllByStatus(Status.PENDING)
                .stream()
                .map(user -> new PendingUserResponse(
                        user.getUserId(),
                        user.getEmail(),
                        user.getNickName(),
                        user.getUnit().getApartmentName(),
                        user.getStatus()
                ))
                .toList();
    }

    //유저 승인
    public void approvedUser(Long userId){
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("해당 유저가 존재하지 않음"));

        user.setStatus(Status.APPROVED);
    }

    //유저 거절
    public void rejectUser(Long userId){
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("해당 유저가 존재하지 않음"));

        user.setStatus(Status.REJECTED);
    }
}
