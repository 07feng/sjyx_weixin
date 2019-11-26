package com.sunnet.org.cover.model;

import com.sunnet.code.util.ModelUtil;
import com.sunnet.framework.model.BaseModel;

import javax.persistence.*;

/**
 * @ClassName: TbCover
 * @Description: 封面信息实体类
 * @Author: Bryce
 * @Date 2019/9/27 0027下午 14:13
 * @Version 1.0
 */

@Entity
@Table(name = "tb_filmfestivalvip_cover")
public class TbFilmfestivalvipCover extends BaseModel {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(length = 32)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    /**
     * 封面标题
     */
    private String covertitle;
    /**
     * 封面类别id
     */
    private Integer covercateid;
    /**
     * 封面链接url
     */
    private String coverurl;
    /**
     * 封面类别名称
     */
    private String covercatename;

    private Integer sort;

    /**
     * 是否下架
     */
    private int issoldout;

    @Column(name = "CoverTitle")
    public String getCovertitle() {
        return covertitle;
    }

    public void setCovertitle(String covertitle) {
        this.covertitle = covertitle;
    }

    @Column(name = "CoverCateId")
    public Integer getCovercateid() {
        return covercateid;
    }

    public void setCovercateid(Integer covercateid) {
        this.covercateid = covercateid;
    }

    @Column(name = "CoverUrl")
    public String getCoverurl() {
        return coverurl;
    }

    public void setCoverurl(String coverurl) {
        this.coverurl = coverurl;
    }

    @Column(name = "CoverCateName")
    public String getCovercatename() {
        return covercatename;
    }

    public void setCovercatename(String covercatename) {
        this.covercatename = covercatename;
    }

    @Column(name = "Sort")
    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    @Column(name = "Id")
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Column(name = "IsSoldOut")
    public int getIssoldout() {
        return issoldout;
    }

    public void setIssoldout(int issoldout) {
        this.issoldout = issoldout;
    }

    public void getMap(){
        ModelUtil.total.put("id","编号");
        ModelUtil.total.put("covertitle","封面标题");
        ModelUtil.total.put("covercateid","封面类别id");
        ModelUtil.total.put("coverurl","封面链接url");
        ModelUtil.total.put("covercatename","封面类别名称");
        ModelUtil.total.put("sort","排序");
        ModelUtil.total.put("issoldout","是否下架");
    }

    @Override
    public String toString() {
        return "TbFilmfestivalvipCover{" +
                "id=" + id +
                ", covertitle='" + covertitle + '\'' +
                ", covercateid=" + covercateid +
                ", coverurl='" + coverurl + '\'' +
                ", covercatename='" + covercatename + '\'' +
                ", sort=" + sort +
                ", issoldout=" + issoldout +
                '}';
    }
}
