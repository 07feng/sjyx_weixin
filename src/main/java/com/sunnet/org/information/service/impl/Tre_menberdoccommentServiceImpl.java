package com.sunnet.org.information.service.impl;

import com.sunnet.framework.pagenation.PageBean;
import com.sunnet.framework.pagenation.QueryResult;
import com.sunnet.framework.service.impl.BaseServiceImpl;
import com.sunnet.org.information.dao.IT_comment_fidDao;
import com.sunnet.org.information.dao.ITb_docDao;
import com.sunnet.org.information.dao.ITre_menberdoccommentDao;
import com.sunnet.org.information.model.T_comment_fid;
import com.sunnet.org.information.model.Tre_menberdoccomment;
import com.sunnet.org.information.service.ITre_menberdoccommentService;
import com.sunnet.org.information.vo.T_comment_fidUtil;
import com.sunnet.org.member.dao.ITb_memberDao;
import com.sunnet.org.util.MyBeanUtils;
import com.sunnet.org.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 
 * <br>
 * <b>功能：</b>Tre_menberdoccomment Service<br>
 * <b>作者：</b>jing<br>
 * <b>日期：</b> 2016/12/1 <br>
 */
@Service
public class Tre_menberdoccommentServiceImpl extends BaseServiceImpl<Tre_menberdoccomment>  implements ITre_menberdoccommentService
{
	
	@Autowired
	private ITre_menberdoccommentDao tre_menberdoccommentDao;
	@Autowired
	private IT_comment_fidDao t_comment_fidDao;
	@Autowired
	private ITb_docDao iTb_docDao;
	@Autowired
	private ITb_memberDao iTb_memberDao;
	
