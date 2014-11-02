package com.openshift.ff.ws;

import com.openshift.ff.data.RoadkillEntity;
import com.openshift.ff.data.WebKillRecord;
import com.vividsolutions.jts.geom.GeometryFactory;
import com.vividsolutions.jts.geom.Point;
import com.vividsolutions.jts.geom.PrecisionModel;
import com.vividsolutions.jts.io.WKTReader;
import io.iron.ironmq.Client;
import io.iron.ironmq.Cloud;
import io.iron.ironmq.Queue;

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

    Queue newKillQueue;

    public KillWS() {

        //for MessageQue
        Client client = new Client(System.getenv("OPENSHIFT_IRONMQ_PROJECT"), System.getenv("OPENSHIFT_IRONMQ_TOKEN"), Cloud.ironAWSUSEast);
        newKillQueue = client.queue("new_kill_recorded");
    }

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
    public HashMap recordKill(WebKillRecord input){
        RoadkillEntity roadkillEntity = new RoadkillEntity();

        roadkillEntity.setDescription(input.getDescription());
        roadkillEntity.setKilltypeidKilltype(input.getKilltype_id());
        roadkillEntity.setNotes(input.getNotes());
        roadkillEntity.setUsersidUsers(input.getUser_id());
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

        roadkillEntity.setLocation(inputPoint);

        System.out.println("Here is the env: " + System.getenv("OPENSHIFT_IRONMQ_PROJECT"));
        try {

            em.persist(roadkillEntity);
            newKillQueue.push("{ 'userid': " + roadkillEntity.getUsersidUsers() + " }");

        } catch (Exception e) {
            e.printStackTrace();
        }


        return processRoadKillEntity(roadkillEntity);

    }


    private List<HashMap> processKillsFromDB(ArrayList<RoadkillEntity> inputList){
        ArrayList<HashMap> results = new ArrayList<HashMap>(inputList.size());

        for (RoadkillEntity rawKill : inputList){
            results.add(processRoadKillEntity(rawKill));
        }
        return  results;

    }

    private HashMap processRoadKillEntity(RoadkillEntity entity){
        HashMap kill = new HashMap();
        kill.put("id", entity.getRoadkillid());
        kill.put("description", entity.getDescription());
        kill.put("notes", entity.getNotes());
        kill.put("user_id", entity.getUsersidUsers());
        kill.put("killtype_id", entity.getKilltypeidKilltype());
        double[] positions = {entity.getLocation().getX(),entity.getLocation().getY()};
        kill.put("position", positions);

        return kill;

    }

}
