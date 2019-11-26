package com.sunnet.org.cover.service.impl;

import com.sunnet.framework.pagenation.PageBean;
import com.sunnet.framework.pagenation.QueryResult;
import com.sunnet.framework.service.impl.BaseServiceImpl;
import com.sunnet.org.cover.dao.TbCoverMusicDao;
import com.sunnet.org.cover.model.TbFilmfestivalvipMusic;
import com.sunnet.org.cover.service.TbCoverMusicService;
import com.sunnet.org.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ClassName: TbCoverMusicServiceImpl
 * @Description:
 * @Author: Bryce
 * @Date 2019/9/30 0030下午 15:32
 * @Version 1.0
 */
@Service
public class TbCoverMusicServiceImpl extends BaseServiceImpl<TbFilmfestivalvipMusic> implements TbCoverMusicService {

    @Autowired
    private TbCoverMusicDao tbCoverMusicDao;

    /**
     *  分页查询音乐数据
     * @param pageBean
     * @param request
     * @return
     */
    @Override
    public QueryResult<TbFilmfestivalvipMusic> list(PageBean pageBean, HttpServletRequest request) {
        StringBuffer str = new StringBuffer();
        str.append(" from TbFilmfestivalvipMusic o where 1=1 "); //初始化语句
        if (StringUtils.isStringNull(request.getParameter("typeid"))){
            str.append(" and o.musiccateid = '"+request.getParameter("typeid")+"'");
        }
        pageBean.setHql(str.toString());
        int totalRecord = tbCoverMusicDao.findByHQLCount(TbFilmfestivalvipMusic.class, pageBean);

        String sort=" order by o.id desc ";
        str.append(sort);
        pageBean.setHql(str.toString());
        List<TbFilmfestivalvipMusic> list = tbCoverMusicDao.findByHQLPage(TbFilmfestivalvipMusic.class, pageBean);
        System.out.println("list.size()=="+list.size());
        QueryResult<TbFilmfestivalvipMusic> result = new QueryResult();
        result.setResultList(list);
        pageBean.setTotalRecord(totalRecord);
        pageBean.setHql("");
        pageBean.setSql("");
        result.setPageBean(pageBean);
        return result;
    }

    /**
     * 添加音乐
     * @param tbCoverMusic
     */
    @Override
    public void addCoverMusic(TbFilmfestivalvipMusic tbCoverMusic) {
        tbCoverMusicDao.save(tbCoverMusic);
    }

    /**
     * 根据id删除音乐
     * @param id
     */
    @Override
    public void delCoverMusic(Integer id) {
        String hql = "id = "+id.toString();
        tbCoverMusicDao.delete(TbFilmfestivalvipMusic.class,hql);
    }

    /**
     * 修改音乐信息
     * @param tbCoverMusic
     */
    @Override
    public void updateCoverMusic(TbFilmfestivalvipMusic tbCoverMusic) {
        tbCoverMusicDao.update(tbCoverMusic);
    }

    /**
     * 音乐类型
     * @return
     */
    @Override
    public List<Map> typeList() {
        List<Object[]> list = tbCoverMusicDao.findBySql("SELECT MusicCateId,MusicCateName FROM TB_FilmfestivalVip_Music GROUP BY MusicCateId,MusicCateName");
        List back = new ArrayList();
        for (Object[] obj:list) {
            Map map = new HashMap();
            map.put("musiccateid",(Integer)obj[0]);
            map.put("musiccatename",obj[1].toString());
            back.add(map);
        }
        return back;
    }

    /**
     * 根据id查询音乐信息
     * @param id
     * @return
     */
    @Override
    public TbFilmfestivalvipMusic selectByKey(Integer id) {
        return tbCoverMusicDao.findByPrimaryKey(TbFilmfestivalvipMusic.class,id);
    }

    /**
     * 根据音乐类型查询音乐数据
     * @param typeId
     * @return
     */
    @Override
    public List<TbFilmfestivalvipMusic> coverMusicList(Integer typeId) {
        if (null != typeId){
            return tbCoverMusicDao.findByHQL("from TbFilmfestivalvipMusic o where o.MusicCateId = ?",typeId);
        } else{
            return tbCoverMusicDao.findByHQL("from TbFilmfestivalvipMusic");
        }
    }

    /**
     * 获取音乐数量
     * @param sql
     * @return
     */
    @Override
    public int findCount(String sql) {
        return tbCoverMusicDao.findCount(sql,null);
    }
}
