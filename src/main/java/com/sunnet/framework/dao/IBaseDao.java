package com.sunnet.framework.dao;

import java.io.Serializable;
import java.util.List;

import org.apache.poi.ss.formula.functions.T;
import org.springframework.web.multipart.MultipartFile;

import com.sunnet.framework.pagenation.PageBean;
import com.sunnet.framework.pagenation.QueryResult;

public abstract interface IBaseDao<T> {
	public abstract void save(Object paramObject);

	public abstract  int getMaxId(Class<T> entityClass);

	public abstract void saveOrUpdate(Object paramObject);

	public abstract void update(Object paramObject);

	public abstract void remove(Class<T> paramClass, Object paramObject);

	public abstract void removeEntity(T entity);

	public abstract void delete(Class<T> paramClass, String paramString);

	public abstract T findByPrimaryKey(Class<T> paramClass, Serializable paramSerializable);

	public abstract List<T> findAll(Class<T> paramClass);

	public abstract List<T> findByHQLPage(Class<T> paramClass, PageBean paramPageBean);

	public abstract int findByHQLCount(Class<T> paramClass, PageBean paramPageBean);

	public abstract int findByHQLCountNull(Class<T> paramClass, PageBean paramPageBean);

	public abstract void update(String hql);

	public abstract List<T> findByHQL(String hql, Object... values);

	public abstract List<T> findByHQL(String hql, PageBean pageBean, Object... values);

	public abstract int findByHQLCount(String hql, Object... values);

	public abstract int findByHQLCount(String hql, List list);

	public abstract int findBySQLCount(Class<T> clazz, PageBean pageBean);

	public abstract List<Object[]> findBySql(final String jsql, final Object... values);
	
	public abstract int sqlexecuteUpdate(final String sql);

	public abstract int findBySqlCount(final String jsql, final Object... values);

	public abstract List<Object[]> findBySqlPage(Class<T> entityClass, PageBean pagenation);

	public abstract T get(Class<T> paramClass, String propertyName, Object value);
	
	public abstract List<T> findByHQL(String hql, Integer starIndex,Integer endIndex, Object... values);
	
	public String updateHead(long userId, String imgUrl);
	
	public abstract int findConutBySql(String sql);
	
	public abstract List findConutBySqllist(String sql);
	
	public abstract List<T> findlistBySql(Class<T> t,String sql);
	
	public QueryResult<T> getPage(PageBean pagebean,String wherename,String TbName,String TbId,String order,Class c);
	
	public abstract void updatePicList(Class<T> paramClass, String paramString);

	public abstract List<T> findByHQL(String hql, List<Object> list);

	public abstract List<T> findByHQL(String hql, List<Object> list, PageBean pagenation);

	public List<T> findPageSqlBy(PageBean pagenation,String sql ,List condition,Class<T> t);

	public List<T> findPageHqlBy(PageBean pagenation,String hql ,List condition,Class<T> t);

	public List<T> findByHQL(String hql, PageBean pageBean);

	public List<T> findByHQLCX(String hql, PageBean pageBean, Object... values);

	public int findCount(String sql,List condition);

	public List<T> findByHQL(String hql);

	public void updateSql(String sql, Object... values);

	public int getAllNum(String sql,Object... values);

	public int findBySQLCountNull(Class<T> clazz, PageBean pageBean);
}
