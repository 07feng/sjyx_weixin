package com.sunnet.org.system.model;

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

import com.sunnet.code.util.ModelUtil;

import org.hibernate.annotations.GenericGenerator;

import com.sunnet.framework.model.BaseModel;
import com.sunnet.org.system.model.User;

/**
 * 水印设置
 * 
 * @author 
 *
 *         时间: 2017年00月00日
 */
@Entity
@Table(name = "tb_watermark")
public class Tb_watermark extends BaseModel {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(length = 32)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	private int watermarktype; // 水印类型 0图片 1文字

	private String watermarktxt; // 文字水印

	private String watermarkimg; // 水印图

	private int watermarkwidth; // 水印图宽度

	private int watermarkheight; // 水印图高度

	private int watermarkposition; // 水印位置 0顶部左 1顶部居中 2顶部右3中部左 4中部居中 5中部右6底部左
									// 7底部居中 8底部右

	private Date edittime; // 修改时间
	
	@ManyToOne
	@JoinColumn(name = "Edituserid")
	private User edituserid; // 修改人ID

	@Column(name = "WatermarkType")
	public int getWatermarktype() {
		return watermarktype;
	}

	public void setWatermarktype(int watermarktype) {
		this.watermarktype = watermarktype;

	}

	@Column(name = "WatermarkTxt")
	public String getWatermarktxt() {
		return watermarktxt;
	}

	public void setWatermarktxt(String watermarktxt) {
		this.watermarktxt = watermarktxt;

	}

	@Column(name = "WatermarkImg")
	public String getWatermarkimg() {
		return watermarkimg;
	}

	public void setWatermarkimg(String watermarkimg) {
		this.watermarkimg = watermarkimg;

	}

	@Column(name = "WatermarkWidth")
	public int getWatermarkwidth() {
		return watermarkwidth;
	}

	public void setWatermarkwidth(int watermarkwidth) {
		this.watermarkwidth = watermarkwidth;

	}

	@Column(name = "WatermarkHeight")
	public int getWatermarkheight() {
		return watermarkheight;
	}

	public void setWatermarkheight(int watermarkheight) {
		this.watermarkheight = watermarkheight;

	}

	@Column(name = "WatermarkPosition")
	public int getWatermarkposition() {
		return watermarkposition;
	}

	public void setWatermarkposition(int watermarkposition) {
		this.watermarkposition = watermarkposition;

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
		ModelUtil.total.put("watermarktype", "水印类型 0图片  1文字");
		ModelUtil.total.put("watermarktxt", "文字水印");
		ModelUtil.total.put("watermarkimg", "水印图");
		ModelUtil.total.put("watermarkwidth", "水印图宽度");
		ModelUtil.total.put("watermarkheight", "水印图高度");
		ModelUtil.total.put("watermarkposition", "水印位置");
		ModelUtil.total.put("edittime", "修改时间");
		ModelUtil.total.put("edituserid", "修改人ID");
	}

}
