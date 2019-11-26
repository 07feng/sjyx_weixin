package com.sunnet.org.cover.model;

import com.sunnet.code.util.ModelUtil;
import com.sunnet.framework.model.BaseModel;

import javax.persistence.*;

/**
 * @ClassName: TbCoverMusic
 * @Description: 图文使用的音乐
 * @Author: Bryce
 * @Date 2019/9/30 0030下午 14:40
 * @Version 1.0
 */
@Entity
@Table(name = "tb_filmfestivalvip_music")
public class TbFilmfestivalvipMusic extends BaseModel {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(length = 32)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 音乐名称
     */
    private String musicname;
    /**
     * 音乐url
     */
    private String musicurl;
    /**
     * 音乐类型id
     */
    private Integer musiccateid;
    /**
     * 音乐类型名称
     */
    private String musiccatename;
    /**
     * 音乐封面url
     */
    private String imgurl;

    private Integer sort;

    /**
     * 是否下架
     */
    private int issoldout;

    @Column(name = "Id")
    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    @Column(name = "MusicName")
    public String getMusicname() {
        return musicname;
    }

    public void setMusicname(String musicname) {
        this.musicname = musicname;
    }
    @Column(name = "MusicUrl")
    public String getMusicurl() {
        return musicurl;
    }

    public void setMusicurl(String musicurl) {
        this.musicurl = musicurl;
    }
    @Column(name = "MusicCateId")
    public Integer getMusiccateid() {
        return musiccateid;
    }

    public void setMusiccateid(Integer musiccateid) {
        this.musiccateid = musiccateid;
    }
    @Column(name = "MusicCateName")
    public String getMusiccatename() {
        return musiccatename;
    }

    public void setMusiccatename(String musiccatename) {
        this.musiccatename = musiccatename;
    }
    @Column(name = "ImgUrl")
    public String getImgurl() {
        return imgurl;
    }

    public void setImgurl(String imgurl) {
        this.imgurl = imgurl;
    }

    @Column(name = "Sort")
    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    @Column(name = "IsSoldOut")
    public int getIssoldout() {
        return issoldout;
    }

    public void setIssoldout(int issoldout) {
        this.issoldout = issoldout;
    }

    @Override
    public String toString() {
        return "TbFilmfestivalvipMusic{" +
                "id=" + id +
                ", musicname='" + musicname + '\'' +
                ", musicurl='" + musicurl + '\'' +
                ", musiccateid=" + musiccateid +
                ", musiccatename='" + musiccatename + '\'' +
                ", imgurl='" + imgurl + '\'' +
                ", sort=" + sort +
                ", issoldout=" + issoldout +
                '}';
    }

    public void getMap() {
        ModelUtil.total.put("id", "编号");
        ModelUtil.total.put("musicname", "音乐名称");
        ModelUtil.total.put("musicurl", "音乐路径");
        ModelUtil.total.put("musiccateid", "音乐类别id");
        ModelUtil.total.put("musiccatename", "音乐类别名称");
        ModelUtil.total.put("imgurl", "音乐封面路径");
        ModelUtil.total.put("sort", "排序");
        ModelUtil.total.put("issoldout", "是否下架");
    }
}
