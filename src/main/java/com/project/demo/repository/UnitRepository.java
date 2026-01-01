package com.project.demo.repository;

import com.project.demo.domain.unit.Unit;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UnitRepository extends JpaRepository<Unit, Long> {
}
