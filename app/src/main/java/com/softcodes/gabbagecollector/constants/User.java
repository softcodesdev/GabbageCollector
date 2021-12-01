package com.softcodes.gabbagecollector.constants;

public class User {
    private int userID;
    private String userPassword;
    private String userEmail;
    private String userRole;
    private String username;
    private String fname;
    private String lname;

    public User(int userID, String userPassword,String userEmail,String userRole,String username,String fname,String lname){

        this.userID =userID;
        this.userPassword =userPassword;
        this.userEmail = userEmail;
        this.userRole = userRole;
        this.username =username;
        this.fname = fname;
        this.lname = lname;

    }

    public int getUserID() {
        return userID;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public String getUserRole() {
        return userRole;
    }

    public String getUsername() {
        return username;
    }

    public String getFname() {
        return fname;
    }

    public String getLname() {
        return lname;
    }
}
