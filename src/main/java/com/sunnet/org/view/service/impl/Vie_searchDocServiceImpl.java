package com.sunnet.org.view.service.impl;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sunnet.framework.pagenation.PageBean;
import com.sunnet.framework.pagenation.QueryResult;
import com.sunnet.framework.service.impl.BaseServiceImpl;
import com.sunnet.org.information.model.Tb_filetype;
import com.sunnet.org.member.model.Tb_filelabel;
import com.sunnet.org.util.MyBeanUtils;
import com.sunnet.org.util.StringUtils;
import com.sunnet.org.view.dao.IVie_searchDocDao;
import com.sunnet.org.view.model.Vie_searchdoc;
import com.sunnet.org.view.model.Vie_searchdoc;
import com.sunnet.org.view.service.IVie_searchDocService;
import com.sunnet.org.view.vo.Vie_searchDocUtil;

/**
 * 
 * <br>
 * <b>功能：</b>Vie_searchdoc Service<br>
 * <b>作者：</b>jing<br>
 * <b>日期：</b> 2016/12/1 <br>
 */
@Service
public class Vie_searchDocServiceImpl extends BaseServiceImpl<Vie_searchdoc>  implements IVie_searchDocService
{
	
	@Autowired
	private IVie_searchDocDao Vie_searchdocDao;

	@Override
	public QueryResult<Vie_searchdoc> list(PageBean pageBean, HttpServletRequest request) {
		StringBuffer str = new StringBuffer();
		str.append(" from Vie_searchdoc o where 1=1 "); //初始化语句
		
		pageBean.setHql(str.toString());
		int totalRecord = Vie_searchdocDao.findByHQLCount(Vie_searchdoc.class, pageBean);
		List<Vie_searchdoc> list = Vie_searchdocDao.findByHQLPage(Vie_searchdoc.class, pageBean);
		QueryResult<Vie_searchdoc> result = new QueryResult<Vie_searchdoc>();
		result.setResultList(Vie_searchDocUtil.getControllerList(list));
		pageBean.setTotalRecord(totalRecord);
		pageBean.setHql("");
		pageBean.setSql("");
		result.setPageBean(pageBean);
		return result;
	}

	@Override
	public void update(Vie_searchdoc Vie_searchdoc) {
		Vie_searchdoc Vie_searchdoc2 = Vie_searchdocDao.findByPrimaryKey(Vie_searchdoc.class,
				Vie_searchdoc.getId());
		try {
			MyBeanUtils.copyBeanNotNull2Bean(Vie_searchdoc, Vie_searchdoc2);
		} catch (Exception e) {
			e.printStackTrace();
		}
		Vie_searchdocDao.update(Vie_searchdoc2);
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
		Vie_searchdocDao.delete(Vie_searchdoc.class, hql.toString());
	}

	@Override
	public String selectCreate(String str, List<Tb_filetype> type) {
		String where="";
		 if(str.length()>0){
			 for (Tb_filetype tb_filelabel : type) {
				
				if(null!=tb_filelabel.getTypeName() && tb_filelabel.getTypeName().length()>0 && str.indexOf(tb_filelabel.getTypeName())!=-1){
					where+=" and v.type_name='"+tb_filelabel.getTypeName()+"'";
					str=str.replaceAll(tb_filelabel.getTypeName(), "");
					break;
				}
			}
		 }
		/* if(str.length()>0){
			 for (Tb_filelabel labels : label) {
					if(null!=labels.getName() && labels.getName().length()>0 && str.indexOf(labels.getName())!=-1){
						where+=" and v.labelname='"+labels.getName()+"'";
						str=str.replaceAll(labels.getName(), "");
						break;
					}
				}
		 }*/
		 if(str.length()>0){
					if(str.indexOf("图片")!=-1){
						where+=" and v.filetypename='图片' ";
						str=str.replaceAll("图片", "");
					}else if(str.indexOf("视频")!=-1){
						where+=" and v.filetypename='视频' ";
						str=str.replaceAll("视频", "");
					}
				 
		 }
		 if(str.length()>0){
					where+=" and (v.membername like '%"+str+"%' or v.doctitle like '%"+str+"%' or charindex(v.membername,'"+str+"')>0 or  charindex(v.doctitle,'"+str+"')>0 )  ";
	    }
		return where;
	}
	
	

}
