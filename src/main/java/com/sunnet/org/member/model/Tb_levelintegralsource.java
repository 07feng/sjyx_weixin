package com.sunnet.org.member.model;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.sunnet.code.util.ModelUtil;
import com.sunnet.framework.model.BaseModel;
import com.sunnet.org.system.model.User;

/**
 * 会员等级积分来源表
 * 
 * @author jing
 *
 *         时间: 2017年00月00日
 */
@Entity
@Table(name = "tb_levelintegralsource")
public class Tb_levelintegralsource extends BaseModel {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(length = 32)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@ManyToOne
	@JoinColumn(name = "Levelid")
	private Tb_level levelid; // 等级ID

	private Double login; // 登录1

	private Double upload; // 上传1

	private Double comments; // 评论1

	private Double uncomments; // 被评论1

	private Double good; // 点赞1

	private Double ungood; // 被点赞1

	private Double keep; // 收藏1

	private Double unkeep; // 被收藏1

	private Double focus;//关注
	
	private Double unfocus; // 被关注1

	private Double chargemoney; // 充值可获得多次积分

	private Double pay; // 打赏1

	private Double unpay; // 被打赏1

	private Double shortlisted; // 入围可获得 多次积分

	private int game;// 参赛可获得 多次积分

	private int personshow;// 个人影展可获得 多次积分

	private Double winning; // 获奖可获得多次积分

	private Date edittime; // 修改时间

	@ManyToOne
	@JoinColumn(name = "edituserid")
	private User edituserid; // 修改人ID

	 
	public Tb_level getLevelid() {
		return levelid;
	}

	public void setLevelid(Tb_level levelid) {
		this.levelid = levelid;

	}
	@Column(name = "Focus")
	public Double getFocus() {
		return focus;
	}

	public void setFocus(Double focus) {
		this.focus = focus;
	}

	@Column(name = "game")
	public int getGame() {
		return game;
	}

	public void setGame(int game) {
		this.game = game;
	}

	@Column(name = "personshow")
	public int getPersonshow() {
		return personshow;
	}

	public void setPersonshow(int personshow) {
		this.personshow = personshow;
	}

	@Column(name = "Login")
	public Double getLogin() {
		return login;
	}

	public void setLogin(Double login) {
		this.login = login;

	}

	@Column(name = "Upload")
	public Double getUpload() {
		return upload;
	}

	public void setUpload(Double upload) {
		this.upload = upload;

	}

	@Column(name = "Comments")
	public Double getComments() {
		return comments;
	}

	public void setComments(Double comments) {
		this.comments = comments;

	}

	@Column(name = "UnComments")
	public Double getUncomments() {
		return uncomments;
	}

	public void setUncomments(Double uncomments) {
		this.uncomments = uncomments;

	}

	@Column(name = "Good")
	public Double getGood() {
		return good;
	}

	public void setGood(Double good) {
		this.good = good;

	}

	@Column(name = "UnGood")
	public Double getUngood() {
		return ungood;
	}

	public void setUngood(Double ungood) {
		this.ungood = ungood;

	}

	@Column(name = "keep")
	public Double getKeep() {
		return keep;
	}

	public void setKeep(Double keep) {
		this.keep = keep;

	}

	@Column(name = "UnKeep")
	public Double getUnkeep() {
		return unkeep;
	}

	public void setUnkeep(Double unkeep) {
		this.unkeep = unkeep;

	}

	@Column(name = "UnFocus")
	public Double getUnfocus() {
		return unfocus;
	}

	public void setUnfocus(Double unfocus) {
		this.unfocus = unfocus;

	}

	@Column(name = "ChargeMoney")
	public Double getChargemoney() {
		return chargemoney;
	}

	public void setChargemoney(Double chargemoney) {
		this.chargemoney = chargemoney;

	}

	@Column(name = "Pay")
	public Double getPay() {
		return pay;
	}

	public void setPay(Double pay) {
		this.pay = pay;

	}

	@Column(name = "UnPay")
	public Double getUnpay() {
		return unpay;
	}

	public void setUnpay(Double unpay) {
		this.unpay = unpay;

	}

	@Column(name = "Shortlisted")
	public Double getShortlisted() {
		return shortlisted;
	}

	public void setShortlisted(Double shortlisted) {
		this.shortlisted = shortlisted;

	}

	@Column(name = "Winning")
	public Double getWinning() {
		return winning;
	}

	public void setWinning(Double winning) {
		this.winning = winning;

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

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public void getMap() {
		ModelUtil.total.put("id", "编号");
		ModelUtil.total.put("levelid", "等级ID");
		ModelUtil.total.put("login", "登录");
		ModelUtil.total.put("upload", "上传");
		ModelUtil.total.put("comments", "评论");
		ModelUtil.total.put("uncomments", "被评论");
		ModelUtil.total.put("good", "点赞");
		ModelUtil.total.put("ungood", "被点赞");
		ModelUtil.total.put("keep", "收藏");
		ModelUtil.total.put("unkeep", "被收藏");
		ModelUtil.total.put("focus", "关注");
		ModelUtil.total.put("unfocus", "被关注");
		ModelUtil.total.put("chargemoney", "充值");
		ModelUtil.total.put("pay", "打赏");
		ModelUtil.total.put("unpay", "被打赏");
		ModelUtil.total.put("shortlisted", "入围");
		ModelUtil.total.put("winning", "获奖");
		ModelUtil.total.put("edittime", "修改时间");
		ModelUtil.total.put("edituserid", "修改人ID");
		ModelUtil.total.put("game", "参赛");
		ModelUtil.total.put("personshow", "个人影展");
	 

		
	}

}
