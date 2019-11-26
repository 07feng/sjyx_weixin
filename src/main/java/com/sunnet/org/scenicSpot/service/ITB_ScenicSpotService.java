package com.sunnet.org.scenicSpot.service;

import com.sunnet.framework.pagenation.PageBean;
import com.sunnet.framework.pagenation.QueryResult;
import com.sunnet.framework.service.IBaseService;
import com.sunnet.org.app.entity.AddFilm;
import com.sunnet.org.filmfestival.model.Filmfestival;
import com.sunnet.org.information.model.Tb_doc;
import com.sunnet.org.scenicSpot.model.TB_ScenicSpot;
import com.sunnet.org.scenicSpot.model.TB_ScenicSpotFile;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 *
 * <br>
 * <b>功能：</b>TB_ScenicSpot Service<br>
 * <b>作者：</b>jinhao<br>
 * <b>日期：</b> 2018.6/20 <br>
 */
public interface ITB_ScenicSpotService extends IBaseService<TB_ScenicSpot>
{
    /**
     * 推荐景点信息
     * @return
     */
    public List<Object[]> tourismRecommend();

    /**
     * 搜索景点
     * @param spotName
     * @return
     */
    public List<Map> searchSpot(String areaId,String spotName);

    /**
     * 景点详细信息
     * @param spotId
     * @return
     */
    public Map getSpotData(int spotId,String memberId);

    /**
     * 最近旅游作品列表
     * @param page
     * @return
     */
    public List getTourismImgList(int page,int spotId,String memberId);

    /**
     * 景点列表
     * @param page
     * @param cityId
     * @return
     */
    public List getTourismList(int page,int cityId,String memberId);

    /**
     * 景点信息
     * @param spotId
     * @return
     */
    public Object[] spotInfo(int spotId);

    /**
     * 修改景点信息
     * @param paraSql
     * @param values
     */
    public void updateSpotInfo(String paraSql, Object... values);

    /**
     * 发现者修改置顶文章
     * @param spotId
     * @param spotFileId
     */
    public void updateSpotFileTop(String spotId,String spotFileId);

    /**
     * 审核成为发现在权限
     * @param spotId
     * @param memberId
     * @return
     */
    public Map<String,Integer> adminSpotStatus(int spotId,String memberId);

    public void saveSpotFile(TB_ScenicSpotFile ssf);
}
