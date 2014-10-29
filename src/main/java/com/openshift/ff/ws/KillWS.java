package com.openshift.ff.ws;

import com.openshift.ff.data.RoadkillEntity;
import com.openshift.ff.data.WebKillRecord;
import com.vividsolutions.jts.geom.GeometryFactory;
import com.vividsolutions.jts.geom.Point;
import com.vividsolutions.jts.geom.PrecisionModel;
import com.vividsolutions.jts.io.WKTReader;

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

        result.setDescription(input.getDescription());
        result.setKilltypeidKilltype(input.getKilltype_id());
        result.setNotes(input.getNotes());
        result.setUsersidUsers(input.getUser_id());
        double[] inCoords = input.getPosition();

        //since our data in PostGIS has a srid of 4326, we need to use a geometryfactory to get a geom with that model
        GeometryFactory geometryFactory = new GeometryFactory(new PrecisionModel(PrecisionModel.FLOATING), 4326);
        WKTReader wktReader = new WKTReader(geometryFactory);

        String point = "POINT(" + inCoords[0] + " " + inCoords[1] + ")";
        Point inputPoint = null;
        try {
            inputPoint = (Point) wktReader.read(point);
        } catch (Exception e){
            System.out.println("Threw an exception trying to parse point: " + e.getMessage());
        }

        result.setLocation(inputPoint);

        em.persist(result);

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