	@Override
	public QueryResult<Tre_menberdoccomment> list(PageBean pageBean, HttpServletRequest request) {
		StringBuffer str = new StringBuffer();
		str.append(" from Tre_menberdoccomment o where 1=1 "); //初始化语句
		if (StringUtils.isStringNull(request.getParameter("docid.id"))) {
			str.append(" and o.docid = '" + request.getParameter("docid.id")+"' " );
		}
		if (StringUtils.isStringNull(request.getParameter("membername"))) {
			str.append(" and o.docid.memberid.usersname like '%" + request.getParameter("membername")+"%' " );
		}
		
		if (StringUtils.isStringNull(request.getParameter("mname"))) {
			str.append(" and o.member_id.usersname like '%" + request.getParameter("mname")+"%' " );
		}
		 
		pageBean.setHql(str.toString());  
		int totalRecord = tre_menberdoccommentDao.findByHQLCount(Tre_menberdoccomment.class, pageBean);
		
		str.append(" order by comment_time desc ");
		pageBean.setHql(str.toString());  
		List<Tre_menberdoccomment> list = tre_menberdoccommentDao.findByHQLPage(Tre_menberdoccomment.class, pageBean);
		QueryResult<Tre_menberdoccomment> result = new QueryResult<Tre_menberdoccomment>();
		List item = new ArrayList();
		for (Tre_menberdoccomment obj : list) {
			Map<Object, Object> map = new HashMap<Object, Object>();
			map.put("id", obj.getId());
			map.put("fid", obj.getFid());
			map.put("comments", obj.getComments());
			//map.put("member_id", obj.getMember_id());
			 if(obj.getMember_id()!=null){
				 map.put("memberid", obj.getMember_id().getId());
				 map.put("usersname", obj.getMember_id().getUsersname());
			 }else{
				 map.put("memberid", "");
				 map.put("usersname","");
			 }
			if (obj.getDocid() != null) {
				map.put("docid", obj.getDocid().getId());
				map.put("docmembername", obj.getDocid().getMemberid().getUsersname());
				map.put("doctitle", obj.getDocid().getDoctitle());
				map.put("filegoodcount", obj.getDocid().getFilegoodcount());
				map.put("filekeepcount", obj.getDocid().getFilekeepcount());
				map.put("filepaycount", obj.getDocid().getFilepaycount());
				map.put("fileviewcount", obj.getDocid().getFileviewcount());
				map.put("filecommentscount", obj.getDocid()
						.getFilecommentscount());
				map.put("filepath", obj.getDocid().getFilepath());
				map.put("filetype", obj.getDocid().getFiletype());
			} else {
				map.put("docid", "");
				map.put("doctitle", "");
				map.put("filegoodcount", "");
				map.put("filekeepcount", "");
				map.put("filepaycount", "");
				map.put("fileviewcount", "");
				map.put("filecommentscount", "");
				map.put("filepath", "");
			}
			if (obj.getEdit_time() != null) {
				map.put("edit_time", obj.getEdit_time());
			} else {
				map.put("edit_time", "");
			}
			if (obj.getMember_id() != null) {
				map.put("member_id", obj.getMember_id().getUsersname());
			} else {
				map.put("usersname", "");
			}
			if (obj.getComment_time() != null) {
				map.put("comment_time", obj.getComment_time());
			} else {
				map.put("comment_time", "");
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
			List<T_comment_fid> listdoc = t_comment_fidDao.findByHQL("from T_comment_fid where fd_doccomment_id=?  order by fd_comment_time desc ",
					obj.getId());
	        map.put("listdoc",T_comment_fidUtil.getcomList(listdoc));
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
	public void update(Tre_menberdoccomment tre_menberdoccomment) {
		Tre_menberdoccomment tre_menberdoccomment2 = tre_menberdoccommentDao.findByPrimaryKey(Tre_menberdoccomment.class,
				tre_menberdoccomment.getId());
		try {
			MyBeanUtils.copyBeanNotNull2Bean(tre_menberdoccomment, tre_menberdoccomment2);
		} catch (Exception e) {
			e.printStackTrace();
		}
		tre_menberdoccommentDao.update(tre_menberdoccomment2);
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
		this.tre_menberdoccommentDao.delete(Tre_menberdoccomment.class, hql.toString());
	}

	@Override
	public void updateStatus(Object[] entityids) {
		// TODO Auto-generated method stub
		StringBuilder hql = new StringBuilder("");
		for (int i = 0; i < entityids.length; i++) {
			if (i == 0) {
				hql.append(" id='").append(entityids[i]).append("'");
			} else {
				hql.append(" or id='").append(entityids[i]).append("'");
			}
		}
		tre_menberdoccommentDao.updateStatus(Tre_menberdoccomment.class, hql.toString());
	}

	@Override
	public void updateStatus2(Object[] entityids) {
		// TODO Auto-generated method stub
		StringBuilder hql = new StringBuilder("");
		for (int i = 0; i < entityids.length; i++) {
			if (i == 0) {
				hql.append(" id='").append(entityids[i]).append("'");
			} else {
				hql.append(" or id='").append(entityids[i]).append("'");
			}
		}
		tre_menberdoccommentDao.updateStatus2(Tre_menberdoccomment.class, hql.toString());
	}

	/**
	 * authorjinhao
	 * @param docid
	 * @return
	 */
	@Override
	public List<Object[]> getAllByDocId(String docid,int startRow,int endRow) {
		String sql = "SELECT * FROM (SELECT m.id,m.UsersName,m.HeadImg,mdc.Comments,mdc.id as mid,mdc.comment_time,ROW_NUMBER() over(order by mdc.comment_time desc)T FROM TRE_MenberDocComment AS mdc LEFT JOIN Tb_member AS m ON mdc.member_id = m.id WHERE mdc.docid=?)TT WHERE TT.T between ? and ?";
		return tre_menberdoccommentDao.findBySql(sql,docid,startRow,endRow);
	}

	@Override
	public int findCount(List condition) {
		String sql = "select count(*) from Tre_menberdoccomment where is_public='1' and docid= ?";
		return tre_menberdoccommentDao.findCount(sql,condition);
	}

	@Override
	public Integer getmaxId() {
		return tre_menberdoccommentDao.getmaxId();
	}

	@Override
	public void saveComment(List condition) {
		String sql = "INSERT into TRE_MenberDocComment(Id,Fid,Docid,Comments,comment_time,is_public,member_id) VALUES(?,0,?,?,GETDATE(),'1',?)";
		tre_menberdoccommentDao.saveComment(sql,condition);
	}

	@Override
	public Integer addComment(Tre_menberdoccomment comment,Object[] mem,Object[] mem1,String docid,String memid){
		tre_menberdoccommentDao.save(comment);	//保存评论
		Double levelIntegral = Double.valueOf(mem[2].toString())+ Integer.parseInt(mem[3].toString());
		//判断 两位用户是否满足升级条件
		String sqlLevel = "update Tb_member set levelid=?,levelintegral = ? where id=?";		//修改等级和经验
		String sqlLevel1 = "update Tb_member set levelintegral=? where id=?";		//修改经验
		if (levelIntegral>Double.valueOf(mem[4].toString()) && Integer.parseInt(mem[3].toString())<=4){
			if (Integer.parseInt(mem[1].toString()) ==4)
				iTb_memberDao.updateSql(sqlLevel,15,levelIntegral,memid);
			else
				iTb_memberDao.updateSql(sqlLevel,Integer.parseInt(mem[1].toString())+1,levelIntegral,memid);
		}else
			iTb_memberDao.updateSql(sqlLevel1,levelIntegral,memid);

		//被评论人------------------------------------------------
		Double levelIntegral2 = Double.valueOf(mem1[4].toString())+ Integer.parseInt(mem1[5].toString());
		//判断 两位用户是否满足升级条件
		if (levelIntegral2>Double.valueOf(mem1[6].toString()) && Integer.parseInt(mem1[5].toString())<=4){
			if (Integer.parseInt(mem1[3].toString())==4)
				iTb_memberDao.updateSql(sqlLevel,15,levelIntegral2,mem1[2].toString());
			else
				iTb_memberDao.updateSql(sqlLevel,Integer.parseInt(mem1[3].toString())+1,levelIntegral2,mem1[2].toString());
		}else
			iTb_memberDao.updateSql(sqlLevel1,levelIntegral2,mem1[2].toString());

		Integer result = Integer.parseInt(mem1[1].toString())+1;
		iTb_docDao.updateSql("update Tb_doc set filecommentscount = ? where id =?",result,docid);  //修改文件评论数
		return result;
	}

}
