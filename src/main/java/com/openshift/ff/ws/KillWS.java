package com.openshift.ff.ws;

import com.openshift.ff.data.RoadkillEntity;

import javax.ejb.Stateless;
import javax.enterprise.context.RequestScoped;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import java.util.ArrayList;
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
    public List<RoadkillEntity> getKills(){

        List<RoadkillEntity> users = new ArrayList<RoadkillEntity>();

        Query query = em.createQuery("select r from RoadkillEntity r");

        users = (ArrayList<RoadkillEntity>) query.getResultList();

        return users;


    }
}
