package com.sunnet.org.filmfestival.service.impl;

import java.net.URLEncoder;
import java.time.temporal.Temporal;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.sunnet.org.app.entity.AddFilm;
import com.sunnet.org.cover.model.TbFilmfestivalvipMusic;
import com.sunnet.org.filmfestival.dao.IFilmfestivalvipDao;
import com.sunnet.org.filmfestival.dao.IFilmfestivalvipopentimeDao;
import com.sunnet.org.filmfestival.model.Filmfestivalvip;
import com.sunnet.org.information.dao.ITb_docDao;
import com.sunnet.org.information.model.Tb_doc;
import com.sunnet.org.member.dao.ITb_memberDao;
import com.sunnet.org.member.dao.ITb_membermessagesteupDao;
import com.sunnet.org.member.model.Tb_member;
import com.sunnet.org.member.model.Tb_membermessagesteup;
import com.sunnet.org.scenicSpot.dao.ITb_ScenicSpotDao;
import com.sunnet.org.scenicSpot.dao.ITb_ScenicSpotFileDao;
import com.sunnet.org.scenicSpot.model.TB_ScenicSpot;
import com.sunnet.org.scenicSpot.model.TB_ScenicSpotFile;
import com.sunnet.org.util.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sunnet.framework.pagenation.PageBean;
import com.sunnet.framework.pagenation.QueryResult;
import com.sunnet.framework.service.impl.BaseServiceImpl;
import com.sunnet.org.util.MyBeanUtils;
import com.sunnet.org.util.StringUtils;
import com.sunnet.org.filmfestival.dao.IFilmfestivalDao;
import com.sunnet.org.filmfestival.model.Filmfestival;
import com.sunnet.org.filmfestival.service.IFilmfestivalService;
import com.sunnet.org.filmfestival.vo.FilmfestivalUtil;

/**
 * 
 * <br>
 * <b>功能：</b>Filmfestival Service<br>
 * <b>作者：</b>jing<br>
 * <b>日期：</b> 2016/12/1 <br>
 */
@Service
public class FilmfestivalServiceImpl extends BaseServiceImpl<Filmfestival>  implements IFilmfestivalService
{
	
	@Autowired
	private IFilmfestivalDao filmfestivalDao;
	@Autowired
    private IFilmfestivalvipDao iFilmfestivalvipDao;
	@Autowired
    private IFilmfestivalvipopentimeDao iFilmfestivalvipopentimeDao;
	@Autowired
    private ITb_docDao iTb_docDao;
	@Autowired
    private ITb_membermessagesteupDao iTb_membermessagesteupDao;
	@Autowired
    private ITb_memberDao iTb_memberDao;
	@Autowired
	private ITb_memberDao tb_memberDao;
	@Autowired
	private ITb_ScenicSpotDao iTb_scenicSpotDao;
	@Autowired
	private ITb_ScenicSpotFileDao iTb_scenicSpotFileDao;

