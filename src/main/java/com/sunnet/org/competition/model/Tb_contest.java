package com.sunnet.org.competition.model;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.sunnet.code.util.ModelUtil;

import org.hibernate.annotations.GenericGenerator;

import com.sunnet.framework.model.BaseModel;
import com.sunnet.org.competition.model.Tb_contesttheme;
import com.sunnet.org.member.model.Tb_contesttype;
import com.sunnet.org.system.model.Role;
import com.sunnet.org.system.model.User;

/**
 * 赛事列表
 * 
 * @author 
 *
 *         时间: 2017年00月00日
 */
@Entity
@Table(name = "tb_contest")
public class Tb_contest extends BaseModel {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(length = 32)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	@ManyToOne
	@JoinColumn(name = "Contesttypeid")
	private Tb_contesttype contesttypeid; // 赛事类型ID

	private String contestname; // 赛事名称

	private String contestinfo; // 赛事摘要

	private Date conteststarttime; // 赛事开始时间

	private Date contestendtime; // 结束时间

	@ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinTable(name = "t_contest_theme", joinColumns = @JoinColumn(name = "contest_id"), inverseJoinColumns = @JoinColumn(name = "Contesttheme_id"))
	private Set<Tb_contesttheme> contesttheme = new HashSet<Tb_contesttheme>();
	 

	private String ispublichome; // 前台首页显示

	 

	public Set<Tb_contesttheme> getContesttheme() {
		return contesttheme;
	}

	public void setContesttheme(Set<Tb_contesttheme> contesttheme) {
		this.contesttheme = contesttheme;
	}

	private String isrelease; // 是否发布

	private String isaudit; // 参赛作品是否需要审核

	private Integer conteststatus; // 赛事状态 0征集 1评审 2结束

	private String contestawardinfo; // 奖项说明

	private String contestminimg; // 赛事宣传小图

	private String contestmaximg; // 赛事宣传大图

	private String isdouble; // 是否允许组照

	private String goodscore; // 普通用户点赞分数
	
	private String youkegoodscore; // 游客点赞分数
	
	private String dazhonggoodscore; // 大众评委点赞分数
	
	private String expertsgoodscore; // 专家评委点赞分数

	private Date edittime; // 修改时间

	private String showorder; //赛事显示顺序倒序

	private String isactive; //是否在当前活动页显示 1显示 0不显示

	private String posterurl;

	@Column(name = "youkegoodscore")
	public String getYoukegoodscore() {
		return youkegoodscore;
	}

	public void setYoukegoodscore(String youkegoodscore) {
		this.youkegoodscore = youkegoodscore;
	}

	@Column(name = "dazhonggoodscore")
	public String getDazhonggoodscore() {
		return dazhonggoodscore;
	}

	public void setDazhonggoodscore(String dazhonggoodscore) {
		this.dazhonggoodscore = dazhonggoodscore;
	}

	@ManyToOne
	@JoinColumn(name = "Edituserid")
	private User edituserid; // 修改人

	public Tb_contesttype getContesttypeid() {
		return contesttypeid;
	}

	public void setContesttypeid(Tb_contesttype contesttypeid) {
		this.contesttypeid = contesttypeid;

	}

	@Column(name = "ContestName")
	public String getContestname() {
		return contestname;
	}

	public void setContestname(String contestname) {
		this.contestname = contestname;

	}

	@Column(name = "ContestInfo")
	public String getContestinfo() {
		return contestinfo;
	}

	public void setContestinfo(String contestinfo) {
		this.contestinfo = contestinfo;

	}

	@Column(name = "ContestStartTime")
	public Date getConteststarttime() {
		return conteststarttime;
	}

	public void setConteststarttime(Date conteststarttime) {
		this.conteststarttime = conteststarttime;

	}

	@Column(name = "ContestEndTime")
	public Date getContestendtime() {
		return contestendtime;
	}

	public void setContestendtime(Date contestendtime) {
		this.contestendtime = contestendtime;

	}

	 

	@Column(name = "IsPublicHome")
	public String getIspublichome() {
		return ispublichome;
	}

	public void setIspublichome(String ispublichome) {
		this.ispublichome = ispublichome;

	}

