package com.example.lg.myrecyclerview.model;

/**
 * Created by Tomdog on 2018/3/19.
 */

public abstract class SourceDate {
    private int picture;
    private String disc;
    private String text;
    private int type;

    public int getPicture() {
        return picture;
    }

    public void setPicture(int picture) {
        this.picture = picture;
    }

    public String getDisc() {
        return disc;
    }

    public void setDisc(String disc) {
        this.disc = disc;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
