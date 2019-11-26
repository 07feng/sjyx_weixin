package com.sunnet.org.information.service.impl;

import com.sunnet.framework.pagenation.PageBean;
import com.sunnet.framework.pagenation.QueryResult;
import com.sunnet.framework.service.impl.BaseServiceImpl;
import com.sunnet.org.information.dao.IT_comment_fidDao;
import com.sunnet.org.information.model.T_comment_fid;
import com.sunnet.org.information.service.IT_comment_fidService;
import com.sunnet.org.information.vo.T_comment_fidUtil;
import com.sunnet.org.util.DateUtil;
import com.sunnet.org.util.MyBeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 
 * <br>
 * <b>功能：</b>T_comment_fid Service<br>
 * <b>作者：</b>jing<br>
 * <b>日期：</b> 2016/12/1 <br>
 */
@Service
public class T_comment_fidServiceImpl extends BaseServiceImpl<T_comment_fid>  implements IT_comment_fidService
{
	
	@Autowired
	private IT_comment_fidDao t_comment_fidDao;

	@Override
	public QueryResult<T_comment_fid> list(PageBean pageBean, HttpServletRequest request) {
		StringBuffer str = new StringBuffer();
		str.append(" from T_comment_fid o where 1=1 "); //初始化语句
		
		pageBean.setHql(str.toString());
		int totalRecord = t_comment_fidDao.findByHQLCount(T_comment_fid.class, pageBean);
		List<T_comment_fid> list = t_comment_fidDao.findByHQLPage(T_comment_fid.class, pageBean);
		QueryResult<T_comment_fid> result = new QueryResult<T_comment_fid>();
		result.setResultList(T_comment_fidUtil.getControllerList(list));
		pageBean.setTotalRecord(totalRecord);
		pageBean.setHql("");
		pageBean.setSql("");
		result.setPageBean(pageBean);
		return result;
	}

	@Override
	public void update(T_comment_fid t_comment_fid) {
		T_comment_fid t_comment_fid2 = t_comment_fidDao.findByPrimaryKey(T_comment_fid.class,
				t_comment_fid.getId());
		try {
			MyBeanUtils.copyBeanNotNull2Bean(t_comment_fid, t_comment_fid2);
		} catch (Exception e) {
			e.printStackTrace();
		}
		t_comment_fidDao.update(t_comment_fid2);
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
		t_comment_fidDao.delete(T_comment_fid.class, hql.toString());
	}

	@Override
	public int getMaxId() {
		return t_comment_fidDao.getMaxId();
	}

	@Override
	public List<Object[]> findByCommentId(int commentId){
		String sql = "SELECT * FROM (select m.id,ms.id AS mid,m.UsersName AS un,ms.UsersName,f.fd_comment_note,f.fd_comment_time,ROW_NUMBER() over(order by f.fd_comment_time asc)T from t_comment_fid AS f LEFT JOIN Tb_member AS m ON m.id = f.fd_member_id LEFT JOIN Tb_member AS ms ON ms.id = f.fd_c_member_id where f.fd_doccomment_id = ? )TT WHERE TT.T between 1 and 15";
		return t_comment_fidDao.findBySql(sql,commentId);
	}

	@Override
	public void sqlSave(int fd_doccomment_id,String memid,String cMemid,String fid_comment){
		String sql = "insert into t_comment_fid values(?,?,?,?,?,?)";
		int id = t_comment_fidDao.getMaxId()+1;
		t_comment_fidDao.updateSql(sql,id,fd_doccomment_id,memid,cMemid, DateUtil.getDate(),fid_comment);
	}

}
