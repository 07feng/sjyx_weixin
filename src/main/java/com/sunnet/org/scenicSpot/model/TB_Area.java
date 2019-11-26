package com.sunnet.org.scenicSpot.model;

import com.sunnet.framework.model.BaseModel;

import javax.persistence.*;

/**
 * 地区表
 * author jinhao
 * 时间 2018.6.20
 */
@Entity
@Table(name = "TB_Area")
public class TB_Area extends BaseModel {
    private static final long serialVersionUID = 1L;

    @Id
    @Column(length = 32)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String areaname;

    private Integer sort;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Column(name = "areaname")
    public String getAreaname() {
        return areaname;
    }

    public void setAreaname(String areaname) {
        this.areaname = areaname;
    }

    @Column(name = "sort")
    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }
}
