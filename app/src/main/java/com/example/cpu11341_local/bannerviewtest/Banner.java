package com.example.cpu11341_local.bannerviewtest;

/**
 * Created by CPU11341-local on 8/7/2017.
 */

public class Banner {
    private int imageID;
    private int roomID;
    private int action_type;
    private String link;

    public Banner(int imageID, int roomID) {
        this.imageID = imageID;
        this.roomID = roomID;
        this.action_type = 1;
    }
    public Banner(int imageID, String link) {
        this.imageID = imageID;
        this.link = link;
        this.action_type = 2;
    }

    public int getImageID() {
        return imageID;
    }


    public int getRoomID() {
        return roomID;
    }

    public int getAction_type() {
        return action_type;
    }

    public String getLink() {
        return link;
    }
}
