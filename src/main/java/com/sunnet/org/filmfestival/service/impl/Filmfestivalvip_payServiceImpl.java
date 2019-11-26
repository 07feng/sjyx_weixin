package com.sunnet.org.filmfestival.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sunnet.framework.pagenation.PageBean;
import com.sunnet.framework.pagenation.QueryResult;
import com.sunnet.framework.service.impl.BaseServiceImpl;
import com.sunnet.org.util.MyBeanUtils;
import com.sunnet.org.util.StringUtils;
import com.sunnet.org.doc.dao.ITre_friendscircleDao;
import com.sunnet.org.doc.model.Tb_docpay;
import com.sunnet.org.doc.model.Tre_friendscircle;
import com.sunnet.org.filmfestival.dao.IFilmfestivalvip_payDao;
import com.sunnet.org.filmfestival.model.Filmfestivalvip_pay;
import com.sunnet.org.filmfestival.service.IFilmfestivalvip_payService;
import com.sunnet.org.filmfestival.vo.Filmfestivalvip_payUtil;

/**
 * 
 * <br>
 * <b>功能：</b>Filmfestivalvip_pay Service<br>
 * <b>作者：</b>jing<br>
 * <b>日期：</b> 2016/12/1 <br>
 */
@Service
public class Filmfestivalvip_payServiceImpl extends
		BaseServiceImpl<Filmfestivalvip_pay> implements
		IFilmfestivalvip_payService {

	@Autowired
	private IFilmfestivalvip_payDao filmfestivalvip_payDao;
	@Autowired
	private ITre_friendscircleDao tre_friendscircleDao;
	@Override
	public QueryResult<Filmfestivalvip_pay> list(PageBean pageBean,
			HttpServletRequest request) {
		StringBuffer str = new StringBuffer();
		str.append(" from Filmfestivalvip_pay o where 1=1 "); // 初始化语句
		if (StringUtils.isStringNull(request.getParameter("vipid"))) {
			str.append(" and o.vipid.id = '" + request.getParameter("vipid") + "'");
		}
		if (StringUtils.isStringNull(request.getParameter("memberid"))) {
			str.append(" and o.memberid.id = '" + request.getParameter("memberid") + "'");
		}
		if (StringUtils.isStringNull(request.getParameter("memberid_id"))) {
			str.append(" and o.vipid.memberid.id = '" + request.getParameter("memberid_id") + "'");
		}
		if (StringUtils.isStringNull(request.getParameter("membername"))) {
			str.append(" and o.memberid.usersname like '%" + request.getParameter("membername") + "%' ");
		}
		pageBean.setHql(str.toString());
		int totalRecord = filmfestivalvip_payDao.findByHQLCount(
				Filmfestivalvip_pay.class, pageBean);
		str.append(" order by o.addtime desc ");
		pageBean.setHql(str.toString());
		List<Filmfestivalvip_pay> list = filmfestivalvip_payDao.findByHQLPage(
				Filmfestivalvip_pay.class, pageBean);
		QueryResult<Filmfestivalvip_pay> result = new QueryResult<Filmfestivalvip_pay>();
		result.setResultList(Filmfestivalvip_payUtil.getControllerList(list));
		pageBean.setTotalRecord(totalRecord);
		pageBean.setHql("");
		pageBean.setSql("");
		result.setPageBean(pageBean);
		return result;
	}

	@Override
	public QueryResult<Filmfestivalvip_pay> lists(PageBean pageBean,
			HttpServletRequest request) {
		StringBuffer str = new StringBuffer();
		str.append(" from Filmfestivalvip_pay o where 1=1 "); // 初始化语句
		if (StringUtils.isStringNull(request.getParameter("vipid"))) {
			str.append(" and o.vipid.id = '" + request.getParameter("vipid") + "'");
		}
		if (StringUtils.isStringNull(request.getParameter("memberid"))) {
			str.append(" and o.memberid.id = '" + request.getParameter("memberid") + "'");
		}
		//根据文件发布人查询这个文件的商客
		if (StringUtils.isStringNull(request.getParameter("memberid_id"))) {
			str.append(" and o.vipid.memberid.id = '" + request.getParameter("memberid_id") + "'");
		}
		/*if (StringUtils.isStringNull(request.getParameter("memberid"))) {
			str.append(" and o.memberid.id = '" + request.getParameter("memberid") + "'");
		}*/
		pageBean.setHql(str.toString());
		int totalRecord = filmfestivalvip_payDao.findByHQLCount(
				Filmfestivalvip_pay.class, pageBean);
		List<Filmfestivalvip_pay> list = filmfestivalvip_payDao.findByHQLPage(
				Filmfestivalvip_pay.class, pageBean);
		QueryResult<Filmfestivalvip_pay> result = new QueryResult<Filmfestivalvip_pay>();
		List item = new ArrayList();
		for (Filmfestivalvip_pay obj : list) {
			Map<Object, Object> map = new HashMap<Object, Object>();

			map.put("id", obj.getId());
			if (obj.getMemberid() != null) {
				map.put("memberid", obj.getMemberid().getId());
				map.put("membername", obj.getMemberid().getUsersname());
				map.put("headimg", obj.getMemberid().getHeadimg());
				if (obj.getMemberid().getLevelid() != null) {
					map.put("levelid", obj.getMemberid().getLevelid().getId());
					map.put("levelname", obj.getMemberid().getLevelid()
							.getLevelname());
				} else {
					map.put("levelid", "");
					map.put("levelname", "");
				}
			} else {
				map.put("memberid", "");
				map.put("membername", "");
				map.put("headimg", "");
				map.put("levelid", "");
				map.put("levelname", "");
			}
			if(obj.getVipid()!=null){
				map.put("vipid", obj.getVipid().getId());
				map.put("viptitel", obj.getVipid().getTitel());
			}
			map.put("vipid", obj.getVipid());
			map.put("payamount", obj.getPayamount());
			map.put("addtime", obj.getAddtime());
			//查询文件发布人关注了谁
			if (StringUtils.isStringNull(request.getParameter("memberid_id"))) {
				StringBuffer strs = new StringBuffer();
				strs.append(" from Tre_friendscircle o where 1=1 "); // 初始化语句
				strs.append(" and o.circlememberid = '" + obj.getMemberid().getId() + "' ");
				strs.append(" and o.memberid = '"
						+ request.getParameter("memberid_id") + "'");
				List<Tre_friendscircle> listdoc = tre_friendscircleDao
						.findByHQL(strs.toString());
				if (null != listdoc && listdoc.size() > 0) {
				    map.put("isfriend", listdoc.get(0).getIsfriends());
					map.put("isfriendto", "yes");
				} else {
					map.put("isfriendto", "no");
				}
			} else {
				map.put("isfriendto", "no");
			}
			//查询 我关注了 这个文件发布人的商客没有
			if (StringUtils
					.isStringNull(request.getParameter("my_memberid_id"))) {
				StringBuffer strs = new StringBuffer();
				strs.append(" from Tre_friendscircle o where 1=1 "); // 初始化语句
				strs.append(" and o.circlememberid = '" + obj.getMemberid().getId() + "' ");
				strs.append(" and o.memberid = '"
						+ request.getParameter("my_memberid_id") + "'");
				List<Tre_friendscircle> listdoc = tre_friendscircleDao
						.findByHQL(strs.toString());
				if (null != listdoc && listdoc.size() > 0) {
					map.put("my_isfriend", listdoc.get(0).getIsfriends());
					map.put("my_isfriendto", "yes");
				} else {
					map.put("my_isfriendto", "no");
				}
			} else {
				map.put("my_isfriendto", "no");
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
	public void update(Filmfestivalvip_pay filmfestivalvip_pay) {
		Filmfestivalvip_pay filmfestivalvip_pay2 = filmfestivalvip_payDao
				.findByPrimaryKey(Filmfestivalvip_pay.class,
						filmfestivalvip_pay.getId());
		try {
			MyBeanUtils.copyBeanNotNull2Bean(filmfestivalvip_pay,
					filmfestivalvip_pay2);
		} catch (Exception e) {
			e.printStackTrace();
		}
		filmfestivalvip_payDao.update(filmfestivalvip_pay2);
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
		filmfestivalvip_payDao
				.delete(Filmfestivalvip_pay.class, hql.toString());
	}

	@Override
	public QueryResult<Filmfestivalvip_pay> getPages(PageBean pagebean, String wherename,
			String TbName, String TbId, String order, Class c) {
		// TODO Auto-generated method stub
		return filmfestivalvip_payDao.getPages(pagebean, wherename, TbName, TbId, order, c);
	}
	@Override
	public int findCount(String memberId){
		String sql =  "select count(*) from filmfestivalvip_pay where memberId = ?";
		List condition = new ArrayList();
		condition.add(memberId);
		return filmfestivalvip_payDao.findCount(sql,condition);
	}

}
