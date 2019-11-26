package  com.sunnet.org.fileserver.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.sunnet.framework.model.BaseModel;
import com.sunnet.org.information.model.Tb_doc;
import com.sunnet.org.information.model.Tre_menberdoccomment;

/**
 * 存储文件服务器
 * @author  
 *
 * 时间: 2017年00月00日
 */
@Entity
@Table(name = "tb_fileserver")
public class Tb_fileserver extends BaseModel
{
	private static final long serialVersionUID = 1L;

	@Id
	@Column(length = 32)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
		 @Column(name = "FId")  	 private int fId; //null   	 @Column(name = "Server_Name")  	 private String serverName; //null   	 @Column(name = "Server_Url")  	 private String serverUrl; //null   	 @Column(name = "Server_Ip")  	 private String serverIp; //null   	 @Column(name = "Server_Status")  	 private String serverStatus; //null   	 @Column(name = "Server_capacity")  	 private String serverCapacity; //null   	 @Column(name = "Server_Remaining_Capacity")  	 private String serverRemainingCapacity; //null   	 @Column(name = "Server_Physical_Path")  	 private String serverPhysicalPath; //null   	 @Column(name = "Server_Virtual_Directory")  	 private String serverVirtualDirectory; //null   	 /*@OneToMany(cascade=CascadeType.ALL,fetch=FetchType.LAZY)
	 @JoinColumn(name="id")
	 private Set<Tb_doc> doc = new HashSet<Tb_doc>();*/
	
		 public int getFId() 	 {                       	 return fId;        	   	 }                       	 public int getfId() {
			return fId;
		}

		public void setfId(int fId) {
			this.fId = fId;
		}

		

	public void setFId(int fId) 	 {                       	 this.fId = fId;       	 }				 	 public String getServerName() 	 {                       	 return serverName;        	   	 }                       	 public void setServerName(String serverName) 	 {                       	 this.serverName = serverName;       	 }				 	 public String getServerUrl() 	 {                       	 return serverUrl;        	   	 }                       	 public void setServerUrl(String serverUrl) 	 {                       	 this.serverUrl = serverUrl;       	 }				 	 public String getServerIp() 	 {                       	 return serverIp;        	   	 }                       	 public void setServerIp(String serverIp) 	 {                       	 this.serverIp = serverIp;       	 }				 	 public String getServerStatus() 	 {                       	 return serverStatus;        	   	 }                       	 public void setServerStatus(String serverStatus) 	 {                       	 this.serverStatus = serverStatus;       	 }				 	 public String getServerCapacity() 	 {                       	 return serverCapacity;        	   	 }                       	 public void setServerCapacity(String serverCapacity) 	 {                       	 this.serverCapacity = serverCapacity;       	 }				 	 public String getServerRemainingCapacity() 	 {                       	 return serverRemainingCapacity;        	   	 }                       	 public void setServerRemainingCapacity(String serverRemainingCapacity) 	 {                       	 this.serverRemainingCapacity = serverRemainingCapacity;       	 }				 	 public String getServerPhysicalPath() 	 {                       	 return serverPhysicalPath;        	   	 }                       	 public void setServerPhysicalPath(String serverPhysicalPath) 	 {                       	 this.serverPhysicalPath = serverPhysicalPath;       	 }				 	 public String getServerVirtualDirectory() 	 {                       	 return serverVirtualDirectory;        	   	 }                       	 public void setServerVirtualDirectory(String serverVirtualDirectory) 	 {                       	 this.serverVirtualDirectory = serverVirtualDirectory;       	 }				 
	
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Override
	public String toString() {
		return "Tb_fileserver [id=" + id + ", fId=" + fId + ", serverName="
				+ serverName + ", serverUrl=" + serverUrl + ", serverIp="
				+ serverIp + ", serverStatus=" + serverStatus
				+ ", serverCapacity=" + serverCapacity
				+ ", serverRemainingCapacity=" + serverRemainingCapacity
				+ ", serverPhysicalPath=" + serverPhysicalPath
				+ ", serverVirtualDirectory=" + serverVirtualDirectory + "]";
	}

	
}
