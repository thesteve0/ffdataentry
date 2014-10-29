package com.openshift.ff.data;

/**
 *
 *  This is just a POJO to hold the data coming from the web so I can turn it into a RoadKillentity
 * Created by spousty on 10/29/14.
 */
public class WebKillRecord {

    private String description = "";
    private String notes = "";
    private int killtype = 0;
    private int userid = 1;
    private double[] position;


    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public int getKilltype() {
        return killtype;
    }

    public void setKilltype(int killtype) {
        this.killtype = killtype;
    }

    public int getUserid() {
        return userid;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }

    public double[] getPoints() {
        return points;
    }

    public void setPoints(double[] points) {
        this.points = points;
    }
}
