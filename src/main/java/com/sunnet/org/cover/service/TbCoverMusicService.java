package com.sunnet.org.cover.service;

import com.sunnet.framework.pagenation.PageBean;
import com.sunnet.framework.pagenation.QueryResult;
import com.sunnet.framework.service.IBaseService;
import com.sunnet.org.cover.model.TbFilmfestivalvipMusic;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

public interface TbCoverMusicService extends IBaseService<TbFilmfestivalvipMusic> {
    QueryResult<TbFilmfestivalvipMusic> list(PageBean pageBean, HttpServletRequest request);

    void addCoverMusic(TbFilmfestivalvipMusic tbCoverMusic);

    void delCoverMusic(Integer id);

    void updateCoverMusic(TbFilmfestivalvipMusic tbCoverMusic);

    List<Map> typeList();

    TbFilmfestivalvipMusic selectByKey(Integer id);

    List<TbFilmfestivalvipMusic> coverMusicList(Integer typeId);

    int findCount(String sql);
}
