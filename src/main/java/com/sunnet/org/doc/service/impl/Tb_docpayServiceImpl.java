package com.sunnet.org.doc.service.impl;

import com.sunnet.framework.pagenation.PageBean;
import com.sunnet.framework.pagenation.QueryResult;
import com.sunnet.framework.service.impl.BaseServiceImpl;
import com.sunnet.org.doc.dao.ITb_docpayDao;
import com.sunnet.org.doc.dao.ITre_friendscircleDao;
import com.sunnet.org.doc.model.Tb_docpay;
import com.sunnet.org.doc.model.Tre_friendscircle;
import com.sunnet.org.doc.service.ITb_docpayService;
import com.sunnet.org.doc.vo.Tb_docpayUtil;
import com.sunnet.org.util.DateUtil;
import com.sunnet.org.util.MyBeanUtils;
import com.sunnet.org.util.StringUtils;
import com.sunnet.org.view.dao.Ivie_docpayDao;
import com.sunnet.org.view.model.vie_docpay;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.text.DecimalFormat;
import java.util.*;

/**
 * 
 * <br>
 * <b>功能：</b>Tb_docpay Service<br>
 * <b>作者：</b>jing<br>
 * <b>日期：</b> 2016/12/1 <br>
 */
@Service
public class Tb_docpayServiceImpl extends BaseServiceImpl<Tb_docpay>  implements ITb_docpayService
{
	@Autowired
	private ITre_friendscircleDao tre_friendscircleDao;
	@Autowired
	private ITb_docpayDao tb_docpayDao;
	@Autowired
	private Ivie_docpayDao vie_docpayDao;

	@Override
	public QueryResult<Tb_docpay> list(PageBean pageBean, HttpServletRequest request) {
		StringBuffer str = new StringBuffer();
		str.append(" from Tb_docpay o where 1=1 "); //初始化语句
		if (StringUtils.isStringNull(request.getParameter("memberid"))) {
			str.append(" and o.memberid.id = '" + request.getParameter("memberid") + "'");
		}
		if (StringUtils.isStringNull(request.getParameter("docid"))) {
			str.append(" and o.docid.id = '" + request.getParameter("docid") + "'");
		}
		if (StringUtils.isStringNull(request.getParameter("memberid_id"))) {
			str.append(" and o.docid.memberid.id = '" + request.getParameter("memberid_id") + "'");
		}
		if (StringUtils.isStringNull(request.getParameter("membername"))) {
			str.append(" and o.memberid.usersname like '%" + request.getParameter("membername") + "%' ");
		}
		pageBean.setHql(str.toString());
		int totalRecord = tb_docpayDao.findByHQLCount(Tb_docpay.class, pageBean);
		
		str.append(" order by o.addtime desc ");
		pageBean.setHql(str.toString());
		List<Tb_docpay> list = tb_docpayDao.findByHQLPage(Tb_docpay.class, pageBean);
		QueryResult<Tb_docpay> result = new QueryResult<Tb_docpay>();
		result.setResultList(Tb_docpayUtil.getControllerList(list));
		pageBean.setTotalRecord(totalRecord);
		pageBean.setHql("");
		pageBean.setSql("");
		result.setPageBean(pageBean);
		return result;
	}
	@Override
	public QueryResult<vie_docpay> listf(PageBean pageBean, HttpServletRequest request) {
		StringBuffer str = new StringBuffer();
		str.append(" from vie_docpay o where 1=1 "); //初始化语句
		if (StringUtils.isStringNull(request.getParameter("memberid_id"))) {
			str.append(" and o.memberid = '" + request.getParameter("memberid_id") + "'");
		}
		pageBean.setHql(str.toString());
		int totalRecord = vie_docpayDao.findByHQLCount(vie_docpay.class, pageBean);
		List<vie_docpay> list = vie_docpayDao.findByHQLPage(vie_docpay.class, pageBean);
		
		QueryResult<vie_docpay> result = new QueryResult<vie_docpay>();
		List item = new ArrayList();
		for (vie_docpay obj : list) {
			Map<Object, Object> map = new HashMap<Object, Object>();

			map.put("id", obj.getId());
			map.put("usersname", obj.getUsersname());
			map.put("headimg", obj.getHeadimg());
			map.put("levelid", obj.getLevelid());
			map.put("levelname", obj.getLevelname());
			map.put("memberid", obj.getMemberid());
			map.put("cpaynum", obj.getCpaynum());
			 if (StringUtils.isStringNull(request.getParameter("memberid_id"))) {
					StringBuffer strs = new StringBuffer();
					strs.append(" from Tre_friendscircle o where 1=1 "); //初始化语句
					strs.append(" and o.circlememberid = '" + obj.getId() + "' ");
					strs.append(" and o.memberid = '" + request.getParameter("memberid_id") + "'");
					List<Tre_friendscircle> listdoc=tre_friendscircleDao.findByHQL(strs.toString());
					if(null!=listdoc && listdoc.size()>0){
						map.put("isfriendto", "yes");
					}else{
						map.put("isfriendto", "no");
				} 
			}
			item.add(map);
		}
		
		result.setResultList(item);
		pageBean.setTotalRecord(totalRecord);
		pageBean.setHql("");
		pageBean.setSql("");
		result.setPageBean(pageBean);
		return result;
	}

