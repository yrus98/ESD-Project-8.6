package com.example.erp.controller;

import com.example.erp.bean.Placement_Student;
import com.example.erp.bean.Placement_Student_Web;
import com.example.erp.bean.Students;
import com.example.erp.services.StudentsService;
import org.glassfish.jersey.media.multipart.FormDataParam;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.net.URISyntaxException;
import java.sql.Date;

@Path("students")
public class StudentsController {
    @POST
    @Path("/login")
    @Produces(MediaType.TEXT_PLAIN)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response loginStudent(Students student) throws URISyntaxException {

        if(new StudentsService().verifyEmail(student)){
            return Response.ok().build();
        }else{
            return Response.status(203).build();
        }
    }


    @POST
    @Path("/show_hist")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response showApplicationHistory(Students student) throws URISyntaxException {

        String resp = new StudentsService().showApplicationListJSON(student);
        return Response.ok(resp, MediaType.APPLICATION_JSON).build();
    }

    @POST
    @Path("/apply")
    @Produces(MediaType.TEXT_PLAIN)
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    public Response applyPlacement(@FormDataParam("placement_id") Integer placement_id,
                                   @FormDataParam("student_id") Integer student_id,
                                   @FormDataParam("about") String about,
                                   @FormDataParam("cv_application") byte[] cv_application,
                                   @FormDataParam("acceptance") String acceptance,
                                   @FormDataParam("comments") String comments,
                                   @FormDataParam("date") Date date) throws URISyntaxException {
        Placement_Student_Web placement_student = new Placement_Student_Web(placement_id,student_id,cv_application,about,acceptance,comments,date);
        if(new StudentsService().applyPlacement(placement_student)){
            return Response.ok().build();
        }else{
            return Response.status(203).build();
        }
    }


}
