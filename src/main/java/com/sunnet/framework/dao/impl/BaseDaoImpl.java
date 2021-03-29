package com.sunnet.framework.dao.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import com.sunnet.framework.dao.IBaseDao;
import com.sunnet.framework.pagenation.PageBean;
import com.sunnet.framework.pagenation.QueryResult;
import com.sunnet.org.util.StringUtils;

@Repository("baseDao")
public class BaseDaoImpl<T> implements IBaseDao<T> {

	@Resource
	private SessionFactory sessionFactory;

	protected Session getSession() {
		return this.sessionFactory.getCurrentSession();
	}
 

	/*	protected Session getSession() {
		return this.sessionFactory.getCurrentSession();
	}
*/
	@Override
	public List<T> findlistBySql(Class<T> t, String sql) {
		List<T> list=getSession().createSQLQuery(sql).addEntity(t).list(); 
		if(list.size()>0){
			return list;
		}
		return null;
	}
	/**
	 *sql分页
	 */
	public QueryResult<T> getPage(PageBean pagebean,String wherename,String TbName,String TbId,String order,Class c)
	{
		List<T> list = new ArrayList<T>();
		String sql="select * from ( SELECT ROW_NUMBER() OVER ( "+order+")AS row,* from   "+TbName+"  "+wherename+") TT WHERE TT.row between "+((pagebean.getStartPage()-1)*pagebean.getPageSize()+1)+" and "+((pagebean.getStartPage()-1)*pagebean.getPageSize()+pagebean.getPageSize());
		String sql1String=" select  "+TbId+"  from "+TbName+" "+wherename;
		List querycount=getSession().createSQLQuery(sql1String).list();
		//List lisst=getSession().createSQLQuery(sql).list();
		list=getSession().createSQLQuery(sql).addEntity(c).list();
		pagebean.setTotalRecord(querycount.size());
		pagebean.setTotalPage((int)Math.ceil(querycount.size()/(double)pagebean.getPageSize()));
		
		QueryResult<T> result = new QueryResult<T>();
		result.setResultList(list);
		result.setPageBean(pagebean);
		return  result;
		
	}
	public void save(Object entity) {
		getSession().save(entity);
	}

	@Override
	public int getMaxId(Class<T> entityClass) {
		String hql = "select max(route.id) from "+entityClass.getSimpleName()+" as route";
		Session session = getSession();
		Query query = session.createQuery(hql.toString());
		int maxid = (int)query.uniqueResult();
		return maxid;
	}

	public void saveOrUpdate(Object entity) {
		getSession().saveOrUpdate(entity);
	}

	public void update(Object entity) {
		getSession().update(entity);
	}

	public void remove(Class<T> entityClass, Object entityid) {
		getSession().delete(getSession().get(entityClass, (Serializable) entityid));
	}

	public T findByPrimaryKey(Class<T> entityClass, Serializable entityId) {
		return (T) getSession().get(entityClass, entityId);
	}

	public List<T> findAll(Class<T> entityClass) {
		List<T> list = getSession().createQuery(" from " + entityClass.getSimpleName()).list();
		return list;
	}

	 
	public List<T> findByHQLPage(Class<T> entityClass, PageBean pagenation) {
		if ((pagenation.getHql() == null) || (pagenation.getHql().equals(""))) {
			pagenation.setHql(" from " + entityClass.getSimpleName() + " o where 1=1 ");
		}
		Session session = getSession();
		Query query = session.createQuery(pagenation.getHql());
		query.setFirstResult(pagenation.getStartRow().intValue());
		query.setMaxResults(pagenation.getPageSize());
		 
		List<T> list = query.list();
		return list;
	}

	public int findByHQLCount(Class<T> entityClass, PageBean pageBean) {
		StringBuilder sb = new StringBuilder();
		sb.append(" select count(o)  ");
		if ((pageBean.getHql() != null) && (!pageBean.getHql().equals(""))) {
			sb.append(pageBean.getHql());
		} else {
			sb.append(" from " + entityClass.getSimpleName() + " o where 1=1 ");
		}
		Session session = getSession();
		Query query = session.createQuery(sb.toString());
		int count = query.list().size();
		pageBean.setTotalRecord(count);
		return count;
	}

