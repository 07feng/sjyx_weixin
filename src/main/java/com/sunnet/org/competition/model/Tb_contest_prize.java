package com.sunnet.org.competition.model;

import com.sunnet.framework.model.BaseModel;

import javax.persistence.*;

/**
 * @ClassName: Tb_contest_prize
 * @Description:
 * @Author: Bryce
 * @Date 2019/10/23 0023下午 16:36
 * @Version 1.0
 */
@Entity
@Table(name = "tb_contest_prize")
public class Tb_contest_prize extends BaseModel{
    private static final long serialVersionUID = 1L;

    @Id
    @Column(length = 32)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String prizename;

    private String prizeimgurl;

    @ManyToOne
    @JoinColumn(name = "ContestId")
    private Tb_contest contestid;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPrizename() {
        return prizename;
    }

    public void setPrizename(String prizename) {
        this.prizename = prizename;
    }

    public String getPrizeimgurl() {
        return prizeimgurl;
    }

    public void setPrizeimgurl(String prizeimgurl) {
        this.prizeimgurl = prizeimgurl;
    }

    public Tb_contest getContestid() {
        return contestid;
    }

    public void setContestid(Tb_contest contestid) {
        this.contestid = contestid;
    }
}
