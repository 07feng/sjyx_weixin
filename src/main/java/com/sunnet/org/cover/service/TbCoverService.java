package com.sunnet.org.cover.service;

import com.sunnet.framework.pagenation.PageBean;
import com.sunnet.framework.pagenation.QueryResult;
import com.sunnet.framework.service.IBaseService;
import com.sunnet.org.cover.model.TbFilmfestivalvipCover;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

public interface TbCoverService extends IBaseService<TbFilmfestivalvipCover> {
    QueryResult<TbFilmfestivalvipCover> list(PageBean pageBean, HttpServletRequest request);

    void addCover(TbFilmfestivalvipCover tbCover);

    void delCover(Integer id);

    void updateCover(TbFilmfestivalvipCover tbCover);

    List<Map> typeList();

    TbFilmfestivalvipCover selectByKey(Integer id);

    List<TbFilmfestivalvipCover> coverList(Integer typeId);

    int findCount(String sql);
}
