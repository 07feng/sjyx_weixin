package com.sunnet.org.system.dao.impl;

import java.util.List;

import javax.annotation.Resource;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import com.sunnet.framework.dao.impl.BaseDaoImpl;
import com.sunnet.org.system.dao.IUserDao;
import com.sunnet.org.system.model.User;

/**
 * 
 * <br>
 * <b>功能：</b>User接口<br>
 * <b>作者：</b>强强<br>
 * <b>日期：</b> 2016/12/1 <br>
 */
@Repository
public class UserDaoImpl extends BaseDaoImpl<User> implements IUserDao
{

	@Resource
	private SessionFactory sessionFactory;

	@Override
	public void update(String hql)
	{
		Session session = this.getSession();
		Query query = session.createQuery(hql);
		query.executeUpdate();
	}

	@Override
	public User findUserByLoginName(String loginName)
	{
		StringBuilder sb = new StringBuilder(" from User where 1=1 ");
		sb.append(" and fdStatus=1 ");
		sb.append(" and fdUserName='").append(loginName).append("'");
		Session session = getSession();
		Query query = session.createQuery(sb.toString());
		query.setMaxResults(1);
		User user = (User) query.uniqueResult();
		return user;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<User> findByHQL(String jhql,Object... values) {
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

	@Override
	public void updateStatus(Class<User> entityClass, String hql) {
		// TODO Auto-generated method stub
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append(" update User set fd_isdelete=1 where 1=1 and ");
		stringBuilder.append(hql);
		Session session = getSession();
		Query query = session.createQuery(stringBuilder.toString());
		query.executeUpdate();
	}


}
