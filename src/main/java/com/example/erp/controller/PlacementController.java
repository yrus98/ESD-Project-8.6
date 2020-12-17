package com.example.erp.controller;

import com.example.erp.bean.Placement;
import com.example.erp.bean.Students;
import com.example.erp.services.PlacementService;
import com.example.erp.services.StudentsService;
import com.example.erp.utils.SessionUtil;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.query.Query;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.net.URISyntaxException;
import java.util.List;

@Path("placements")
public class PlacementController {
    @POST
    @Path("/show_list")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response showPlacementsList(Students student) throws URISyntaxException {

        String resp = new StudentsService().fetchPlacementsListJSON(student);
//        return Response.ok(resp, MediaType.APPLICATION_JSON).build();
        if(!resp.equals("Error")){
            return Response.ok(resp, MediaType.APPLICATION_JSON).build();
        }else{
            return Response.status(203).build();
        }
    }

}
