package com.sunnet.org.cover.model;

import com.sunnet.framework.model.BaseModel;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @ClassName: TbCoverCategory
 * @Description: 封面分类
 * @Author: Bryce
 * @Date 2019/9/27 0027下午 17:28
 * @Version 1.0
 */
@Entity
@Table(name = "tb_filmfestivalvip_covercategory")
public class TbFilmfestivalvipCoverCategory extends BaseModel {

    @Id
    @Column(length = 36,name = "Id")
    private Integer id;

    private String catename;

    private int sort;

    @Column(name = "Id")
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
    @Column(name = "CateName")
    public String getCatename() {
        return catename;
    }

    public void setCatename(String catename) {
        this.catename = catename;
    }
    @Column(name = "Sort")
    public int getSort() {
        return sort;
    }

    public void setSort(int sort) {
        this.sort = sort;
    }
}
