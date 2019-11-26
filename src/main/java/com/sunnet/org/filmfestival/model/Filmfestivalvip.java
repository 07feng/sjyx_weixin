package com.sunnet.org.filmfestival.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.sunnet.code.util.ModelUtil;

import com.sunnet.org.cover.model.TbFilmfestivalvipMusic;

import com.sunnet.framework.model.BaseModel;
import com.sunnet.org.member.model.Tb_member;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

/**
 * 影展会员表
 * 
 * @author
 *
 *         时间: 2017年00月00日
 */
@Entity
@Table(name = "filmfestivalvip")
public class Filmfestivalvip extends BaseModel {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(length = 32)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	private String titel;// 个人影展标题

	private Date open_time; // 开通时间

	private int time_length; // 开通时长(天)

	private int sort; // 排序(后台可干预)

	private String titelnote;// 个人简介

	private Integer filegoodcount; // 点赞数量

	private Integer filecommentscount; // 评论数量

	private Integer filepaycount; // 打赏数量

	private Integer viewnum;

	@ManyToOne
	@NotFound(action= NotFoundAction.IGNORE)
	@JoinColumn(name = "musicid")
	private TbFilmfestivalvipMusic musicid;

	private String coverurl;

	private int isboutique;

	private Integer showtypeid;

	private Integer recommend;

	@Column(name = "recommend")
	public Integer getRecommend() {
		return recommend;
	}

	public void setRecommend(Integer recommend) {
		this.recommend = recommend;
	}

	@Column(name = "showtypeid")
	public Integer getShowtypeid() {
		return showtypeid;
	}

	public void setShowtypeid(Integer showtypeid) {
		this.showtypeid = showtypeid;
	}

	public TbFilmfestivalvipMusic getMusicid() {
		return musicid;
	}

	public void setMusicid(TbFilmfestivalvipMusic musicid) {
		this.musicid = musicid;
	}

	@Column(name = "viewnum")
	public Integer getViewnum() {
		return viewnum;
	}

	public void setViewnum(Integer viewnum) {
		this.viewnum = viewnum;
	}

	@ManyToOne
	@JoinColumn(name = "Member_id")
	private Tb_member member_id; // 用户

	@Column(name = "filegoodcount")
	public Integer getFilegoodcount() {
		return filegoodcount;
	}

	public void setFilegoodcount(Integer filegoodcount) {
		this.filegoodcount = filegoodcount;
	}

	@Column(name = "filecommentscount")
	public Integer getFilecommentscount() {
		return filecommentscount;
	}

	public void setFilecommentscount(Integer filecommentscount) {
		this.filecommentscount = filecommentscount;
	}

	@Column(name = "filepaycount")
	public Integer getFilepaycount() {
		return filepaycount;
	}

	public void setFilepaycount(Integer filepaycount) {
		this.filepaycount = filepaycount;
	}

	@Column(name = "titel")
	public String getTitel() {
		return titel;
	}

	public void setTitel(String titel) {
		this.titel = titel;
	}

	@Column(name = "titelnote")
	public String getTitelnote() {
		return titelnote;
	}

	public void setTitelnote(String titelnote) {
		this.titelnote = titelnote;
	}

	@Column(name = "Open_time")
	public Date getOpen_time() {
		return open_time;
	}

	public void setOpen_time(Date open_time) {
		this.open_time = open_time;

	}

	@Column(name = "Time_length")
	public int getTime_length() {
		return time_length;
	}

	public void setTime_length(int time_length) {
		this.time_length = time_length;

	}

	@Column(name = "Sort")
	public int getSort() {
		return sort;
	}

	public void setSort(int sort) {
		this.sort = sort;

	}

	@Column(name = "CoverUrl")
	public String getCoverurl() {
		return coverurl;
	}

	public void setCoverurl(String coverurl) {
		this.coverurl = coverurl;
	}

	@Column(name = "IsBoutique")
	public int getIsboutique() {
		return isboutique;
	}

	public void setIsboutique(int isboutique) {
		this.isboutique = isboutique;
	}

	public Tb_member getMember_id() {
		return member_id;
	}

	public void setMember_id(Tb_member member_id) {
		this.member_id = member_id;

	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public void getMap() {
		ModelUtil.total.put("id", "编号");
		ModelUtil.total.put("open_time", "开通时间");
		ModelUtil.total.put("time_length", "开通时长(天)");
		ModelUtil.total.put("sort", "排序(后台可干预)");
		ModelUtil.total.put("member_id", "null");
		ModelUtil.total.put("filegoodcount", "点赞数量");
		ModelUtil.total.put("filecommentscount", "评论数量");
		ModelUtil.total.put("filepaycount", "打赏数量");
	}

}
