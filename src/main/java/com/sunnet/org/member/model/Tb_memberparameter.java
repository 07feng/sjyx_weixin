package  com.sunnet.org.member.model;

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
 * 会员参数表  
 * @author  
 *
 * 时间: 2017年00月00日
 */
@Entity
@Table(name = "tb_memberparameter")
public class Tb_memberparameter extends BaseModel
{
	private static final long serialVersionUID = 1L;

	@Id
	@Column(length = 32)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
		private String isopenregmember; //是否开启注册功能   

	 private String membercapacity; //会员初始容量大小   

	 private String isopenmobileauth; //是否开启手机短信验证   

	 private String isrepeatname; //是否允许会员重名   

	 private String isopenaudit; //是否开启会员审核   

	 private String noregnickname; //不允许注册昵称   

	 private String regagreement; //注册协议   

 
	 @Column(name = "IsOpenRegMember")    
	 public String getIsopenregmember() 
	 {                       
	 return isopenregmember;        	   
	 }                       

	 public void setIsopenregmember(String isopenregmember) 
	 {                       
	 this.isopenregmember = isopenregmember;       

	 }				 

	 @Column(name = "MemberCapacity")  
	 public String getMembercapacity() 
	 {                       
	 return membercapacity;        	   
	 }                       

	 public void setMembercapacity(String membercapacity) 
	 {                       
	 this.membercapacity = membercapacity;       

	 }				 

	 @Column(name = "IsOpenMobileAuth")  
	 public String getIsopenmobileauth() 
	 {                       
	 return isopenmobileauth;        	   
	 }                       

	 public void setIsopenmobileauth(String isopenmobileauth) 
	 {                       
	 this.isopenmobileauth = isopenmobileauth;       

	 }				 

	 @Column(name = "IsRepeatName")  
	 public String getIsrepeatname() 
	 {                       
	 return isrepeatname;        	   
	 }                       

	 public void setIsrepeatname(String isrepeatname) 
	 {                       
	 this.isrepeatname = isrepeatname;       

	 }				 

	 @Column(name = "IsOpenAudit")  
	 public String getIsopenaudit() 
	 {                       
	 return isopenaudit;        	   
	 }                       

	 public void setIsopenaudit(String isopenaudit) 
	 {                       
	 this.isopenaudit = isopenaudit;       

	 }				 

	 @Column(name = "NoRegNickName")  
	 public String getNoregnickname() 
	 {                       
	 return noregnickname;        	   
	 }                       

	 public void setNoregnickname(String noregnickname) 
	 {                       
	 this.noregnickname = noregnickname;       

	 }				 

	 @Column(name = "RegAgreement")  
	 public String getRegagreement() 
	 {                       
	 return regagreement;        	   
	 }                       

	 public void setRegagreement(String regagreement) 
	 {                       
	 this.regagreement = regagreement;       

	 }				 


	
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

		 public void getMap() { 
	 	ModelUtil.total.put("id", "编号"); 
	 	ModelUtil.total.put("isopenregmember", "是否开启注册功能"); 
	 	ModelUtil.total.put("membercapacity", "会员初始容量大小"); 
	 	ModelUtil.total.put("isopenmobileauth", "是否开启手机短信验证"); 
	 	ModelUtil.total.put("isrepeatname", "是否允许会员重名"); 
	 	ModelUtil.total.put("isopenaudit", "是否开启会员审核"); 
	 	ModelUtil.total.put("noregnickname", "不允许注册昵称"); 
	 	ModelUtil.total.put("regagreement", "注册协议"); 
	 } 

	
}