	@Override
	public QueryResult<Filmfestival> list(PageBean pageBean, HttpServletRequest request) {
		StringBuffer str = new StringBuffer();
		str.append(" from Filmfestival o where 1=1 "); //初始化语句
		if (StringUtils.isStringNull(request.getParameter("vip_titel"))) {
			str.append(" and o.vip_id.titel  like '%" + request.getParameter("vip_titel") + "%' ");
		}
		if (StringUtils.isStringNull(request.getParameter("vip_id"))) {
			str.append(" and o.vip_id.id  = '" + request.getParameter("vip_id") + "' ");
		}
		//查询是否是草稿箱
		if (StringUtils.isStringNull(request.getParameter("ispayto"))) {
			if(request.getParameter("ispayto")=="0" || "0".equals(request.getParameter("ispayto"))){
				//草稿箱
				str.append(" and o.vip_id.time_length  = '" + request.getParameter("ispayto") + "' ");
			}else{
				//已发布的影展
				str.append(" and o.vip_id.time_length  >= '" + request.getParameter("ispayto") + "' ");
			}
			
		}
		if (StringUtils.isStringNull(request.getParameter("doc_id"))) {
			str.append(" and o.doc_id.doctitle  like '%" + request.getParameter("doc_id") + "%' ");
		}//
		if (StringUtils.isStringNull(request.getParameter("usersname"))) {
			str.append(" and o.member_id.usersname  like '%" + request.getParameter("usersname") + "%' ");
		}
		if (StringUtils.isStringNull(request.getParameter("startDate_01"))) {
			str.append(" and o.vip_id.open_time >= '" + request.getParameter("startDate_01") + "'");
		}
		if (StringUtils.isStringNull(request.getParameter("endDate_01"))) {
			str.append(" and o.vip_id.open_time <= '"+request.getParameter("endDate_01")+"'"); 
		}
		pageBean.setHql(str.toString());
		int totalRecord = filmfestivalDao.findByHQLCount(Filmfestival.class, pageBean);
		
		String sort="";
		if (StringUtils.isStringNull(request.getParameter("vip_id"))) {
			sort=" order by o.sort ";
		}else{
			sort=" order by o.sort,o.doc_id.uploadtime desc ";
		}
		str.append(sort);
		pageBean.setHql(str.toString());
		List<Filmfestival> list = filmfestivalDao.findByHQLPage(Filmfestival.class, pageBean);
		QueryResult<Filmfestival> result = new QueryResult<Filmfestival>();
		result.setResultList(FilmfestivalUtil.getControllerList(list));
		pageBean.setTotalRecord(totalRecord);
		pageBean.setHql("");
		pageBean.setSql("");
		result.setPageBean(pageBean);
		return result;
	}

	@Override
	public void update(Filmfestival filmfestival) {
		Filmfestival filmfestival2 = filmfestivalDao.findByPrimaryKey(Filmfestival.class,
				filmfestival.getId());
		try {
			MyBeanUtils.copyBeanNotNull2Bean(filmfestival, filmfestival2);
		} catch (Exception e) {
			e.printStackTrace();
		}
		filmfestivalDao.update(filmfestival2);
	}

	@Override
	public List<Object[]> festivalList(String memberId,int startRow,int endRow,String code){
		StringBuffer sql = new StringBuffer("SELECT * FROM (SELECT ffv.id,ffv.Sort,ffv.FileGoodCount,ffv.FileCommentScount,ffv.titel,ffv.Time_length,ffv.viewnum,m.Id AS mid,m.UsersName,m.HeadImg,l.levelName,ffv.Open_time,ffv.showtypeid,ROW_NUMBER() over(order by ffv.Sort DESC,ffv.Open_time DESC)T FROM FilmfestivalVIP AS ffv LEFT JOIN Tb_member AS m ON ffv.Member_id = m.Id LEFT JOIN TB_Level as l ON m.Levelid = l.id WHERE 1=1 ");
		if (null != memberId){
			sql.append("and ffv.Member_id ='").append(memberId).append("' ");
		}else
			sql.append("and ffv.Time_length<>0 ");
//		if ("1".equals(code))
//            sql.append("and (ffv.showtypeid is NULL OR ffv.showtypeid = 0)");
//        else
//			sql.append("and ffv.showtypeid = 1");

		sql.append(")TT WHERE TT.T BETWEEN ? AND ?");
		return filmfestivalDao.findBySql(sql.toString(),startRow,endRow);
	}

    /**
     * 我的影展列表
     *
     * @param memberId
     * @param startRow
     * @param endRow
     * @param code
     * @return
     */
    @Override
    public List<Object[]> festivalListForMine(String memberId, int startRow, int endRow, String code) {
        StringBuffer sql = new StringBuffer("SELECT * FROM (SELECT ffv.id,ffv.Sort,ffv.FileGoodCount,ffv.FileCommentScount,ffv.titel,ffv.Time_length,ffv.viewnum,m.Id AS mid,m.UsersName,m.HeadImg,l.levelName,ffv.Open_time,ffv.showtypeid,ROW_NUMBER() over(order by ffv.Open_time DESC)T FROM FilmfestivalVIP AS ffv LEFT JOIN Tb_member AS m ON ffv.Member_id = m.Id LEFT JOIN TB_Level as l ON m.Levelid = l.id WHERE 1=1 ");
        if (null != memberId){
            sql.append("and ffv.Member_id ='").append(memberId).append("' ");
        }else
            sql.append("and ffv.Time_length<>0 ");
        if ("1".equals(code))
            sql.append("and (ffv.showtypeid is NULL OR ffv.showtypeid = 0)");
        else
            sql.append("and ffv.showtypeid = 1");

        sql.append(")TT WHERE TT.T BETWEEN ? AND ?");
        return filmfestivalDao.findBySql(sql.toString(),startRow,endRow);
    }


