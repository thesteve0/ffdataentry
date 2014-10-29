package com.openshift.ff.data;

/**
 *
 *  This is just a POJO to hold the data coming from the web so I can turn it into a RoadKillentity
 * Created by spousty on 10/29/14.
 */
public class WebKillRecord {

    private String description = "";
    private String notes = "";
    private int killtype_id = 0;
    private int user_id = 1;
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

    public int getKilltype_id() {
        return killtype_id;
    }

    public void setKilltype_id(int killtype_id) {
        this.killtype_id = killtype_id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public double[] getPosition() {
        return position;
    }

    public void setPosition(double[] position) {
        this.position = position;
    }
}
