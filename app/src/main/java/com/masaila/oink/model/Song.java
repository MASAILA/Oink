package com.masaila.oink.model;

/**
 * Created by MASAILA on 16/5/13.
 */
public class Song {

    private int duration;
    private boolean local;
    private String path;

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public boolean isLocal() {
        return local;
    }

    public void setLocal(boolean local) {
        this.local = local;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
