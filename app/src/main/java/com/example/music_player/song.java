package com.example.music_player;

import java.io.Serializable;

public class song implements Serializable {
    private String Title;
    private String Album;
    private  String path;


    public song(String title, String album, String path) {
        this.Title = title;
        this.Album = album;
        this.path = path;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getAlbum() {
        return Album;
    }

    public void setAlbum(String album) {
        Album = album;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
