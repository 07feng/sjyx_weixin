package com.sunnet.org.scenicSpot.service.impl;

import com.sunnet.framework.service.impl.BaseServiceImpl;
import com.sunnet.org.doc.dao.ITre_friendscircleDao;
import com.sunnet.org.doc.service.ITre_friendscircleService;
import com.sunnet.org.filmfestival.dao.IFilmfestivalDao;
import com.sunnet.org.filmfestival.service.IFilmfestivalService;
import com.sunnet.org.information.dao.ITb_docDao;
import com.sunnet.org.information.service.ITb_docService;
import com.sunnet.org.scenicSpot.dao.ITb_ScenicSpotDao;
import com.sunnet.org.scenicSpot.dao.ITb_ScenicSpotFileDao;
import com.sunnet.org.scenicSpot.model.TB_ScenicSpot;
import com.sunnet.org.scenicSpot.model.TB_ScenicSpotFile;
import com.sunnet.org.scenicSpot.service.ITB_ScenicSpotService;
import com.sunnet.org.scenicSpot.vo.ScenicSoptUtil;
import com.sunnet.org.util.Constants;
import com.sunnet.org.util.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.*;

/**
 *
 * <br>
 * <b>功能：</b>TB_ScenicSpot Service<br>
 * <b>作者：</b>jinhao<br>
 * <b>日期：</b> 2018.6/20 <br>
 */
@Service
public class TB_ScenicSpotServiceImpl extends BaseServiceImpl<TB_ScenicSpot>  implements ITB_ScenicSpotService {
    @Autowired
    private ITb_ScenicSpotDao iTb_scenicSpotDao;
    @Autowired
    private ITb_docService ITb_docService;
    @Autowired
    private IFilmfestivalService IFilmfestivalService;
    @Autowired
    private ITre_friendscircleDao tre_friendscircleDao;
    @Autowired
    private ITre_friendscircleService iTre_friendscircleService;

    @Override
    @CachePut(value = "sunnet_flb_ehcache")
    public List<Object[]> tourismRecommend(){
        return iTb_scenicSpotDao.findBySql("SELECT * FROM(select id,CoverPicUrl,ScenicSpotName,ROW_NUMBER() over(order by Sort DESC,UpdateTime DESC)T from TB_ScenicSpot WHERE isClose =0)TT WHERE TT.T BETWEEN 1 AND 8");
    }

    @Override
    @CachePut(value = "sunnet_flb_ehcache")
    public List<Map> searchSpot(String areaId,String spotName){
        StringBuffer sb = new StringBuffer("SELECT * FROM(select *,ROW_NUMBER() OVER(order by ss.sort desc,ss.updateTime desc)T from TB_ScenicSpot as ss where 1=1");
        if (!"0".equals(areaId))
            sb.append(" and CityId =").append(areaId);
        if (!"0".equals(spotName))
            sb.append(" and ScenicSpotName like '%").append(spotName).append("%'");
        sb.append(")TT where TT.T BETWEEN 1 and 100");
        List<Object[]> data = iTb_scenicSpotDao.findBySql(sb.toString());
        List<Map> result = new ArrayList<>();
        if (data.size()>0){
            for (Object[] obj:data) {
                Map map = new HashMap();
                map.put("spotId",obj[0].toString());
                map.put("spotName",obj[2].toString());
                result.add(map);
            }
        }
        return result;
    }

