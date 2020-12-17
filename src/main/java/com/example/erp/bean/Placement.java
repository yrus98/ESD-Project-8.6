package com.example.erp.bean;

import javax.persistence.*;

@Entity
@Table(name="placement")
public class Placement {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String organization;

    @Column(nullable = false)
    private String profile;

    private String description;
    @Column(nullable = false)
    private Integer intake;

    private Float minimum_grade;

    @OneToOne(mappedBy = "placement")
    private Placement_Filter placement_filter;

    public Placement(){

    }
    public Placement(String organization, String profile, String description, Integer intake, Float minimum_grade) {
        this.organization = organization;
        this.profile = profile;
        this.description = description;
        this.intake = intake;
        this.minimum_grade = minimum_grade;
    }

    public String getOrganization() {
        return organization;
    }

    public void setOrganization(String organization) {
        this.organization = organization;
    }

    public String getProfile() {
        return profile;
    }

    public void setProfile(String profile) {
        this.profile = profile;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getIntake() {
        return intake;
    }

    public void setIntake(Integer intake) {
        this.intake = intake;
    }

    public Float getMinimum_grade() {
        return minimum_grade;
    }

    public void setMinimum_grade(Float minimum_grade) {
        this.minimum_grade = minimum_grade;
    }

    public Placement_Filter getPlacement_filter() {
        return placement_filter;
    }

    public void setPlacement_filter(Placement_Filter placement_filter) {
        this.placement_filter = placement_filter;
    }

    public Integer getId() {
        return id;
    }

    public String toString(){
        return "PlacementList [placement_id=" + getId() + ", org_name=" + getOrganization() +
                        ", profile=" + getProfile() + ", description=" + getDescription() + ", intake=" + getIntake().toString() + ", minimum_grade=" + getMinimum_grade().toString()+"]";
    }
}