	@Override
	public List payList(PageBean pageBean, String memberId,int code) {
		StringBuffer str = new StringBuffer();
		str.append("SELECT * FROM(SELECT *,ROW_NUMBER() over(order by TT.AddTime DESC)A FROM("); //初始化语句

		if(code==1){
			str.append("SELECT dp.id,dp.MemberId,dp.DocId,NULL AS vipid,dp.PayAmount,dp.AddTime,m.UsersName,m.id AS memId,m.HeadImg FROM " +
					"Tb_DocPay AS dp LEFT JOIN TB_Doc AS d ON dp.DocId = d.id " +
					"LEFT JOIN Tb_member AS m ON d.memberid = m.id " +
					"UNION ALL " +
					"SELECT ffvp.id,ffvp.MemberId,NULL AS DocId,ffvp.vipid,ffvp.PayAmount,ffvp.AddTime,m.UsersName,m.id AS memId,m.HeadImg FROM " +
					"Filmfestivalvip_Pay AS ffvp  LEFT JOIN FilmfestivalVIP AS ffv ON ffvp.vipid = ffv.id " +
					"LEFT JOIN Tb_member AS m ON ffv.Member_id = m.id)TT WHERE TT.MemberId = ?)AA WHERE AA.A BETWEEN ? AND ?");
		}else{
			str.append("SELECT dp.id,d.MemberId,dp.DocId,NULL AS vipid,dp.PayAmount,dp.AddTime,m.UsersName,m.id AS memId,m.HeadImg FROM " +
					"Tb_DocPay AS dp LEFT JOIN TB_Doc AS d ON dp.DocId = d.id " +
					"LEFT JOIN Tb_member AS m ON dp.Memberid = m.id " +
					"UNION ALL " +
					"SELECT ffvp.id,ffv.Member_id,NULL AS DocId,ffvp.vipid,ffvp.PayAmount,ffvp.AddTime,m.UsersName,m.id AS memId,m.HeadImg FROM " +
					"Filmfestivalvip_Pay AS ffvp  LEFT JOIN FilmfestivalVIP AS ffv ON ffvp.vipid = ffv.id " +
					"LEFT JOIN Tb_member AS m ON ffvp.MemberId = m.id " +
					")TT WHERE TT.MemberId = ?)AA WHERE AA.A BETWEEN ? AND ?");
		}
		pageBean.setHql(str.toString());

		List<Object[]> list = tb_docpayDao.findBySql(str.toString(),memberId,pageBean.getStartRow(),pageBean.getEndRow());
		List item = new ArrayList();
		DecimalFormat df = new DecimalFormat("0.00");
		for (Object[] obj : list) {
			Map<Object, Object> map = new HashMap<Object, Object>();
			map.put("id", Integer.parseInt(obj[0].toString()));
			map.put("amount", df.format(new Double(obj[4].toString())*10));
			if (code == 2) {
				map.put("typeName","收入");
			}else {
				map.put("typeName","支出");
			}

			map.put("finishtime", DateUtil.DateToString((Date)obj[5],"YYYY-MM-dd hh:mm:ss"));
			map.put("member_id", obj[7].toString());
			map.put("member_name", obj[6].toString());
			map.put("member_headimg", obj[8].toString());
			item.add(map);
		}
		return item;
	}

	@Override
	public void update(Tb_docpay tb_docpay) {
		Tb_docpay tb_docpay2 = tb_docpayDao.findByPrimaryKey(Tb_docpay.class,
				tb_docpay.getId());
		try {
			MyBeanUtils.copyBeanNotNull2Bean(tb_docpay, tb_docpay2);
		} catch (Exception e) {
			e.printStackTrace();
		}
		tb_docpayDao.update(tb_docpay2);
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
		tb_docpayDao.delete(Tb_docpay.class, hql.toString());
	}
	@Override
	public QueryResult<Tb_docpay> getPages(PageBean pagebean, String wherename,
			String TbName, String TbId, String order, Class c) {
		// TODO Auto-generated method stub
		return tb_docpayDao.getPages(pagebean, wherename, TbName, TbId, order, c);
	}

	@Override
	public int findCount(String memberId) {
		String sql =  "SELECT count(1) FROM Tb_DocPay AS dp LEFT JOIN TB_Doc AS d ON dp.DocId=d.Id WHERE d.MemberId=? GROUP BY dp.MemberId";
		List list = tb_docpayDao.findBySql(sql,memberId);
		if (null == list)
			return 0;
		else
			return list.size();
	}


}
