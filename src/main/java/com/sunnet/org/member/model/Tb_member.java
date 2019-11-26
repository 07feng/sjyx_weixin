package com.sunnet.org.member.model;

import java.lang.reflect.Method;
import java.util.Date;

import javax.annotation.Generated;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.sunnet.code.util.ModelUtil;
import com.sunnet.framework.model.BaseModel;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

/**
 * 会员表
 * 
 * @author
 *
 *         时间: 2017年00月00日
 */
@Entity
@Table(name = "Tb_member")
public class Tb_member extends BaseModel {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(length = 36, name = "Id")
	// , columnDefinition = "uniqueidentifier default newid()"
	@GeneratedValue(generator = "system-guid")
	@NotFound(action= NotFoundAction.IGNORE)
	@GenericGenerator(name = "system-guid", strategy = "guid")
	private String id;

	private String registrationid;// 保存注册极光推送id

	private Integer randroidios;// 判断是ios还是安卓0：ios ，1安卓

	private String usersname; // 昵称

	private String headimg; // 头像

	private String introduction; // 个人简介

	private String realname; // 真实姓名

	private String mobilenumber; // 手机号码

	private String password; // 密码

	private String email; // 电子邮箱

	private String address; // 通讯地址

	private String qqnumber; // QQ号码

	private String weixinnumber; // 微信号码

	private String weibonumber; // 微博号码

	private String birthday; // 出生日期

	private Integer gender; // 性别（0男1女）

	private String backgroundimgoneimg; // 背景图1

	private String backgroundtwoimg; // 背景图2

	private String backgroundthreeimg; // 背景图3

	@ManyToOne
	@JoinColumn(name = "Levelid")
	private Tb_level levelid; // 会员等级ID

	private Double levelintegral; // 等级积分

	@ManyToOne
	@JoinColumn(name = "Groupid")
	private Tb_group groupid; // 会员组ID

	private String status; // 状态 0启用 1禁用

	private Double wallet; // 钱包余额

	private String capacity; // 总空间容量

	private String remainingcapacity; // 剩余空间容量

	private Date regtime; // 注册时间

	private String devicid;//设备id

	private String alipayname;//支付宝名称（无用）
	
	private int islogin;//是否登录 0未登录 1登录

	private String openid;//微信的唯一ID

	private Integer viewnum;	//访问量

	private Date messagetag;	//消息更新时间

	private Double income;	//收益账户

	private String idcard;	//用户身份证号码

	@Column(name="idcard")
	public String getIdcard() {
		return idcard;
	}

	public void setIdcard(String idcard) {
		this.idcard = idcard;
	}

	@Column(name="income")
	public Double getIncome() {
		return income;
	}

	public void setIncome(Double income) {
		this.income = income;
	}

	public Integer getViewnum() {
		return viewnum;
	}

	public void setViewnum(Integer viewnum) {
		this.viewnum = viewnum;
	}

	@Column(name = "islogin")
	public int getIslogin() {
		return islogin;
	}

	public void setIslogin(int islogin) {
		this.islogin = islogin;
	}

	@Column(name = "alipayname")
	public String getAlipayname() {
		return alipayname;
	}

	public void setAlipayname(String alipayname) {
		this.alipayname = alipayname;
	}

	@Column(name = "UsersName")
	public String getUsersname() {
		return usersname;
	}

	@Column(name = "devicid")
	public String getDevicid() {
		return devicid;
	}

	@Column(name = "randroidios")
	public Integer getRandroidios() {
		return randroidios;
	}

	public void setRandroidios(Integer randroidios) {
		this.randroidios = randroidios;
	}

	@Column(name = "registrationid")
	public String getRegistrationid() {
		return registrationid;
	}

	public void setRegistrationid(String registrationid) {
		this.registrationid = registrationid;
	}

	public void setDevicid(String devicid) {
		this.devicid = devicid;
	}

	public void setUsersname(String usersname) {
		this.usersname = usersname;

	}

	@Column(name = "HeadImg")
	public String getHeadimg() {
		return headimg;
	}

	public void setHeadimg(String headimg) {
		this.headimg = headimg;

	}

	@Column(name = "Introduction")
	public String getIntroduction() {
		return introduction;
	}

	public void setIntroduction(String introduction) {
		this.introduction = introduction;

	}

	@Column(name = "RealName")
	public String getRealname() {
		return realname;
	}

	public void setRealname(String realname) {
		this.realname = realname;

	}

	@Column(name = "MobileNumber")
	public String getMobilenumber() {
		return mobilenumber;
	}

	public void setMobilenumber(String mobilenumber) {
		this.mobilenumber = mobilenumber;

	}

