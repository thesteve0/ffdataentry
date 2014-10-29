package com.openshift.ff.data;

import com.vividsolutions.jts.geom.Point;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Calendar;

/**
 * Created by spousty on 10/27/14.
 */
@Entity
@Table(name = "roadkill", schema = "public", catalog = "flatfluffy")
public class RoadkillEntity {
    private int roadkillid;
    private Point location;
    private String description;
    private String notes;
    private Timestamp entrytimestamp;
    private Integer usersidUsers;
    private Integer killtypeidKilltype;

    @PrePersist
    protected void onCreate() {
        entrytimestamp = new Timestamp(Calendar.getInstance().getTime().getTime());
    }

    @Id
    @Column(name = "roadkillid", unique = true, updatable = false, nullable = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "roadkill_roadkillid_seq")
    @SequenceGenerator(name =  "roadkill_roadkillid_seq", sequenceName =  "roadkill_roadkillid_seq", allocationSize = 1)
    public int getRoadkillid() {
        return roadkillid;
    }

    public void setRoadkillid(int roadkillid) {
        this.roadkillid = roadkillid;
    }

    @Basic
    @Column(name = "location")
    @Type(type="org.hibernate.spatial.GeometryType")
    public Point getLocation() {
        return location;
    }

    public void setLocation(Point location) {
        this.location = location;
    }

    @Basic
    @Column(name = "description")
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Basic
    @Column(name = "notes")
    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    @Basic
    @Column(name = "entrytimestamp")
    public Timestamp getEntrytimestamp() {
        return entrytimestamp;
    }

    public void setEntrytimestamp(Timestamp entrytimestamp) {
        this.entrytimestamp = entrytimestamp;
    }

    @Basic
    @Column(name = "usersid_users")
    public Integer getUsersidUsers() {
        return usersidUsers;
    }

    public void setUsersidUsers(Integer usersidUsers) {
        this.usersidUsers = usersidUsers;
    }

    @Basic
    @Column(name = "killtypeid_killtype")
    public Integer getKilltypeidKilltype() {
        return killtypeidKilltype;
    }

    public void setKilltypeidKilltype(Integer killtypeidKilltype) {
        this.killtypeidKilltype = killtypeidKilltype;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        RoadkillEntity that = (RoadkillEntity) o;

        if (roadkillid != that.roadkillid) return false;
        if (description != null ? !description.equals(that.description) : that.description != null) return false;
        if (entrytimestamp != null ? !entrytimestamp.equals(that.entrytimestamp) : that.entrytimestamp != null)
            return false;
        if (killtypeidKilltype != null ? !killtypeidKilltype.equals(that.killtypeidKilltype) : that.killtypeidKilltype != null)
            return false;
        if (location != null ? !location.equals(that.location) : that.location != null) return false;
        if (notes != null ? !notes.equals(that.notes) : that.notes != null) return false;
        if (usersidUsers != null ? !usersidUsers.equals(that.usersidUsers) : that.usersidUsers != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = roadkillid;
        result = 31 * result + (location != null ? location.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + (notes != null ? notes.hashCode() : 0);
        result = 31 * result + (entrytimestamp != null ? entrytimestamp.hashCode() : 0);
        result = 31 * result + (usersidUsers != null ? usersidUsers.hashCode() : 0);
        result = 31 * result + (killtypeidKilltype != null ? killtypeidKilltype.hashCode() : 0);
        return result;
    }
}
