package com.sunnet.org.adv.model;

import com.sunnet.framework.model.BaseModel;

import javax.persistence.*;

@Entity
@Table(name = "tb_adv_show")
public class Tb_adv_show extends BaseModel{

    public static final long serialVersionUID = 1L;

    @Id
    @Column(length = 32)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;

    private Integer advplace;

    private Tb_adv advid;

    private Integer sort;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Column(name = "adv_place")
    public Integer getAdvplace() {
        return advplace;
    }

    public void setAdvplace(Integer advplace) {
        this.advplace = advplace;
    }

    @Column(name = "adv_id")
    public Tb_adv getAdvid() {
        return advid;
    }

    public void setAdvid(Tb_adv advid) {
        this.advid = advid;
    }

    @Column(name = "sort")
    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }
}
