package com.sunnet.org.information.dao;

import com.sunnet.framework.dao.IBaseDao;
import com.sunnet.org.information.model.T_comment_fid;

/**
 * 
 * <br>
 * <b>功能：</b>T_comment_fid接口<br>
 * <b>作者：</b>jing<br>
 * <b>日期：</b> 2016/12/1 <br>
 */
public interface IT_comment_fidDao extends IBaseDao<T_comment_fid>
{
    public  int getMaxId();
}