    @Override
    @CachePut(value = "sunnet_flb_ehcache")
    public Map getSpotData(int spotId,String memberId){
        Map map = new HashMap();
        String sql = "SELECT ss.id AS ssid,ss.ScenicSpotName,ss.Intro,ss.CoverPicUrl,ss.PhotoNum,ss.ArticleNum,ss.VideoNum,ss.UpdateTime,a.id AS aid,a.AreaName,c.id AS cid,c.CityName,m.id,m.UsersName,m.HeadImg,l.LevelName FROM TB_ScenicSpot AS ss " +
                "LEFT JOIN Tb_member AS m ON ss.AdminMemberId = m.id " +
                "LEFT JOIN TB_Level AS l ON m.Levelid = l.id " +
                "LEFT JOIN TB_City AS c ON ss.CityId = c.id " +
                "LEFT JOIN TB_Area AS a ON c.AreaId = a.id " +
                "where ss.id = ? ";
        List<Object[]> temp = iTb_scenicSpotDao.findBySql(sql,spotId);
        if (temp.size()>0){
            Object[] obj = temp.get(0);
            map.put("spotId", obj[0].toString());
            map.put("image", obj[3].toString()+Constants.DOC_PATH_END2);
            map.put("spotDesc",obj[2].toString());
            map.put("spotAddress", obj[11].toString()+" - "+obj[1].toString());
            map.put("cityId", obj[10].toString());
            map.put("cityName", obj[11].toString());
            map.put("spotName", obj[1].toString());
            map.put("photoNum", obj[4].toString());
            map.put("articleNum", obj[5].toString());
            map.put("videoNum", obj[6].toString());
            if (null != obj[12]) {
                if ("0".equals(memberId))
                    map.put("isAuthor",0);
                else {
                    if (memberId.equals(obj[12].toString()))
                        map.put("isAuthor", 1);
                    else
                        map.put("isAuthor", 0);
                }
                map.put("author_id", obj[12].toString());
                map.put("author_name", obj[13].toString());
                map.put("author_portrait", obj[14].toString());
                map.put("rank", obj[15].toString());
            }else{
                map.put("isAuthor","0");
                map.put("author_id", "");
                map.put("author_name", "");
                map.put("headImg", "");
                map.put("rank", "");
            }
            //是否关注作者
            if (!"0".equals(memberId) && null != obj[12]){
                Boolean isFocus = iTre_friendscircleService.idFucos(memberId,obj[12].toString());
                if (isFocus)
                    map.put("isFollow", 1);
                else
                    map.put("isFollow", 0);
            }else
                map.put("isFollow", 0);
        }
        return map;
    }

