package com.sunnet.org.vote.service.impl;

import com.sunnet.framework.service.impl.BaseServiceImpl;
import com.sunnet.org.vote.dao.ITb_club_voteDao;
import com.sunnet.org.vote.model.Tb_club_stat;
import com.sunnet.org.vote.model.Tb_club_vote;
import com.sunnet.org.vote.service.ITb_club_statService;
import com.sunnet.org.vote.service.ITb_club_voteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 
 * <br>
 * <b>功能：</b>Tb_space Service<br>
 * <b>作者：</b>jing<br>
 * <b>日期：</b> 2016/12/1 <br>
 */
@Service
public class Tb_club_statServiceImpl extends BaseServiceImpl<Tb_club_stat>  implements ITb_club_statService
{
	
	@Autowired
	private ITb_club_voteDao iTb_club_voteDao;

	@Override
	public int todayVoteCount(String openId,int picId){
		List list = iTb_club_voteDao.findByHQL("from Tb_club_vote o where DateDiff(dd,o.votetime,getdate())=0 AND o.openid = ? AND o.picid.id = ?",openId,picId);
		return list.size();
	}
}
