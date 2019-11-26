package com.sunnet.org.cover.service.impl;

import com.sunnet.framework.pagenation.PageBean;
import com.sunnet.framework.pagenation.QueryResult;
import com.sunnet.framework.service.impl.BaseServiceImpl;
import com.sunnet.org.cover.dao.TbCoverDao;
import com.sunnet.org.cover.model.TbFilmfestivalvipCover;
import com.sunnet.org.cover.service.TbCoverService;
import com.sunnet.org.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ClassName: TbCoverServiceImpl
 * @Description: 封面service层实现类
 * @Author: Bryce
 * @Date 2019/9/27 0027下午 14:57
 * @Version 1.0
 */
@Service
public class TbCoverServiceImpl extends BaseServiceImpl<TbFilmfestivalvipCover> implements TbCoverService {

    @Autowired
    private TbCoverDao tbCoverDao;

    /**
     * 分页查询封面数据
     * @param pageBean
     * @param request
     * @return
     */
    @Override
    public QueryResult<TbFilmfestivalvipCover> list(PageBean pageBean, HttpServletRequest request) {
        StringBuffer str = new StringBuffer();
        str.append(" from TbFilmfestivalvipCover o where 1=1 "); //初始化语句
        if (StringUtils.isStringNull(request.getParameter("typeid"))){
            str.append(" and o.covercateid = '"+request.getParameter("typeid")+"'");
        }
        pageBean.setHql(str.toString());
        int totalRecord = tbCoverDao.findByHQLCount(TbFilmfestivalvipCover.class, pageBean);

        String sort=" order by o.id desc ";
        str.append(sort);
        pageBean.setHql(str.toString());
        List<TbFilmfestivalvipCover> list = tbCoverDao.findByHQLPage(TbFilmfestivalvipCover.class, pageBean);
        System.out.println("list.size()=="+list.size());
        QueryResult<TbFilmfestivalvipCover> result = new QueryResult();
        result.setResultList(list);
        pageBean.setTotalRecord(totalRecord);
        pageBean.setHql("");
        pageBean.setSql("");
        result.setPageBean(pageBean);
        return result;
    }

    /**
     * 新增封面
     * @param tbCover
     */
    @Override
    public void addCover(TbFilmfestivalvipCover tbCover) {
        tbCoverDao.save(tbCover);
    }

    /**
     * 删除封面
     * @param id
     */
    @Override
    public void delCover(Integer id) {
        String hql = "id = "+id.toString();
        tbCoverDao.delete(TbFilmfestivalvipCover.class,hql);
    }

    /**
     * 修改封面
     * @param tbCover
     */
    @Override
    public void updateCover(TbFilmfestivalvipCover tbCover) {
        tbCoverDao.update(tbCover);
    }

    /**
     *
     * @return
     */
    @Override
    public List<Map> typeList() {
        List<Object[]> list = tbCoverDao.findBySql("SELECT CoverCateId,CoverCateName FROM TB_FilmfestivalVip_Cover GROUP BY CoverCateId,CoverCateName");
        List back = new ArrayList();
        for (Object[] obj:list) {
            Map map = new HashMap();
            map.put("covercateid",(Integer)obj[0]);
            map.put("covercatename",obj[1].toString());
            back.add(map);
        }
        return back;
    }

    /**
     * 根据id查询封面信息
     * @param id
     * @return
     */
    @Override
    public TbFilmfestivalvipCover selectByKey(Integer id) {
        return tbCoverDao.findByPrimaryKey(TbFilmfestivalvipCover.class,id);
    }

    /**
     * 根据类型查询封面数据
     * @param typeId
     * @return
     */
    @Override
    public List<TbFilmfestivalvipCover> coverList(Integer typeId) {
        if (null != typeId){
            return tbCoverDao.findByHQL("from TbFilmfestivalvipCover o where o.CoverCateId = ?",typeId);
        } else{
            return tbCoverDao.findByHQL("from TbFilmfestivalvipCover");
        }
    }

    /**
     * 查询封面总数
     * @param sql
     * @return
     */
    @Override
    public int findCount(String sql) {
        return tbCoverDao.findCount(sql,null);
    }
}
