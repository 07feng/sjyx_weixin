package com.sunnet.org.cover.service.impl;

import com.sunnet.framework.pagenation.PageBean;
import com.sunnet.framework.pagenation.QueryResult;
import com.sunnet.framework.service.impl.BaseServiceImpl;
import com.sunnet.org.cover.dao.TbCoverCategoryDao;
import com.sunnet.org.cover.model.TbFilmfestivalvipCoverCategory;
import com.sunnet.org.cover.service.TbCoverCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @ClassName: TbCoverCategoryServiceImpl
 * @Description:
 * @Author: Bryce
 * @Date 2019/9/27 0027下午 17:39
 * @Version 1.0
 */
@Service
public class TbCoverCategoryServiceImpl extends BaseServiceImpl<TbFilmfestivalvipCoverCategory> implements TbCoverCategoryService {

    @Autowired
    private TbCoverCategoryDao tbCoverCategoryDao;

    /**
     * 分页查询分类信息
     * @param pageBean
     * @return
     */
    @Override
    public QueryResult<TbFilmfestivalvipCoverCategory> list(PageBean pageBean) {
        StringBuffer str = new StringBuffer();
        str.append(" from TbFilmfestivalvipCoverCategory o where 1=1 "); //初始化语句
        pageBean.setHql(str.toString());
        int totalRecord = tbCoverCategoryDao.findByHQLCount(TbFilmfestivalvipCoverCategory.class, pageBean);

        String sort=" order by o.sort desc ";
        str.append(sort);
        pageBean.setHql(str.toString());
        System.out.println("---------"+str.toString()+"totalRecord="+totalRecord);
        List<TbFilmfestivalvipCoverCategory> list = tbCoverCategoryDao.findByHQLPage(TbFilmfestivalvipCoverCategory.class, pageBean);
        System.out.println("list.size()=="+list.size());
        QueryResult<TbFilmfestivalvipCoverCategory> result = new QueryResult();
        result.setResultList(list);
        pageBean.setTotalRecord(totalRecord);
        pageBean.setHql("");
        pageBean.setSql("");
        result.setPageBean(pageBean);
        return result;
    }

    /**
     * 新增分类
     * @param tbCoverCategory
     */
    @Override
    public void add(TbFilmfestivalvipCoverCategory tbCoverCategory) {
        tbCoverCategoryDao.save(tbCoverCategory);
    }

    /**
     * 删除分类
     * @param id
     */
    @Override
    public void del(Integer id) {
        String hql = " Id = "+id.toString();
        tbCoverCategoryDao.delete(TbFilmfestivalvipCoverCategory.class,hql);
    }

    /**
     *
     * @param tbFilmfestivalVipCoverCategory
     */
    @Override
    public void update(TbFilmfestivalvipCoverCategory tbFilmfestivalVipCoverCategory) {
        tbCoverCategoryDao.update(tbFilmfestivalVipCoverCategory);
    }

    @Override
    public TbFilmfestivalvipCoverCategory selectBykey(Integer id) {
        return tbCoverCategoryDao.findByPrimaryKey(TbFilmfestivalvipCoverCategory.class,id);
    }

    @Override
    public List<TbFilmfestivalvipCoverCategory> typeList() {
        return tbCoverCategoryDao.findByHQL("FROM TbFilmfestivalvipCoverCategory");
    }

    @Override
    public int getMaxId() {
        return tbCoverCategoryDao.findCount("select max(Id) FROM TB_FilmfestivalVip_CoverCategory",null);
    }
}
