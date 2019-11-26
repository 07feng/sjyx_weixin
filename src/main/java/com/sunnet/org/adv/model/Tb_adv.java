package com.sunnet.org.adv.model;

import com.sunnet.framework.model.BaseModel;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "tb_adv")
public class Tb_adv extends BaseModel {

    public static final long serialVersionUID = 1L;

    @Id
    @Column(length = 32)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;

    private String advtitle;

    private String advurl;

    private Byte advtype;

    private Integer advheight;

    private Integer advwidth;

    private Date createtime;

    private Byte linkpagetype;

    private String appid;

    private String linkpagepath;

    private Byte status;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Column(name = "adv_title")
    public String getAdvtitle() {
        return advtitle;
    }

    public void setAdvtitle(String advtitle) {
        this.advtitle = advtitle;
    }

    @Column(name = "adv_url")
    public String getAdvurl() {
        return advurl;
    }

    public void setAdvurl(String advurl) {
        this.advurl = advurl;
    }

    @Column(name = "adv_type")
    public Byte getAdvtype() {
        return advtype;
    }

    public void setAdvtype(Byte advtype) {
        this.advtype = advtype;
    }

    @Column(name = "adv_height")
    public Integer getAdvheight() {
        return advheight;
    }

    public void setAdvheight(Integer advheight) {
        this.advheight = advheight;
    }

    @Column(name = "adv_width")
    public Integer getAdvwidth() {
        return advwidth;
    }

    public void setAdvwidth(Integer advwidth) {
        this.advwidth = advwidth;
    }

    @Column(name = "create_time")
    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    @Column(name = "linkpage_type")
    public Byte getLinkpagetype() {
        return linkpagetype;
    }

    public void setLinkpagetype(Byte linkpagetype) {
        this.linkpagetype = linkpagetype;
    }

    @Column(name = "app_id")
    public String getAppid() {
        return appid;
    }

    public void setAppid(String appid) {
        this.appid = appid;
    }

    @Column(name = "linkpage_path")
    public String getLinkpagepath() {
        return linkpagepath;
    }

    public void setLinkpagepath(String linkpagepath) {
        this.linkpagepath = linkpagepath;
    }

    @Column(name = "status")
    public Byte getStatus() {
        return status;
    }

    public void setStatus(Byte status) {
        this.status = status;
    }
}