    /**
     * 影展数量
     *
     * @param memberId
     * @return
     */
    @Override
    public int festivalCount(String memberId) {
        StringBuffer sql = new StringBuffer("SELECT count(1) FROM FilmfestivalVIP AS ffv LEFT JOIN Tb_member AS m ON ffv.Member_id = m.Id LEFT JOIN TB_Level as l ON m.Levelid = l.id WHERE 1=1");
        if (null != memberId){
            sql.append("and ffv.Member_id ='").append(memberId).append("' ");
        }else{
            sql.append("and ffv.Time_length<>0 ");
        }
        return filmfestivalDao.findConutBySql(sql.toString());
    }

    @Override
	public List<Object[]> recommendFestivalList(int startRow,int endRow,String code){
		StringBuffer sql = new StringBuffer("SELECT * FROM (SELECT ffv.id,ffv.titel,ROW_NUMBER() over(order by ffv.recommend DESC,ffv.Open_time DESC)T FROM FilmfestivalVIP AS ffv LEFT JOIN Tb_member AS m ON ffv.Member_id = m.Id WHERE 1=1 and ffv.Time_length<>0 ");
		if ("1".equals(code))
			sql.append("and (ffv.showtypeid is NULL OR ffv.showtypeid = 0)");
		else
			sql.append("and ffv.showtypeid = 1");
		sql.append(")TT WHERE TT.T BETWEEN ? AND ?");
		return filmfestivalDao.findBySql(sql.toString(),startRow,endRow);
	}

	@Override
    public List<Object[]> festivalCover(String worksShowId){
        String sql = "SELECT * FROM (SELECT ff.Sort,d.FilePath,d.iwidht,d.iheight,ROW_NUMBER() over(order by ff.Sort ASC)T FROM Filmfestival AS ff LEFT JOIN TB_Doc AS d ON ff.Doc_id = d.id WHERE d.DocStatus =1 AND vip_id =?)TT WHERE TT.T BETWEEN 1 AND 1";
        return filmfestivalDao.findBySql(sql,Integer.parseInt(worksShowId));
    }

	@Override
	public  List<Object[]> festivalDetail(String vip_id){
		String sql = "SELECT ffv.Titel,ffv.Open_time,ffv.FileGoodCount,ffv.FileCommentScount,m.id,m.UsersName,m.HeadImg,d.id AS did,d.filePath,d.FileDescribe,d.iwidht,d.iheight,ffv.filepaycount,ffv.titelnote,ff.Sort,ff.id as fid,ffv.viewnum,l.levelname,tm.id AS tmid,tm.musicname,tm.musicurl,ffv.showtypeid,ffv.recommend,ffv.coverurl FROM Filmfestival AS ff LEFT JOIN TB_Doc AS d ON ff.Doc_id = d.Id LEFT JOIN FilmfestivalVIP AS ffv ON  ffv.Id = ff.vip_id LEFT JOIN Tb_member m ON ffv.Member_id = m.Id LEFT JOIN TB_Level as l ON m.Levelid = l.id LEFT JOIN tb_filmfestivalvip_music AS tm ON ffv.musicid=tm.id WHERE ff.vip_id =? and d.docstatus=1 ORDER BY ff.Sort,d.UploadTime";
		return filmfestivalDao.findBySql(sql,Integer.parseInt(vip_id));
	}

