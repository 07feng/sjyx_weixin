package com.sunnet.org.vote.model;

import com.sunnet.framework.model.BaseModel;
import com.sunnet.org.util.DataTime;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "tb_club_pic")
public class Tb_club_pic extends BaseModel {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(length = 32)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String picurl;

    private String authorname;

    private String picname;

    private String describe;

    private Integer sort;

    private Integer votenum;

    private Date updatetime;

    private int devicetype;

    @Column(name = "updatetime")
    public Date getUpdatetime() {
        return updatetime;
    }

    public void setUpdatetime(Date updatetime) {
        this.updatetime = updatetime;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Column(name = "picurl")
    public String getPicurl() {
        return picurl;
    }

    public void setPicurl(String picurl) {
        this.picurl = picurl;
    }

    @Column(name = "authorname")
    public String getAuthorname() {
        return authorname;
    }

    public void setAuthorname(String authorname) {
        this.authorname = authorname;
    }

    @Column(name = "picname")
    public String getPicname() {
        return picname;
    }

    public void setPicname(String picname) {
        this.picname = picname;
    }

    @Column(name = "describe")
    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }

    @Column(name = "sort")
    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    @Column(name = "votenum")
    public Integer getVotenum() {
        return votenum;
    }

    public void setVotenum(Integer votenum) {
        this.votenum = votenum;
    }

    @Column(name = "devicetype")
    public int getDevicetype() {
        return devicetype;
    }

    public void setDevicetype(int devicetype) {
        this.devicetype = devicetype;
    }
}
