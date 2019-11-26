package com.sunnet.org.app.entity;

import java.util.Date;

public class SortDocTime {
    private  int id;

    private String time;

    public SortDocTime(int id, String time) {
        this.id = id;
        this.time = time;
    }

    public int getId() {
        return id;
    }

    public String getTime() {
        return time;
    }
}