	@Override
	public int findByHQLCountNull(Class<T> entityClass, PageBean pageBean) {
		StringBuilder sb = new StringBuilder();
//		sb.append(" select count(o)  ");
		if ((null != pageBean.getHql()) && (!pageBean.getHql().equals(""))) {
			sb.append(pageBean.getHql());
		} else {
			sb.append(" from " + entityClass.getSimpleName() + " o where 1=1 ");
		}
		Session session = getSession();
		System.out.println("SB="+sb);
		Query query = session.createQuery(sb.toString());
		int count = query.list().size();
		pageBean.setTotalRecord(count);
		return count;
	}

	public void delete(Class<T> entityClass, String hql) {
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append(" delete from " + entityClass.getSimpleName() + " o where 1=1 and ");
		stringBuilder.append(hql);
		Session session = getSession();
		Query query = session.createQuery(stringBuilder.toString());
		query.executeUpdate();
	}
	
	

	@Override
	public void update(String hql) {
		Session session = this.getSession();
		Query query = session.createQuery(hql);
		query.executeUpdate();
	}
	@Override
	public void updateSql(String sql, Object... values){
		Session session = this.getSession();
		Query query = session.createSQLQuery(sql);
		if(null != values)
			for (int i = 0; i<values.length; i++)
				query.setParameter(i,values[i]);
		query.executeUpdate();
	}

	@Override
	public int getAllNum(String sql,Object... values){
		Session session = this.getSession();
		Query query = session.createSQLQuery(sql);
		if(null != values)
			for (int i = 0; i<values.length; i++)
				query.setParameter(i,values[i]);
		return  (int)query.uniqueResult();
	}

	@Override
	public List<T> findByHQL(String hql, Object... values) {
		try {
			Session session = this.getSession();
			Query query = session.createQuery(hql);
			if (values != null)
				for (int i = 0; i < values.length; i++)
					query.setParameter(i, values[i]);
			return query.list();
		} catch (RuntimeException re) {
			throw re;
		}
	}

	@Override
	public void removeEntity(T entity) {
		if (entity != null) {
			getSession().delete(entity);
		}
	}

	@Override
	public T get(Class<T> paramClass, String propertyName, Object value) {
		String jhql = "from " + paramClass.getSimpleName() + " as model where model." + propertyName + " = :"
				+ propertyName;
		List<T> list = getSession().createQuery(jhql).setParameter(propertyName, value).list();
		if (list != null && list.size() > 0) {
			return list.get(0);
		} else {
			return null;
		}
	}

	@Override
	public List<Object[]> findBySql(String sql, Object... values) {
		try {
			Query query = getSession().createSQLQuery(sql);
			if (values != null)
				for (int i = 0; i < values.length; i++)
					query.setParameter(i, values[i]);
			List results = query.list();
			return results;
		} catch (RuntimeException re) {
			throw re;
		}
	}
	
	@Override
	public int sqlexecuteUpdate(String sql) {
		int i=0;
		try {
			i=	getSession().createSQLQuery(sql).executeUpdate();
		} catch (RuntimeException re) {
			return i;
		}
		return i;
	}

	@Override
	public List<Object[]> findBySqlPage(Class<T> entityClass, PageBean pagenation) {
		List list = null;
		try {
			if (!(StringUtils.isStringNull(pagenation.getSql()))) {
				return null;
			}
			Session session = getSession();
			Query query = session.createSQLQuery(pagenation.getSql());
			query.setFirstResult(pagenation.getStartRow().intValue()-1);
			query.setMaxResults(pagenation.getPageSize());
			list = query.list();
			return list;
		} catch (RuntimeException re) {
			throw re;
		}
	}

