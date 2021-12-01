package com.softcodes.gabbagecollector.database;

public class Tips_Model {
    private String tid;
    private String tname;
    private String tdescription;
    private String timage;
    private String tdrname;

    public Tips_Model(String tid, String tname, String tdescription, String timage, String tdrname) {
        this.tid = tid;
        this.tname = tname;
        this.tdescription = tdescription;
        this.timage = timage;
        this.tdrname = tdrname;
    }

    public String getTid() {
        return tid;
    }

    public String getTname() {
        return tname;
    }

    public String getTdescription() {
        return tdescription;
    }

    public String getTimage() {
        return timage;
    }

    public String getTdrname() {
        return tdrname;
    }

}