    @Override
    @CachePut(value = "sunnet_flb_ehcache")
    public List getTourismImgList(int page,int spotId,String memberId){
        StringBuffer sqlDoc = new StringBuffer("SELECT * FROM(SELECT ss.id,c.id AS cid,c.CityName,ss.ScenicSpotName,ssf.FileType,ssf.FileId,ssf.id as ssfid,ssf.updateTime,ROW_NUMBER() ");
        if (spotId != 0) {
            sqlDoc.append("OVER(order by ssf.sort desc,ssf.updateTime desc)T FROM TB_ScenicSpotFile AS ssf LEFT JOIN TB_ScenicSpot AS ss ON ssf.ScenicSpotId = ss.id LEFT JOIN TB_City AS c ON ss.CityId = c.id where 1=1 ");
            sqlDoc.append(" and ss.id = ").append(spotId);
        }else
            sqlDoc.append("OVER(order by ssf.updateTime desc)T FROM TB_ScenicSpotFile AS ssf LEFT JOIN TB_ScenicSpot AS ss ON ssf.ScenicSpotId = ss.id LEFT JOIN TB_City AS c ON ss.CityId = c.id where 1=1  and ss.isClose =0");
        sqlDoc.append(")TT where TT.T BETWEEN ? and ?");
        List<Object[]> docList = iTb_scenicSpotDao.findBySql(sqlDoc.toString(),12*(page-1)+1,12*page);
        List result = new ArrayList();
        for (int i = 0; i<docList.size(); i++) {
            Object[] ob = docList.get(i);
            List<Object[]> temp = null;
            if (null != ob[4]){
                if ("1".equals(ob[4].toString())){   //图片或视频
                    temp = ITb_docService.findBySql("SELECT d.id,d.doctitle,d.filedescribe,d.filetype,d.phonethumbnailpathimg,d.filepath,d.fileviewcount,d.iheight,d.iwidht,d.filegoodcount,d.filecommentscount,convert(varchar(255),d.UploadTime) as deptName,m.id AS mid,m.usersname,m.headimg,l.levelname,d.IsDouble FROM TB_Doc AS d " +
                            "LEFT JOIN Tb_member AS m ON m.id = d.memberId LEFT JOIN TB_Level as l ON m.Levelid = l.id WHERE d.id =? and d.ispublic=1 and d.isdelete=0 and d.docstatus=1",ob[5].toString());
                    System.out.println("ob[5].toString()="+ob[5].toString()+"temp.size()="+temp.size());
                    if (temp.size()>0) {
                        Object[] d = temp.get(0);
                        Map map = new HashMap();
                        map.put("doc_id", d[0].toString());
                        if (null != d[1])
                            map.put("doc_title", d[1].toString());
                        else
                            map.put("doc_title", "");

                        if (null != d[2]) {
                            try {
                                map.put("describe", URLDecoder.decode(d[2].toString(), "utf-8"));
                            } catch (UnsupportedEncodingException e) {
                                e.printStackTrace();
                            }
                        }
                        else
                            map.put("describe", "");
                        map.put("fileType", d[3].toString());
                        if (1 == Integer.parseInt(d[3].toString()))
                            map.put("doc_path", d[4].toString() + Constants.DOC_PATH_END3);
                        if (0 == Integer.parseInt(d[3].toString()))
                            map.put("doc_path", d[5].toString() + Constants.DOC_PATH_END3);
                        if (null != d[6])
                            map.put("view_num", d[6].toString());
                        else
                            map.put("view_num", 0);
                        map.put("iheight", d[7].toString());
                        map.put("iwidth", d[8].toString());
                        if (null != d[9])
                            map.put("good_num", d[9].toString());
                        else
                            map.put("good_num", 0);
                        if (null != d[10])
                            map.put("comment_num", d[10].toString());
                        else
                            map.put("comment_num", 0);
                        //添加时间
                        if (null != d[7]) {
                            String diffDate = DateUtil.getDatePoor(DateUtil.getDate(), (Date) ob[7]);
                            map.put("upload_time", diffDate);
                        } else
                            map.put("upload_time", "");
                        if (null != d[12])
                            map.put("author_id", d[12].toString());
                        else
                            map.put("author_id", "");
                        if (null != d[12])
                            map.put("author_name", d[13].toString());
                        else
                            map.put("author_name", "");
                        if (null != d[14])
                            map.put("author_portrait", d[14].toString());
                        else
                            map.put("author_portrait", "");
                        if (null != d[15])
                            map.put("rank", d[15].toString());
                        else
                            map.put("rank", 1);
                        //是否关注作者
                        if ("0".equals(memberId))
                            map.put("isFollow", 0);
                        else {
                            Boolean isFocus = iTre_friendscircleService.idFucos(memberId,d[12].toString());
                            if (isFocus)
                                map.put("isFollow", 1);
                            else
                                map.put("isFollow", 0);
                        }
                        map.put("isDouble", d[16].toString());
                        map.put("address", ob[2].toString() + " - " + ob[3].toString());
                        map.put("spotId", ob[0].toString());
                        map.put("cityId", ob[1].toString());
                        map.put("cityName", ob[2].toString());
                        map.put("spotName", ob[3].toString());
                        map.put("spotFileId",ob[6].toString());
                        result.add(map);
                    }
                }
                if ("2".equals(ob[4].toString())) {   //影集
                    List<Object[]> list = IFilmfestivalService.findBySql("SELECT ffv.id,ffv.Sort,ffv.FileGoodCount,ffv.FileCommentScount,ffv.titel,ffv.Time_length,ffv.viewnum,m.Id AS mid,m.UsersName,m.HeadImg,m.Levelid,ffv.Open_time,ffv.titelnote,ffv.showtypeid FROM FilmfestivalVIP AS ffv LEFT JOIN Tb_member AS m ON ffv.Member_id = m.Id WHERE ffv.id=? ", ob[5].toString());
                    if (list.size() > 0) {
                        temp = IFilmfestivalService.festivalCover(ob[5].toString());
                        System.out.println("影集.size()="+ob[5].toString()+"----------"+temp.size());
                        if (temp.size() > 0 && temp.get(0).length > 0) {
                            Object[] ob1 = list.get(0);
                            Object[] ob2 = temp.get(0);
                            Map map = new HashMap();
                            map.put("doc_id", ob1[0].toString());
                            if (null != ob1[4]) {
                                try {
                                    map.put("doc_title", URLDecoder.decode(ob1[4].toString(), "utf-8"));
                                } catch (UnsupportedEncodingException e) {
                                    e.printStackTrace();
                                }
                            }
                            else
                                map.put("doc_title", "");

                            if (null != ob1[12]) {
                                try {
                                    map.put("describe", URLDecoder.decode(ob1[12].toString(), "utf-8"));
                                } catch (UnsupportedEncodingException e) {
                                    e.printStackTrace();
                                }
                            }
                            else
                                map.put("describe", "");
                            map.put("fileType", "0");
                            map.put("doc_path", ob2[1].toString() + Constants.DOC_PATH_END3);
                            if (null != ob1[6])
                                map.put("view_num", ob1[6].toString());
                            else
                                map.put("view_num", 0);
                            map.put("iheight", ob2[3].toString());
                            map.put("iwidth", ob2[3].toString());
                            if (null != ob1[2])
                                map.put("good_num", ob1[2].toString());
                            else
                                map.put("good_num", 0);
                            if (null != ob1[3])
                                map.put("comment_num", ob1[3].toString());
                            else
                                map.put("comment_num", 0);
                            //添加时间
                            if (null != ob[7]) {
                                String diffDate = DateUtil.getDatePoor(DateUtil.getDate(), (Date) ob[7]);
                                map.put("upload_time", diffDate);
                            } else
                                map.put("upload_time", "");
                            if (null != ob1[7])
                                map.put("author_id", ob1[7].toString());
                            else
                                map.put("author_id", "");
                            if (null != ob1[8])
                                map.put("author_name", ob1[8].toString());
                            else
                                map.put("author_name", "");
                            if (null != ob1[9])
                                map.put("author_portrait", ob1[9].toString());
                            else
                                map.put("author_portrait", "");
                            if (null != ob1[10])
                                map.put("rank", ob1[10].toString());
                            else
                                map.put("rank", 1);
                            //是否关注作者
                            if ("0".equals(memberId))
                                map.put("isFollow", 0);
                            else {
                                Boolean isFocus = iTre_friendscircleService.idFucos(memberId,ob1[7].toString());
                                if (isFocus)
                                    map.put("isFollow", 1);
                                else
                                    map.put("isFollow", 0);
                            }
                            map.put("isDouble", "2");
                            map.put("address", ob[2].toString() + " - " + ob[3].toString());
                            map.put("spotId", ob[0].toString());
                            map.put("cityId", ob[1].toString());
                            map.put("cityName", ob[2].toString());
                            map.put("spotName", ob[3].toString());
                            map.put("spotFileId",ob[6].toString());
                            result.add(map);
                        }
                    }
                }
            }
        }
        return result;
    }

