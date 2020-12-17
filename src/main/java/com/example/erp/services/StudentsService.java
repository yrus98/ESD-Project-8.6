package com.example.erp.services;

import com.example.erp.bean.Placement;
import com.example.erp.bean.Placement_Student;
import com.example.erp.bean.Placement_Student_Web;
import com.example.erp.bean.Students;
import com.example.erp.dao.impl.StudentsDAOImpl;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.List;

public class StudentsService {
    public boolean verifyEmail(Students student){
        return new StudentsDAOImpl().emailVerify(student);
    }
    public String fetchPlacementsListJSON(Students student) {
        return new StudentsDAOImpl().fetchPlacementsList(student);
    }

    public String showApplicationListJSON(Students student) {
        List<Placement_Student> list = new StudentsDAOImpl().showApplicationList(student);
        String s = "{ \"application_list\" : [ ";
        for (Placement_Student ps:list) {
            s = s + "{ \"placement_id\":" + ps.getPlacement().getId() + ", \"org_name\":\"" + ps.getPlacement().getOrganization() + "\", \"profile\":\"" + ps.getPlacement().getProfile() + "\", \"description\":\"" + ps.getPlacement().getDescription() + "\", \"about\":\"" + ps.getAbout() + "\", \"acceptance\":\""
                    +ps.getAcceptance()+"\", \"comments\":\""+ps.getComments()+"\", \"date\":\""+ps.getDate().toString()+"\"},";
//                s.append(obj.writeValueAsString(p));
        }

        s = s.substring(0,s.length()-1);
        s= s + "] }";
        System.out.println(s);
        return s;
    }

    public boolean applyPlacement(Placement_Student_Web placement_student) {
        return new StudentsDAOImpl().addPlacement_Student(placement_student);
    }
}
