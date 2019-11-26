package com.sunnet.org.cover.controller;

import com.alibaba.fastjson.JSONObject;
import com.sunnet.framework.controller.BaseController;
import com.sunnet.framework.pagenation.PageBean;
import com.sunnet.framework.pagenation.QueryResult;
import com.sunnet.framework.util.JsonResult;
import com.sunnet.org.cover.model.TbFilmfestivalvipMusicCategory;
import com.sunnet.org.cover.service.TbCoverMusicCategoryService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * @ClassName: TbCoverMusicCategoryController
 * @Description:
 * @Author: Bryce
 * @Date 2019/9/30 0030下午 17:15
 * @Version 1.0
 */
@Controller
@RequestMapping("/admin")
public class TbCoverMusicCategoryController extends BaseController {

    @Autowired
    private TbCoverMusicCategoryService tbCoverMusicCategoryService;

    /**
     * 跳转至初始页
     * @param session
     * @param request
     * @return
     */
    @RequestMapping(value = "/covermusicCategory/index")
    @RequiresPermissions("sys_category")
    public String index(HttpSession session, HttpServletRequest request)
    {
        return "view/coverView/covermusicCategory_list";
    }

    /**
     * 分页查询音乐分类数据
     * @param pageBean
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/covermusicCategory/list")
    @RequiresPermissions("sys_category")
    public String list(PageBean pageBean, HttpServletRequest request, HttpServletResponse response)
    {
        JsonResult jsonResult = new JsonResult();
        try
        {
            QueryResult<TbFilmfestivalvipMusicCategory> result = tbCoverMusicCategoryService.list(pageBean);
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
     * 批量删除类别
     * @param ids
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/covermusicCategory/delete")
    @RequiresPermissions("sys_category")
    public String delete(String ids, HttpServletRequest request, HttpServletResponse response)
    {
        JsonResult jsonResult = new JsonResult();
        try
        {
            String[] id = ids.split(",");
            System.out.println("------>>>"+ids);
            for (int i = 0; i< id.length; i++) {
                tbCoverMusicCategoryService.del(Integer.parseInt(id[i]));
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
     * 跳转至新增页面
     * @param fdId
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/covermusicCategory/add")
    @RequiresPermissions("sys_category")
    public String add(String fdId, HttpServletRequest request, HttpServletResponse response)
    {
        TbFilmfestivalvipMusicCategory tbCoverMusicCategory=null;
        try {
            if(fdId != null && !(fdId.equals(""))){
                tbCoverMusicCategory = tbCoverMusicCategoryService.selectBykey(Integer.parseInt(fdId));
            }
        }catch (Exception e){
            e.getMessage();
        }
        request.setAttribute("tbCoverMusicCategory", tbCoverMusicCategory);
        return "view/coverView/covermusicCategory_add";
    }

    /**
     * 新增音乐类别
     * @param cateName
     * @param sort
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/covermusicCategory/save")
    @RequiresPermissions("sys_category")
    public String add(String cateName,String sort, HttpServletRequest request, HttpServletResponse response)
    {
        JsonResult jsonResult = new JsonResult();
        try
        {
            TbFilmfestivalvipMusicCategory tbCoverMusicCategory = new TbFilmfestivalvipMusicCategory();
            tbCoverMusicCategory.setSort(Integer.parseInt(sort));
            tbCoverMusicCategory.setCatename(cateName);
            int maxId = tbCoverMusicCategoryService.getMaxId();
            tbCoverMusicCategory.setId(maxId+1);
            tbCoverMusicCategoryService.add(tbCoverMusicCategory);
            System.out.println(maxId);

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
     * 更新分类数据
     * @param fdId
     * @param cateName
     * @param sort
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/covermusicCategory/update")
    @RequiresPermissions("sys_category")
    public String update(String fdId,String cateName,String sort, HttpServletRequest request, HttpServletResponse response)
    {
        JsonResult jsonResult = new JsonResult();
        try
        {
            TbFilmfestivalvipMusicCategory tbCoverMusicCategory = new TbFilmfestivalvipMusicCategory();
            tbCoverMusicCategory.setId(Integer.parseInt(fdId));
            tbCoverMusicCategory.setCatename(cateName);
            tbCoverMusicCategory.setSort(Integer.parseInt(sort));
            tbCoverMusicCategoryService.update(tbCoverMusicCategory);

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