    @Override
    @CachePut(value = "sunnet_flb_ehcache")
    public List getTourismList(int page,int cityId,String memberId){
        StringBuffer sb = new StringBuffer("Select * from (SELECT ss.id AS ssid,ss.ScenicSpotName,ss.Intro,ss.CoverPicUrl,ss.PhotoNum,ss.ArticleNum,ss.VideoNum,ss.UpdateTime,a.id AS aid,a.AreaName,c.id AS cid,c.CityName,m.id,m.UsersName,m.HeadImg,l.LevelName,ROW_NUMBER() OVER(order by ss.sort desc,ss.updateTime desc)T FROM TB_ScenicSpot AS ss " +
                "LEFT JOIN Tb_member AS m ON ss.AdminMemberId = m.id " +
                "LEFT JOIN TB_Level AS l ON m.Levelid = l.id " +
                "LEFT JOIN TB_City AS c ON ss.CityId = c.id " +
                "LEFT JOIN TB_Area AS a ON c.AreaId = a.id " +
                "where 1=1 and ss.isClose = 0 ");
        if (0 != cityId)
            sb.append("AND cityId=").append(cityId);
        sb.append(")TT where TT.T between ? and ?");
        List<Object[]> list = iTb_scenicSpotDao.findBySql(sb.toString(),16*(page-1),16*page);
        List<Map> result = new ArrayList<>();
        for (int i =0; i<list.size(); i++){
            Object[] obj = list.get(i);
            Map map = new HashMap();
            map.put("spotId", obj[0].toString());
            map.put("image", obj[3].toString()+Constants.DOC_PATH_END2);
            map.put("address", obj[11].toString()+" - "+obj[1].toString());
            map.put("cityId", obj[10].toString());
            map.put("cityName", obj[11].toString());
            map.put("spotName", obj[1].toString());
            map.put("photoNum", obj[4].toString());
            map.put("articleNum", obj[5].toString());
            map.put("videoNum", obj[6].toString());
            if (null != obj[12]) {
                if (memberId.equals(obj[12].toString()))
                    map.put("isAuthor", 1);
                else
                    map.put("isAuthor", 0);
                map.put("author_id", obj[12].toString());
                map.put("author_name", obj[13].toString());
                map.put("headImg", obj[14].toString());
                map.put("rank", obj[15].toString());
            }else{
                map.put("isAuthor", 0);
                map.put("author_id", "");
                map.put("author_name", "");
                map.put("headImg", "");
                map.put("rank", "");
            }
            result.add(map);
        }
        return result;
    }

