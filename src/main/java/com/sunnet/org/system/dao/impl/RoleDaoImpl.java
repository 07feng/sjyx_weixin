package com.sunnet.org.system.dao.impl;

import java.util.List;

import javax.annotation.Resource;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import com.sunnet.framework.dao.impl.BaseDaoImpl;
import com.sunnet.org.system.dao.IRoleDao;
import com.sunnet.org.system.model.Role;

/**
 * 
 * <br>
 * <b>功能：</b>Role接口<br>
 * <b>作者：</b>强强<br>
 * <b>日期：</b> 2016/12/1 <br>
 */
@Repository
public class RoleDaoImpl extends BaseDaoImpl<Role> implements IRoleDao
{

	@Resource
	private SessionFactory sessionFactory;

	@Override
	public void update(String hql) {
		Session session = this.getSession();
		Query query = session.createQuery(hql);
		query.executeUpdate();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Role> findByHQL(String jhql,Object... values) {
		 try{
			Session session = this.getSession();
            Query query = session.createQuery(jhql);
            if (values != null)
                for (int i = 0;i < values.length;i++)
                    query.setParameter(i, values[i]);
            return  query.list();
        }catch (RuntimeException re) {
            throw re;
        }
	}

}
