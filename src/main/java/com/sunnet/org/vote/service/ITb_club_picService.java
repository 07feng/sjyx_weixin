package com.sunnet.org.vote.service;

import com.sunnet.framework.service.IBaseService;
import com.sunnet.org.vote.model.Tb_club_pic;
import com.sunnet.org.vote.model.Tb_club_vote;

import java.util.Date;
import java.util.List;
import java.util.Map;


/**
 * 
 * <br>
 * <b>功能：</b>Tb_space Service<br>
 * <b>作者：</b>jing<br>
 * <b>日期：</b> 2016/12/1 <br>
 */
public interface ITb_club_picService extends IBaseService<Tb_club_pic>
{
    int votePic(Tb_club_vote tbClubVote);

//    List<Map> picList(String type);

    List<Map> picList2(String type, String openId, Date startDate, Date endDate);

    List<Object[]> getVoteInfo(int id);
}