    @Override
    public Object[] spotInfo(int spotId){
        List<Object[]> temp = iTb_scenicSpotDao.findBySql("SELECT AdminMemberId,PhotoNum,ArticleNum,VideoNum,Intro,CoverPicUrl FROM TB_ScenicSpot WHERE id =?",spotId);
        return temp.get(0);
    }

    @Override
    public void updateSpotInfo(String paraSql, Object... values){
        StringBuffer sb = new StringBuffer("update TB_ScenicSpot set ");
        sb.append(paraSql).append(" where id = ?");
        iTb_scenicSpotDao.updateSql(sb.toString(),values);
    }

    @Override
    public void updateSpotFileTop(String spotId,String spotFileId){
        iTb_scenicSpotDao.updateSql("UPDATE TB_ScenicSpotFile SET sort = 0 WHERE ScenicSpotId = ? AND Sort = 1",spotId);
        iTb_scenicSpotDao.updateSql("UPDATE TB_ScenicSpotFile SET sort = 1 WHERE id = ?",spotFileId);
    }

    @Override
    public Map<String,Integer> adminSpotStatus(int spotId,String memberId){
        Map<String,Integer> map = new HashMap();
        int fileNum = iTb_scenicSpotDao.getAllNum("SELECT count(*) FROM TB_ScenicSpotFile AS ssf LEFT JOIN TB_Doc AS d ON ssf.fileId = d.id WHERE ssf.fileType = 1 and d.IsDelete =0 and d.memberid = ? and ssf.ScenicSpotId = ?",memberId,spotId);
        int filmNum = iTb_scenicSpotDao.getAllNum("SELECT count(*) FROM TB_ScenicSpotFile AS ssf LEFT JOIN FilmfestivalVIP AS ffv ON ssf.fileId = ffv.id WHERE ssf.fileType = 2 and ffv.Time_length>0 and ffv.Member_id = ? and ssf.ScenicSpotId = ?",memberId,spotId);
        map.put("fileNum",fileNum);
        map.put("filmNum",filmNum);
        int adminNum = iTb_scenicSpotDao.getAllNum("SELECT count(*) FROM TB_ScenicSpot WHERE AdminMemberId=?",memberId);
        int status;
        if (adminNum>=5)
            status = 3;
        else {
            if (fileNum >= 5 && filmNum >=2)
                status = 2;
            else
                status =1;
        }
        map.put("status",status);
        return map;
    }

    @Autowired
    private ITb_ScenicSpotFileDao iTb_scenicSpotFileDao;

    @Override
    public void saveSpotFile(TB_ScenicSpotFile ssf){
        iTb_scenicSpotFileDao.save(ssf);
    }
}
