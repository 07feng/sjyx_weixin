package com.sunnet.org.vote.service.impl;

import com.sunnet.framework.pagenation.PageBean;
import com.sunnet.framework.pagenation.QueryResult;
import com.sunnet.framework.service.impl.BaseServiceImpl;
import com.sunnet.org.space.dao.ITb_spaceDao;
import com.sunnet.org.space.model.Tb_space;
import com.sunnet.org.space.service.ITb_spaceService;
import com.sunnet.org.space.vo.Tb_spaceUtil;
import com.sunnet.org.util.Constants;
import com.sunnet.org.util.DateUtil;
import com.sunnet.org.util.MyBeanUtils;
import com.sunnet.org.util.StringUtils;
import com.sunnet.org.vote.dao.ITb_club_picDao;
import com.sunnet.org.vote.dao.ITb_club_statDao;
import com.sunnet.org.vote.dao.ITb_club_voteDao;
import com.sunnet.org.vote.model.Tb_club_pic;
import com.sunnet.org.vote.model.Tb_club_stat;
import com.sunnet.org.vote.model.Tb_club_vote;
import com.sunnet.org.vote.service.ITb_club_picService;
import com.sunnet.org.vote.vo.PicList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * 
 * <br>
 * <b>功能：</b>Tb_space Service<br>
 * <b>作者：</b>jing<br>
 * <b>日期：</b> 2016/12/1 <br>
 */
@Service
public class Tb_club_picServiceImpl extends BaseServiceImpl<Tb_club_pic>  implements ITb_club_picService
{
	
	@Autowired
	private ITb_club_picDao iTb_club_picDao;
	@Autowired
	private ITb_club_voteDao iTb_club_voteDao;
	@Autowired
    private ITb_club_statDao iTb_club_statDao;

	@Override
	public List<Map> picList2(String type, String openId, Date startDate, Date endDate){
        StringBuffer sb = new StringBuffer("SELECT p.*,s.VoteNum AS num FROM tb_club_pic AS p LEFT JOIN (SELECT * FROM tb_club_stat WHERE DateDiff(dd,UpdateTime,getdate())=0 AND OpenId = ?)s ON p.id =s.PicId order by ");
		if ("2".equals(type)){  //编号排序
		    sb.append("p.id asc");
        }
        if ("3".equals(type)){  //票数排序
            sb.append("p.votenum desc");
        }
        if ("1".equals(type)) {
            Random random = new Random();
            int number = random.nextInt(2);
            if ( number == 0 ){         //随机排序
                sb.append("p.updatetime  desc");
            }else {     //最新投票排序
                sb = new StringBuffer("SELECT top 300 p.*,s.VoteNum AS num,newid() AS temp FROM tb_club_pic AS p LEFT JOIN (SELECT * FROM tb_club_stat WHERE DateDiff(dd,UpdateTime,getdate())=0 AND OpenId = ?)s ON p.id =s.PicId  ORDER BY temp");
            }
        }
        List<Object[]> picList = iTb_club_picDao.findBySql(sb.toString(),openId);
        if (null != picList){
            return PicList.picListUtilObj(picList,startDate,endDate);
        }else
            return null;
	}

    @Override
    @CachePut(value = "sunnet_flb_ehcache")
    public List<Object[]> getVoteInfo(int id) {
        return iTb_club_picDao.findBySql("select ContestStartTime,ContestEndTime,EditTime from Tb_contest where id = ?",id);
    }

//    @Override
//    public List<Map> picList(String type){
//        if ("1".equals(type)) {
//            Random random = new Random();
//            int number = random.nextInt(2);
//            if ( number == 0 ){         //随机排序
//                List<Object[]> back = iTb_club_picDao.findBySql("select top 300 id,PicUrl,AuthorName,PicName,VoteNum,newid() AS Random from tb_club_pic order by Random ");
//                if (null != back){
//                    return PicList.picListUtilObj(back);
//                }else
//                    return null;
//            }else {     //最新投票排序
//                List<Tb_club_pic> picList = iTb_club_picDao.findByHQL("from Tb_club_pic o order by o.updatetime desc");
//                if (null != picList){
//                    return PicList.picListUtil(picList);
//                }else
//                    return null;
//            }
//
//        }
//        StringBuffer sb = new StringBuffer("from Tb_club_pic o order by ");
//        if ("2".equals(type)){  //编号排序
//            sb.append("o.id asc");
//        }
//        if ("3".equals(type)){  //票数排序
//            sb.append("o.votenum desc");
//        }
//        List<Tb_club_pic> picList = iTb_club_picDao.findByHQL(sb.toString());
//        if (null != picList){
//            return PicList.picListUtil(picList);
//        }else
//            return null;
//    }

	@Override
	@Transactional
	public int votePic(Tb_club_vote tbClubVote){
		List<Tb_club_stat> temp = iTb_club_statDao.findByHQL("from Tb_club_stat o where DateDiff(dd,o.updatetime,getdate())=0 and o.picid = ? and o.openid = ?",tbClubVote.getPicid().getId(),tbClubVote.getOpenid());
        Tb_club_stat tb_club_stat;
        if (null != temp && temp.size() > 0){
            tb_club_stat = temp.get(0);
            if (tb_club_stat.getVotenum() < Constants.MAXVOTENUM) {
                int t = tb_club_stat.getVotenum()+1;
                iTb_club_statDao.updateSql("update tb_club_stat set votenum = ?,updatetime = GETDATE() where id = ?", t, tb_club_stat.getId());
            }else {
                return 0;   //今天对本作品的投票次数达到上限
            }
        }else { //添加一条投票统计
            tb_club_stat = new Tb_club_stat();
            tb_club_stat.setUpdatetime(DateUtil.getDate());
            tb_club_stat.setVotenum(1);
            tb_club_stat.setOpenid(tbClubVote.getOpenid());
            tb_club_stat.setPicid(tbClubVote.getPicid().getId());
            iTb_club_statDao.save(tb_club_stat);
        }
		iTb_club_voteDao.save(tbClubVote);

        int [] arr={1,18,23,24,182,183,227,287};
        boolean isThand=true;
        for(int i=0;i<arr.length;i++){
            if(tbClubVote.getPicid().getId()==arr[i]) {
                isThand=false;
            }
        }
        String sql="UPDATE [dbo].[tb_club_pic] SET ";
         if(isThand){
             sql+=" UpdateTime = GETDATE(),";
         }
            sql+="VoteNum = VoteNum+1 WHERE id = ?";
        iTb_club_picDao.updateSql(sql,tbClubVote.getPicid().getId());
        return 1;
    }

}
