package com.developer.sangbarca.onlineorderfood.model;

import java.io.Serializable;

/**
 * Created by SANG BARCA on 5/9/2017.
 */

public class Information implements Serializable {
    private int id;
    private String name;
    private String link;

    public Information() {
    }

    public Information(int id, String name, String link) {
        this.id = id;
        this.name = name;
        this.link = link;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }
}

