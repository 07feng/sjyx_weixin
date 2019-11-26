package com.sunnet.org.information.dao.impl;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import com.sunnet.framework.dao.impl.BaseDaoImpl;
import com.sunnet.org.information.dao.ITre_menberdoccommentDao;
import com.sunnet.org.information.model.Tre_menberdoccomment;

import java.util.List;

/**
 * 
 * <br>
 * <b>功能：</b>Tre_menberdoccomment接口<br>
 * <b>作者：</b>jing<br>
 * <b>日期：</b> 2016/12/1 <br>
 */
@Repository
public class Tre_menberdoccommentDaoImpl extends
		BaseDaoImpl<Tre_menberdoccomment> implements ITre_menberdoccommentDao {
	/**
	 * 批量显示
	 */
	@Override
	public void updateStatus(Class<Tre_menberdoccomment> entityClass, String hql) {
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder
				.append(" update Tre_menberdoccomment set is_public=1,edit_time=GETDATE() where 1=1 and ");
		stringBuilder.append(hql);
		Session session = getSession();
		Query query = session.createQuery(stringBuilder.toString());
		query.executeUpdate();
	}

	/**
	 * 批量不显示
	 */
	@Override
	public void updateStatus2(Class<Tre_menberdoccomment> entityClass,
			String hql) {
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder
				.append(" update Tre_menberdoccomment set is_public=0,edit_time=GETDATE() where 1=1 and ");
		stringBuilder.append(hql);
		Session session = getSession();
		Query query = session.createQuery(stringBuilder.toString());
		query.executeUpdate();
	}

	@Override
	public Integer getmaxId() {
		String sql = "SELECT MAX(TRE_MenberDocComment.Id) FROM [dbo].[TRE_MenberDocComment]";
		return (Integer)(getSession().createSQLQuery(sql).uniqueResult());
	}

	@Override
	public void saveComment(String sql, List condition) {
		try {
			Session session = getSession();
			Query query = session.createSQLQuery(sql);
			if (condition != null)
				for (int i = 0; i < condition.size(); i++)
					query.setParameter(i,condition.get(i));
		query.uniqueResult();
		} catch (RuntimeException re) {
			throw re;
		}
	}

}
