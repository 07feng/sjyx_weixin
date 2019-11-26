package com.sunnet.org.information.service.impl;

import com.sunnet.framework.pagenation.PageBean;
import com.sunnet.framework.pagenation.QueryResult;
import com.sunnet.framework.service.impl.BaseServiceImpl;
import com.sunnet.org.information.dao.ITb_photoalbumDao;
import com.sunnet.org.information.model.Tb_photoalbum;
import com.sunnet.org.information.service.ITb_photoalbumService;
import com.sunnet.org.information.vo.Tb_photoalbumUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 
 * <br>
 * <b>功能：</b>Tb_photoalbum Service<br>
 * <b>作者：</b>jing<br>
 * <b>日期：</b> 2016/12/1 <br>
 */
@Service
public class Tb_photoalbumServiceImpl extends BaseServiceImpl<Tb_photoalbum>  implements ITb_photoalbumService
{
	
	@Autowired
	private ITb_photoalbumDao tb_photoalbumDao;

	@Override
	public QueryResult<Tb_photoalbum> list(PageBean pageBean, HttpServletRequest request) {
		StringBuffer str = new StringBuffer();
		str.append(" from Tb_photoalbum o where 1=1 "); //初始化语句
		if(null != request.getParameter("menberId")){
			str.append("and memberid = '"+request.getParameter("menberId")+"'");
		}
		pageBean.setHql(str.toString());
		int totalRecord = tb_photoalbumDao.findByHQLCount(Tb_photoalbum.class, pageBean);
		List<Tb_photoalbum> list = tb_photoalbumDao.findByHQLPage(Tb_photoalbum.class, pageBean);
		QueryResult<Tb_photoalbum> result = new QueryResult<Tb_photoalbum>();
		result.setResultList(Tb_photoalbumUtil.getControllerList(list));
		pageBean.setTotalRecord(totalRecord);
		pageBean.setHql("");
		pageBean.setSql("");
		result.setPageBean(pageBean);
		return result;
	}

	@Override
	public void update(Tb_photoalbum tb_photoalbum) {
//		Tb_photoalbum tb_photoalbum2 = tb_photoalbumDao.findByPrimaryKey(Tb_photoalbum.class,
//				tb_photoalbum.getId());
//		try {
//			MyBeanUtils.copyBeanNotNull2Bean(tb_photoalbum, tb_photoalbum2);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
		tb_photoalbumDao.update(tb_photoalbum);
	}
	
	public void delete(Object[] entityids) {
		StringBuilder hql = new StringBuilder("");
		for (int i = 0; i < entityids.length; i++) {
			if (i == 0) {
				hql.append(" id=").append(entityids[i]).append("");
			} else {
				hql.append(" or id=").append(entityids[i]).append("");
			}
		}
		tb_photoalbumDao.delete(Tb_photoalbum.class, hql.toString());
	}


	@Override
	public List<Object[]> memAlbum(String memberId){
		String sql = "select * from Tb_photoalbum where memberid = ?";
		return  tb_photoalbumDao.findBySql(sql,memberId);
	}

}
