package com.sunnet.org.scenicSpot.model;

import com.sunnet.framework.model.BaseModel;
import com.sunnet.org.member.model.Tb_member;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import javax.persistence.*;
import java.util.Date;

/**
 * 风景区表
 * author jinhao
 *
 * 时间2018.6.20
 */
@Entity
@Table(name = "TB_Scenicspot")
public class TB_ScenicSpot extends BaseModel {
    private static final long serialVersionUID = 1L;

    @Id
    @Column(length = 32)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "cityid")
    private TB_City cityid; //城市ID

    private String scenicspotname;

    private String intro;   //景区介绍

    private String coverpicurl; //封面图片地址

    @ManyToOne
    @NotFound(action= NotFoundAction.IGNORE)
    @JoinColumn(name = "recommemberid")
    private Tb_member recommemberid;    //推荐人

    @ManyToOne
    @NotFound(action= NotFoundAction.IGNORE)
    @JoinColumn(name = "adminmemberid")
    private Tb_member adminmemberid;    //管理员

    private Date adddate;

    private Integer isclose;

    private Integer photonum;

    private Integer articlenum;

    private Integer videonum;

    private Integer sort;

    private Date updatetime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public TB_City getCityid() {
        return cityid;
    }

    public void setCityid(TB_City cityid) {
        this.cityid = cityid;
    }

    @Column(name = "intro")
    public String getIntro() {
        return intro;
    }

    public void setIntro(String intro) {
        this.intro = intro;
    }

    @Column(name = "scenicspotname")
    public String getScenicspotname() {
        return scenicspotname;
    }

    public void setScenicspotname(String scenicspotname) {
        this.scenicspotname = scenicspotname;
    }

    @Column(name = "coverpicurl")
    public String getCoverpicurl() {
        return coverpicurl;
    }

    public void setCoverpicurl(String coverpicurl) {
        this.coverpicurl = coverpicurl;
    }

    @Column(name = "isclose")
    public Integer getIsclose() {
        return isclose;
    }

    public void setIsclose(Integer isclose) {
        this.isclose = isclose;
    }

    @Column(name = "photonum")
    public Integer getPhotonum() {
        return photonum;
    }

    public void setPhotonum(Integer photonum) {
        this.photonum = photonum;
    }

    @Column(name = "articlenum")
    public Integer getArticlenum() {
        return articlenum;
    }

    public void setArticlenum(Integer articlenum) {
        this.articlenum = articlenum;
    }

    @Column(name = "videonum")
    public Integer getVideonum() {
        return videonum;
    }

    public void setVideonum(Integer videonum) {
        this.videonum = videonum;
    }

    @Column(name = "sort")
    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    @Column(name = "updatetime")
    public Date getUpdatetime() {
        return updatetime;
    }

    public void setUpdatetime(Date updatetime) {
        this.updatetime = updatetime;
    }

    public Tb_member getRecommemberid() {
        return recommemberid;
    }

    public void setRecommemberid(Tb_member recommemberid) {
        this.recommemberid = recommemberid;
    }

    public Tb_member getAdminmemberid() {
        return adminmemberid;
    }

    public void setAdminmemberid(Tb_member adminmemberid) {
        this.adminmemberid = adminmemberid;
    }

    @Column(name = "adddate")
    public Date getAdddate() {
        return adddate;
    }

    public void setAdddate(Date adddate) {
        this.adddate = adddate;
    }
}