	@Override
	public int findBySQLCount(Class<T> clazz, PageBean pageBean) {
		if (!(StringUtils.isStringNull(pageBean.getSql()))) {
			return 0;
		}
		String[] a = pageBean.getSql().split("from");
		String table = "";
		String whre = "";
		if (a.length > 1) {
			String[] b = a[1].split("where");
			table = b[0];
			if (b.length > 1) {
				whre = b[1];
			}
		}
		StringBuffer str = new StringBuffer();
		str.append("select count(1)  from ");
		str.append(table);
		if (StringUtils.isStringNull(whre)) {
			str.append(" where " + whre);
		}
		Session session = getSession();
		Query query = session.createSQLQuery(str.toString());
		int count = Integer.valueOf(String.valueOf(query.uniqueResult())).intValue();
		pageBean.setTotalRecord(count);
		return count;
	}

	@Override
	public int findBySQLCountNull(Class<T> clazz, PageBean pageBean) {
		if (!(StringUtils.isStringNull(pageBean.getSql()))) {
			return 0;
		}
		String[] a = pageBean.getSql().split("from");
		String table = "";
		String whre = "";
		if (a.length > 1) {
			String[] b = a[1].split("where");
			table = b[0];
			if (b.length > 1) {
				whre = b[1];
			}
		}
		StringBuffer str = new StringBuffer();
		str.append("select count(DISTINCT d.id)  from ");
		str.append(table);
		if (StringUtils.isStringNull(whre)) {
			str.append(" where " + whre);
		}
		Session session = getSession();
		Query query = session.createSQLQuery(str.toString());
		int count = Integer.valueOf(String.valueOf(query.uniqueResult())).intValue();
		pageBean.setTotalRecord(count);
		return count;
	}

	@Override
	public int findBySqlCount(String jsql, Object... values) {
		try {
			List results = new ArrayList();
			Query query = getSession().createSQLQuery(jsql).addEntity("");
			if (values != null)
				for (int i = 0; i < values.length; i++)
					query.setParameter(i + 1, values[i]);
			results = query.list();
			return results.size();
		} catch (RuntimeException re) {
			throw re;
		}
	}

	@Override
	public int findByHQLCount(String hql, Object... values) {
		try {
			List results = new ArrayList();
			Session session = this.getSession();
			Query query = session.createQuery(hql);
			if (values != null)
				for (int i = 0; i < values.length; i++)
					query.setParameter(i, values[i]);
			results = query.list();
			return results.size();
		} catch (RuntimeException re) {
			throw re;
		}
	}

	@Override
	public int findByHQLCount(String hql, List list) {
		try {
			List results = new ArrayList();
			Session session = this.getSession();
			Query query = session.createQuery(hql);
			if (list.size()>0)
				for (int i = 0; i < list.size(); i++)
					query.setParameter(i, list.get(i));
			results = query.list();
			return results.size();
		} catch (RuntimeException re) {
			throw re;
		}
	}

	@Override
	public List<T> findByHQL(String hql, PageBean pageBean, Object... values) {
		try {
			Session session = this.getSession();
			Query query = session.createQuery(hql);
			if (values != null)
				for (int i = 0; i < values.length; i++)
					query.setParameter(i, values[i]);
			query.setFirstResult(pageBean.getStartRow());
			query.setMaxResults(pageBean.getPageSize());
			return query.list();
		} catch (RuntimeException re) {
			throw re;
		}
	}

	@Override
	public List<T> findByHQLCX(String hql, PageBean pageBean, Object... values) {
		try {
			Session session = this.getSession();
			Query query = session.createQuery(hql);
			if (values != null)
				for (int i = 0; i < values.length; i++)
					query.setParameter(i, values[i]);
			query.setFirstResult(Integer.valueOf(pageBean.getCurrent_Page() - 1) * pageBean.getPageSize());
			query.setMaxResults(pageBean.getPageSize());
			return query.list();
		} catch (RuntimeException re) {
			throw re;
		}
	}

