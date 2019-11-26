package com.sunnet.org.app.entity;

import java.io.Serializable;

public class FileType implements Serializable{


    public FileType() {
    }

    private Integer id; //类别Id
    private String type_name; //类别名称

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public FileType(Integer id, String type_name) {
        this.id = id;
        this.type_name = type_name;
    }

    public String getType_name() {

        return type_name;
    }

    public void setType_name(String type_name) {
        this.type_name = type_name;
    }
}
