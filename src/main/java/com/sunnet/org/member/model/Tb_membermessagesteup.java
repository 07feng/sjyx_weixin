package com.sunnet.org.member.model;

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

/**
 * 会员留言设置
 * @author  
 *
 * 时间: 2017年00月00日
 */
@Entity
@Table(name = "tb_membermessagesteup")
public class Tb_membermessagesteup extends BaseModel
{
	private static final long serialVersionUID = 1L;

	@Id
	@Column(length = 32)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
		 private String isaudit; //是否开启留言

	 private int timebetween; //留言间隔时间   

	 private String disablewords; //留言禁用词语   

	 private String replacewords; //替换词语   


	
		 @Column(name = "IsAudit")  
	 public String getIsaudit() 
	 {                       
	 return isaudit;        	   
	 }                       

	 public void setIsaudit(String isaudit) 
	 {                       
	 this.isaudit = isaudit;       

	 }				 

	 @Column(name = "TimeBetween")  
	 public int getTimebetween() 
	 {                       
	 return timebetween;        	   
	 }                       

	 public void setTimebetween(int timebetween) 
	 {                       
	 this.timebetween = timebetween;       

	 }				 

	 @Column(name = "DisableWords")  
	 public String getDisablewords() 
	 {                       
	 return disablewords;        	   
	 }                       

	 public void setDisablewords(String disablewords) 
	 {                       
	 this.disablewords = disablewords;       

	 }				 

	 @Column(name = "ReplaceWords")  
	 public String getReplacewords() 
	 {                       
	 return replacewords;        	   
	 }                       

	 public void setReplacewords(String replacewords) 
	 {                       
	 this.replacewords = replacewords;       

	 }				 


	
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

		 public void getMap() { 
	 	ModelUtil.total.put("id", "编号"); 
	 	ModelUtil.total.put("isaudit", "是否开启留言审核"); 
	 	ModelUtil.total.put("timebetween", "留言间隔时间"); 
	 	ModelUtil.total.put("disablewords", "留言禁用词语"); 
	 	ModelUtil.total.put("replacewords", "替换词语"); 
	 } 

	
}
