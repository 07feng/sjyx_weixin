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
import com.sunnet.org.filmfestival.dao.IFilmfestivalvip_commentDao;
import com.sunnet.org.filmfestival.dao.IFilmfestivalvip_comment_fidDao;
import com.sunnet.org.filmfestival.model.Filmfestivalvip_comment;
import com.sunnet.org.filmfestival.model.Filmfestivalvip_comment_fid;
import com.sunnet.org.filmfestival.service.IFilmfestivalvip_commentService;
import com.sunnet.org.filmfestival.vo.Filmfestivalvip_commentUtil;
import com.sunnet.org.filmfestival.vo.Filmfestivalvip_comment_fidUtil;
import com.sunnet.org.information.model.T_comment_fid;
import com.sunnet.org.information.vo.T_comment_fidUtil;

/**
 * 
 * <br>
 * <b>功能：</b>Filmfestivalvip_comment Service<br>
 * <b>作者：</b>jing<br>
 * <b>日期：</b> 2016/12/1 <br>
 */
@Service
public class Filmfestivalvip_commentServiceImpl extends BaseServiceImpl<Filmfestivalvip_comment>  implements IFilmfestivalvip_commentService
{
	
	@Autowired
	private IFilmfestivalvip_commentDao filmfestivalvip_commentDao;
	@Autowired
	private IFilmfestivalvip_comment_fidDao filmfestivalvip_comment_fidDao;
	@Override
	public QueryResult<Filmfestivalvip_comment> list(PageBean pageBean, HttpServletRequest request) {
		StringBuffer str = new StringBuffer();
		str.append(" from Filmfestivalvip_comment o where 1=1 "); //初始化语句
		if (StringUtils.isStringNull(request.getParameter("usersname"))) {
			str.append(" and o.member_id.usersname like '%" + request.getParameter("usersname") + "%' ");
		}
		pageBean.setHql(str.toString());
		int totalRecord = filmfestivalvip_commentDao.findByHQLCount(Filmfestivalvip_comment.class, pageBean);
		
		str.append(" order by comment_time desc");
		pageBean.setHql(str.toString());
		List<Filmfestivalvip_comment> list = filmfestivalvip_commentDao.findByHQLPage(Filmfestivalvip_comment.class, pageBean);
		QueryResult<Filmfestivalvip_comment> result = new QueryResult<Filmfestivalvip_comment>();
		List item = new ArrayList();
		for (Filmfestivalvip_comment obj : list) {
			Map<Object, Object> map = new HashMap<Object, Object>();

			map.put("id", obj.getId());
			map.put("fid", obj.getFid());
			if(obj.getVipid()!=null){
				map.put("vipid", obj.getVipid().getTitel());
				map.put("vip_id", obj.getVipid().getId());
			}else{
				map.put("vipid", "");
			}
			map.put("comments", obj.getComments());
			if (obj.getComment_time() != null) {
				map.put("comment_time", obj.getComment_time());
			} else {
				map.put("comment_time", "");
			}
			if (obj.getEdit_time() != null) {
				map.put("edit_time", obj.getEdit_time());
			} else {
				map.put("edit_time", "");
			}
			if (obj.getEdit_userid() != null) {
				map.put("edit_userid", obj.getEdit_userid().getFdUserName());
			} else {
				map.put("edit_userid", "");
			}
			if (obj.getIs_public() != null) {
				map.put("is_public", obj.getIs_public());
			} else {
				map.put("is_public", "");
			}
			if (obj.getMember_id() != null) {
				 map.put("memberid", obj.getMember_id().getId());
				 map.put("usersname", obj.getMember_id().getUsersname());
			 }else{
				 map.put("memberid", "");
				 map.put("usersname","");
			 }
			List<Filmfestivalvip_comment_fid> listdoc = filmfestivalvip_comment_fidDao
					.findByHQL("from Filmfestivalvip_comment_fid where fd_doccomment_id=?  order by fd_comment_time desc ",
							obj.getId());
	        map.put("listdoc",Filmfestivalvip_comment_fidUtil.getCList(listdoc));
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
	public void update(Filmfestivalvip_comment filmfestivalvip_comment) {
		Filmfestivalvip_comment filmfestivalvip_comment2 = filmfestivalvip_commentDao.findByPrimaryKey(Filmfestivalvip_comment.class,
				filmfestivalvip_comment.getId());
		try {
			MyBeanUtils.copyBeanNotNull2Bean(filmfestivalvip_comment, filmfestivalvip_comment2);
		} catch (Exception e) {
			e.printStackTrace();
		}
		filmfestivalvip_commentDao.update(filmfestivalvip_comment2);
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
		filmfestivalvip_commentDao.delete(Filmfestivalvip_comment.class, hql.toString());
	}
	
	

}
