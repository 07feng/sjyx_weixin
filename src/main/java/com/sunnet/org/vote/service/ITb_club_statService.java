package com.sunnet.org.vote.service;

import com.sunnet.framework.service.IBaseService;
import com.sunnet.org.vote.model.Tb_club_stat;
import com.sunnet.org.vote.model.Tb_club_vote;

/**
 * 
 * <br>
 * <b>功能：</b>Tb_space Service<br>
 * <b>作者：</b>jing<br>
 * <b>日期：</b> 2016/12/1 <br>
 */
public interface ITb_club_statService extends IBaseService<Tb_club_stat>
{
    int todayVoteCount(String openId, int picId);
}
