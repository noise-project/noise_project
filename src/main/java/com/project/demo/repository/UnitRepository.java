package com.project.demo.repository;

import com.project.demo.domain.unit.Unit;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UnitRepository extends JpaRepository<Unit, Long> {

    Optional<Unit> findByApartmentNameAndBuildingNoAndUnitNo(
            String apartmentName,
            String buildingNo,
            String unitNo
    );
}
