package com.sunnet.org.cover.controller;

import com.alibaba.fastjson.JSONObject;
import com.sunnet.framework.controller.BaseController;
import com.sunnet.framework.pagenation.PageBean;
import com.sunnet.framework.pagenation.QueryResult;
import com.sunnet.framework.util.JsonResult;
import com.sunnet.org.cover.model.TbFilmfestivalvipMusic;
import com.sunnet.org.cover.model.TbFilmfestivalvipMusicCategory;
import com.sunnet.org.cover.service.TbCoverMusicCategoryService;
import com.sunnet.org.cover.service.TbCoverMusicService;
import com.sunnet.org.util.OSSClientUtil2;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * @ClassName: TbCoverMusicController
 * @Description:
 * @Author: Bryce
 * @Date 2019/9/30 0030下午 16:02
 * @Version 1.0
 */
@Controller
@RequestMapping("/admin")
public class TbCoverMusicController extends BaseController {

    @Autowired
    private TbCoverMusicService tbCoverMusicService;
    @Autowired
    private TbCoverMusicCategoryService tbCoverMusicCategoryService;

    /**
     * 跳转至初始化界面
     * @param session
     * @param request
     * @return
     */
    @RequestMapping(value = "/covermusic/index")
    @RequiresPermissions("sys_music")
    public String index(HttpSession session, HttpServletRequest request)
    {
        List<TbFilmfestivalvipMusicCategory> tbCoverMusicCategory = tbCoverMusicCategoryService.typeList();
        request.setAttribute("tbCoverMusicCategory",tbCoverMusicCategory);
        return "view/coverView/covermusic_list";
    }

    /**
     * 分页查询音乐数据
     * @param pageBean
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/covermusic/list")
    @RequiresPermissions("sys_cover")
    public String list(PageBean pageBean, HttpServletRequest request, HttpServletResponse response){
        JsonResult jsonResult = new JsonResult();
        try
        {
            QueryResult<TbFilmfestivalvipMusic> result = tbCoverMusicService.list(pageBean,request);
            jsonResult.setResult(result);
            jsonResult.setCode("200");
            jsonResult.setMessage("执行成功！");
        }
        catch (Exception e)
        {
            jsonResult.setCode("500");
            jsonResult.setMessage("执行错误！");
            log.debug("异常:"+e);
        }
        return ajaxJson(JSONObject.toJSONString(jsonResult), response);
    }

    /**
     * 批量删除音乐数据
     * @param ids
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/covermusic/delete")
    @RequiresPermissions("sys_music")
    public String delete(String ids, HttpServletRequest request, HttpServletResponse response)
    {
        JsonResult jsonResult = new JsonResult();
        try
        {
            String[] id = ids.split(",");
            for (int i = 0; i< id.length; i++) {
                tbCoverMusicService.delCoverMusic(Integer.parseInt(id[i]));
            }

            jsonResult.setCode("200");
            jsonResult.setMessage("执行成功！");
        }
        catch (Exception e)
        {
            jsonResult.setCode("500");
            jsonResult.setMessage("执行错误！");
            log.debug("异常:"+e);
        }
        return ajaxJson(JSONObject.toJSONString(jsonResult), response);
    }

    /**
     * 跳转至新增音乐页面
     * @param fdId
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/covermusic/add")
    @RequiresPermissions("sys_music")
    public String add(String fdId, HttpServletRequest request, HttpServletResponse response)
    {
        List<TbFilmfestivalvipMusicCategory> list = null;
        TbFilmfestivalvipMusic tbCoverMusic=null;
        try {
            if(fdId != null && !(fdId.equals(""))){
                tbCoverMusic = tbCoverMusicService.selectByKey(Integer.parseInt(fdId));
            }
            list = tbCoverMusicCategoryService.typeList();
        }catch (Exception e){
            e.getMessage();
        }
        request.setAttribute("tbCoverMusic", tbCoverMusic);
        request.setAttribute("typeList", list);
        return "view/coverView/covermusic_add";
    }

    /**
     * 保存新增音乐
     * @param tbCoverMusic
     * @param musicfile
     * @param imgfile
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/covermusic/save")
    @RequiresPermissions("sys_cover")
    @ResponseBody
    public String save(String tbCoverMusic, MultipartFile musicfile,MultipartFile imgfile, HttpServletRequest request, HttpServletResponse response)
    {
        JSONObject jsonObject = JSONObject.parseObject(tbCoverMusic);
        TbFilmfestivalvipMusic tbCoverMusic1 = JSONObject.toJavaObject(jsonObject,TbFilmfestivalvipMusic.class);
        JsonResult jsonResult = new JsonResult();
        try
        {

            OSSClientUtil2 ossClientUtil2 = new OSSClientUtil2();
            if (musicfile != null && musicfile.getSize()!=0) {
                String musicUrls = ossClientUtil2.uploadImg2Oss(musicfile);//音乐路径
                tbCoverMusic1.setMusicurl(musicUrls);
            }
            if (imgfile != null && imgfile.getSize()!=0) {
                String imagUrls = ossClientUtil2.uploadImg2Oss(imgfile);//图片路径
                tbCoverMusic1.setImgurl(imagUrls);
            }
            System.out.println(tbCoverMusic1.toString());
            tbCoverMusicService.addCoverMusic(tbCoverMusic1);
            jsonResult.setCode("200");
            jsonResult.setMessage("执行成功！");
        }
        catch (Exception e)
        {
            jsonResult.setCode("500");
            jsonResult.setMessage("执行错误！");
            log.debug("异常:"+e);
        }
        return ajaxJson(JSONObject.toJSONString(jsonResult), response);
    }

    /**
     * 更新音乐
     * @param tbCoverMusic
     * @param musicfile
     * @param imgfile
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/covermusic/update")
    @RequiresPermissions("sys_music")
    public String update(String tbCoverMusic, MultipartFile musicfile, MultipartFile imgfile, HttpServletRequest request, HttpServletResponse response)
    {
        JsonResult jsonResult = new JsonResult();
        JSONObject jsonObject = JSONObject.parseObject(tbCoverMusic);
        TbFilmfestivalvipMusic tbCoverMusic1 = JSONObject.toJavaObject(jsonObject,TbFilmfestivalvipMusic.class);
        try
        {
            OSSClientUtil2 ossClientUtil2 = new OSSClientUtil2();
            if (musicfile != null && musicfile.getSize()!=0) {
                String musicUrls = ossClientUtil2.uploadImg2Oss(musicfile);//音乐路径
                tbCoverMusic1.setMusicurl(musicUrls);
            }

            if (imgfile != null && imgfile.getSize()!=0) {
                String imagUrls = ossClientUtil2.uploadImg2Oss(imgfile);//图片路径
                tbCoverMusic1.setImgurl(imagUrls);
            }
            tbCoverMusicService.updateCoverMusic(tbCoverMusic1);

            jsonResult.setCode("200");
            jsonResult.setMessage("执行成功！");
        }
        catch (Exception e)
        {
            jsonResult.setCode("500");
            jsonResult.setMessage("执行错误！");
            log.debug("异常:"+e);
        }
        return ajaxJson(JSONObject.toJSONString(jsonResult), response);
    }
}
