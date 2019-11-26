package com.sunnet.org.pay.service.impl;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;

import com.sunnet.org.util.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sunnet.framework.pagenation.PageBean;
import com.sunnet.framework.pagenation.QueryResult;
import com.sunnet.framework.service.impl.BaseServiceImpl;
import com.sunnet.org.util.MyBeanUtils;
import com.sunnet.org.util.StringUtils;

import com.sunnet.org.pay.dao.ITb_rechargerecordDao;
import com.sunnet.org.pay.model.Tb_rechargerecord;
import com.sunnet.org.pay.service.ITb_rechargerecordService;
import com.sunnet.org.pay.vo.Tb_rechargerecordUtil;

/**
 * 
 * <br>
 * <b>功能：</b>Tb_rechargerecord Service<br>
 * <b>作者：</b>jing<br>
 * <b>日期：</b> 2016/12/1 <br>
 */
@Service
public class Tb_rechargerecordServiceImpl extends BaseServiceImpl<Tb_rechargerecord>  implements ITb_rechargerecordService
{
	
	@Autowired
	private ITb_rechargerecordDao tb_rechargerecordDao;

	@Override
	public QueryResult<Tb_rechargerecord> list(PageBean pageBean, HttpServletRequest request) {
		StringBuffer str = new StringBuffer();
		str.append(" from Tb_rechargerecord o where 1=1 "); //初始化语句
		if (StringUtils.isStringNull(request.getParameter("way"))) {
			str.append(" and o.way like '%" + request.getParameter("way") + "%' ");
		}
		if (StringUtils.isStringNull(request.getParameter("startDate_01"))) {
			str.append(" and o.rechargetime >= '" + request.getParameter("startDate_01") + "' ");
		}
		if (StringUtils.isStringNull(request.getParameter("endDate_01"))) {
			str.append(" and o.rechargetime <= '"+request.getParameter("endDate_01")+"' "); 
		}
		if (StringUtils.isStringNull(request.getParameter("rrstatus"))) {
			str.append(" and o.rrstatus = '"+request.getParameter("rrstatus")+"' "); 
		}
		if (StringUtils.isStringNull(request.getParameter("memberid.id"))) {
			str.append(" and o.memberid = '"+request.getParameter("memberid.id")+"' "); 
		}
		if (StringUtils.isStringNull(request.getParameter("rrstatus"))) {
			str.append(" and o.rrstatus like '%"+request.getParameter("rrstatus")+"%' "); 
		}
		pageBean.setHql(str.toString());
		int totalRecord = tb_rechargerecordDao.findByHQLCount(Tb_rechargerecord.class, pageBean);
		str.append(" order by  rechargetime desc ");
		pageBean.setHql(str.toString());
		List<Tb_rechargerecord> list = tb_rechargerecordDao.findByHQLPage(Tb_rechargerecord.class, pageBean);
		QueryResult<Tb_rechargerecord> result = new QueryResult<Tb_rechargerecord>();
		result.setResultList(Tb_rechargerecordUtil.getControllerList(list));
		pageBean.setTotalRecord(totalRecord);
		pageBean.setHql("");
		pageBean.setSql("");
		result.setPageBean(pageBean);
		return result;
	}

	@Override
	public QueryResult<Tb_rechargerecord> memberList(PageBean pageBean, String memberId) {
		StringBuffer str = new StringBuffer();
		QueryResult<Tb_rechargerecord> result = new QueryResult<Tb_rechargerecord>();
		str.append(" from Tb_rechargerecord o where rrstatus='TRADE_SUCCESS' and memberId='"+memberId+"' and finishtime is not null"); //初始化语句
		pageBean.setHql(str.toString());
		int totalRecord = tb_rechargerecordDao.findByHQLCount(Tb_rechargerecord.class, pageBean);
		if(pageBean.getTotalPage()<pageBean.getCurrent_Page()){
			return result;
		}
		str.append(" order by  rechargetime desc ");
		pageBean.setHql(str.toString());
		List<Tb_rechargerecord> list = tb_rechargerecordDao.findByHQLCX(str.toString(), pageBean);

		List item= new ArrayList();
		DecimalFormat df = new DecimalFormat("0.00");
		for (Tb_rechargerecord obj : list) {
			Map<Object,Object> map = new HashMap<Object,Object>();
			map.put("id", obj.getRrid());
//			map.put("orno", obj.getOrno());
//			if(obj.getAlino() != null){
//				map.put("alino",obj.getAlino());
//			}else {
//				map.put("alino","");
//			}
			map.put("amount", df.format(obj.getReamount()));
			map.put("way", obj.getWay());
//			map.put("rechargetime", obj.getRechargetime().toString());
			if(obj.getSuccesstime() != null){
				map.put("finishtime", DateUtil.DateToString(obj.getSuccesstime(),"YYYY-MM-dd hh:mm:ss"));
			}else {
				map.put("finishtime", DateUtil.DateToString(obj.getFinishtime(),"YYYY-MM-dd hh:mm:ss"));
			}
			map.put("typeName","充值");
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
	public void update(Tb_rechargerecord tb_rechargerecord) {
		Tb_rechargerecord tb_rechargerecord2 = tb_rechargerecordDao.findByPrimaryKey(Tb_rechargerecord.class,
				tb_rechargerecord.getRrid());
		try {
			MyBeanUtils.copyBeanNotNull2Bean(tb_rechargerecord, tb_rechargerecord2);
		} catch (Exception e) {
			e.printStackTrace();
		}
		tb_rechargerecordDao.update(tb_rechargerecord2);
	}

	@Override
	public void updateRecord(String aliNo,Double reamount,String roNO){
		tb_rechargerecordDao.updateSql("update Tb_rechargerecord set aliNo=?,rrStatus='TRADE_SUCCESS',reamount=?,successTime=GETDATE() where orNo=?",aliNo,reamount,roNO );
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
		tb_rechargerecordDao.delete(Tb_rechargerecord.class, hql.toString());
	}
	
	

}
