package com.openshift.ff.ws;

import com.openshift.ff.data.RoadkillEntity;
import com.openshift.ff.data.WebKillRecord;

import javax.ejb.Stateless;
import javax.enterprise.context.RequestScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.ws.rs.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by spousty on 10/28/14.
 */


@RequestScoped
@Stateless
@Path("/")
public class KillWS {

    @PersistenceContext(name = "kills")
    EntityManager em;

    @GET
    @Produces("application/json")
    public List<HashMap> getKills(){

        List<HashMap> kills = new ArrayList<HashMap>();

        Query query = em.createQuery("select r from RoadkillEntity r");

        kills = processKillsFromDB((ArrayList<RoadkillEntity>) query.getResultList());

        return kills;

    }

    @POST
    @Consumes("application/json")
    @Produces("application/json")
    public RoadkillEntity recordKill(WebKillRecord input){
        RoadkillEntity result = new RoadkillEntity();

        System.out.println(input);

        return result;

    }


    private List<HashMap> processKillsFromDB(ArrayList<RoadkillEntity> inputList){
        ArrayList<HashMap> results = new ArrayList<HashMap>(inputList.size());

        for (RoadkillEntity rawKill : inputList){
            HashMap kill = new HashMap();
            kill.put("id", rawKill.getRoadkillid());
            kill.put("description", rawKill.getDescription());
            kill.put("notes", rawKill.getNotes());
            kill.put("user_id", rawKill.getUsersidUsers());
            kill.put("killtype_id", rawKill.getKilltypeidKilltype());
            double[] positions = {rawKill.getLocation().getX(),rawKill.getLocation().getY()};
            kill.put("position", positions);

            results.add(kill);
        }
        return  results;

    }

    //TODO the input will take easy point and then make them into WKT before putting them in
}