	@Override
	public void updateFilm(List<AddFilm> list,Tb_doc tb_doc,int vip_id,int count,String memberId,String worksDesc,String worksTitle,String space,String isPublic,String musicid,String code,String coverurl){
	    try {
            Tb_member mem = new Tb_member();
            mem.setId(memberId);
            String[] jinyong = null;
            //过滤敏感词
            List<Tb_membermessagesteup> liuyan = iTb_membermessagesteupDao.findByHQL("from Tb_membermessagesteup");
            if (liuyan != null && liuyan.size() > 0) {
                jinyong = liuyan.get(0).getDisablewords().split("[,，]");
            }
            Filmfestivalvip ffv = new Filmfestivalvip();
            ffv.setId(vip_id);
            int temp = 0;	//本次影展上传图片次数
            if (-1==count || 1==count){	//修改的影展作品已在云库中,没有上传的作品   或 修改时-第一次上传作品
                for (String s : jinyong) {		//过滤敏感词 影展标题，描述
                    if (null != worksTitle) {
                        worksTitle = worksTitle.replaceAll(s, liuyan.get(0).getReplacewords());
                    }
                    if (null != worksDesc) {
                        worksDesc = worksDesc.replaceAll(s, liuyan.get(0).getReplacewords());
                    }
                }
                //修改影集描述
                StringBuffer str = new StringBuffer("update FilmfestivalVIP set open_time=GETDATE(),titel=?,titelnote=?,coverurl=?");
                if ("0".equals(isPublic)) {
                    str.append(",Time_length =0");
                    if (null == musicid) {
                        str.append(",musicid=0");
                    } else if ("0".equals(musicid)) {
                        str.append(",musicid=0");
                    } else
                        str.append(",musicid=").append(Integer.parseInt(musicid));
                } else {
                    //查询影展会员套餐信息
                    List<Object[]> ffvOpenTimes = iFilmfestivalvipopentimeDao.findBySql("select * from Filmfestivalvipopentime where id =?", 14);
                    str.append(",Time_length =").append(Integer.parseInt(ffvOpenTimes.get(0)[2].toString()));
                    if (null == musicid )
                        str.append(",musicid=0");
                    else if("0".equals(musicid)) {
                        str.append(",musicid=0");
                    }else
                        str.append(",musicid=").append(Integer.parseInt(musicid));
                }

                //游记，普通影集
                if ("1".equals(code))
                    str.append(",showtypeid = 0");
                if ("2".equals(code))
                    str.append(",showtypeid = 1");
                //str.append(",coverurl=").append(coverurl.toString());
                str.append(" where id=?");
                iFilmfestivalvipDao.updateSql(str.toString(), worksTitle, URLEncoder.encode(worksDesc,"utf-8"),coverurl, vip_id);
                //删除原来影展作品信息
                filmfestivalDao.delete(Filmfestival.class, "vip_id =" + vip_id);
                for (int i = 0; i < list.size(); i++) {
                    Filmfestival ff = new Filmfestival();	//添加影展作品信息
                    String des = list.get(i).getDesc();
                    //过滤敏感词
                    if (null != jinyong) {
                        for (String s : jinyong) {
                            if (null != des || "".equals(des)) {
                                des = des.replaceAll(s, liuyan.get(0).getReplacewords());
                            }
                        }
                    }
                    if ("0".equals(list.get(i).getId())) {
                        temp++;
                        if (temp == count) {		//如果有上传的作品，添加到数据库
                            //修改云库容量
                            tb_memberDao.updateSql("update Tb_member set RemainingCapacity = ? where id = ?", space, memberId);
                            tb_doc.setFiledescribe(URLEncoder.encode(des,"utf-8"));  //这次上传作品的描述
                            iTb_docDao.save(tb_doc);
                            ff.setDoc_id(tb_doc);
                            ff.setMember_id(mem);
                            ff.setSort(i);
                            ff.setVip_id(ffv);
    //            			ff.setId(maxIdff+i);
                            filmfestivalDao.save(ff);   //添加影展作品
                        }
                    }else {
                        //修改作品描述、类型
                        iTb_docDao.updateSql("update Tb_doc set FileDescribe =? where id =?", URLEncoder.encode(list.get(i).getDesc(),"utf-8"), list.get(i).getId());
                        Tb_doc d = new Tb_doc();
                        d.setId(list.get(i).getId());
                        ff.setDoc_id(d);
                        ff.setMember_id(mem);
                        ff.setSort(i);
                        ff.setVip_id(ffv);
    //            		ff.setId(maxIdff+i);
                        filmfestivalDao.save(ff);   //添加影展作品
                    }
                }
            }else {			//第count上传图片，只添加这张图的信息
                for (int i = 0; i < list.size(); i++) {
                    if ("0".equals(list.get(i).getId())) {
                        temp++;
                        if (temp == count) {		//如果有上传的作品，添加到数据库
                            Filmfestival ff = new Filmfestival();	//添加影展作品信息
                            String des = list.get(i).getDesc();
                            //过滤敏感词
                            if (null != jinyong) {
                                for (String s : jinyong) {
                                    if (null != des || "".equals(des)) {
                                        des = des.replaceAll(s, liuyan.get(0).getReplacewords());
                                    }
                                }
                            }
                            tb_memberDao.updateSql("update Tb_member set RemainingCapacity = ? where id = ?", space, memberId);
                            tb_doc.setFiledescribe(URLEncoder.encode(des,"utf-8"));  //这次上传作品的描述
                            iTb_docDao.save(tb_doc);
                            ff.setDoc_id(tb_doc);
                            ff.setMember_id(mem);
                            ff.setSort(i);
                            ff.setVip_id(ffv);
    //            			ff.setId(maxIdff+i);
                            filmfestivalDao.save(ff);   //添加影展作品
                        }
                    }
                }//第count次上传作品
            }
        }catch (Exception e){
            e.printStackTrace();;
        }
	}



