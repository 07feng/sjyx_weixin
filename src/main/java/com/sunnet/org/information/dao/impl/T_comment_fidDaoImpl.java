package com.sunnet.org.information.dao.impl;


import org.springframework.stereotype.Repository;
import com.sunnet.framework.dao.impl.BaseDaoImpl;
import com.sunnet.org.information.dao.IT_comment_fidDao;
import com.sunnet.org.information.model.T_comment_fid;


/**
 * 
 * <br>
 * <b>功能：</b>T_comment_fid接口<br>
 * <b>作者：</b>jing<br>
 * <b>日期：</b> 2016/12/1 <br>
 */
@Repository
public class T_comment_fidDaoImpl extends BaseDaoImpl<T_comment_fid> implements IT_comment_fidDao
{
    public  int getMaxId(){
        String sql = "SELECT MAX(T_comment_fid.Id) FROM [dbo].[T_comment_fid]";
        return (Integer)(getSession().createSQLQuery(sql).uniqueResult());
    }
}
