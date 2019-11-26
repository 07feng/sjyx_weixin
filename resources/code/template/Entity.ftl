package  ${com}.${keyType}.model;

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

import ${framework}.model.BaseModel;

/**
 * ${title}
 * @author 强强
 *
 * 时间: 2017年00月00日
 */
@Entity
@Table(name = "${minName}")
public class ${bigName} extends BaseModel
{
	private static final long serialVersionUID = 1L;

	@Id
	@Column(length = 32)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	${entity}
	
	${entitySetGet}
	
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	${mapvalue}
	
}
