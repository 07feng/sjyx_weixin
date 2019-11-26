package com.sunnet.org.filmfestival.model;

import com.sunnet.code.util.ModelUtil;
import com.sunnet.framework.model.BaseModel;
import com.sunnet.org.information.model.Tb_doc;
import com.sunnet.org.member.model.Tb_member;

import javax.persistence.*;
import java.util.Date;

/**
 * 点赞表
 * 
 * @author
 *
 *         时间: 2017年00月00日
 */
@Entity
@Table(name = "TB_music")
public class Tb_music extends BaseModel {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(length = 32)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	private Integer typeid;//类别idTb_music

	private String typename;//类别名称

	private String musicname;

	private String musicpath;	//音乐路径

	@Column(name = "typeid")
	public Integer getTypeid() {
		return typeid;
	}

	public void setTypeid(Integer typeid) {
		this.typeid = typeid;
	}

	@Column(name = "typename")
	public String getTypename() {
		return typename;
	}

	public void setTypename(String typename) {
		this.typename = typename;
	}

	@Column(name = "musicname")
	public String getMusicname() {
		return musicname;
	}

	public void setMusicname(String musicname) {
		this.musicname = musicname;
	}

	@Column(name = "musicpath")
	public String getMusicpath() {
		return musicpath;
	}

	public void setMusicpath(String musicpath) {
		this.musicpath = musicpath;
	}


	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public void getMap() {
		ModelUtil.total.put("id", "编号");
		ModelUtil.total.put("typeid", "类别id");
		ModelUtil.total.put("typename", "类别名称");
		ModelUtil.total.put("musicname", "音乐名称");
		ModelUtil.total.put("musicpath", "音乐路径");
	}

}
