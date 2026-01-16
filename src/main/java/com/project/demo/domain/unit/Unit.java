package com.project.demo.domain.unit;

import jakarta.persistence.*;

@Entity
@Table(
        name = "units",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "uq_unit",
                        columnNames = {"post_no", "adress", "detail_adress"}
                )
        }
)

public class Unit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "unit_id")
    private Long id;

    @Column(name = "post_no", nullable = false, length = 20)
    private String postNo;

    @Column(name = "adress", nullable = false, length = 100)
    private String adress;

    @Column(name = "detail_adress", nullable = false, length = 100)
    private String detailAdress;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPostNo() {
        return postNo;
    }

    public void setPostNo(String postNo) {
        this.postNo = postNo;
    }

    public String getAdress() {
        return adress;
    }

    public void setAdress(String adress) {
        this.adress = adress;
    }

    public String getDetailAdress() {
        return detailAdress;
    }

    public void setDetailAdress(String detailAdress) {
        this.detailAdress = detailAdress;
    }
}
