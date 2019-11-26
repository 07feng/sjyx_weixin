package com.sunnet.org.information.model;

import com.sunnet.code.util.ModelUtil;
import com.sunnet.framework.model.BaseModel;
import com.sunnet.org.member.model.Tb_member;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;
import org.hibernate.annotations.Cache;
import javax.persistence.*;
import java.util.Date;

/**
 * 文件表
 * @author 强强
 *
 * 时间: 2017年00月00日
 */
@Entity
@Table(name = "tb_doc")
public class Tb_doc extends BaseModel
{
	private static final long serialVersionUID = 1L;

	@Id
	@Column(length = 36, name = "Id")
	//@GeneratedValue(generator = "system-uuid")
	//@GenericGenerator(name = "system-uuid", strategy = "com.sunnet.framework.util.IDGenerator")
	private String id;
		 private String fid; //组图父ID   

	 private String doctitle; //文档标题   

	 private String filedescribe; //文件描述   

	 private Integer filetype; //文件类型(0图片,1视频)   

	 private Date uploadtime; //上传时间   

	 private String shootingtime; //拍摄时间   

	 private Date publictime; //公开时间   

    @ManyToOne
	@NotFound(action= NotFoundAction.IGNORE)
	@JoinColumn(name = "Filetypeid")
	private Tb_filetype filetypeid; // 文件分类ID

	@ManyToOne
	@NotFound(action= NotFoundAction.IGNORE)
	@JoinColumn(name = "memberid")
	private Tb_member memberid; // 会员ID

	@ManyToOne
	@NotFound(action= NotFoundAction.IGNORE)
	@JoinColumn(name = "Photoalbumid")
	private Tb_photoalbum photoalbumid; // 相册ID 

	 private String devicenumber; //设备型号   

	 private Integer iwidht; //图像宽度   

	 private Integer iheight; //图像高度   

	 private String exposuretime; //曝光时间   

	 private String exposureprogram; //曝光程序   

	 private String exposurecompensation; //曝光补偿   

	 private String exposuremodel; //曝光模式   

	 private String isorate; //ISO速率   

	 private String shutterspeed; //快门速度   

	 private String aperture; //光圈(p)值   

	 private String lensaperture; //镜头光圈   

	 private String largestaperture; //最大光圈   

	 private String flash; //闪光灯   

	 private String focallength; //焦距

	 private String whitebalance; //白平衡   

	 private Integer filelength; //文件大小   

	 private String gpslat; //纬度   

	 private String gpslon; //经度   

	 private String pcthumbnailpathimg; //PC缩略图   

	 private String phonethumbnailpathimg; //手机缩略图   

	 private Integer docstatus; //文档审核状态 0未审核 1已审核   

	 private String padthumbnailpathimg; //平板缩略图   

	 private String filepath; //源文件路径   

	 private String filescore; //文件分数   

	 private String nsfw; //非法图片分数   

	 private Integer ispublic; //是否公开   

	 private Integer isdelete; //回收站   

	 private Integer isparticipating; //是否参赛   

	 private Integer isportrait; //是否人像   

	 private Integer isdouble; //是否组图 0单图 1组图   

	 private Integer fileviewcount; //浏览数量   

	 private Integer filegoodcount; //点赞数量   

	 private Integer filekeepcount; //收藏数量   

	 private Integer filecommentscount; //评论数量   

	 private Integer filepaycount; //打赏数量   

	 private Integer fileserverid; //null   

	 private String storageformat; //null   

	 private Integer actityforwarcount; //null   

	 private long createdate; //null   

	 private Integer isboutique; //是否是精品

	private Integer preisboutique;	//精品预设置

	private Byte devicetype;	 //设备类型

	@Column(name = "preisboutique")
	public Integer getPreisboutique() {
		return preisboutique;
	}

	public void setPreisboutique(Integer preisboutique) {
		this.preisboutique = preisboutique;
	}


	@Column(name = "FID")
	 public String getFid() 
	 {                       
	 return fid;        	   
	 }                       

	 public void setFid(String fid) 
	 {                       
	 this.fid = fid;       

	 }				 

	 @Column(name = "DocTitle")  
	 public String getDoctitle() 
	 {                       
	 return doctitle;        	   
	 }                       

	 public void setDoctitle(String doctitle) 
	 {                       
	 this.doctitle = doctitle;       

	 }				 

