package com.sunnet.org.vote.model;

import com.sunnet.framework.model.BaseModel;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "tb_club_stat")
public class Tb_club_stat extends BaseModel {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(length = 32)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String openid;

    private Integer picid;

    private Integer votenum;

    private Date updatetime;

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

    @Column(name = "openid")
    public String getOpenid() {
        return openid;
    }


    public void setOpenid(String openid) {
        this.openid = openid;
    }

    @Column(name = "picid")
    public Integer getPicid() {
        return picid;
    }

    public void setPicid(Integer picid) {
        this.picid = picid;
    }

    @Column(name = "votenum")
    public Integer getVotenum() {
        return votenum;
    }

    public void setVotenum(Integer votenum) {
        this.votenum = votenum;
    }
}
