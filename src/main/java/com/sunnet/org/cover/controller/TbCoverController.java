package com.sunnet.org.cover.controller;

import com.alibaba.fastjson.JSONObject;
import com.sunnet.framework.controller.BaseController;
import com.sunnet.framework.pagenation.PageBean;
import com.sunnet.framework.pagenation.QueryResult;
import com.sunnet.framework.util.JsonResult;
import com.sunnet.org.cover.model.TbFilmfestivalvipCover;
import com.sunnet.org.cover.model.TbFilmfestivalvipCoverCategory;
import com.sunnet.org.cover.service.TbCoverCategoryService;
import com.sunnet.org.cover.service.TbCoverService;
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
 * @ClassName: TbCoverController
 * @Description:
 * @Author: Bryce
 * @Date 2019/9/27 0027下午 15:33
 * @Version 1.0
 */
@Controller
@RequestMapping("/admin")
public class TbCoverController extends BaseController {

    @Autowired
    private TbCoverService tbCoverService;
    @Autowired
    private TbCoverCategoryService tbCoverCategoryService;

    /**
     * 跳转至初始化界面
     * @param session
     * @param request
     * @return
     */
    @RequestMapping(value = "/cover/index")
    @RequiresPermissions("sys_cover")
    public String index(HttpSession session, HttpServletRequest request)
    {
        List<TbFilmfestivalvipCoverCategory> tbCoverCategory = tbCoverCategoryService.typeList();
        request.setAttribute("tbCoverCategory",tbCoverCategory);
        return "view/coverView/cover_list";
    }

    /**
     * 分页查询封面数据
     * @param pageBean
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/cover/list")
    @RequiresPermissions("sys_cover")
    public String list(PageBean pageBean, HttpServletRequest request, HttpServletResponse response){
        JsonResult jsonResult = new JsonResult();
        try
        {
            QueryResult<TbFilmfestivalvipCover> result = tbCoverService.list(pageBean,request);
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
     * 批量删除封面数据
     * @param ids
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/cover/delete")
    @RequiresPermissions("sys_cover")
    public String delete(String ids, HttpServletRequest request, HttpServletResponse response)
    {
        JsonResult jsonResult = new JsonResult();
        try
        {
            String[] id = ids.split(",");
            for (int i = 0; i< id.length; i++) {
                tbCoverService.delCover(Integer.parseInt(id[i]));
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
     * 跳转至新增封面页面
     * @param fdId
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/cover/add")
    @RequiresPermissions("sys_cover")
    public String add(String fdId, HttpServletRequest request, HttpServletResponse response)
    {
        List<TbFilmfestivalvipCoverCategory> list = null;
        TbFilmfestivalvipCover tbCover=null;
        try {
            if(fdId != null && !(fdId.equals(""))){
                tbCover = tbCoverService.selectByKey(Integer.parseInt(fdId));
            }
            list = tbCoverCategoryService.typeList();
        }catch (Exception e){
            e.getMessage();
        }
        request.setAttribute("tbCover", tbCover);
        request.setAttribute("typeList", list);
        return "view/coverView/cover_add";
    }

    /**
     * 保存新增封面
     * @param tbCover
     * @param file
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/cover/save")
    @RequiresPermissions("sys_cover")
    @ResponseBody
    public String save(String tbCover, MultipartFile file, HttpServletRequest request, HttpServletResponse response)
    {
        JSONObject jsonObject = JSONObject.parseObject(tbCover);
        TbFilmfestivalvipCover tbCover1 = JSONObject.toJavaObject(jsonObject,TbFilmfestivalvipCover.class);
        JsonResult jsonResult = new JsonResult();
        try
        {

            OSSClientUtil2 ossClientUtil2 = new OSSClientUtil2();
            if (file != null && file.getSize()!=0) {
                String imagUrls = ossClientUtil2.uploadImg2Oss(file);//图片路径
                tbCover1.setCoverurl(imagUrls);
            }
            System.out.println(tbCover1.toString());
            tbCoverService.addCover(tbCover1);
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
     * 更新封面
     * @param tbCover
     * @param file
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/cover/update")
    @RequiresPermissions("sys_cover")
    public String update(String tbCover, MultipartFile file, HttpServletRequest request, HttpServletResponse response)
    {
        JsonResult jsonResult = new JsonResult();
        JSONObject jsonObject = JSONObject.parseObject(tbCover);
        TbFilmfestivalvipCover tbCover1 = JSONObject.toJavaObject(jsonObject,TbFilmfestivalvipCover.class);
        try
        {
            OSSClientUtil2 ossClientUtil2 = new OSSClientUtil2();
            if (file != null && file.getSize()!=0) {
                String imagUrls = ossClientUtil2.uploadImg2Oss(file);//图片路径
                tbCover1.setCoverurl(imagUrls);
            }
            tbCoverService.updateCover(tbCover1);

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