    @Override
	public int isGood(String vip_id,String memberId,String opId){
		StringBuffer sql = new StringBuffer("SELECT count(*) FROM Filmfestivalvip_Good WHERE DATEDIFF(DAY, todaytime, GETDATE())<1 AND Vipid =? AND ");
		if(null != memberId)
			sql.append("Memberid ='").append(memberId).append("' AND ");
		sql.append("DeviceId ='").append(opId).append("'");
		return filmfestivalDao.getAllNum(sql.toString(),Integer.parseInt(vip_id));
	}

	@Override
    public String addFilm (Tb_doc tb_doc,Object[] memInfo,List<AddFilm> list,String worksDesc,String worksTitle,String memberId,int count,String worksShowId,String space,String isPublic,String musicid,String code,String spotId,String coverurl){
        try {
            Tb_member mem = new Tb_member();
            mem.setId(memberId);
            String[] jinyong = null;
            List<Tb_membermessagesteup> liuyan = null;
            Filmfestivalvip ffv = new Filmfestivalvip();
            int temp = 0;	//本次影展上传图片次数
    //		Integer maxIdffv = iFilmfestivalvipDao.getMaxId(Filmfestivalvip.class)+1;
    //		ffv.setId(maxIdffv);
    //		Integer maxIdff = filmfestivalDao.getMaxId(Filmfestival.class)+1;
            if (1 == count || -1 == count) {       //添加影展vip信息
                //过滤敏感词
                liuyan = iTb_membermessagesteupDao.findByHQL("from Tb_membermessagesteup");
                if (liuyan != null && liuyan.size() > 0) {
                    jinyong = liuyan.get(0).getDisablewords().split("[,，]");
                }
                if (null != jinyong) {
                    for (String s : jinyong) {
                        if (null != worksTitle) {
                            worksTitle = worksTitle.replaceAll(s, liuyan.get(0).getReplacewords());
                        }
                        if (null != worksDesc) {
                            worksDesc = worksDesc.replaceAll(s, liuyan.get(0).getReplacewords());
                        }
                    }
                }
                //修改用户经验值 参赛积分，上传作品积分 验证等级  修改剩余容量
                if (null != memInfo) {
                    // 修改会员积分
                    Double s = Double.valueOf(memInfo[3].toString()) + 1;
                    //判断 用户是否满足升级条件
                    if (s > Double.valueOf(memInfo[4].toString()) && Integer.parseInt(memInfo[2].toString()) <= 4) {
                        String sqlLevel = "update Tb_member set levelid=?,levelintegral = ? where id=?";
                        if (Integer.parseInt(memInfo[1].toString()) == 4)
                            iTb_memberDao.updateSql(sqlLevel, 15, s, memberId);
                        else
                            iTb_memberDao.updateSql(sqlLevel, Integer.parseInt(memInfo[1].toString()) + 1, s, memberId);
                    } else {
                        String sqlLevel = "update Tb_member set levelintegral=? where id=?";
                        iTb_memberDao.updateSql(sqlLevel, s, memberId);
                    }
                }

                //添加影展会员信息
                ffv.setFilegoodcount(0);
                ffv.setFilecommentscount(0);
                ffv.setOpen_time(DateUtil.getDate());
                ffv.setRecommend(0);
                if ("1".equals(code))
                    ffv.setShowtypeid(0);
                if ("2".equals(code))
                    ffv.setShowtypeid(1);
    //			System.out.println("背景音乐开始--------musicid-------"+musicid);
                TbFilmfestivalvipMusic tm = new TbFilmfestivalvipMusic();
                if (null == musicid || "0".equals(musicid)) {
                    tm.setId(0);
                }else {
                    tm.setId(Integer.parseInt(musicid));
                }
                ffv.setMusicid(tm);
    //            System.out.println("背景音乐结束---------musicid------"+musicid);
                ffv.setCoverurl(coverurl);

                if ("0".equals(isPublic))
                    ffv.setTime_length(0);
                else {
                    //查询影展会员套餐信息
                    List<Object[]> ffvOpenTimes = iFilmfestivalvipopentimeDao.findBySql("select * from Filmfestivalvipopentime where id =?", 14);
                    ffv.setTime_length(Integer.parseInt(ffvOpenTimes.get(0)[2].toString()));
                }


                ffv.setTitel(URLEncoder.encode(worksTitle,"utf-8"));

                ffv.setTitelnote(URLEncoder.encode(worksDesc,"utf-8"));
                ffv.setFilepaycount(0);
                ffv.setMember_id(mem);
                ffv.setSort(0);
                ffv.setViewnum(0);
                iFilmfestivalvipDao.save(ffv);  //添加影展

                System.out.println("ffv.getId()==="+ffv.getId());
                worksShowId = ffv.getId().toString();   //影展id
                //是否景点影集
                if (!"0".equals(spotId)){
                    TB_ScenicSpotFile ssf = new TB_ScenicSpotFile();

                    TB_ScenicSpot ss = new TB_ScenicSpot();
                    ss.setId(Integer.parseInt(spotId));
                    ssf.setScenicspotid(ss);

                    ssf.setFiletype(2);
                    ssf.setSort(0);
                    ssf.setUpdatetime(DateUtil.getDate());
                    ssf.setFileid(worksShowId);
                    iTb_scenicSpotFileDao.save(ssf);
                    System.out.println("---------传文章到景点！");
                    List<Object[]> spotInfo = iTb_scenicSpotDao.findBySql("SELECT PhotoNum,ArticleNum,VideoNum FROM TB_ScenicSpot WHERE id =?",spotId);
                    //修改景点作品数量
                    iTb_scenicSpotDao.updateSql("UPDATE TB_ScenicSpot SET ArticleNum = ? WHERE id =?",Integer.parseInt(spotInfo.get(0)[1].toString())+1,spotId);
                }
            }else {
                ffv.setId(Integer.parseInt(worksShowId));
            }

            if (1 == count || -1 == count) {	//第一次，修改所有的作品描述，添加本次上传作品的信息
                for (int i = 0; i < list.size(); i++) {
                    Filmfestival ff = new Filmfestival();	//添加影展作品信息
                    String des = list.get(i).getDesc();
                    //过滤敏感词
                    if (null != jinyong) {
                        for (String s : jinyong) {
                            if (null != des || "".equals(des)) {
                                des = des.replaceAll(s, liuyan.get(0).getReplacewords());
                            }
                        }
                    }
                    if ("0".equals(list.get(i).getId())) {
                        temp++;
                        if (temp == count) {		//如果有上传的作品，添加到数据库
                            //修改云库容量
                            tb_memberDao.updateSql("update Tb_member set RemainingCapacity = ? where id = ?", space, memberId);
                            tb_doc.setFiledescribe(URLEncoder.encode(des,"utf-8"));  //这次上传作品的描述
                            iTb_docDao.save(tb_doc);
                            ff.setDoc_id(tb_doc);
                            ff.setMember_id(mem);
                            ff.setSort(i);
                            ff.setVip_id(ffv);
    //            			ff.setId(maxIdff+i);
                            filmfestivalDao.save(ff);   //添加影展作品
                        }
                    }else {
                        //修改作品描述、类型
                        iTb_docDao.updateSql("update Tb_doc set FileDescribe =? where id =?", URLEncoder.encode(list.get(i).getDesc(),"utf-8"), list.get(i).getId());
                        Tb_doc d = new Tb_doc();
                        d.setId(list.get(i).getId());
                        ff.setDoc_id(d);
                        ff.setMember_id(mem);
                        ff.setSort(i);
                        ff.setVip_id(ffv);
    //            		ff.setId(maxIdff+i);
                        filmfestivalDao.save(ff);   //添加影展作品
                    }
                }
            }else {		//第count上传图片，只添加这张图的信息
                for (int i = 0; i < list.size(); i++) {
                    if ("0".equals(list.get(i).getId())) {
                        temp++;
                        if (temp == count) {		//如果有上传的作品，添加到数据库
                            Filmfestival ff = new Filmfestival();	//添加影展作品信息
                            String des = list.get(i).getDesc();
                            //过滤敏感词
                            if (null != jinyong) {
                                for (String s : jinyong) {
                                    if (null != des || "".equals(des)) {
                                        des = des.replaceAll(s, liuyan.get(0).getReplacewords());
                                    }
                                }
                            }
                            tb_doc.setFiledescribe(URLEncoder.encode(des,"utf-8"));  //这次上传作品的描述
                            iTb_docDao.save(tb_doc);
                            ff.setDoc_id(tb_doc);
                            ff.setMember_id(mem);
                            ff.setSort(i);
                            ff.setVip_id(ffv);
    //            			ff.setId(maxIdff+i);
                            filmfestivalDao.save(ff);   //添加影展作品
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return worksShowId;
    }

    @Override
    public List<Object[]> goodList(Integer vip_id,int startRow,int endRow){
        String sql = "select * from(select m.id,m.UsersName,m.Levelid,m.HeadImg,ROW_NUMBER() over(order by dg.todaytime DESC)TT FROM Filmfestivalvip_Good AS dg LEFT JOIN Tb_member AS m ON dg.MemberId=m.Id LEFT JOIN TB_Level as l ON m.Levelid = l.id WHERE dg.MemberId is not null AND dg.vipid =?)TTT WHERE TTT.TT between ? and ?";
        return  filmfestivalDao.findBySql(sql,vip_id,startRow,endRow);
    }

	public void delete(Object[] entityids) {
		StringBuilder hql = new StringBuilder("");
		for (int i = 0; i < entityids.length; i++) {
			if (i == 0) {
				hql.append(" id='").append(entityids[i]).append("'");
			} else {
				hql.append(" or id='").append(entityids[i]).append("'");
			}
		}
		filmfestivalDao.delete(Filmfestival.class, hql.toString());
	}
	public void deletevipid(Integer entityids) {
		StringBuilder hql = new StringBuilder("");
		hql.append(" vip_id='").append(entityids).append("'");
		filmfestivalDao.delete(Filmfestival.class, hql.toString());
	}

	@Override
	public int updatedoc(String sql) {
		// TODO Auto-generated method stub
		return filmfestivalDao.updatedoc(sql);
	}

	@Override
	public QueryResult<Filmfestival> findval(PageBean pagebean) {
		// TODO Auto-generated method stub
		return filmfestivalDao.findval(pagebean);
	}

	@Override
	public QueryResult<Filmfestival> findmemberval(PageBean pagebean,
			String memberid) {
		// TODO Auto-generated method stub
		return filmfestivalDao.findmemberval(pagebean, memberid);
	}

	@Override
	public QueryResult<Filmfestival> findmemberval_caogao(PageBean pagebean,
			String memberid) {
		// TODO Auto-generated method stub
		return filmfestivalDao.findmemberval_caogao(pagebean, memberid);
	}
	
	

}