	@Column(name = "IsRelease")
	public String getIsrelease() {
		return isrelease;
	}

	public void setIsrelease(String isrelease) {
		this.isrelease = isrelease;

	}

	@Column(name = "IsAudit")
	public String getIsaudit() {
		return isaudit;
	}

	public void setIsaudit(String isaudit) {
		this.isaudit = isaudit;

	}

	@Column(name = "ContestStatus")
	public Integer getConteststatus() {
		return conteststatus;
	}

	public void setConteststatus(Integer conteststatus) {
		this.conteststatus = conteststatus;

	}

	@Column(name = "ContestAwardInfo")
	public String getContestawardinfo() {
		return contestawardinfo;
	}

	public void setContestawardinfo(String contestawardinfo) {
		this.contestawardinfo = contestawardinfo;

	}

	@Column(name = "ContestMinImg")
	public String getContestminimg() {
		return contestminimg;
	}

	public void setContestminimg(String contestminimg) {
		this.contestminimg = contestminimg;

	}

	@Column(name = "ContestMaxImg")
	public String getContestmaximg() {
		return contestmaximg;
	}

	public void setContestmaximg(String contestmaximg) {
		this.contestmaximg = contestmaximg;

	}

	@Column(name = "IsDouble")
	public String getIsdouble() {
		return isdouble;
	}

	public void setIsdouble(String isdouble) {
		this.isdouble = isdouble;

	}

	@Column(name = "GoodScore")
	public String getGoodscore() {
		return goodscore;
	}

	public void setGoodscore(String goodscore) {
		this.goodscore = goodscore;

	}

	@Column(name = "ExpertsGoodScore")
	public String getExpertsgoodscore() {
		return expertsgoodscore;
	}

	public void setExpertsgoodscore(String expertsgoodscore) {
		this.expertsgoodscore = expertsgoodscore;

	}

	@Column(name = "EditTime")
	public Date getEdittime() {
		return edittime;
	}

	public void setEdittime(Date edittime) {
		this.edittime = edittime;

	}

	 
	public User getEdituserid() {
		return edituserid;
	}

	public void setEdituserid(User edituserid) {
		this.edituserid = edituserid;

	}
	@Column(name = "showorder")
	public String getShoworder() {
		return showorder;
	}

	public void setShoworder(String showorder) {
		this.showorder = showorder;
	}
	@Column(name = "isactive")
	public String getIsactive() {
		return isactive;
	}

	public void setIsactive(String isactive) {
		this.isactive = isactive;
	}

	@Column(name = "PosterUrl")
	public String getPosterurl() {
		return posterurl;
	}

	public void setPosterurl(String posterurl) {
		this.posterurl = posterurl;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public void getMap() {
		ModelUtil.total.put("id", "编号");
		ModelUtil.total.put("contesttypeid", "赛事类型ID");
		ModelUtil.total.put("contestname", "赛事名称");
		ModelUtil.total.put("contestinfo", "赛事摘要");
		ModelUtil.total.put("conteststarttime", "赛事开始时间");
		ModelUtil.total.put("contestendtime", "结束时间");
		ModelUtil.total.put("contestthemeid", "赛事主题ID");
		ModelUtil.total.put("ispublichome", "前台首页显示");
		ModelUtil.total.put("isrelease", "是否发布");
		ModelUtil.total.put("isaudit", "参赛作品是否需要审核");
		ModelUtil.total.put("conteststatus", "赛事状态 0征集  1评审  2结束");
		ModelUtil.total.put("contestawardinfo", "奖项说明");
		ModelUtil.total.put("contestminimg", "赛事宣传小图");
		ModelUtil.total.put("contestmaximg", "赛事宣传大图");
		ModelUtil.total.put("isdouble", "是否允许组照");
		ModelUtil.total.put("goodscore", "普通用户点赞分数");
		ModelUtil.total.put("expertsgoodscore", "专家点赞分数");
		ModelUtil.total.put("edittime", "修改时间");
		ModelUtil.total.put("edituserid", "修改人");
		ModelUtil.total.put("posterurl","海报路径");
	}

}
