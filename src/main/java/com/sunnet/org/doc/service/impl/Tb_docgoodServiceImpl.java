package com.sunnet.org.doc.service.impl;

import com.sunnet.framework.pagenation.PageBean;
import com.sunnet.framework.pagenation.QueryResult;
import com.sunnet.framework.service.impl.BaseServiceImpl;
import com.sunnet.org.doc.dao.ITb_docgoodDao;
import com.sunnet.org.doc.model.Tb_docgood;
import com.sunnet.org.doc.service.ITb_docgoodService;
import com.sunnet.org.doc.vo.Tb_docgoodUtil;
import com.sunnet.org.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * 
 * <br>
 * <b>功能：</b>Tb_docgood Service<br>
 * <b>作者：</b>jing<br>
 * <b>日期：</b> 2016/12/1 <br>
 */
@Service
public class Tb_docgoodServiceImpl extends BaseServiceImpl<Tb_docgood>  implements ITb_docgoodService
{
	
	
	@Autowired
	private ITb_docgoodDao tb_docgoodDao;

	@Override
	public QueryResult<Tb_docgood> list(PageBean pageBean, HttpServletRequest request) {
		StringBuffer str = new StringBuffer();
		str.append(" from Tb_docgood o where 1=1  "); //初始化语句
		if (StringUtils.isStringNull(request.getParameter("docid.id"))) {
			str.append(" and o.docid = '" + request.getParameter("docid.id") + "'");
		}
		if (StringUtils.isStringNull(request.getParameter("memberid.id"))) {
			str.append(" and o.memberid = '" + request.getParameter("memberid.id") + "'");
		}
		/*if (StringUtils.isStringNull(request.getParameter("messageinfo"))) {
			str.append(" and o.messageinfo like '%" + request.getParameter("messageinfo") + "%'");
		}*/
		pageBean.setHql(str.toString());
		int totalRecord = tb_docgoodDao.findByHQLCount(Tb_docgood.class, pageBean);
		str.append(" order by o.goodcound desc "); 
		pageBean.setHql(str.toString());
		//goodcound
		List<Tb_docgood> list = tb_docgoodDao.findByHQLPage(Tb_docgood.class, pageBean);
		QueryResult<Tb_docgood> result = new QueryResult<Tb_docgood>();
		result.setResultList(Tb_docgoodUtil.getControllerList(list));
		pageBean.setTotalRecord(totalRecord);
		pageBean.setHql("");
		pageBean.setSql("");
		result.setPageBean(pageBean);
		return result;
	}

	@Override
	public void update(Tb_docgood tb_docgood) {
//		Tb_docgood tb_docgood2 = tb_docgoodDao.findByPrimaryKey(Tb_docgood.class,
//				tb_docgood.getId());
//		try {
//			MyBeanUtils.copyBeanNotNull2Bean(tb_docgood, tb_docgood2);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
		tb_docgoodDao.update(tb_docgood);
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
		tb_docgoodDao.delete(Tb_docgood.class, hql.toString());
	}

	@Override
	public List<Tb_docgood> listgood(String docid, String memberid,String deviceid) {
		// TODO Auto-generated method stub
		return tb_docgoodDao.listgood(docid, memberid, deviceid);
	}

	@Override
	public int findcountbyId(String docid, String memberId) {
		String hql  = "SELECT COUNT(1) FROM TB_DocGood where DocId = ? and MemberId = ? and DATEDIFF(DAY, todayTime, GETDATE())<1";
		List condition = new ArrayList();
		condition.add(docid);
		condition.add(memberId);
		return tb_docgoodDao.findCount(hql, condition);
	}

	@Override
	public Integer getmaxId() {
		return tb_docgoodDao.getmaxId();
	}

	@Override
	public int isgood(String docid, String opId){
		String sql = "SELECT count(*) from Tb_docgood  where docid =? and DATEDIFF(DAY, todaytime, GETDATE())<1 AND MemberId =?";
		return  tb_docgoodDao.getAllNum(sql,docid,opId);
	}

	@Override
	public List<Object[]> goodDocList(String docid){
		String sql = "select * from(select m.id,m.HeadImg,ROW_NUMBER() over(order by dg.todaytime DESC)T FROM TB_DocGood AS dg LEFT JOIN Tb_member AS m ON dg.MemberId=m.Id WHERE dg.MemberId is not null AND dg.DocId =?)TT WHERE TT.T between 1 and 6";
		return  tb_docgoodDao.findBySql(sql,docid);
	}

	@Override
	public List<Object[]> groupList(String fid){
		String sql = "SELECT d.filetype,d.iwidht,d.iheight,d.filepath FROM TB_Doc AS d WHERE d.docstatus =1 and d.ispublic =1 and d.isdelete =0 AND d.FID =?";
		return  tb_docgoodDao.findBySql(sql,fid);
	}

	@Override
	public List<Object[]> goodMemDocList(String memberId,String startRow,String endRow){
		return  null;
	}
}
