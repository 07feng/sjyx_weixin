package com.sunnet.org.information.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.sunnet.code.util.ModelUtil;
import com.sunnet.org.member.model.Tb_member;

@Entity
@Table(name = "vie_Tb_DocInfo")
public class vie_Tb_DocInfo {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(length = 36, name = "Id")
	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "com.sunnet.framework.util.IDGenerator")
	private String id;

	private Date todaytime;//点赞时间
	
	private String memberiddz;// 是否点赞状态

	private String memberidsc;// 是否收藏状态

	private String fid; // 组图父ID

	private int isboutique;// 是否精品(o否1是)

	private String doctitle; // 文档标题

	private String filedescribe; // 文件描述

	private int filetype; // 文件类型(0图片,1视频)

	private String uploadtime; // 上传时间

	private String shootingtime; // 拍摄时间

	private Date publictime; // 公开时间

	private Long createdate; // 时间戳

	@ManyToOne
	@JoinColumn(name = "Filetypeid")
	private Tb_filetype filetypeid; // 文件分类ID

	@ManyToOne
	@JoinColumn(name = "memberid")
	private Tb_member memberid; // 会员ID

	@ManyToOne
	@JoinColumn(name = "Photoalbumid")
	private Tb_photoalbum photoalbumid; // 相册ID

	private String devicenumber; // 设备型号

	private int iwidht; // 图像宽度

	private int iheight; // 图像高度

	private String exposuretime; // 曝光时间

	private String exposureprogram; // 曝光程序

	private String exposurecompensation; // 曝光补偿

	private String exposuremodel; // 曝光模式

	private String isorate; // ISO速率

	private String shutterspeed; // 快门速度

	private String aperture; // 光圈(p)值

	private String lensaperture; // 镜头光圈

	private String largestaperture; // 最大光圈

	private String flash; // 闪光灯

	private String focallength; // 焦距

	@Column(name = "createdate")
	public Long getCreatedate() {
		return createdate;
	}

	public void setCreatedate(Long createdate) {
		this.createdate = createdate;
	}

	private String whitebalance; // 白平衡

	private int filelength; // 文件大小

	private String gpslat; // 纬度

	private String gpslon; // 经度

	private String pcthumbnailpathimg; // PC缩略图

	private String phonethumbnailpathimg; // 手机缩略图

	private int docstatus; // 文档审核状态 0未审核 1已审核

	private String padthumbnailpathimg; // 平板缩略图

	private String filepath; // 源文件路径

	private double filescore; // 文件分数

	private String nsfw; // 非法图片分数

	private int ispublic; // 是否公开（0公开，1不公开）

	private int isdelete; // 回收站(1删除 0不删除)

	private int isparticipating; // 是否参赛（0不参赛1参）

	private int isportrait; // 是否人像

	private int isdouble; // 是否组图 0单图 1组图

	private int fileviewcount; // 浏览数量

	private int filegoodcount; // 点赞数量

	private int filekeepcount; // 收藏数量

	private int filecommentscount; // 评论数量

	private int filepaycount; // 打赏数量

	private int fileserverid; // null

	private String storageformat; // 存储格式

	private int actityforwarcount; // 分享转发次数

	@Column(name = "FID")
	public String getFid() {
		return fid;
	}

	@Column(name = "isboutique")
	public int getIsboutique() {
		return isboutique;
	}

	public void setIsboutique(int isboutique) {
		this.isboutique = isboutique;
	}

	public void setFid(String fid) {
		this.fid = fid;

	}

	@Column(name = "DocTitle")
	public String getDoctitle() {
		return doctitle;
	}

	public void setDoctitle(String doctitle) {
		this.doctitle = doctitle;

	}

	@Column(name = "FileDescribe")
	public String getFiledescribe() {
		return filedescribe;
	}

	public void setFiledescribe(String filedescribe) {
		this.filedescribe = filedescribe;

	}

	@Column(name = "FileType")
	public int getFiletype() {
		return filetype;
	}

	public void setFiletype(int filetype) {
		this.filetype = filetype;

	}

	@Column(name = "UploadTime")
	public String getUploadtime() {
		return uploadtime;
	}

	public void setUploadtime(String uploadtime) {
		this.uploadtime = uploadtime;

	}

	@Column(name = "ShootingTime")
	public String getShootingtime() {
		return shootingtime;
	}

	public void setShootingtime(String shootingtime) {
		this.shootingtime = shootingtime;

	}

	@Column(name = "PublicTime")
	public Date getPublictime() {
		return publictime;
	}

	public void setPublictime(Date publictime) {
		this.publictime = publictime;

	}

	public Tb_filetype getFiletypeid() {
		return filetypeid;
	}

	public void setFiletypeid(Tb_filetype filetypeid) {
		this.filetypeid = filetypeid;
	}

	public Tb_member getMemberid() {
		return memberid;
	}

	public void setMemberid(Tb_member memberid) {
		this.memberid = memberid;
	}

	public Tb_photoalbum getPhotoalbumid() {
		return photoalbumid;
	}

	public void setPhotoalbumid(Tb_photoalbum photoalbumid) {
		this.photoalbumid = photoalbumid;
	}

	@Column(name = "DeviceNumber")
	public String getDevicenumber() {
		return devicenumber;
	}

	public void setDevicenumber(String devicenumber) {
		this.devicenumber = devicenumber;

	}

	@Column(name = "iwidht")
	public int getIwidht() {
		return iwidht;
	}

	public void setIwidht(int iwidht) {
		this.iwidht = iwidht;

	}

	@Column(name = "iheight")
	public int getIheight() {
		return iheight;
	}

	public void setIheight(int iheight) {
		this.iheight = iheight;

	}

	@Column(name = "ExposureTime")
	public String getExposuretime() {
		return exposuretime;
	}

	public void setExposuretime(String exposuretime) {
		this.exposuretime = exposuretime;

	}

	@Column(name = "ExposureProgram")
	public String getExposureprogram() {
		return exposureprogram;
	}

	public void setExposureprogram(String exposureprogram) {
		this.exposureprogram = exposureprogram;

	}

	@Column(name = "ExposureCompensation")
	public String getExposurecompensation() {
		return exposurecompensation;
	}

	public void setExposurecompensation(String exposurecompensation) {
		this.exposurecompensation = exposurecompensation;

	}

	@Column(name = "ExposureModel")
	public String getExposuremodel() {
		return exposuremodel;
	}

	public void setExposuremodel(String exposuremodel) {
		this.exposuremodel = exposuremodel;

	}

	@Column(name = "ISOrate")
	public String getIsorate() {
		return isorate;
	}

	public void setIsorate(String isorate) {
		this.isorate = isorate;

	}

	@Column(name = "ShutterSpeed")
	public String getShutterspeed() {
		return shutterspeed;
	}

	public void setShutterspeed(String shutterspeed) {
		this.shutterspeed = shutterspeed;

	}

	@Column(name = "Aperture")
	public String getAperture() {
		return aperture;
	}

	public void setAperture(String aperture) {
		this.aperture = aperture;

	}

	@Column(name = "LensAperture")
	public String getLensaperture() {
		return lensaperture;
	}

	public void setLensaperture(String lensaperture) {
		this.lensaperture = lensaperture;

	}

	@Column(name = "LargestAperture")
	public String getLargestaperture() {
		return largestaperture;
	}

	public void setLargestaperture(String largestaperture) {
		this.largestaperture = largestaperture;

	}

	@Column(name = "Flash")
	public String getFlash() {
		return flash;
	}

	public void setFlash(String flash) {
		this.flash = flash;

	}

	@Column(name = "FocalLength")
	public String getFocallength() {
		return focallength;
	}

	public void setFocallength(String focallength) {
		this.focallength = focallength;

	}

	@Column(name = "WhiteBalance")
	public String getWhitebalance() {
		return whitebalance;
	}

	public void setWhitebalance(String whitebalance) {
		this.whitebalance = whitebalance;

	}

	@Column(name = "FileLength")
	public int getFilelength() {
		return filelength;
	}

	public void setFilelength(int filelength) {
		this.filelength = filelength;

	}

	@Column(name = "GPSlat")
	public String getGpslat() {
		return gpslat;
	}

	public void setGpslat(String gpslat) {
		this.gpslat = gpslat;

	}

	@Column(name = "GPSlon")
	public String getGpslon() {
		return gpslon;
	}

	public void setGpslon(String gpslon) {
		this.gpslon = gpslon;

	}

	@Column(name = "PCThumbnailPathimg")
	public String getPcthumbnailpathimg() {
		return pcthumbnailpathimg;
	}

	public void setPcthumbnailpathimg(String pcthumbnailpathimg) {
		this.pcthumbnailpathimg = pcthumbnailpathimg;

	}

	@Column(name = "PhoneThumbnailPathimg")
	public String getPhonethumbnailpathimg() {
		return phonethumbnailpathimg;
	}

	public void setPhonethumbnailpathimg(String phonethumbnailpathimg) {
		this.phonethumbnailpathimg = phonethumbnailpathimg;

	}

	@Column(name = "DocStatus")
	public int getDocstatus() {
		return docstatus;
	}

	public void setDocstatus(int docstatus) {
		this.docstatus = docstatus;

	}

	@Column(name = "PadThumbnailPathimg")
	public String getPadthumbnailpathimg() {
		return padthumbnailpathimg;
	}

	public void setPadthumbnailpathimg(String padthumbnailpathimg) {
		this.padthumbnailpathimg = padthumbnailpathimg;

	}

	@Column(name = "FilePath")
	public String getFilepath() {
		return filepath;
	}

	public void setFilepath(String filepath) {
		this.filepath = filepath;

	}

	@Column(name = "FileScore")
	public double getFilescore() {
		return filescore;
	}

	public void setFilescore(double filescore) {
		this.filescore = filescore;

	}

	@Column(name = "NSFW")
	public String getNsfw() {
		return nsfw;
	}

	public void setNsfw(String nsfw) {
		this.nsfw = nsfw;

	}

	@Column(name = "IsPublic")
	public int getIspublic() {
		return ispublic;
	}

	public void setIspublic(int ispublic) {
		this.ispublic = ispublic;

	}

	@Column(name = "IsDelete")
	public int getIsdelete() {
		return isdelete;
	}

	public void setIsdelete(int isdelete) {
		this.isdelete = isdelete;

	}

	@Column(name = "IsParticiPating")
	public int getIsparticipating() {
		return isparticipating;
	}

	public void setIsparticipating(int isparticipating) {
		this.isparticipating = isparticipating;

	}

	@Column(name = "IsPortrait")
	public int getIsportrait() {
		return isportrait;
	}

	public void setIsportrait(int isportrait) {
		this.isportrait = isportrait;

	}

	@Column(name = "IsDouble")
	public int getIsdouble() {
		return isdouble;
	}

	public void setIsdouble(int isdouble) {
		this.isdouble = isdouble;

	}

	@Column(name = "FileViewCount")
	public int getFileviewcount() {
		return fileviewcount;
	}

	public void setFileviewcount(int fileviewcount) {
		this.fileviewcount = fileviewcount;

	}

	@Column(name = "FileGoodCount")
	public int getFilegoodcount() {
		return filegoodcount;
	}

	public void setFilegoodcount(int filegoodcount) {
		this.filegoodcount = filegoodcount;

	}

	@Column(name = "FilekeepCount")
	public int getFilekeepcount() {
		return filekeepcount;
	}

	public void setFilekeepcount(int filekeepcount) {
		this.filekeepcount = filekeepcount;

	}

	@Column(name = "FileCommentsCount")
	public int getFilecommentscount() {
		return filecommentscount;
	}

	public void setFilecommentscount(int filecommentscount) {
		this.filecommentscount = filecommentscount;

	}

	@Column(name = "FilePayCount")
	public int getFilepaycount() {
		return filepaycount;
	}

	public void setFilepaycount(int filepaycount) {
		this.filepaycount = filepaycount;

	}

	@Column(name = "Fileserverid")
	public int getFileserverid() {
		return fileserverid;
	}

	public void setFileserverid(int fileserverid) {
		this.fileserverid = fileserverid;

	}

	@Column(name = "Storageformat")
	public String getStorageformat() {
		return storageformat;
	}

	public void setStorageformat(String storageformat) {
		this.storageformat = storageformat;

	}

	@Column(name = "actityforwarcount")
	public int getActityforwarcount() {
		return actityforwarcount;
	}

	public void setActityforwarcount(int actityforwarcount) {
		this.actityforwarcount = actityforwarcount;

	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Column(name = "memberiddz")
	public String getMemberiddz() {
		return memberiddz;
	}

	public void setMemberiddz(String memberiddz) {
		this.memberiddz = memberiddz;
	}

	@Column(name = "memberidsc")
	public String getMemberidsc() {
		return memberidsc;
	}

	public void setMemberidsc(String memberidsc) {
		this.memberidsc = memberidsc;
	}
	@Column(name = "todaytime")
	public Date getTodaytime() {
		return todaytime;
	}

	public void setTodaytime(Date todaytime) {
		this.todaytime = todaytime;
	}

	public void getMap() {
		ModelUtil.total.put("id", "编号");
		ModelUtil.total.put("fid", "组图父ID");
		ModelUtil.total.put("doctitle", "文档标题");
		ModelUtil.total.put("filedescribe", "文件描述");
		ModelUtil.total.put("filetype", "文件类型(0图片,1视频)");
		ModelUtil.total.put("uploadtime", "上传时间");
		ModelUtil.total.put("shootingtime", "拍摄时间");
		ModelUtil.total.put("publictime", "公开时间");
		ModelUtil.total.put("filetypeid", "文件分类ID");
		ModelUtil.total.put("memberid", "会员ID");
		ModelUtil.total.put("photoalbumid", "相册ID");
		ModelUtil.total.put("devicenumber", "设备型号");
		ModelUtil.total.put("iwidht", "图像宽度");
		ModelUtil.total.put("iheight", "图像高度");
		ModelUtil.total.put("exposuretime", "曝光时间");
		ModelUtil.total.put("exposureprogram", "曝光程序");
		ModelUtil.total.put("exposurecompensation", "曝光补偿");
		ModelUtil.total.put("exposuremodel", "曝光模式");
		ModelUtil.total.put("isorate", "ISO速率");
		ModelUtil.total.put("shutterspeed", "快门速度");
		ModelUtil.total.put("aperture", "光圈(p)值");
		ModelUtil.total.put("lensaperture", "镜头光圈");
		ModelUtil.total.put("largestaperture", "最大光圈");
		ModelUtil.total.put("flash", "闪光灯");
		ModelUtil.total.put("focallength", "焦距");
		ModelUtil.total.put("whitebalance", "白平衡");
		ModelUtil.total.put("filelength", "文件大小");
		ModelUtil.total.put("gpslat", "纬度");
		ModelUtil.total.put("gpslon", "经度");
		ModelUtil.total.put("pcthumbnailpathimg", "PC缩略图");
		ModelUtil.total.put("phonethumbnailpathimg", "手机缩略图");
		ModelUtil.total.put("docstatus", "文档审核状态 0未审核 1已审核");
		ModelUtil.total.put("padthumbnailpathimg", "平板缩略图");
		ModelUtil.total.put("filepath", "源文件路径");
		ModelUtil.total.put("filescore", "文件分数");
		ModelUtil.total.put("nsfw", "非法图片分数");
		ModelUtil.total.put("ispublic", "是否公开");
		ModelUtil.total.put("isdelete", "回收站");
		ModelUtil.total.put("isparticipating", "是否参赛");
		ModelUtil.total.put("isportrait", "是否人像");
		ModelUtil.total.put("isdouble", "是否组图 0单图 1组图");
		ModelUtil.total.put("fileviewcount", "浏览数量");
		ModelUtil.total.put("filegoodcount", "点赞数量");
		ModelUtil.total.put("filekeepcount", "收藏数量");
		ModelUtil.total.put("filecommentscount", "评论数量");
		ModelUtil.total.put("filepaycount", "打赏数量");
		ModelUtil.total.put("fileserverid", "null");
		ModelUtil.total.put("storageformat", "存储格式");
		ModelUtil.total.put("actityforwarcount", "分享次数");
		ModelUtil.total.put("memberiddz", "是否点赞");
		ModelUtil.total.put("memberidsc", "是否收藏");
		ModelUtil.total.put("todaytime", "点赞时间");
		 
	}

}
