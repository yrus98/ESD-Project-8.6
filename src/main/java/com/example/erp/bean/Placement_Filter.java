package com.example.erp.bean;

import javax.persistence.*;

@Entity
@Table(name="placement_filter")
public class Placement_Filter {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer id;

    @OneToOne
    @JoinColumn(name="placement_id", referencedColumnName = "id")
    private Placement placement;

    private String specialization;
    private String domain;

    public Placement_Filter(){

    }

    public Placement_Filter(Placement placement, String specialization, String domain) {
        this.placement = placement;
        this.specialization = specialization;
        this.domain = domain;
    }

    public Placement getPlacement() {
        return placement;
    }

    public void setPlacement(Placement placement) {
        this.placement = placement;
    }

    public String getSpecialization() {
        return specialization;
    }

    public void setSpecialization(String specialization) {
        this.specialization = specialization;
    }

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

}
