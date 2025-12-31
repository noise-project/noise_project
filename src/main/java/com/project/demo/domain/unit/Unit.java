package com.project.demo.domain.unit;

import jakarta.persistence.*;

@Entity
@Table(
        name = "units",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "uq_unit",
                        columnNames = {"apartment_name", "building_no", "unit_no"}
                )
        }
)
public class Unit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "unit_id")
    private Long id;

    @Column(name = "apartment_name", nullable = false, length = 100)
    private String apartmentName;

    @Column(name = "building_no", nullable = false, length = 20)
    private String buildingNo;

    @Column(name = "unit_no", nullable = false, length = 20)
    private String unitNo;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getApartmentName() {
        return apartmentName;
    }

    public void setApartmentName(String apartmentName) {
        this.apartmentName = apartmentName;
    }

    public String getBuildingNo() {
        return buildingNo;
    }

    public void setBuildingNo(String buildingNo) {
        this.buildingNo = buildingNo;
    }

    public String getUnitNo() {
        return unitNo;
    }

    public void setUnitNo(String unitNo) {
        this.unitNo = unitNo;
    }
}
