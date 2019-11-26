package com.sunnet.org.vote.model;

import com.sunnet.framework.model.BaseModel;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "tb_club_vote")
public class Tb_club_vote extends BaseModel{

    private static final long serialVersionUID = 1L;

    @Id
    @Column(length = 32)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String openid;

    private String nickname;

    private String headimg;

    @ManyToOne
    @JoinColumn(name = "picid")
    private Tb_club_pic picid;

    private Date votetime;

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

    @Column(name = "nickname")
    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    @Column(name = "headimg")
    public String getHeadimg() {
        return headimg;
    }

    public void setHeadimg(String headimg) {
        this.headimg = headimg;
    }

    public Tb_club_pic getPicid() {
        return picid;
    }

    public void setPicid(Tb_club_pic picid) {
        this.picid = picid;
    }

    @Column(name = "votetime")
    public Date getVotetime() {
        return votetime;
    }

    public void setVotetime(Date votetime) {
        this.votetime = votetime;
    }
}
