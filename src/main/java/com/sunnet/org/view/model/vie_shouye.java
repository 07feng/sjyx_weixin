package com.sunnet.org.view.model;

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
import com.sunnet.org.information.model.Tb_filetype;
import com.sunnet.org.information.model.Tb_photoalbum;
import com.sunnet.org.member.model.Tb_member;

@Entity
@Table(name = "vie_shouye")
public class vie_shouye {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(length = 36, name = "Id")
	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "com.sunnet.framework.util.IDGenerator")
	private String id;

	private String doctitle; // 文档标题

	private Integer filetype; // 文件类型(0图片,1视频)

	private Long createdate; // 时间戳

	private Integer filetypeid; // 文件分类ID

	private String memberid; // 会员ID
	
	private Integer ispublic; // 是否公开（0不公开，1公开）

	private Integer isdelete; // 回收站(1删除 0不删除)
	
	private Integer docstatus; // 文档审核状态 0未审核 1已审核
	
	private String usersname; // 会员ID

	private Integer iwidht; // 图像宽度

	private Integer iheight; // 图像高度

	private String phonethumbnailpathimg; // 手机缩略图

	private String filepath; // 源文件路径

	private String filescore; // 文件分数

	private Integer isparticipating; // 是否参赛（0不参赛1参）

	private Integer fileviewcount; // 浏览数量

	private Integer filegoodcount; // 点赞数量

	private Integer filekeepcount; // 收藏数量

	private Integer filecommentscount; // 评论数量

	private Integer filepaycount; // 打赏数量

	private Integer actityforwarcount; // 分享转发次数

	@Column(name = "createdate")
	public Long getCreatedate() {
		return createdate;
	}

	public void setCreatedate(Long createdate) {
		this.createdate = createdate;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Column(name = "doctitle")
	public String getDoctitle() {
		return doctitle;
	}

	public void setDoctitle(String doctitle) {
		this.doctitle = doctitle;
	}

	@Column(name = "filetype")
	public Integer getFiletype() {
		return filetype;
	}

	public void setFiletype(Integer filetype) {
		this.filetype = filetype;
	}

	@Column(name = "filetypeid")
	public Integer getFiletypeid() {
		return filetypeid;
	}

	public void setFiletypeid(Integer filetypeid) {
		this.filetypeid = filetypeid;
	}

	@Column(name = "memberid")
	public String getMemberid() {
		return memberid;
	}

	public void setMemberid(String memberid) {
		this.memberid = memberid;
	}

	@Column(name = "usersname")
	public String getUsersname() {
		return usersname;
	}

	public void setUsersname(String usersname) {
		this.usersname = usersname;
	}

	@Column(name = "iwidht")
	public Integer getIwidht() {
		return iwidht;
	}

	public void setIwidht(Integer iwidht) {
		this.iwidht = iwidht;
	}

	@Column(name = "iheight")
	public Integer getIheight() {
		return iheight;
	}

	public void setIheight(Integer iheight) {
		this.iheight = iheight;
	}

	@Column(name = "phonethumbnailpathimg")
	public String getPhonethumbnailpathimg() {
		return phonethumbnailpathimg;
	}

	public void setPhonethumbnailpathimg(String phonethumbnailpathimg) {
		this.phonethumbnailpathimg = phonethumbnailpathimg;
	}

	@Column(name = "filepath")
	public String getFilepath() {
		return filepath;
	}

	public void setFilepath(String filepath) {
		this.filepath = filepath;
	}

	@Column(name = "filescore")
	public String getFilescore() {
		return filescore;
	}

	public void setFilescore(String filescore) {
		this.filescore = filescore;
	}

	@Column(name = "isparticipating")
	public Integer getIsparticipating() {
		return isparticipating;
	}

	public void setIsparticipating(Integer isparticipating) {
		this.isparticipating = isparticipating;
	}

	@Column(name = "fileviewcount")
	public Integer getFileviewcount() {
		return fileviewcount;
	}

	public void setFileviewcount(Integer fileviewcount) {
		this.fileviewcount = fileviewcount;
	}

	@Column(name = "filegoodcount")
	public Integer getFilegoodcount() {
		return filegoodcount;
	}

	public void setFilegoodcount(Integer filegoodcount) {
		this.filegoodcount = filegoodcount;
	}

	@Column(name = "filekeepcount")
	public Integer getFilekeepcount() {
		return filekeepcount;
	}

	public void setFilekeepcount(Integer filekeepcount) {
		this.filekeepcount = filekeepcount;
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

	@Column(name = "actityforwarcount")
	public Integer getActityforwarcount() {
		return actityforwarcount;
	}

	public void setActityforwarcount(Integer actityforwarcount) {
		this.actityforwarcount = actityforwarcount;
	}
	@Column(name = "ispublic")
	public Integer getIspublic() {
		return ispublic;
	}

	public void setIspublic(Integer ispublic) {
		this.ispublic = ispublic;
	}
	@Column(name = "isdelete")
	public Integer getIsdelete() {
		return isdelete;
	}

	public void setIsdelete(Integer isdelete) {
		this.isdelete = isdelete;
	}
	@Column(name = "docstatus")
	public Integer getDocstatus() {
		return docstatus;
	}

	public void setDocstatus(Integer docstatus) {
		this.docstatus = docstatus;
	}

	public void getMap() {
		ModelUtil.total.put("id", "编号");
		ModelUtil.total.put("doctitle", "文档标题");
		ModelUtil.total.put("filetype", "文件类型(0图片,1视频)");
		ModelUtil.total.put("filetypeid", "文件分类ID");
		ModelUtil.total.put("memberid", "会员ID");
		ModelUtil.total.put("iwidht", "图像宽度");
		ModelUtil.total.put("iheight", "图像高度");
		ModelUtil.total.put("phonethumbnailpathimg", "手机缩略图");
		ModelUtil.total.put("filepath", "源文件路径");
		ModelUtil.total.put("filescore", "文件分数");
		ModelUtil.total.put("isparticipating", "是否参赛");
		ModelUtil.total.put("fileviewcount", "浏览数量");
		ModelUtil.total.put("filegoodcount", "点赞数量");
		ModelUtil.total.put("filekeepcount", "收藏数量");
		ModelUtil.total.put("filecommentscount", "评论数量");
		ModelUtil.total.put("filepaycount", "打赏数量");
		ModelUtil.total.put("actityforwarcount", "分享次数");
		ModelUtil.total.put("usersname", "会员名称");
		ModelUtil.total.put("ispublic", "是否公开");
		ModelUtil.total.put("isdelete", "回收站");
		ModelUtil.total.put("docstatus", "文档审核状态 0未审核 1已审核");
	}
}