	@Column(name = "PASSWORD")
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;

	}

	@Column(name = "EMail")
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;

	}

	@Column(name = "Address")
	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;

	}

	@Column(name = "QQNumber")
	public String getQqnumber() {
		return qqnumber;
	}

	public void setQqnumber(String qqnumber) {
		this.qqnumber = qqnumber;

	}

	@Column(name = "WeiXinNumber")
	public String getWeixinnumber() {
		return weixinnumber;
	}

	public void setWeixinnumber(String weixinnumber) {
		this.weixinnumber = weixinnumber;

	}

	@Column(name = "WeiBoNumber")
	public String getWeibonumber() {
		return weibonumber;
	}

	public void setWeibonumber(String weibonumber) {
		this.weibonumber = weibonumber;

	}

	@Column(name = "birthDay")
	public String getBirthday() {
		return birthday;
	}

	public void setBirthday(String birthday) {
		this.birthday = birthday;

	}

	@Column(name = "Gender")
	public Integer getGender() {
		return gender;
	}

	public void setGender(Integer gender) {
		this.gender = gender;

	}

	public Tb_level getLevelid() {
		return levelid;
	}

	public void setLevelid(Tb_level levelid) {
		this.levelid = levelid;
	}

	@Column(name = "LevelIntegral")
	public double getLevelintegral() {
		return levelintegral;
	}

	public void setLevelintegral(double d) {
		this.levelintegral = d;

	}

	public Tb_group getGroupid() {
		return groupid;
	}

	public void setGroupid(Tb_group groupid) {
		this.groupid = groupid;
	}

	@Column(name = "STATUS")
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;

	}

	@Column(name = "Wallet")
	public Double getWallet() {
		return wallet;
	}

	public void setWallet(Double wallet) {
		this.wallet = wallet;

	}

	@Column(name = "Capacity")
	public String getCapacity() {
		return capacity;
	}

	public void setCapacity(String capacity) {
		this.capacity = capacity;

	}

	@Column(name = "RemainingCapacity")
	public String getRemainingcapacity() {
		return remainingcapacity;
	}

	public void setRemainingcapacity(String remainingcapacity) {
		this.remainingcapacity = remainingcapacity;

	}

	@Column(name = "RegTime")
	public Date getRegtime() {
		return regtime;
	}

	public void setRegtime(Date regtime) {
		this.regtime = regtime;

	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Column(name = "backgroundimgoneimg")
	public String getBackgroundimgoneimg() {
		return backgroundimgoneimg;
	}

	public void setBackgroundimgoneimg(String backgroundimgoneimg) {
		this.backgroundimgoneimg = backgroundimgoneimg;
	}

	@Column(name = "backgroundtwoimg")
	public String getBackgroundtwoimg() {
		return backgroundtwoimg;
	}

	public void setBackgroundtwoimg(String backgroundtwoimg) {
		this.backgroundtwoimg = backgroundtwoimg;
	}

	@Column(name = "backgroundthreeimg")
	public String getBackgroundthreeimg() {
		return backgroundthreeimg;
	}
	public void setBackgroundthreeimg(String backgroundthreeimg) {
		this.backgroundthreeimg = backgroundthreeimg;
	}

	@Column(name = "openid")
	public String getOpenid() {
		return openid;
	}
	public void setOpenid(String openid) {
		this.openid = openid;
	}

	@Column(name = "messagetag")
	public Date getMessagetag() {
		return messagetag;
	}
	public void setMessagetag(Date messagetag) {
		this.messagetag = messagetag;
	}

	public void getMap() {
		ModelUtil.total.put("id", "会员ID");
		ModelUtil.total.put("usersname", "昵称");
		ModelUtil.total.put("headimg", "头像");
		ModelUtil.total.put("introduction", "个人简介");
		ModelUtil.total.put("realname", "真实姓名");
		ModelUtil.total.put("mobilenumber", "手机号码");
		ModelUtil.total.put("password", "密码");
		ModelUtil.total.put("email", "电子邮箱");
		ModelUtil.total.put("address", "通讯地址");
		ModelUtil.total.put("qqnumber", "QQ号码");
		ModelUtil.total.put("weixinnumber", "微信号码");
		ModelUtil.total.put("weibonumber", "微博号码");
		ModelUtil.total.put("birthday", "出生日期");
		ModelUtil.total.put("gender", "性别");
		ModelUtil.total.put("levelid", "等级ID");
		ModelUtil.total.put("levelintegral", "等级积分");
		ModelUtil.total.put("groupid", "会员组ID");
		ModelUtil.total.put("status", "状态 0启用 1禁用");
		ModelUtil.total.put("wallet", "钱包余额");
		ModelUtil.total.put("capacity", "总空间容量");
		ModelUtil.total.put("remainingcapacity", "剩余空间容量");
		ModelUtil.total.put("regtime", "注册时间");
		ModelUtil.total.put("alipayname", "支付宝账户名");
		ModelUtil.total.put("backgroundimgoneimg", "背景图1");
		ModelUtil.total.put("backgroundtwoimg", "背景图2");
		ModelUtil.total.put("backgroundthreeimg", "背景图3");
		ModelUtil.total.put("devicid", "设备号");
		ModelUtil.total.put("registrationid", "极光推送id");
	}

	public Tb_member() {
	}

	public Tb_member(String id, String usersname, String headimg, String introduction, String realname, String mobilenumber, String email, String alipayname, String address, String qqnumber, String weixinnumber, String weibonumber, String birthday, Integer gender, String backgroundimgoneimg, String backgroundtwoimg, String backgroundthreeimg, Tb_level levelid, String capacity, String remainingcapacity) {
		this.id = id;
		this.usersname = usersname;
		this.headimg = headimg;
		this.introduction = introduction;
		this.realname = realname;
		this.mobilenumber = mobilenumber;
		this.email = email;
		this.alipayname = alipayname;
		this.address = address;
		this.qqnumber = qqnumber;
		this.weixinnumber = weixinnumber;
		this.weibonumber = weibonumber;
		this.birthday = birthday;
		this.gender = gender;
		this.backgroundimgoneimg = backgroundimgoneimg;
		this.backgroundtwoimg = backgroundtwoimg;
		this.backgroundthreeimg = backgroundthreeimg;
		this.levelid = levelid;
		this.capacity = capacity;
		this.remainingcapacity = remainingcapacity;
	}
}
