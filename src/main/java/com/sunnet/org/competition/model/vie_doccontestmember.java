package com.sunnet.org.competition.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.sunnet.org.member.model.Tb_member;

@Entity
@Table(name = "vie_doccontestmember")
public class vie_doccontestmember {

	private int contest_id;
	private String id;// 文件id
	private String usersname; // 昵称
	private String uploadtime; // 上传时间
	private String memberid; // 会员ID
	private int iwidht; // 图像宽度
	private int iheight; // 图像高度
	private int fileviewcount; // 浏览数量
	private int filegoodcount; // 点赞数量
	private double filescore; // 文件分数
	private String filepath; // 源文件路径
	private int isparticipating; // 是否参赛（0不参赛1参）
	private int filetype; // 文件类型(0图片,1视频)
	private String doctitle;// 文件标题
//	private String filedescribe;// 文件说明
	private String isaudit; // 参赛作品是否需要审核
	private int audit_status; // 审核状态 0未审核 1已审核 2未通过
	private Integer filecommentscount; // 评论数量
	private Integer groupid; // 会员组编号
	private Date todaytime; // 点赞时间
    private int docstatus;

    @Column(name = "docstatus")
	public int getDocstatus() {
		return docstatus;
	}

	public void setDocstatus(int docstatus) {
		this.docstatus = docstatus;
	}

	@Column(name = "filecommentscount")
	public Integer getFilecommentscount() {
		return filecommentscount;
	}

	public void setFilecommentscount(Integer filecommentscount) {
		this.filecommentscount = filecommentscount;
	}

	
 

	@Column(name = "todaytime")
	public Date getTodaytime() {
		return todaytime;
	}
	@Column(name = "groupid")
	public Integer getGroupid() {
		return groupid;
	}

	public void setGroupid(Integer groupid) {
		this.groupid = groupid;
	}

	public void setTodaytime(Date todaytime) {
		this.todaytime = todaytime;
	}

	/**
	 * FileCommentsCount,todaytime,Groupid
	 * 
	 * @return
	 */
	@Column(name = "audit_status")
	public int getAudit_status() {
		return audit_status;
	}

	public void setAudit_status(int audit_status) {
		this.audit_status = audit_status;
	}

	@Column(name = "isaudit")
	public String getIsaudit() {
		return isaudit;
	}

	public void setIsaudit(String isaudit) {
		this.isaudit = isaudit;
	}

	@Column(name = "filetype")
	public int getFiletype() {
		return filetype;
	}

	public void setFiletype(int filetype) {
		this.filetype = filetype;
	}

	@Column(name = "doctitle")
	public String getDoctitle() {
		return doctitle;
	}

	public void setDoctitle(String doctitle) {
		this.doctitle = doctitle;
	}

	@Column(name = "contest_id")
	public int getContest_id() {
		return contest_id;
	}

	public void setContest_id(int contest_id) {
		this.contest_id = contest_id;
	}

	@Id
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Column(name = "usersname")
	public String getUsersname() {
		return usersname;
	}

	public void setUsersname(String usersname) {
		this.usersname = usersname;
	}

	@Column(name = "uploadtime")
	public String getUploadtime() {
		return uploadtime;
	}

	public void setUploadtime(String uploadtime) {
		this.uploadtime = uploadtime;
	}

	@Column(name = "memberid")
	public String getMemberid() {
		return memberid;
	}

	public void setMemberid(String memberid) {
		this.memberid = memberid;
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

	@Column(name = "fileviewcount")
	public int getFileviewcount() {
		return fileviewcount;
	}

	public void setFileviewcount(int fileviewcount) {
		this.fileviewcount = fileviewcount;
	}

	@Column(name = "filegoodcount")
	public int getFilegoodcount() {
		return filegoodcount;
	}

	public void setFilegoodcount(int filegoodcount) {
		this.filegoodcount = filegoodcount;
	}

	@Column(name = "filescore")
	public double getFilescore() {
		return filescore;
	}

	public void setFilescore(double filescore) {
		this.filescore = filescore;
	}

	@Column(name = "filepath")
	public String getFilepath() {
		return filepath;
	}

	public void setFilepath(String filepath) {
		this.filepath = filepath;
	}

	@Column(name = "isparticipating")
	public int getIsparticipating() {
		return isparticipating;
	}

	public void setIsparticipating(int isparticipating) {
		this.isparticipating = isparticipating;
	}

//	@Column(name = "filedescribe")
//	public String getFiledescribe() {
//		return filedescribe;
//	}
//
//	public void setFiledescribe(String filedescribe) {
//		this.filedescribe = filedescribe;
//	}
}
