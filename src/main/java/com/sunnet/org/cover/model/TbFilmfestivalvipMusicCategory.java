package com.sunnet.org.cover.model;

import com.sunnet.framework.model.BaseModel;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @ClassName: TbCoverMusicCategory
 * @Description: 音乐分类
 * @Author: Bryce
 * @Date 2019/9/30 0030下午 16:28
 * @Version 1.0
 */
@Entity
@Table(name = "tb_filmfestivalvip_musiccategory")
public class TbFilmfestivalvipMusicCategory extends BaseModel {

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