	@Override
	public List<T> findByHQL(String hql, Integer starIndex, Integer endIndex,
			Object... values) {
		try {
			Session session = this.getSession();
			Query query = session.createQuery(hql);
			if (values != null)
				for (int i = 0; i < values.length; i++)
					query.setParameter(i, values[i]);
			query.setFirstResult(starIndex);
			query.setMaxResults(endIndex);
			return query.list();
		} catch (RuntimeException re) {
			throw re;
		}
	}

	@Override
	public List<T> findByHQL(String hql, List<Object> list) {
		try {
			Session session = this.getSession();
			Query query = session.createQuery(hql);
			if (list != null)
				for (int i = 0; i < list.size(); i++)
					query.setParameter(i, list.get(i));
			return query.list();
		} catch (RuntimeException re) {
			throw re;
		}
	}

	@Override
	public List<T> findByHQL(String hql, List<Object> list, PageBean pagenation) {
		return null;
	}

	@Override
	public String updateHead(long userId, String imgUrl) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int findConutBySql(String sql) {
		// TODO Auto-generated method stub
		List list=getSession().createSQLQuery(sql).list();
		if( list.size()>0 && null!=list.get(0)){
			return (int)list.get(0);
		}
		return 0;
	}

	public List findConutBySqllist(String sql) {
		// TODO Auto-generated method stub
		List<Object[]> list=getSession().createSQLQuery(sql).list();
			return list;
	 
	}
	
	 

	@Override
	public void updatePicList(Class<T> entityClass, String hql) {
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append(" update " + entityClass.getSimpleName() + " ");
		stringBuilder.append(hql);
		Session session = getSession();
		Query query = session.createQuery(stringBuilder.toString());
		query.executeUpdate();
		
	}

	@Override
	public List<T> findPageSqlBy(PageBean pagenation, String sql, List condition,Class<T> t) {
		List<T> list = null;
		try {
			Session session = getSession();
			Query query = session.createSQLQuery(sql)
					.addEntity(t);
			if (condition != null)
				for (int i = 0; i < condition.size(); i++)
					query.setParameter(i,condition.get(i));
			query.setFirstResult(pagenation.getStartRow().intValue());
			query.setMaxResults(pagenation.getPageSize());
			list = query.list();
			return list;
		} catch (RuntimeException re) {
			throw re;
		}
	}

	@Override
	public List<T> findPageHqlBy(PageBean pagenation, String hql, List condition,Class<T> t) {
		List<T> list = null;
		try {
			Session session = getSession();
			Query query = session.createQuery(hql);
			if (condition != null) {
				for (int i = 0; i < condition.size(); i++) {
					query.setParameter(i, condition.get(i));
				}
			}
			query.setFirstResult(pagenation.getStartRow().intValue());
			query.setMaxResults(pagenation.getPageSize());
			list = query.list();
			return list;
		} catch (RuntimeException re) {
			throw re;
		}
	}

	@Override
	public List<T> findByHQL(String hql, PageBean pageBean) {
		try {
			Session session = this.getSession();
			Query query = session.createQuery(hql);
			query.setFirstResult(pageBean.getStartRow());
			query.setMaxResults(pageBean.getEndRow());
			return query.list();
		} catch (RuntimeException re) {
			throw re;
		}
	}

	@Override
	public List<T> findByHQL(String hql){
		try {
			Session session = this.getSession();
			Query query = session.createQuery(hql);
			return query.list();
		} catch (RuntimeException re) {
			throw re;
		}
	}

	@Override
	public int findCount(String sql, List condition) {
		try {
			Session session = getSession();
			Query query = session.createSQLQuery(sql);
			if (condition != null)
				for (int i = 0; i < condition.size(); i++)
					query.setParameter(i,condition.get(i));
			if(query.uniqueResult() == null)
				return  0;
			else
				return Integer.valueOf(String.valueOf(query.uniqueResult())).intValue();
		} catch (RuntimeException re) {
			throw re;
		}
	}
}
