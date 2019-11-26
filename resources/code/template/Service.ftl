package ${com}.${keyType}.service;

import ${framework}.pagenation.QueryResult;
import javax.servlet.http.HttpServletRequest;
import ${framework}.pagenation.PageBean;
import ${framework}.service.IBaseService;
import ${com}.${keyType}.model.${bigName};

/**
 * 
 * <br>
 * <b>功能：</b>${bigName} Service<br>
 * <b>作者：</b>${auto}<br>
 * <b>日期：</b> 2016/12/1 <br>
 */
public interface I${bigName}Service extends IBaseService<${bigName}>
{
	/**
	 * 返回list
	 * @param pageBean
	 * @return
	 */
	public QueryResult<${bigName}> list(PageBean pageBean,HttpServletRequest request);
	
	/**
	 * 更新
	 * @param ${bigName}
	 */
	public void update(${bigName} ${minName});
	
	public void delete(Object[] entityids);
}
