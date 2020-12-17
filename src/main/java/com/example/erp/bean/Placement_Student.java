package com.example.erp.bean;

import javax.persistence.*;
import java.sql.Date;

@Entity
@Table(name="placement_student")
public class Placement_Student {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name="placement_id", referencedColumnName = "id")
    private Placement placement;

    @ManyToOne
    @JoinColumn(name="student_id", referencedColumnName = "student_id")
    private Students students;


    private byte[] cv_application;
    private String about;
    private String acceptance;
    private String comments;
    private Date date;

    public Placement_Student(){}

    public Placement_Student(Placement placement, Students students, byte[] cv_application, String about, String acceptance, String comments, Date date) {
        this.placement = placement;
        this.students = students;
        this.cv_application = cv_application;
        this.about = about;
        this.acceptance = acceptance;
        this.comments = comments;
        this.date = date;
    }

    public Placement getPlacement() {
        return placement;
    }

    public void setPlacement(Placement placement) {
        this.placement = placement;
    }

    public void setStudents(Students students) {
        this.students = students;
    }

    public byte[] getCv_application() {
        return cv_application;
    }

    public void setCv_application(byte[] cv_application) {
        this.cv_application = cv_application;
    }

    public String getAbout() {
        return about;
    }

    public void setAbout(String about) {
        this.about = about;
    }

    public String getAcceptance() {
        return acceptance;
    }

    public void setAcceptance(String acceptance) {
        this.acceptance = acceptance;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
    public Students getStudents() {
        return students;
    }

}
