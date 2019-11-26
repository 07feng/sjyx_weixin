package com.sunnet.org.vote.quartz;

import com.sunnet.framework.controller.BaseController;
import com.sunnet.org.competition.model.Tb_contest;
import com.sunnet.org.competition.service.ITb_contestService;
import com.sunnet.org.competition.service.ITre_doccontestService;
import com.sunnet.org.util.DateStyle;
import com.sunnet.org.util.DateUtil;
import com.sunnet.org.vote.service.ITb_club_statService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class voteStatTimer extends BaseController {

    @Autowired
    private ITb_club_statService iTb_club_statService;
    @Autowired
    private ITb_contestService iTb_contestService;
    @Autowired
    private ITre_doccontestService iTre_doccontestService;

//    @Scheduled(cron = "0 0 1 * * ?")
//    public void statQuartz(){
//        log.info("定时器开启---删除上一天投票统计数据。");
//        try {
//            iTb_club_statService.delete("DateDiff(dd,updatetime,getdate()) >0");
//        }catch (Exception e){
//            e.getMessage();
//            log.error("定时删除上一天投票统计数据----操作失败！");
//            return;
//        }
//        log.info("定时删除上一天投票统计数据---成功。");
//    }

//    @Scheduled(cron = "59 59 23 26 2 *")
//    public void contestEndQuartz(){
//        log.info("定时器开启---修改赛事状态设置入围作品。");
//        try {
//            iTb_contestService.updateHql("update Tb_contest c SET c.conteststatus =1 WHERE c.id = 51");
//            iTre_doccontestService.updateSql2("UPDATE TRE_DocContest set is_shortlisted = 1 FROM TB_GroupDocGood AS gdg LEFT JOIN TB_Doc AS d ON  gdg.doc_id = d.id LEFT JOIN TRE_DocContest AS dc ON  dc.doc_id = d.id WHERE gdg.contest_id = 51");
//        }catch (Exception e){
//            e.getMessage();
//            log.error("定时器开启---修改赛事状态设置入围作品。");
//            return;
//        }
//        log.info("定时器开启---修改赛事状态设置入围作品。");
//    }
}
