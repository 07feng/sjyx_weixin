package com.sunnet.org.competition.service.impl;

import com.sunnet.framework.service.impl.BaseServiceImpl;
import com.sunnet.org.competition.dao.ITb_contest_prizeDao;
import com.sunnet.org.competition.model.Tb_contest_prize;
import com.sunnet.org.competition.service.ITb_contest_prizeService;
import com.sunnet.org.information.model.Tb_doc;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @ClassName: ITb_contest_prizeServiceImpl
 * @Description:
 * @Author: Bryce
 * @Date 2019/10/24 0024下午 15:12
 * @Version 1.0
 */
@Service
public class ITb_contest_prizeServiceImpl extends BaseServiceImpl<Tb_contest_prize> implements ITb_contest_prizeService {

    @Autowired
    private ITb_contest_prizeDao iTb_contest_prizeDao;

    @Override
    public List<Tb_contest_prize> getByContestId(String contestId) {
        String hql= "from Tb_contest_prize as d where d.contestid =?";
        List<Tb_contest_prize> list = iTb_contest_prizeDao.findByHQL(hql, contestId);
        return list;
    }
}
