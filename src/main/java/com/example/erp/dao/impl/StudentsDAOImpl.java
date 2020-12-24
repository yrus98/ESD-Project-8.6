package com.example.erp.dao.impl;

import com.example.erp.bean.Placement;
import com.example.erp.bean.Placement_Student;
import com.example.erp.bean.Placement_Student_Web;
import com.example.erp.bean.Students;
import com.example.erp.dao.StudentsDAO;
import com.example.erp.utils.SessionUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.util.ArrayList;
import java.util.List;

public class StudentsDAOImpl implements StudentsDAO {
    @Override
    public boolean emailVerify(Students student) {
        Session session = SessionUtil.getSession();
        try{
            Query query = session.createQuery("from Students where email=:email");
            query.setParameter("email", student.getEmail());
            if(query.getResultList().size()==1){
                return true;
            }
        } catch (HibernateException exception) {
            System.out.print(exception.getLocalizedMessage());
            return false;
        }finally {
            session.close();
        }
        return false;
    }

    @Override
    public Students fetchStudentDataFromEmail(Students student){
        Session session = SessionUtil.getSession();
        try{
            Query query = session.createQuery("from Students where email=:email");
            query.setParameter("email", student.getEmail());
            return (Students) query.getResultList().get(0);
        } catch (HibernateException exception) {
            System.out.print(exception.getLocalizedMessage());
            return new Students();
        }finally {
            session.close();
        }
    }

    @Override
    public String fetchPlacementsList(Students student) {
        try (Session session = SessionUtil.getSession()) {
            ObjectMapper obj = new ObjectMapper();
            Students studData = fetchStudentDataFromEmail(student);
            boolean isPlaced = studData.getPlacement() != null;
            String s = "{ \"student_data\" : { \"student_id\":" + studData.getStudent_id() + ", \"fist_name\":\"" + studData.getFirst_name() + "\", \"last_name\":\"" + studData.getLast_name() + "\", \"roll_no\":\"" + studData.getRoll_no() + "\", \"isPlaced\":\"" + isPlaced + "\", \"email\":\"" + studData.getEmail()
                    + "\", \"photo_path\":\"" + studData.getPhotograph_path() + "\", \"cgpa\":" + studData.getCgpa().toString() + ", \"credits\":" + studData.getTotal_credits().toString() + ", \"grad_year\":" + studData.getGraduation_year() + ", \"domain\":\"" + studData.getDomain() + "\", \"spec\":\"" + studData.getSpecialization() + "\"}";
            s += ", \"placement_list\" : [ ";
            if (!isPlaced) {
                List<Integer> appliedList = new ArrayList<>();
                for (Placement_Student p_s:studData.getPlacement_studentsList()) {
                    appliedList.add(p_s.getPlacement().getId());
                }
                if(appliedList==null || appliedList.isEmpty()){
                    appliedList.add(0);
                }
                Query query;
                query = session.createQuery("select p from Placement p left join p.placement_filter f where p.id not in (:appliedList) and p.minimum_grade <= :studGrade");
                query.setParameter("appliedList", appliedList);
                query.setParameter("studGrade", studData.getCgpa());
                List<Placement> list = query.getResultList();
                System.out.println(studData.getCgpa() + " " + list.size());


                for (Placement p : list) {
                    if ((p.getPlacement_filter().getSpecialization() != null && !p.getPlacement_filter().getSpecialization().isEmpty() && !p.getPlacement_filter().getSpecialization().equals(studData.getSpecialization())) ||
                            (p.getPlacement_filter().getDomain() != null && !p.getPlacement_filter().getDomain().isEmpty() && !p.getPlacement_filter().getDomain().equals(studData.getDomain())))
                        continue;
                    s = s + "{ \"placement_id\":" + p.getId() + ", \"org_name\":\"" + p.getOrganization() + "\", \"profile\":\"" + p.getProfile() + "\", \"description\":\"" + p.getDescription() + "\", \"intake\":" + p.getIntake().toString() + ", \"minimum_grade\":" + p.getMinimum_grade().toString() + "},";
                    //                s.append(obj.writeValueAsString(p));
                }
                s = s.substring(0, s.length() - 1) + " ] ";
            } else {
                s += "], \"company_details\" : {";
                s += "\"placement_id\":" + studData.getPlacement().getId() + ", \"org_name\":\"" + studData.getPlacement().getOrganization() + "\", \"profile\":\"" + studData.getPlacement().getProfile() + "\", \"description\":\"" + studData.getPlacement().getDescription() + "\" }";
            }
            s = s + "}";
            System.out.println(s);
            return s.toString();
        } catch (HibernateException exception) {
            System.out.print(exception.getLocalizedMessage());
            return "Error";
        } catch (Exception e) {
            e.printStackTrace();
            return "Error";
        }
    }

    @Override
    public List<Placement_Student> showApplicationList(Students student) {
        Session session = SessionUtil.getSession();
        ObjectMapper obj = new ObjectMapper();
        try {
            Students studData = fetchStudentDataFromEmail(student);
            Query query;
            query = session.createQuery("select ps from Placement_Student ps inner join ps.students s where ps.students.student_id = :studId");
            query.setParameter("studId", studData.getStudent_id());
            List<Placement_Student> list = query.getResultList();
            return list;
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    @Override
    public boolean addPlacement_Student(Placement_Student_Web placement_student_web) {
        try (Session session = SessionUtil.getSession()) {
            Query query;
            query = session.createQuery("select ps from Placement_Student ps inner join ps.students s inner join ps.placement p where s.student_id = :studId and p.id = :placId");
            query.setParameter("studId", placement_student_web.getStudent_id());
            query.setParameter("placId", placement_student_web.getPlacement_id());

            if(query.getResultList().size()>0)  return false;

            Placement placement = session.load(Placement.class, placement_student_web.getPlacement_id());
            Students students = session.load(Students.class, placement_student_web.getStudent_id());

            Placement_Student placement_student = new Placement_Student(placement, students, placement_student_web.getCv_application(), placement_student_web.getAbout(), placement_student_web.getAcceptance(), placement_student_web.getComments(), placement_student_web.getDate());

            session.beginTransaction();
            Integer id = (Integer) session.save(placement_student);
            System.out.println("Placement_Student record created with id:" + id);
            session.getTransaction().commit();
            return true;
        } catch (HibernateException e) {
            e.printStackTrace();
            return false;
        }
    }
}
