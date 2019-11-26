package com.sunnet.org.cover.service;

import com.sunnet.framework.pagenation.PageBean;
import com.sunnet.framework.pagenation.QueryResult;
import com.sunnet.framework.service.IBaseService;
import com.sunnet.org.cover.model.TbFilmfestivalvipCoverCategory;

import java.util.List;

public interface TbCoverCategoryService extends IBaseService<TbFilmfestivalvipCoverCategory> {
    QueryResult<TbFilmfestivalvipCoverCategory> list(PageBean pageBean);

    void add(TbFilmfestivalvipCoverCategory tbFilmfestivalVipCoverCategory);

    void del(Integer id);

    void update(TbFilmfestivalvipCoverCategory tbCoverCategory);

    TbFilmfestivalvipCoverCategory selectBykey(Integer id);

    List<TbFilmfestivalvipCoverCategory> typeList();

    int getMaxId();
}
