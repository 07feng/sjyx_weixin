package com.sunnet.org.scenicSpot.model;

import com.sunnet.framework.model.BaseModel;
import com.sunnet.org.member.model.Tb_member;

import javax.persistence.*;

/**
 * 城市表
 * author jinhao
 * 时间 2018.6.20
 */
@Entity
@Table(name = "TB_City")
public class TB_City extends BaseModel {
    private static final long serialVersionUID = 1L;

    @Id
    @Column(length = 32)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "areaid")
    private TB_Area areaid; //地区

    private String cityname;

    private Integer sort;

    @ManyToOne
    @JoinColumn(name = "recommemberid")
    private Tb_member recommemberid; //添加人

    public Tb_member getRecommemberid() {
        return recommemberid;
    }

    public void setRecommemberid(Tb_member recommemberid) {
        this.recommemberid = recommemberid;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public TB_Area getAreaid() {
        return areaid;
    }

    public void setAreaid(TB_Area areaid) {
        this.areaid = areaid;
    }

    @Column(name = "cityname")
    public String getCityname() {
        return cityname;
    }

    public void setCityname(String cityname) {
        this.cityname = cityname;
    }

    @Column(name = "sort")
    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

}
