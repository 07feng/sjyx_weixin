package com.sunnet.org.vote.vo;

import com.sunnet.org.util.Constants;
import com.sunnet.org.util.DateStyle;
import com.sunnet.org.util.DateUtil;
import com.sunnet.org.vote.model.Tb_club_pic;

import javax.xml.crypto.Data;
import java.util.*;

public class PicList {

    public static List<Map> picListUtilObj(List<Object[]> list,Date startTime, Date endTime){
        Date now = DateUtil.getDate();
        boolean status = false;
        if (VoteTime.belongCalendar(now,startTime,endTime)){
            status = true;
        }

        List<Map> back = new ArrayList<>();
        for (Object[] objects: list) {
            Map map = new HashMap();
            map.put("id",objects[0]);
            map.put("picUrl",objects[1].toString()+Constants.DOC_PATH_END);
            map.put("authorName",objects[2]);
            map.put("picName",objects[3]);
            map.put("voteNum",objects[6]);
            if (status) {
                if (null == objects[9]){
                    map.put("num",0);
                }else {
                    map.put("num", objects[9]);
                }
            }else
                map.put("num",3);
            back.add(map);
        }
        return back;
    }

    public static List<Map> picListUtil(List<Tb_club_pic> list){
        List<Map> back = new ArrayList<>();
        for (Tb_club_pic tbClubPic: list) {
            Map map = new HashMap();
            if (null != tbClubPic.getId())
                map.put("id",tbClubPic.getId());
            else
                map.put("id","");

            if (null != tbClubPic.getPicurl())
                map.put("picUrl",tbClubPic.getPicurl()+ Constants.DOC_PATH_END);
            else
                map.put("picUrl","");

            if (null != tbClubPic.getAuthorname())
                map.put("authorName",tbClubPic.getAuthorname());
            else
                map.put("authorName","");

            if (null != tbClubPic.getPicname())
                map.put("picName",tbClubPic.getPicname());
            else
                map.put("picName","");

            if (null != tbClubPic.getVotenum())
                map.put("voteNum",tbClubPic.getVotenum());
            else
                map.put("voteNum","");

            back.add(map);
        }
        return back;
    }
}
