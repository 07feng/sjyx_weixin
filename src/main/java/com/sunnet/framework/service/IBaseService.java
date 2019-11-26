package com.sunnet.framework.service;

import java.io.Serializable;
import java.util.List;

import com.sunnet.framework.pagenation.PageBean;
import com.sunnet.framework.pagenation.QueryResult;

public abstract interface IBaseService<T> {
	public abstract void save(Object paramObject);

	public abstract  int getMaxId(Class<T> entityClass);

	public abstract void saveOrUpdate(Object paramObject);

	public abstract void update(Object paramObject);

	public abstract void updateHql(String hql);

	public void delete(String whereHql);

	public abstract void remove(Object paramObject);

	public abstract void removeEntity(T entity);

	public abstract void delete(Object[] paramArrayOfObject);

	public abstract T findByPrimaryKey(Serializable paramSerializable);

	public abstract List<T> findAll();

	public abstract QueryResult<T> findByHQLPage(PageBean paramPageBean);

	public abstract List<T> findByHQL(String hql, Object... values);
	
	public abstract List<T> findByHQL(String hql, List<Object> list);

	public abstract List<T> findByHQL(String hql, PageBean pageBean, Object... values);
	
	public abstract List<T> findByHQL(String hql, Integer starIndex,Integer endIndex, Object... values);

	public abstract List<T> findByHQL(String hql, String wherehql, PageBean pageBean, Object... values);

	public abstract List<T> findByHQL(String hql, String wherehql, String order, PageBean pageBean, Object... values);

	public abstract int findByHQLCount(String hql, Object... values);

	public abstract List<Object[]> findBySql(final String jsql, final Object... values);

	public abstract int findBySqlCount(final String jsql, final Object... values);
	
	public abstract int sqlexecuteUpdate(final String sql);

	public abstract QueryResult findBySqlPage(PageBean pageBean);

	public abstract T get(String propertyName, Object value);
	
	public abstract int findConutBySql(String sql);
	
	public abstract List findConutBySqllist(String sql);
	
	public abstract List<T> findlistBySql(Class<T> t,String sql);
	
	public QueryResult<T> getPage(PageBean pagebean,String wherename,String TbName,String TbId,String order,Class c);

	public int findNum(String sql,String memberId);

	public void saveBySql(String sql,Object... values);

	public int getAllNum(String sql,Object... values);
}
