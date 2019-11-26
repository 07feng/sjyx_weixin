package com.sunnet.org.information.dao;

import com.sunnet.framework.dao.IBaseDao;
import com.sunnet.org.competition.model.Tre_doccontest;
import com.sunnet.org.information.model.Tre_menberdoccomment;

import java.util.List;

/**
 * 
 * <br>
 * <b>功能：</b>Tre_menberdoccomment接口<br>
 * <b>作者：</b>jing<br>
 * <b>日期：</b> 2016/12/1 <br>
 */
public interface ITre_menberdoccommentDao extends IBaseDao<Tre_menberdoccomment>
{
	public void updateStatus(Class<Tre_menberdoccomment> entityClass, String hql);
	public void updateStatus2(Class<Tre_menberdoccomment> entityClass, String hql);
	public Integer getmaxId();

	/**
	 * author jinhao
	 * @param sql
	 * @param condition
	 * @return
	 */
	public void saveComment(String sql,List condition);
}
