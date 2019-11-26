package com.sunnet.org.cover.service;

import com.sunnet.framework.pagenation.PageBean;
import com.sunnet.framework.pagenation.QueryResult;
import com.sunnet.framework.service.IBaseService;
import com.sunnet.org.cover.model.TbFilmfestivalvipMusicCategory;

import java.util.List;

public interface TbCoverMusicCategoryService extends IBaseService<TbFilmfestivalvipMusicCategory> {
    QueryResult<TbFilmfestivalvipMusicCategory> list(PageBean pageBean);

    void add(TbFilmfestivalvipMusicCategory tbCoverMusicCategory);

    void del(Integer id);

    void update(TbFilmfestivalvipMusicCategory tbCoverMusicCategory);

    TbFilmfestivalvipMusicCategory selectBykey(Integer id);

    List<TbFilmfestivalvipMusicCategory> typeList();

    int getMaxId();
}
