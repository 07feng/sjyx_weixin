package com.sunnet.org.cover.controller;

import com.alibaba.fastjson.JSONObject;
import com.sunnet.framework.controller.BaseController;
import com.sunnet.framework.pagenation.PageBean;
import com.sunnet.framework.pagenation.QueryResult;
import com.sunnet.framework.util.JsonResult;
import com.sunnet.org.cover.model.TbFilmfestivalvipCoverCategory;
import com.sunnet.org.cover.service.TbCoverCategoryService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * @ClassName: TbCoverCategoryController
 * @Description:
 * @Author: Bryce
 * @Date 2019/9/29 0029上午 9:22
 * @Version 1.0
 */
@Controller
@RequestMapping("/admin")
public class TbCoverCategoryController extends BaseController {

    @Autowired
    private TbCoverCategoryService tbCoverCategoryService;

    /**
     * 跳转至初始页
     * @param session
     * @param request
     * @return
     */
    @RequestMapping(value = "/coverCategory/index")
    @RequiresPermissions("sys_category")
    public String index(HttpSession session, HttpServletRequest request)
    {
        return "view/coverView/coverCategory_list";
    }

    /**
     * 分页查询封面分类数据
     * @param pageBean
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/coverCategory/list")
    @RequiresPermissions("sys_category")
    public String list(PageBean pageBean, HttpServletRequest request, HttpServletResponse response)
    {
        JsonResult jsonResult = new JsonResult();
        try
        {
            QueryResult<TbFilmfestivalvipCoverCategory> result = tbCoverCategoryService.list(pageBean);
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
    @RequestMapping(value = "/coverCategory/delete")
    @RequiresPermissions("sys_category")
    public String delete(String ids, HttpServletRequest request, HttpServletResponse response)
    {
        JsonResult jsonResult = new JsonResult();
        try
        {
            String[] id = ids.split(",");
            System.out.println("------>>>"+ids);
            for (int i = 0; i< id.length; i++) {
                tbCoverCategoryService.del(Integer.parseInt(id[i]));
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
    @RequestMapping(value = "/coverCategory/add")
    @RequiresPermissions("sys_category")
    public String add(String fdId, HttpServletRequest request, HttpServletResponse response)
    {
        TbFilmfestivalvipCoverCategory tbCoverCategory=null;
        try {
            if(fdId != null && !(fdId.equals(""))){
                tbCoverCategory = tbCoverCategoryService.selectBykey(Integer.parseInt(fdId));
            }
        }catch (Exception e){
            e.getMessage();
        }
        request.setAttribute("tbCoverCategory", tbCoverCategory);
        return "view/coverView/coverCategory_add";
    }

    /**
     * 新增封面类别
     * @param cateName
     * @param sort
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/coverCategory/save")
    @RequiresPermissions("sys_category")
    public String add(String cateName,String sort, HttpServletRequest request, HttpServletResponse response)
    {
        JsonResult jsonResult = new JsonResult();
        try
        {
            TbFilmfestivalvipCoverCategory tbCoverCategory = new TbFilmfestivalvipCoverCategory();
            tbCoverCategory.setSort(Integer.parseInt(sort));
            tbCoverCategory.setCatename(cateName);
            int maxId = tbCoverCategoryService.getMaxId();
            tbCoverCategory.setId(maxId+1);
            tbCoverCategoryService.add(tbCoverCategory);
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
     * 更新封面数据
     * @param fdId
     * @param cateName
     * @param sort
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/coverCategory/update")
    @RequiresPermissions("sys_category")
    public String update(String fdId,String cateName,String sort, HttpServletRequest request, HttpServletResponse response)
    {
        JsonResult jsonResult = new JsonResult();
        try
        {
            TbFilmfestivalvipCoverCategory tbCoverCategory = new TbFilmfestivalvipCoverCategory();
            tbCoverCategory.setId(Integer.parseInt(fdId));
            tbCoverCategory.setCatename(cateName);
            tbCoverCategory.setSort(Integer.parseInt(sort));
            tbCoverCategoryService.update(tbCoverCategory);

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
