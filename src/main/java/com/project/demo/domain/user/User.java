package com.project.demo.domain.user;

import com.project.demo.domain.unit.Unit;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long userId;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String nickName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "unit_id", nullable = false)
    private Unit unit;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role = Role.USER;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Status status = Status.PENDING;

    @Column(name = "join_date", nullable = false)
    private LocalDateTime joinDate;

    @Column(name = "last_login")
    private LocalDateTime lastLoginDate;

    @Column(name = "refresh_token", length = 1000)
    private String refreshToken;

    public void updateRefreshToken(String refreshToken){
        this.refreshToken = refreshToken;
    }

    // protected인 이유? 외부에서 생성 불가, JPA에서 접근가능
    protected User() {}

    // 일반 유저 생성
    public static User create(String email, String password, String nickName, Unit unit, Status status) {
        User user = new User();
        user.email = email;
        user.password = password;
        user.nickName = nickName;
        user.unit = unit;
        user.role = Role.USER;
        user.status = Status.PENDING;
        return user;
    }

    //관리자 생성
    public static User createAdmin(String email, String password, String nickName, Unit unit) {
        User admin = new User();
        admin.email = email;
        admin.password = password;
        admin.nickName = nickName;
        admin.unit = unit;
        admin.role = Role.ADMIN;
        admin.status = Status.APPROVED;
        return admin;
    }
    // 로그아웃
    public void logout() {
        this.refreshToken = null;
    }

    //저장 직전에 실행
    @PrePersist
    public void prePersist() {
        this.joinDate = LocalDateTime.now();
        this.lastLoginDate = LocalDateTime.now();
    }

    //상태 변경
    public void approved() {
        this.status = Status.APPROVED;
    }

    public void reject() {
        this.status = Status.REJECTED;
    }

    public boolean isApproved() {
        return this.status == Status.APPROVED;
    }

    public Long getUserId() {return userId;}

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public Unit getUnit() {
        return unit;
    }

    public void setUnit(Unit unit) {
        this.unit = unit;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public LocalDateTime getJoinDate() {
        return joinDate;
    }

    public void setJoinDate(LocalDateTime joinDate) {
        this.joinDate = joinDate;
    }

    public LocalDateTime getLastLoginDate() {
        return lastLoginDate;
    }

    public void setLastLoginDate(LocalDateTime lastLoginDate) {
        this.lastLoginDate = lastLoginDate;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
}
