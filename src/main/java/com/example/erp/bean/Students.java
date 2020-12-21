package com.example.erp.bean;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "students")
public class Students {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer student_id;

	@Column(name = "roll_number", nullable = false, unique = true)
	private String roll_no;
	@Column(nullable = false)
	private String first_name;
	private String last_name;
	@Column(nullable = false, unique = true)
	private String email;

	private String photograph_path;
	@Column(nullable = false)
	private Float cgpa;
	@Column(nullable = false)
	private Integer total_credits;
	private Integer graduation_year;
	private String domain;
	private String specialization;

	@OneToOne
	@JoinColumn(name = "placement_id", referencedColumnName = "id")
	private Placement placement;

	@OneToMany(mappedBy = "students", fetch = FetchType.EAGER)
	private List<Placement_Student> placement_studentsList;

	public Students() {

	}

	public Students(String roll_no, String first_name, String last_name, String email, String photograph_path, Float cgpa, Integer total_credits, Integer graduation_year, String domain, String specialization, Placement placement, List<Placement_Student> placement_studentsList) {
		this.roll_no = roll_no;
		this.first_name = first_name;
		this.last_name = last_name;
		this.email = email;
		this.photograph_path = photograph_path;
		this.cgpa = cgpa;
		this.total_credits = total_credits;
		this.graduation_year = graduation_year;
		this.domain = domain;
		this.specialization = specialization;
		this.placement = placement;
		this.placement_studentsList = placement_studentsList;
	}

	public String getRoll_no() {
		return roll_no;
	}

	public void setRoll_no(String roll_no) {
		this.roll_no = roll_no;
	}

	public String getFirst_name() {
		return first_name;
	}

	public void setFirst_name(String first_name) {
		this.first_name = first_name;
	}

	public String getLast_name() {
		return last_name;
	}

	public void setLast_name(String last_name) {
		this.last_name = last_name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhotograph_path() {
		return photograph_path;
	}

	public void setPhotograph_path(String photograph_path) {
		this.photograph_path = photograph_path;
	}

	public Float getCgpa() {
		return cgpa;
	}

	public void setCgpa(Float cgpa) {
		this.cgpa = cgpa;
	}

	public Integer getTotal_credits() {
		return total_credits;
	}

	public void setTotal_credits(Integer total_credits) {
		this.total_credits = total_credits;
	}

	public Integer getGraduation_year() {
		return graduation_year;
	}

	public void setGraduation_year(Integer graduation_year) {
		this.graduation_year = graduation_year;
	}

	public String getDomain() {
		return domain;
	}

	public void setDomain(String domain) {
		this.domain = domain;
	}

	public String getSpecialization() {
		return specialization;
	}

	public void setSpecialization(String specialization) {
		this.specialization = specialization;
	}

	public Placement getPlacement() {
		return placement;
	}

	public void setPlacement(Placement placement) {
		this.placement = placement;
	}

	public List<Placement_Student> getPlacement_studentsList() {
		return placement_studentsList;
	}

	public void setPlacement_studentsList(List<Placement_Student> placement_studentsList) {
		this.placement_studentsList = placement_studentsList;
	}

	public Integer getStudent_id() {
		return student_id;
	}
}
