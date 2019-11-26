package ${com}.${keyType}.service.impl;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ${framework}.pagenation.PageBean;
import ${framework}.pagenation.QueryResult;
import ${framework}.service.impl.BaseServiceImpl;
import com.sunnet.org.util.MyBeanUtils;
import com.sunnet.org.util.StringUtils;

import ${com}.${keyType}.dao.I${bigName}Dao;
import ${com}.${keyType}.model.${bigName};
import ${com}.${keyType}.service.I${bigName}Service;
import ${com}.${keyType}.vo.${bigName}Util;

/**
 * 
 * <br>
 * <b>功能：</b>${bigName} Service<br>
 * <b>作者：</b>${auto}<br>
 * <b>日期：</b> 2016/12/1 <br>
 */
@Service
public class ${bigName}ServiceImpl extends BaseServiceImpl<${bigName}>  implements I${bigName}Service
{
	
	@Autowired
	private I${bigName}Dao ${minName}Dao;

	@Override
	public QueryResult<${bigName}> list(PageBean pageBean, HttpServletRequest request) {
		StringBuffer str = new StringBuffer();
		str.append(" from ${bigName} o where 1=1 "); //初始化语句
		${toService}
		pageBean.setHql(str.toString());
		int totalRecord = ${minName}Dao.findByHQLCount(${bigName}.class, pageBean);
		List<${bigName}> list = ${minName}Dao.findByHQLPage(${bigName}.class, pageBean);
		QueryResult<${bigName}> result = new QueryResult<${bigName}>();
		result.setResultList(${bigName}Util.getControllerList(list));
		pageBean.setTotalRecord(totalRecord);
		pageBean.setHql("");
		pageBean.setSql("");
		result.setPageBean(pageBean);
		return result;
	}

	@Override
	public void update(${bigName} ${minName}) {
		${bigName} ${minName}2 = ${minName}Dao.findByPrimaryKey(${bigName}.class,
				${minName}.getId());
		try {
			MyBeanUtils.copyBeanNotNull2Bean(${minName}, ${minName}2);
		} catch (Exception e) {
			e.printStackTrace();
		}
		${minName}Dao.update(${minName}2);
	}
	
	public void delete(Object[] entityids) {
		StringBuilder hql = new StringBuilder("");
		for (int i = 0; i < entityids.length; i++) {
			if (i == 0) {
				hql.append(" id='").append(entityids[i]).append("'");
			} else {
				hql.append(" or id='").append(entityids[i]).append("'");
			}
		}
		${minName}Dao.delete(${bigName}.class, hql.toString());
	}
	
	${delVlue}

}