	 @Column(name = "FileDescribe")  
	 public String getFiledescribe() 
	 {                       
	 return filedescribe;        	   
	 }                       

	 public void setFiledescribe(String filedescribe) 
	 {                       
	 this.filedescribe = filedescribe;       

	 }				 

	 @Column(name = "FileType")  
	 public Integer getFiletype() 
	 {                       
	 return filetype;        	   
	 }                       

	 public void setFiletype(Integer filetype) 
	 {                       
	 this.filetype = filetype;       

	 }				 

	 @Column(name = "UploadTime")  
	 public Date getUploadtime() 
	 {                       
	 return uploadtime;        	   
	 }                       

	 public void setUploadtime(Date uploadtime) 
	 {                       
	 this.uploadtime = uploadtime;       

	 }				 

	 @Column(name = "ShootingTime")  
	 public String getShootingtime() 
	 {                       
	 return shootingtime;        	   
	 }                       

	 public void setShootingtime(String shootingtime) 
	 {                       
	 this.shootingtime = shootingtime;       

	 }				 

	 @Column(name = "PublicTime")  
	 public Date getPublictime() 
	 {                       
	 return publictime;        	   
	 }                       

	 public void setPublictime(Date publictime) 
	 {                       
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
	 public String getDevicenumber() 
	 {                       
	 return devicenumber;        	   
	 }                       

	 public void setDevicenumber(String devicenumber) 
	 {                       
	 this.devicenumber = devicenumber;       

	 }				 

	 @Column(name = "iwidht")  
	 public Integer getIwidht() 
	 {                       
	 return iwidht;        	   
	 }                       

	 public void setIwidht(Integer iwidht) 
	 {                       
	 this.iwidht = iwidht;       

	 }				 

	 @Column(name = "iheight")  
	 public Integer getIheight() 
	 {                       
	 return iheight;        	   
	 }                       

	 public void setIheight(Integer iheight) 
	 {                       
	 this.iheight = iheight;       

	 }				 

	 @Column(name = "ExposureTime")  
	 public String getExposuretime() 
	 {                       
	 return exposuretime;        	   
	 }                       

	 public void setExposuretime(String exposuretime) 
	 {                       
	 this.exposuretime = exposuretime;       

	 }				 

	 @Column(name = "ExposureProgram")  
	 public String getExposureprogram() 
	 {                       
	 return exposureprogram;        	   
	 }                       

	 public void setExposureprogram(String exposureprogram) 
	 {                       
	 this.exposureprogram = exposureprogram;       

	 }				 

	 @Column(name = "ExposureCompensation")  
	 public String getExposurecompensation() 
	 {                       
	 return exposurecompensation;        	   
	 }                       

	 public void setExposurecompensation(String exposurecompensation) 
	 {                       
	 this.exposurecompensation = exposurecompensation;       

	 }				 

	 @Column(name = "ExposureModel")  
	 public String getExposuremodel() 
	 {                       
	 return exposuremodel;        	   
	 }                       

	 public void setExposuremodel(String exposuremodel) 
	 {                       
	 this.exposuremodel = exposuremodel;       

	 }				 

	 @Column(name = "ISOrate")  
	 public String getIsorate() 
	 {                       
	 return isorate;        	   
	 }                       

	 public void setIsorate(String isorate) 
	 {                       
	 this.isorate = isorate;       

	 }				 

	 @Column(name = "ShutterSpeed")  
	 public String getShutterspeed() 
	 {                       
	 return shutterspeed;        	   
	 }                       

	 public void setShutterspeed(String shutterspeed) 
	 {                       
	 this.shutterspeed = shutterspeed;       

	 }				 

	 @Column(name = "Aperture")  
	 public String getAperture() 
	 {                       
	 return aperture;        	   
	 }                       

	 public void setAperture(String aperture) 
	 {                       
	 this.aperture = aperture;       

	 }				 

	 @Column(name = "LensAperture")  
	 public String getLensaperture() 
	 {                       
	 return lensaperture;        	   
	 }                       

	 public void setLensaperture(String lensaperture) 
	 {                       
	 this.lensaperture = lensaperture;       

	 }				 

	 @Column(name = "LargestAperture")  
	 public String getLargestaperture() 
	 {                       
	 return largestaperture;        	   
	 }                       

	 public void setLargestaperture(String largestaperture) 
	 {                       
	 this.largestaperture = largestaperture;       

	 }				 

	 @Column(name = "Flash")  
	 public String getFlash() 
	 {                       
	 return flash;        	   
	 }                       

	 public void setFlash(String flash) 
	 {                       
	 this.flash = flash;       

	 }				 

	 @Column(name = "FocalLength")  
	 public String getFocallength() 
	 {                       
	 return focallength;        	   
	 }                       

	 public void setFocallength(String focallength) 
	 {                       
	 this.focallength = focallength;       

	 }				 

	 @Column(name = "WhiteBalance")  
	 public String getWhitebalance() 
	 {                       
	 return whitebalance;        	   
	 }                       

	 public void setWhitebalance(String whitebalance) 
	 {                       
	 this.whitebalance = whitebalance;       

	 }				 

	 @Column(name = "FileLength")  
	 public Integer getFilelength() 
	 {                       
	 return filelength;        	   
	 }                       

	 public void setFilelength(Integer filelength) 
	 {                       
	 this.filelength = filelength;       

	 }				 

	 @Column(name = "GPSlat")  
	 public String getGpslat() 
	 {                       
	 return gpslat;        	   
	 }                       

	 public void setGpslat(String gpslat) 
	 {                       
	 this.gpslat = gpslat;       

	 }				 

	 @Column(name = "GPSlon")  
	 public String getGpslon() 
	 {                       
	 return gpslon;        	   
	 }                       

	 public void setGpslon(String gpslon) 
	 {                       
	 this.gpslon = gpslon;       

	 }				 

	 @Column(name = "PCThumbnailPathimg")  
	 public String getPcthumbnailpathimg() 
	 {                       
	 return pcthumbnailpathimg;        	   
	 }                       

	 public void setPcthumbnailpathimg(String pcthumbnailpathimg) 
	 {                       
	 this.pcthumbnailpathimg = pcthumbnailpathimg;       

	 }				 

	 @Column(name = "PhoneThumbnailPathimg")  
	 public String getPhonethumbnailpathimg() 
	 {                       
	 return phonethumbnailpathimg;        	   
	 }                       

	 public void setPhonethumbnailpathimg(String phonethumbnailpathimg) 
	 {                       
	 this.phonethumbnailpathimg = phonethumbnailpathimg;       

	 }				 

	 @Column(name = "DocStatus")  
	 public Integer getDocstatus() 
	 {                       
	 return docstatus;        	   
	 }                       

	 public void setDocstatus(Integer docstatus) 
	 {                       
	 this.docstatus = docstatus;       

	 }				 

	 @Column(name = "PadThumbnailPathimg")  
	 public String getPadthumbnailpathimg() 
	 {                       
	 return padthumbnailpathimg;        	   
	 }                       

	 public void setPadthumbnailpathimg(String padthumbnailpathimg) 
	 {                       
	 this.padthumbnailpathimg = padthumbnailpathimg;       

	 }				 

	 @Column(name = "FilePath")  
	 public String getFilepath() 
	 {                       
	 return filepath;        	   
	 }                       

	 public void setFilepath(String filepath) 
	 {                       
	 this.filepath = filepath;       

	 }				 

	 @Column(name = "FileScore")  
	 public String getFilescore() 
	 {                       
	 return filescore;        	   
	 }                       

	 public void setFilescore(String filescore) 
	 {                       
	 this.filescore = filescore;       

	 }				 

	 @Column(name = "NSFW")  
	 public String getNsfw() 
	 {                       
	 return nsfw;        	   
	 }                       

	 public void setNsfw(String nsfw) 
	 {                       
	 this.nsfw = nsfw;       

	 }				 

	 @Column(name = "IsPublic")  
	 public Integer getIspublic() 
	 {                       
	 return ispublic;        	   
	 }                       

	 public void setIspublic(Integer ispublic) 
	 {                       
	 this.ispublic = ispublic;       

	 }				 

	 @Column(name = "IsDelete")  
	 public Integer getIsdelete() 
	 {                       
	 return isdelete;        	   
	 }                       

	 public void setIsdelete(Integer isdelete) 
	 {                       
	 this.isdelete = isdelete;       

	 }				 

	 @Column(name = "IsParticiPating")  
	 public Integer getIsparticipating() 
	 {                       
	 return isparticipating;        	   
	 }                       

	 public void setIsparticipating(Integer isparticipating) 
	 {                       
	 this.isparticipating = isparticipating;       

	 }				 

	 @Column(name = "IsPortrait")  
	 public Integer getIsportrait() 
	 {                       
	 return isportrait;        	   
	 }                       

	 public void setIsportrait(Integer isportrait) 
	 {                       
	 this.isportrait = isportrait;       

	 }				 

	 @Column(name = "IsDouble")  
	 public Integer getIsdouble() 
	 {                       
	 return isdouble;        	   
	 }                       

	 public void setIsdouble(Integer isdouble) 
	 {                       
	 this.isdouble = isdouble;       

	 }				 

	 @Column(name = "FileViewCount")  
	 public Integer getFileviewcount() 
	 {                       
	 return fileviewcount;        	   
	 }                       

	 public void setFileviewcount(Integer fileviewcount) 
	 {                       
	 this.fileviewcount = fileviewcount;       

	 }				 

	 @Column(name = "FileGoodCount")  
	 public Integer getFilegoodcount() 
	 {                       
	 return filegoodcount;        	   
	 }                       

	 public void setFilegoodcount(Integer filegoodcount) 
	 {                       
	 this.filegoodcount = filegoodcount;       

	 }				 

	 @Column(name = "FilekeepCount")  
	 public Integer getFilekeepcount() 
	 {                       
	 return filekeepcount;        	   
	 }                       

	 public void setFilekeepcount(Integer filekeepcount) 
	 {                       
	 this.filekeepcount = filekeepcount;       

	 }				 

	 @Column(name = "FileCommentsCount")  
	 public Integer getFilecommentscount() 
	 {                       
	 return filecommentscount;        	   
	 }                       

	 public void setFilecommentscount(Integer filecommentscount) 
	 {                       
	 this.filecommentscount = filecommentscount;       

	 }				 

	 @Column(name = "FilePayCount")  
	 public Integer getFilepaycount() 
	 {                       
	 return filepaycount;        	   
	 }                       

	 public void setFilepaycount(Integer filepaycount) 
	 {                       
	 this.filepaycount = filepaycount;       

	 }				 

	 @Column(name = "Fileserverid")  
	 public Integer getFileserverid() 
	 {                       
	 return fileserverid;        	   
	 }                       

	 public void setFileserverid(Integer fileserverid) 
	 {                       
	 this.fileserverid = fileserverid;       

	 }				 

	 @Column(name = "Storageformat")  
	 public String getStorageformat() 
	 {                       
	 return storageformat;        	   
	 }                       

	 public void setStorageformat(String storageformat) 
	 {                       
	 this.storageformat = storageformat;       

	 }				 

	 @Column(name = "actityforwarcount")  
	 public Integer getActityforwarcount() 
	 {                       
	 return actityforwarcount;        	   
	 }                       

	 public void setActityforwarcount(Integer actityforwarcount) 
	 {                       
	 this.actityforwarcount = actityforwarcount;       

	 }				 

	 @Column(name = "createdate")  
	 public long getCreatedate() 
	 {                       
	 return createdate;        	   
	 }                       

	 public void setCreatedate(long createdate) 
	 {                       
	 this.createdate = createdate;       

	 }				 

	 @Column(name = "isboutique")  
	 public Integer getIsboutique() 
	 {                       
	 return isboutique;        	   
	 }                       

	 public void setIsboutique(Integer isboutique) 
	 {                       
	 this.isboutique = isboutique;       

	 }
	 @Column(name = "DeviceType")
	 public Byte getDevicetype() { return devicetype; }

	 public void setDevicetype(Byte devicetype) {
		this.devicetype = devicetype;
	 }

	 public String getId() {
		return id;
	}
	 public void setId(String id) {
		this.id = id;
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
	 	ModelUtil.total.put("storageformat", "null"); 
	 	ModelUtil.total.put("actityforwarcount", "null"); 
	 	ModelUtil.total.put("createdate", "null"); 
	 	ModelUtil.total.put("isboutique", "是否是精品");
		ModelUtil.total.put("preISbOutIQue", "精品预设置");
		ModelUtil.total.put("deviceType","设备类型");
	 }

	



}
