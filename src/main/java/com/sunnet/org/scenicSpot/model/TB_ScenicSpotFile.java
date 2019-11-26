package com.sunnet.org.scenicSpot.model;

import com.sunnet.framework.model.BaseModel;

import javax.persistence.*;
import java.util.Date;

/**
 * 风景区作品表
 * author jinhao
 *
 * 时间2018.6.20
 */
@Entity
@Table(name = "TB_Scenicspotfile")
public class TB_ScenicSpotFile extends BaseModel {
    private static final long serialVersionUID = 1L;

    @Id
    @Column(length = 32)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "scenicspotid")
    private TB_ScenicSpot scenicspotid;

    private String fileid;

    private Integer filetype;

    private Integer sort;

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

    public TB_ScenicSpot getScenicspotid() {
        return scenicspotid;
    }

    public void setScenicspotid(TB_ScenicSpot scenicspotid) {
        this.scenicspotid = scenicspotid;
    }

    @Column(name = "fileid")
    public String getFileid() {
        return fileid;
    }

    public void setFileid(String fileid) {
        this.fileid = fileid;
    }

    @Column(name = "filetype")
    public Integer getFiletype() {
        return filetype;
    }

    public void setFiletype(Integer filetype) {
        this.filetype = filetype;
    }

    @Column(name = "sort")
    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }
}
