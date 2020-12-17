package com.example.erp.dao;

import com.example.erp.bean.Placement_Student;
import com.example.erp.bean.Placement_Student_Web;
import com.example.erp.bean.Students;

import java.util.List;

public interface StudentsDAO {
    public boolean emailVerify(Students student);

    public Students fetchStudentDataFromEmail(Students student);

    public String fetchPlacementsList(Students student);

    public List<Placement_Student> showApplicationList(Students student);

    public boolean addPlacement_Student(Placement_Student_Web placement_student);
}
