package com.sunnet.org.vote.service;

import com.sunnet.framework.pagenation.PageBean;
import com.sunnet.framework.pagenation.QueryResult;
import com.sunnet.framework.service.IBaseService;
import com.sunnet.org.space.model.Tb_space;
import com.sunnet.org.vote.model.Tb_club_vote;

import javax.servlet.http.HttpServletRequest;

/**
 * 
 * <br>
 * <b>功能：</b>Tb_space Service<br>
 * <b>作者：</b>jing<br>
 * <b>日期：</b> 2016/12/1 <br>
 */
public interface ITb_club_voteService extends IBaseService<Tb_club_vote>
{
    int todayVoteCount(String openId,int picId);
}
