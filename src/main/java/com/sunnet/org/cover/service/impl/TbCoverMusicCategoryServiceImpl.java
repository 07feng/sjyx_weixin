package com.sunnet.org.cover.service.impl;

import com.sunnet.framework.pagenation.PageBean;
import com.sunnet.framework.pagenation.QueryResult;
import com.sunnet.framework.service.impl.BaseServiceImpl;
import com.sunnet.org.cover.dao.TbCoverMusicCategoryDao;
import com.sunnet.org.cover.model.TbFilmfestivalvipMusicCategory;
import com.sunnet.org.cover.service.TbCoverMusicCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @ClassName: TbCoverMusicCategoryServiceImpl
 * @Description:
 * @Author: Bryce
 * @Date 2019/9/30 0030下午 16:48
 * @Version 1.0
 */
@Service
public class TbCoverMusicCategoryServiceImpl extends BaseServiceImpl<TbFilmfestivalvipMusicCategory> implements TbCoverMusicCategoryService {

    @Autowired
    private TbCoverMusicCategoryDao tbCoverMusicCategoryDao;

    /**
     * 分页查询分类数据
     * @param pageBean
     * @return
     */
    @Override
    public QueryResult<TbFilmfestivalvipMusicCategory> list(PageBean pageBean) {
        StringBuffer str = new StringBuffer();
        str.append(" from TbFilmfestivalvipMusicCategory o where 1=1 "); //初始化语句
        pageBean.setHql(str.toString());
        int totalRecord = tbCoverMusicCategoryDao.findByHQLCount(TbFilmfestivalvipMusicCategory.class, pageBean);

        String sort=" order by o.sort desc ";
        str.append(sort);
        pageBean.setHql(str.toString());
        System.out.println("---------"+str.toString()+"totalRecord="+totalRecord);
        List<TbFilmfestivalvipMusicCategory> list = tbCoverMusicCategoryDao.findByHQLPage(TbFilmfestivalvipMusicCategory.class, pageBean);
        System.out.println("list.size()=="+list.size());
        QueryResult<TbFilmfestivalvipMusicCategory> result = new QueryResult();
        result.setResultList(list);
        pageBean.setTotalRecord(totalRecord);
        pageBean.setHql("");
        pageBean.setSql("");
        result.setPageBean(pageBean);
        return result;
    }

    /**
     * 新增分类
     * @param tbCoverMusicCategory
     */
    @Override
    public void add(TbFilmfestivalvipMusicCategory tbCoverMusicCategory) {
        tbCoverMusicCategoryDao.save(tbCoverMusicCategory);
    }

    /**
     * 根据id删除分类信息
     * @param id
     */
    @Override
    public void del(Integer id) {
        String hql = " Id = "+id.toString();
        tbCoverMusicCategoryDao.delete(TbFilmfestivalvipMusicCategory.class,hql);
    }

    /**
     * 修改分类信息
     * @param tbCoverMusicCategory
     */
    @Override
    public void update(TbFilmfestivalvipMusicCategory tbCoverMusicCategory) {
        tbCoverMusicCategoryDao.update(tbCoverMusicCategory);
    }

    /**
     * 根据id查询分类信息
     * @param id
     * @return
     */
    @Override
    public TbFilmfestivalvipMusicCategory selectBykey(Integer id) {
        return tbCoverMusicCategoryDao.findByPrimaryKey(TbFilmfestivalvipMusicCategory.class,id);
    }

    /**
     * 查询所有分类数据
     * @return
     */
    @Override
    public List<TbFilmfestivalvipMusicCategory> typeList() {
        return tbCoverMusicCategoryDao.findByHQL("from TbFilmfestivalvipMusicCategory");
    }

    /**
     * 查询id最大值
     * @return
     */
    @Override
    public int getMaxId() {
        return tbCoverMusicCategoryDao.findCount("select max(Id) FROM TB_FilmfestivalVip_MusicCategory",null);

    }
}
