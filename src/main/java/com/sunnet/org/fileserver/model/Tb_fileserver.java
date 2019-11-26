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
		 @Column(name = "FId")  
	 @JoinColumn(name="id")
	 private Set<Tb_doc> doc = new HashSet<Tb_doc>();*/
	
		 public int getFId() 
			return fId;
		}

		public void setfId(int fId) {
			this.fId = fId;
		}

		

	public void setFId(int fId) 
	
	
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