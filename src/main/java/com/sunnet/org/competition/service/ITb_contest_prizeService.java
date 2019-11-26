package com.sunnet.org.competition.service;

import com.sunnet.framework.service.IBaseService;
import com.sunnet.org.competition.model.Tb_contest_prize;

import java.util.List;

public interface ITb_contest_prizeService extends IBaseService<Tb_contest_prize> {

    public List<Tb_contest_prize> getByContestId(String contestId);
}
