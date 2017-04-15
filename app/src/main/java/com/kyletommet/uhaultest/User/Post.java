package com.kyletommet.uhaultest.User;

/**
 * Created by Kyle Tommet on 4/14/2017.
 */

public class Post {
    private String title, body;
    private int postersID;

    public Post(String title, String body, int postersID){
        this.body = body;
        this.title = title;
        this.postersID = postersID;
    }
    public String getTitle(){return this.title;}
    public String getPostBody(){return this.body;}
    public int getPostersID(){return this.postersID;}
}
