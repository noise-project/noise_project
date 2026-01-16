package com.project.demo.init;

import com.project.demo.domain.unit.Unit;
import com.project.demo.domain.user.User;
import com.project.demo.repository.UnitRepository;
import com.project.demo.repository.UserRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AdminInitializer {

    private final UserRepository userRepository;
    private final UnitRepository unitRepository;
    private final PasswordEncoder passwordEncoder;

    @PostConstruct
    public void initAdmin() {

        if (userRepository.findByEmail("admin@test.com").isPresent()) {
            return;
        }

        Unit unit = unitRepository.findByPostNoAndAdressAndDetailAdress(
                "관리자 아파트",
                "101동",
                "1호"
        )
                .orElseGet(() -> {
                    Unit u = new Unit();
                    u.setPostNo("11923");
                    u.setAdress("서울특별시 관악구 ");
                    u.setDetailAdress("1층 101호");
                    return unitRepository.save(u);
                });

        User admin = User.createAdmin(
                "admin@test.com",
                passwordEncoder.encode("1234"),
                "관리자",
                unit

        );

        userRepository.save(admin);
    }
}

