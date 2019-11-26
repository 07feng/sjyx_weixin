package com.sunnet.framework.service.impl;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import com.sunnet.framework.dao.IBaseDao;
import com.sunnet.framework.pagenation.PageBean;
import com.sunnet.framework.pagenation.QueryResult;
import com.sunnet.framework.service.IBaseService;
import com.sunnet.org.util.StringUtils;

@Service("baseService")
@Lazy(true)
public class BaseServiceImpl<T> implements IBaseService<T> {
	private Class<T> clazz;
	@Resource
	private IBaseDao<T> baseDao;

	public BaseServiceImpl() {
		ParameterizedType type = (ParameterizedType) getClass().getGenericSuperclass();
		this.clazz = ((Class) type.getActualTypeArguments()[0]);
	}

	public void save(Object entity) {
		this.baseDao.save(entity);
	}

	@Override
	public int getMaxId(Class<T> entityClass) {
		return this.baseDao.getMaxId(entityClass);
	}

	public void saveOrUpdate(Object entity) {
		this.baseDao.saveOrUpdate(entity);
	}

	public void update(Object entity) {
		this.baseDao.update(entity);
	}

	public void remove(Object entityid) {
		this.baseDao.remove(this.clazz, entityid);
	}

	public T findByPrimaryKey(Serializable entityId) {
		return (T) this.baseDao.findByPrimaryKey(this.clazz, entityId);
		 
	}

	public int getAllNum(String sql,Object... values){
		return  baseDao.getAllNum(sql,values);
	}

	public List<T> findAll() {
		return this.baseDao.findAll(this.clazz);
	}

	public QueryResult<T> findByHQLPage(PageBean pageBean) {
		int totalRecord = this.baseDao.findByHQLCount(this.clazz, pageBean);
		List<T> resultList = this.baseDao.findByHQLPage(this.clazz, pageBean);
		QueryResult<T> queryResult = new QueryResult();
		queryResult.setResultList(resultList);
		pageBean.setTotalRecord(totalRecord);
		pageBean.setHql("");
		pageBean.setSql("");
		queryResult.setPageBean(pageBean);
		return queryResult;
	}

	public void delete(Object[] entityids) {
		StringBuilder hql = new StringBuilder("");
		for (int i = 0; i < entityids.length; i++) {
			if (i == 0) {
				hql.append(" fdId='").append(entityids[i]).append("'");
			} else {
				hql.append(" or fdId='").append(entityids[i]).append("'");
			}
		}
		this.baseDao.delete(this.clazz, hql.toString());
	}

	public void delete(String whereHql) {
		this.baseDao.delete(this.clazz, whereHql);
	}

	@Override
	public List<T> findByHQL(String hql, Object... values) {
		return baseDao.findByHQL(hql, values);
	}

	@Override
	public void removeEntity(T entity) {
		baseDao.removeEntity(entity);
	}

	@Override
	public List<Object[]> findBySql(String jsql, Object... values) {
		return baseDao.findBySql(jsql, values);
	}

	@Override
	public T get(String propertyName, Object value) {
		return baseDao.get(this.clazz, propertyName, value);
	}

	@Override
	public QueryResult findBySqlPage(PageBean pageBean) {
		int totalRecord = this.baseDao.findBySQLCount(this.clazz, pageBean);
		List resultList = this.baseDao.findBySqlPage(this.clazz, pageBean);
		QueryResult<T> queryResult = new QueryResult();
		queryResult.setResultList(resultList);
		pageBean.setTotalRecord(totalRecord);
		pageBean.setHql("");
		pageBean.setSql("");
		queryResult.setPageBean(pageBean);
		return queryResult;
	}

	@Override
	public int findByHQLCount(String hql, Object... values) {
		// TODO Auto-generated method stub
		return baseDao.findByHQLCount(hql, values);
	}

	@Override
	public int findBySqlCount(String jsql, Object... values) {
		// TODO Auto-generated method stub
		return baseDao.findBySqlCount(jsql, values);
	}

	@Override
	public List<T> findByHQL(String hql, PageBean pageBean, Object... values) {
		return baseDao.findByHQL(hql, pageBean, values);
	}

	@Override
	public List<T> findByHQL(String hql, String wherehql, PageBean pageBean, Object... values) {
		boolean flag = true;
		if (values != null) {
			if(values[0] == null || values[0].toString().equals("")){
				flag = false;
			}
		}
		if (flag) {
			if (StringUtils.isStringNull(wherehql)) {
				hql = hql + wherehql;
			}
			return baseDao.findByHQL(hql, pageBean, values);
		} else {
			return baseDao.findByHQL(hql, pageBean);
		}
	}

	@Override
	public List<T> findByHQL(String hql, String wherehql, String order, PageBean pageBean, Object... values) {
		boolean flag = true;
		if (values != null) {
			if(values[0] == null || values[0].toString().equals("")){
				flag = false;
			}
		}
		if (flag) {
			if (StringUtils.isStringNull(wherehql)) {
				hql = hql + wherehql;
			}
			if (StringUtils.isStringNull(order)) {
				hql = hql + order;
			}
			return baseDao.findByHQL(hql, pageBean, values);
		} else {
			if (StringUtils.isStringNull(order)) {
				hql = hql + order;
			}
			return baseDao.findByHQL(hql, pageBean);
		}
	}

	@Override
	public void updateHql(String hql) {
		baseDao.update(hql);
	}

	@Override
	public List<T> findByHQL(String hql, Integer starIndex, Integer endIndex,
			Object... values) {
		return baseDao.findByHQL(hql, starIndex, endIndex, values);
	}

	@Override
	public List<T> findByHQL(String hql, List<Object> list) {
		return baseDao.findByHQL(hql, list);
	}

	@Override
	public int findConutBySql(String sql) {
		// TODO Auto-generated method stub
		return baseDao.findConutBySql(sql);
	}

	@Override
	public List findlistBySql(Class<T> t, String sql) {
		// TODO Auto-generated method stub
		return baseDao.findlistBySql(t, sql);
	}

	@Override
	public List findConutBySqllist(String sql) {
		// TODO Auto-generated method stub
		return baseDao.findConutBySqllist(sql);
	}

	@Override
	public QueryResult<T> getPage(PageBean pagebean, String wherename,
			String TbName, String TbId,String order, Class c) {
		// TODO Auto-generated method stub
		return baseDao.getPage(pagebean, wherename, TbName, TbId,order, c);
	}

	@Override
	public int sqlexecuteUpdate(String sql) {
		return baseDao.sqlexecuteUpdate(sql);
	}

	public int findNum(String sql,String memberId){
		List condition = new ArrayList();
		condition.add(memberId);
		return baseDao.findCount(sql,condition);
	}
	public void saveBySql(String sql,Object... values){
		baseDao.updateSql(sql,values);
	};
}
