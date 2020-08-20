package com.example.pijeonchat;

public class User {
    String id ;
    String username ;
    String imageurl ;
    String status ;

    public User(String id, String username, String imageurl ,String status) {
        this.id = id;
        this.username = username;
        this.imageurl = imageurl;
        this.status = status;
    }

    public User() {
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getImageurl() {
        return imageurl;
    }

    public void setImageurl(String imageurl) {
        this.imageurl = imageurl;
    }
}
