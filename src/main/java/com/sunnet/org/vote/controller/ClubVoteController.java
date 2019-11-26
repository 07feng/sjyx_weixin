package com.sunnet.org.vote.controller;

import com.alibaba.fastjson.JSONObject;
import com.sunnet.framework.controller.BaseController;
import com.sunnet.framework.util.JsonResult;
import com.sunnet.org.util.Constants;
import com.sunnet.org.util.DateUtil;
import com.sunnet.org.vote.model.Tb_club_pic;
import com.sunnet.org.vote.model.Tb_club_vote;
import com.sunnet.org.vote.service.ITb_club_picService;
import com.sunnet.org.vote.service.ITb_club_statService;
import com.sunnet.org.vote.vo.PicList;
import com.sunnet.org.vote.vo.VoteTime;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.*;

@Controller
public class ClubVoteController extends BaseController {
	@Autowired
	private ITb_club_picService iTb_club_picService;
	@Autowired
	private ITb_club_statService iTb_club_statService;

	/**
	 * author jinhao
	 * @param type 1 最新投票，3，实时榜单，2 编号排序
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/app/clubPicList")
	public String clubPicList(String type, HttpServletRequest request, HttpServletResponse response)
	{
		JsonResult jsonResult = new JsonResult();
		try
		{
			String openId = getOpenId(request, response);
//			String openId = "oZO5n5K9mMlrGFhXSP1z_tVMDFIY";
			List<Object[]> result = iTb_club_picService.getVoteInfo(Constants.VOTEID);
			Object[] obj = result.get(0);
			Date startTime = (Date)obj[0];	//起始时间
			Date endTime = (Date)obj[1];	//结束时间
			List<Map> picList = iTb_club_picService.picList2(type,openId,startTime,endTime);
			jsonResult.setData(picList);
			jsonResult.setCode(Constants.SUCCESS_CODE);
			jsonResult.setMessage(Constants.SUCCESS_DATA);
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
	 * 用户投票
	 * @param headImgUrl
	 * @param picId
	 * @param nickName
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/app/votePic")
	public String votePic(String headImgUrl, String picId, String nickName, HttpServletRequest request, HttpServletResponse response)
	{
		JsonResult jsonResult = new JsonResult();
		try
		{
			String openId = getOpenId(request, response);
//            String openId = "oZO5n5K9mMlrGFhXSP1z_tVMDFIY";
			List<Object[]> result = iTb_club_picService.getVoteInfo(Constants.VOTEID);
			Object[] obj = result.get(0);
			Date startTime = (Date)obj[0];	//起始时间
			Date endTime = (Date)obj[1];	//结束时间
			Map map = new HashMap();
			if (VoteTime.belongCalendar(DateUtil.getDate(),startTime,endTime)) {
				Tb_club_vote tbClubVote = new Tb_club_vote();
				tbClubVote.setOpenid(openId);
				tbClubVote.setHeadimg(headImgUrl);
				tbClubVote.setNickname(nickName);
				tbClubVote.setVotetime(DateUtil.getDate());

				Tb_club_pic tbClubPic = new Tb_club_pic();
				tbClubPic.setId(Integer.parseInt(picId));

				tbClubVote.setPicid(tbClubPic);
				int back = iTb_club_picService.votePic(tbClubVote);
				map.put("status", back);

			}else {
				if (!(VoteTime.AbigBCalendar(DateUtil.getDate(),startTime))) {
					map.put("status", -1);
				}
				if (VoteTime.AbigBCalendar(DateUtil.getDate(),endTime)) {
					map.put("status", -2);
				}
			}
			jsonResult.setData(map);
			jsonResult.setCode(Constants.SUCCESS_CODE);
			jsonResult.setMessage(Constants.SUCCESS_DATA);
		}
		catch (Exception e)
		{
			jsonResult.setCode(Constants.SERVICE_EXCEPTION_CODE);
			jsonResult.setMessage(Constants.SERVICE_EXCEPTION_DATA);
			log.debug("异常:"+e);
		}
		return ajaxJson(JSONObject.toJSONString(jsonResult), response);
	}

	/**
	 * 图片详情
	 * @param picId
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/app/picUrl")
	public String picUrl(String picId, HttpServletRequest request, HttpServletResponse response)
	{
		JsonResult jsonResult = new JsonResult();
		try
		{
			Tb_club_pic tbClubPic = iTb_club_picService.findByPrimaryKey(Integer.parseInt(picId));

			jsonResult.setData(tbClubPic.getPicurl());
			jsonResult.setCode(Constants.SUCCESS_CODE);
			jsonResult.setMessage(Constants.SUCCESS_DATA);
		}
		catch (Exception e)
		{
			jsonResult.setCode(Constants.SERVICE_EXCEPTION_CODE);
			jsonResult.setMessage(Constants.SERVICE_EXCEPTION_DATA);
			log.debug("异常:"+e);
		}
		return ajaxJson(JSONObject.toJSONString(jsonResult), response);
	}

//	/**
//	 * 图片详情
//	 * @param
//	 * @param request
//	 * @param response
//	 * @return
//	 */
//	@RequestMapping(value = "/app/addPic")
//	public String addPic(MultipartFile file, HttpServletRequest request, HttpServletResponse response)
//	{
//		JsonResult jsonResult = new JsonResult();
//		try
//		{
//			Tb_club_pic tbClubPic = new Tb_club_pic();
////            System.out.println(",,,,,,,,,,,,");
////            BufferedReader reader = new BufferedReader(new FileReader("F:/11.csv"));//换成你的文件名
////            InputStreamReader isr = new InputStreamReader(new FileInputStream(new File("F:/11.csv")), "UTF-8");
////            BufferedReader reader = new BufferedReader(isr);
//
//            File excelFile = new File("F:/phone.xls");
////			File excelFile = new File("F:/camera.xls");
//            HSSFWorkbook wb = new HSSFWorkbook(new FileInputStream(excelFile));
//            HSSFSheet sheet = wb.getSheetAt(0);
////            BufferedReader readerCamera = new BufferedReader(new FileReader("F:/camera.xls"));//换成你的文件名
//
////            reader.readLine();//第一行信息，为标题信息，不用,如果需要，注释掉
//            String line = "sjyx/activity/votePic/phone/《团队拓展训练》裴蕾-时代新材.jpg,http://sjyximage.oss-cn-shenzhen.aliyuncs.com/sjyx/activity/votePic/phone/%E3%80%8A%E5%9B%A2%E9%98%9F%E6%8B%93%E5%B1%95%E8%AE%AD%E7%BB%83%E3%80%8B%E8%A3%B4%E8%95%BE-%E6%97%B6%E4%BB%A3%E6%96%B0%E6%9D%90.jpg\n" +
//					"sjyx/activity/votePic/phone/《喝彩》王端-长沙银行.JPG,http://sjyximage.oss-cn-shenzhen.aliyuncs.com/sjyx/activity/votePic/phone/%E3%80%8A%E5%96%9D%E5%BD%A9%E3%80%8B%E7%8E%8B%E7%AB%AF-%E9%95%BF%E6%B2%99%E9%93%B6%E8%A1%8C.JPG\n" +
//					"sjyx/activity/votePic/phone/《唐风宋雅》陈曦-湖南投资.jpg,http://sjyximage.oss-cn-shenzhen.aliyuncs.com/sjyx/activity/votePic/phone/%E3%80%8A%E5%94%90%E9%A3%8E%E5%AE%8B%E9%9B%85%E3%80%8B%E9%99%88%E6%9B%A6-%E6%B9%96%E5%8D%97%E6%8A%95%E8%B5%84.jpg\n" +
//					"sjyx/activity/votePic/phone/《哥俩好》余英-湖南投资.jpg,http://sjyximage.oss-cn-shenzhen.aliyuncs.com/sjyx/activity/votePic/phone/%E3%80%8A%E5%93%A5%E4%BF%A9%E5%A5%BD%E3%80%8B%E4%BD%99%E8%8B%B1-%E6%B9%96%E5%8D%97%E6%8A%95%E8%B5%84.jpg\n" +
//					"sjyx/activity/votePic/phone/《向往》杨婵-湖南投资.jpg,http://sjyximage.oss-cn-shenzhen.aliyuncs.com/sjyx/activity/votePic/phone/%E3%80%8A%E5%90%91%E5%BE%80%E3%80%8B%E6%9D%A8%E5%A9%B5-%E6%B9%96%E5%8D%97%E6%8A%95%E8%B5%84.jpg\n" +
//					"sjyx/activity/votePic/phone/《古镇印象》蒋鹤-湖南投资.jpg,http://sjyximage.oss-cn-shenzhen.aliyuncs.com/sjyx/activity/votePic/phone/%E3%80%8A%E5%8F%A4%E9%95%87%E5%8D%B0%E8%B1%A1%E3%80%8B%E8%92%8B%E9%B9%A4-%E6%B9%96%E5%8D%97%E6%8A%95%E8%B5%84.jpg\n" +
//					"sjyx/activity/votePic/phone/《去你说的地方》徐磊-盐津铺子.jpg,http://sjyximage.oss-cn-shenzhen.aliyuncs.com/sjyx/activity/votePic/phone/%E3%80%8A%E5%8E%BB%E4%BD%A0%E8%AF%B4%E7%9A%84%E5%9C%B0%E6%96%B9%E3%80%8B%E5%BE%90%E7%A3%8A-%E7%9B%90%E6%B4%A5%E9%93%BA%E5%AD%90.jpg\n" +
//					"sjyx/activity/votePic/phone/《医者仁心 情满医患》李贵明-爱尔眼科.jpg,http://sjyximage.oss-cn-shenzhen.aliyuncs.com/sjyx/activity/votePic/phone/%E3%80%8A%E5%8C%BB%E8%80%85%E4%BB%81%E5%BF%83%20%E6%83%85%E6%BB%A1%E5%8C%BB%E6%82%A3%E3%80%8B%E6%9D%8E%E8%B4%B5%E6%98%8E-%E7%88%B1%E5%B0%94%E7%9C%BC%E7%A7%91.jpg\n" +
//					"sjyx/activity/votePic/phone/《劳作的老人》李建华-旗滨集团.jpg,http://sjyximage.oss-cn-shenzhen.aliyuncs.com/sjyx/activity/votePic/phone/%E3%80%8A%E5%8A%B3%E4%BD%9C%E7%9A%84%E8%80%81%E4%BA%BA%E3%80%8B%E6%9D%8E%E5%BB%BA%E5%8D%8E-%E6%97%97%E6%BB%A8%E9%9B%86%E5%9B%A2.jpg\n" +
//					"sjyx/activity/votePic/phone/《出海》印文-湖南投资.jpg,http://sjyximage.oss-cn-shenzhen.aliyuncs.com/sjyx/activity/votePic/phone/%E3%80%8A%E5%87%BA%E6%B5%B7%E3%80%8B%E5%8D%B0%E6%96%87-%E6%B9%96%E5%8D%97%E6%8A%95%E8%B5%84.jpg\n" +
//					"sjyx/activity/votePic/phone/《凌雾行》王徉华菱钢铁.jpg,http://sjyximage.oss-cn-shenzhen.aliyuncs.com/sjyx/activity/votePic/phone/%E3%80%8A%E5%87%8C%E9%9B%BE%E8%A1%8C%E3%80%8B%E7%8E%8B%E5%BE%89%E5%8D%8E%E8%8F%B1%E9%92%A2%E9%93%81.jpg\n" +
//					"sjyx/activity/votePic/phone/《公园夜晚的休闲》曾时雨-中南传媒.jpg,http://sjyximage.oss-cn-shenzhen.aliyuncs.com/sjyx/activity/votePic/phone/%E3%80%8A%E5%85%AC%E5%9B%AD%E5%A4%9C%E6%99%9A%E7%9A%84%E4%BC%91%E9%97%B2%E3%80%8B%E6%9B%BE%E6%97%B6%E9%9B%A8-%E4%B8%AD%E5%8D%97%E4%BC%A0%E5%AA%92.jpg\n" +
//					"sjyx/activity/votePic/phone/《国耀生辉》任美泽-永清环保.jpg,http://sjyximage.oss-cn-shenzhen.aliyuncs.com/sjyx/activity/votePic/phone/%E3%80%8A%E5%9B%BD%E8%80%80%E7%94%9F%E8%BE%89%E3%80%8B%E4%BB%BB%E7%BE%8E%E6%B3%BD-%E6%B0%B8%E6%B8%85%E7%8E%AF%E4%BF%9D.jpg\n" +
//					"sjyx/activity/votePic/phone/《光明》印文-湖南投资.jpg,http://sjyximage.oss-cn-shenzhen.aliyuncs.com/sjyx/activity/votePic/phone/%E3%80%8A%E5%85%89%E6%98%8E%E3%80%8B%E5%8D%B0%E6%96%87-%E6%B9%96%E5%8D%97%E6%8A%95%E8%B5%84.jpg\n" +
//					"sjyx/activity/votePic/phone/《僧推灯下门》文颖-现代投资.jpg,http://sjyximage.oss-cn-shenzhen.aliyuncs.com/sjyx/activity/votePic/phone/%E3%80%8A%E5%83%A7%E6%8E%A8%E7%81%AF%E4%B8%8B%E9%97%A8%E3%80%8B%E6%96%87%E9%A2%96-%E7%8E%B0%E4%BB%A3%E6%8A%95%E8%B5%84.jpg\n" +
//					"sjyx/activity/votePic/phone/《人与机械的纠缠》李崇伦-九芝堂.jpg,http://sjyximage.oss-cn-shenzhen.aliyuncs.com/sjyx/activity/votePic/phone/%E3%80%8A%E4%BA%BA%E4%B8%8E%E6%9C%BA%E6%A2%B0%E7%9A%84%E7%BA%A0%E7%BC%A0%E3%80%8B%E6%9D%8E%E5%B4%87%E4%BC%A6-%E4%B9%9D%E8%8A%9D%E5%A0%82.jpg\n" +
//					"sjyx/activity/votePic/phone/《交相辉映》李铭-湖南投资.jpg,http://sjyximage.oss-cn-shenzhen.aliyuncs.com/sjyx/activity/votePic/phone/%E3%80%8A%E4%BA%A4%E7%9B%B8%E8%BE%89%E6%98%A0%E3%80%8B%E6%9D%8E%E9%93%AD-%E6%B9%96%E5%8D%97%E6%8A%95%E8%B5%84.jpg\n" +
//					"sjyx/activity/votePic/phone/《0#块的钢筋世界》肖戈-现代投资.jpg,http://sjyximage.oss-cn-shenzhen.aliyuncs.com/sjyx/activity/votePic/phone/%E3%80%8A0%23%E5%9D%97%E7%9A%84%E9%92%A2%E7%AD%8B%E4%B8%96%E7%95%8C%E3%80%8B%E8%82%96%E6%88%88-%E7%8E%B0%E4%BB%A3%E6%8A%95%E8%B5%84.jpg\n" +
//					"sjyx/activity/votePic/phone/《一池荷影映炊烟》明健飞 -中南传媒.jpg,http://sjyximage.oss-cn-shenzhen.aliyuncs.com/sjyx/activity/votePic/phone/%E3%80%8A%E4%B8%80%E6%B1%A0%E8%8D%B7%E5%BD%B1%E6%98%A0%E7%82%8A%E7%83%9F%E3%80%8B%E6%98%8E%E5%81%A5%E9%A3%9E%20-%E4%B8%AD%E5%8D%97%E4%BC%A0%E5%AA%92.jpg\n" +
//					"sjyx/activity/votePic/phone/《“和平”与她》韩鑫芝-九芝堂.jpg,http://sjyximage.oss-cn-shenzhen.aliyuncs.com/sjyx/activity/votePic/phone/%E3%80%8A%E2%80%9C%E5%92%8C%E5%B9%B3%E2%80%9D%E4%B8%8E%E5%A5%B9%E3%80%8B%E9%9F%A9%E9%91%AB%E8%8A%9D-%E4%B9%9D%E8%8A%9D%E5%A0%82.jpg\n" +
//					"sjyx/activity/votePic/phone/《严谨》江艺玲-长高集团.jpg,http://sjyximage.oss-cn-shenzhen.aliyuncs.com/sjyx/activity/votePic/phone/%E3%80%8A%E4%B8%A5%E8%B0%A8%E3%80%8B%E6%B1%9F%E8%89%BA%E7%8E%B2-%E9%95%BF%E9%AB%98%E9%9B%86%E5%9B%A2.jpg\n" +
//					"sjyx/activity/votePic/phone/《云卷云舒》欧阳璐-湖南投资.jpg,http://sjyximage.oss-cn-shenzhen.aliyuncs.com/sjyx/activity/votePic/phone/%E3%80%8A%E4%BA%91%E5%8D%B7%E4%BA%91%E8%88%92%E3%80%8B%E6%AC%A7%E9%98%B3%E7%92%90-%E6%B9%96%E5%8D%97%E6%8A%95%E8%B5%84.jpg\n" +
//					"sjyx/activity/votePic/phone/《丰收》瞿西-中广天择.jpg,http://sjyximage.oss-cn-shenzhen.aliyuncs.com/sjyx/activity/votePic/phone/%E3%80%8A%E4%B8%B0%E6%94%B6%E3%80%8B%E7%9E%BF%E8%A5%BF-%E4%B8%AD%E5%B9%BF%E5%A4%A9%E6%8B%A9.jpg\n" +
//					"sjyx/activity/votePic/phone/《互拍的恋人》李建华-旗滨集团.jpg,http://sjyximage.oss-cn-shenzhen.aliyuncs.com/sjyx/activity/votePic/phone/%E3%80%8A%E4%BA%92%E6%8B%8D%E7%9A%84%E6%81%8B%E4%BA%BA%E3%80%8B%E6%9D%8E%E5%BB%BA%E5%8D%8E-%E6%97%97%E6%BB%A8%E9%9B%86%E5%9B%A2.jpg\n" +
//					"sjyx/activity/votePic/phone/《代替你去看整个世界》蔡艺龙-旗滨集团.jpg,http://sjyximage.oss-cn-shenzhen.aliyuncs.com/sjyx/activity/votePic/phone/%E3%80%8A%E4%BB%A3%E6%9B%BF%E4%BD%A0%E5%8E%BB%E7%9C%8B%E6%95%B4%E4%B8%AA%E4%B8%96%E7%95%8C%E3%80%8B%E8%94%A1%E8%89%BA%E9%BE%99-%E6%97%97%E6%BB%A8%E9%9B%86%E5%9B%A2.jpg\n" +
//					"sjyx/activity/votePic/phone/《傍晚的码头》王佯-华菱钢铁.jpg,http://sjyximage.oss-cn-shenzhen.aliyuncs.com/sjyx/activity/votePic/phone/%E3%80%8A%E5%82%8D%E6%99%9A%E7%9A%84%E7%A0%81%E5%A4%B4%E3%80%8B%E7%8E%8B%E4%BD%AF-%E5%8D%8E%E8%8F%B1%E9%92%A2%E9%93%81.jpg\n" +
//					"sjyx/activity/votePic/phone/《在水一方》杨婷-方正证券.jpg,http://sjyximage.oss-cn-shenzhen.aliyuncs.com/sjyx/activity/votePic/phone/%E3%80%8A%E5%9C%A8%E6%B0%B4%E4%B8%80%E6%96%B9%E3%80%8B%E6%9D%A8%E5%A9%B7-%E6%96%B9%E6%AD%A3%E8%AF%81%E5%88%B8.jpg\n" +
//					"sjyx/activity/votePic/phone/《墨西哥的蓝花楹》王况-楚天科技.jpg,http://sjyximage.oss-cn-shenzhen.aliyuncs.com/sjyx/activity/votePic/phone/%E3%80%8A%E5%A2%A8%E8%A5%BF%E5%93%A5%E7%9A%84%E8%93%9D%E8%8A%B1%E6%A5%B9%E3%80%8B%E7%8E%8B%E5%86%B5-%E6%A5%9A%E5%A4%A9%E7%A7%91%E6%8A%80.jpeg\n" +
//					"sjyx/activity/votePic/phone/《地球有多大》曾时雨-中南传媒.jpg,http://sjyximage.oss-cn-shenzhen.aliyuncs.com/sjyx/activity/votePic/phone/%E3%80%8A%E5%9C%B0%E7%90%83%E6%9C%89%E5%A4%9A%E5%A4%A7%E3%80%8B%E6%9B%BE%E6%97%B6%E9%9B%A8-%E4%B8%AD%E5%8D%97%E4%BC%A0%E5%AA%92.jpg\n" +
//					"sjyx/activity/votePic/phone/《奋斗人生》王新跃-华菱钢铁.jpg,http://sjyximage.oss-cn-shenzhen.aliyuncs.com/sjyx/activity/votePic/phone/%E3%80%8A%E5%A5%8B%E6%96%97%E4%BA%BA%E7%94%9F%E3%80%8B%E7%8E%8B%E6%96%B0%E8%B7%83-%E5%8D%8E%E8%8F%B1%E9%92%A2%E9%93%81.jpg\n" +
//					"sjyx/activity/votePic/phone/《太平山顶》王蔚-长沙银行.JPG,http://sjyximage.oss-cn-shenzhen.aliyuncs.com/sjyx/activity/votePic/phone/%E3%80%8A%E5%A4%AA%E5%B9%B3%E5%B1%B1%E9%A1%B6%E3%80%8B%E7%8E%8B%E8%94%9A-%E9%95%BF%E6%B2%99%E9%93%B6%E8%A1%8C.JPG\n" +
//					"sjyx/activity/votePic/phone/《敦煌之行-尽头》曹佳丽-华升股份.jpg,http://sjyximage.oss-cn-shenzhen.aliyuncs.com/sjyx/activity/votePic/phone/%E3%80%8A%E5%B0%BD%E5%A4%B4%E3%80%8B%E6%9B%B9%E4%BD%B3%E4%B8%BD-%E5%8D%8E%E5%8D%87%E8%82%A1%E4%BB%BD.jpg\n" +
//					"sjyx/activity/votePic/phone/《山寨晨早》尹海波-岳阳兴长.jpg,http://sjyximage.oss-cn-shenzhen.aliyuncs.com/sjyx/activity/votePic/phone/%E3%80%8A%E5%B1%B1%E5%AF%A8%E6%99%A8%E6%97%A9.%E5%B0%B9%E6%B5%B7%E6%B3%A2-%E5%B2%B3%E9%98%B3%E5%85%B4%E9%95%BF.jpeg\n" +
//					"sjyx/activity/votePic/phone/《岗位》曾时雨-中南传媒.jpg,http://sjyximage.oss-cn-shenzhen.aliyuncs.com/sjyx/activity/votePic/phone/%E3%80%8A%E5%B2%97%E4%BD%8D%E3%80%8B%E6%9B%BE%E6%97%B6%E9%9B%A8-%E4%B8%AD%E5%8D%97%E4%BC%A0%E5%AA%92.jpg\n" +
//					"sjyx/activity/votePic/phone/《川西路上的风景》周伯勋-中南传媒.jpg,http://sjyximage.oss-cn-shenzhen.aliyuncs.com/sjyx/activity/votePic/phone/%E3%80%8A%E5%B7%9D%E8%A5%BF%E8%B7%AF%E4%B8%8A%E7%9A%84%E9%A3%8E%E6%99%AF%E3%80%8B%E5%91%A8%E4%BC%AF%E5%8B%8B-%E4%B8%AD%E5%8D%97%E4%BC%A0%E5%AA%92.jpg\n" +
//					"sjyx/activity/votePic/phone/《工作的乐趣》彭孟池-湖南投资.jpg,http://sjyximage.oss-cn-shenzhen.aliyuncs.com/sjyx/activity/votePic/phone/%E3%80%8A%E5%B7%A5%E4%BD%9C%E7%9A%84%E4%B9%90%E8%B6%A3%E3%80%8B%E5%BD%AD%E5%AD%9F%E6%B1%A0-%E6%B9%96%E5%8D%97%E6%8A%95%E8%B5%84.jpg\n" +
//					"sjyx/activity/votePic/phone/《布达拉宫》杨君-九芝堂.jpg,http://sjyximage.oss-cn-shenzhen.aliyuncs.com/sjyx/activity/votePic/phone/%E3%80%8A%E5%B8%83%E8%BE%BE%E6%8B%89%E5%AE%AB%E3%80%8B%E6%9D%A8%E5%90%9B-%E4%B9%9D%E8%8A%9D%E5%A0%82.jpg\n" +
//					"sjyx/activity/votePic/phone/《希望的田野上》胡岚-长沙银行.jpg,http://sjyximage.oss-cn-shenzhen.aliyuncs.com/sjyx/activity/votePic/phone/%E3%80%8A%E5%B8%8C%E6%9C%9B%E7%9A%84%E7%94%B0%E9%87%8E%E4%B8%8A%E3%80%8B%E8%83%A1%E5%B2%9A-%E9%95%BF%E6%B2%99%E9%93%B6%E8%A1%8C.jpeg\n" +
//					"sjyx/activity/votePic/phone/《建筑美》邵皇矣-湖南投资.jpg,http://sjyximage.oss-cn-shenzhen.aliyuncs.com/sjyx/activity/votePic/phone/%E3%80%8A%E5%BB%BA%E7%AD%91%E7%BE%8E%E3%80%8B%E9%82%B5%E7%9A%87%E7%9F%A3-%E6%B9%96%E5%8D%97%E6%8A%95%E8%B5%84.jpg\n" +
//					"sjyx/activity/votePic/phone/《归园田居》刘扬-湖南投资.jpg,http://sjyximage.oss-cn-shenzhen.aliyuncs.com/sjyx/activity/votePic/phone/%E3%80%8A%E5%BD%92%E5%9B%AD%E7%94%B0%E5%B1%85%E3%80%8B%E5%88%98%E6%89%AC-%E6%B9%96%E5%8D%97%E6%8A%95%E8%B5%84.jpg\n" +
//					"sjyx/activity/votePic/phone/《彩虹瀑布》李程时黛-新五丰.jpg,http://sjyximage.oss-cn-shenzhen.aliyuncs.com/sjyx/activity/votePic/phone/%E3%80%8A%E5%BD%A9%E8%99%B9%E7%80%91%E5%B8%83%E3%80%8B%E6%9D%8E%E7%A8%8B%E6%97%B6%E9%BB%9B-%E6%96%B0%E4%BA%94%E4%B8%B0.jpg\n" +
//					"sjyx/activity/votePic/phone/《影韵－－树映湖中，花来镜里》胡艳-湖南投资.jpg,http://sjyximage.oss-cn-shenzhen.aliyuncs.com/sjyx/activity/votePic/phone/%E3%80%8A%E5%BD%B1%E9%9F%B5%EF%BC%8D%E6%A0%91%E6%98%A0%E6%B9%96%E4%B8%AD%EF%BC%8C%E8%8A%B1%E6%9D%A5%E9%95%9C%E9%87%8C%E3%80%8B%E8%83%A1%E8%89%B3-%E6%B9%96%E5%8D%97%E6%8A%95%E8%B5%84.jpg\n" +
//					"sjyx/activity/votePic/phone/《微笑服务》王湘颖-湖南投资.jpg,http://sjyximage.oss-cn-shenzhen.aliyuncs.com/sjyx/activity/votePic/phone/%E3%80%8A%E5%BE%AE%E7%AC%91%E6%9C%8D%E5%8A%A1%E3%80%8B%E7%8E%8B%E6%B9%98%E9%A2%96-%E6%B9%96%E5%8D%97%E6%8A%95%E8%B5%84.jpg\n" +
//					"sjyx/activity/votePic/phone/《我们》王思雨-方正证券.JPG,http://sjyximage.oss-cn-shenzhen.aliyuncs.com/sjyx/activity/votePic/phone/%E3%80%8A%E6%88%91%E4%BB%AC%E3%80%8B%E7%8E%8B%E6%80%9D%E9%9B%A8-%E6%96%B9%E6%AD%A3%E8%AF%81%E5%88%B8.JPG\n" +
//					"sjyx/activity/votePic/phone/《心中的殿堂》刘燕-九芝堂.jpg,http://sjyximage.oss-cn-shenzhen.aliyuncs.com/sjyx/activity/votePic/phone/%E3%80%8A%E5%BF%83%E4%B8%AD%E7%9A%84%E6%AE%BF%E5%A0%82%E3%80%8B%E5%88%98%E7%87%95-%E4%B9%9D%E8%8A%9D%E5%A0%82.jpg\n" +
//					"sjyx/activity/votePic/phone/《换照片上岗》雷楷铭-现代投资.jpg,http://sjyximage.oss-cn-shenzhen.aliyuncs.com/sjyx/activity/votePic/phone/%E3%80%8A%E6%8D%A2%E7%85%A7%E7%89%87%E4%B8%8A%E5%B2%97%E3%80%8B%E9%9B%B7%E6%A5%B7%E9%93%AD-%E7%8E%B0%E4%BB%A3%E6%8A%95%E8%B5%84.jpg\n" +
//					"sjyx/activity/votePic/phone/《护航》何小兰-湖南投资.jpg,http://sjyximage.oss-cn-shenzhen.aliyuncs.com/sjyx/activity/votePic/phone/%E3%80%8A%E6%8A%A4%E8%88%AA%E3%80%8B%E4%BD%95%E5%B0%8F%E5%85%B0-%E6%B9%96%E5%8D%97%E6%8A%95%E8%B5%84.jpg\n" +
//					"sjyx/activity/votePic/phone/《揽胜》王六合-凯美特气.jpg,http://sjyximage.oss-cn-shenzhen.aliyuncs.com/sjyx/activity/votePic/phone/%E3%80%8A%E6%8F%BD%E8%83%9C%E3%80%8B%E7%8E%8B%E5%85%AD%E5%90%88-%E5%87%AF%E7%BE%8E%E7%89%B9%E6%B0%94.jpg\n" +
//					"sjyx/activity/votePic/phone/《收工》刘杜建-现代投资.jpg,http://sjyximage.oss-cn-shenzhen.aliyuncs.com/sjyx/activity/votePic/phone/%E3%80%8A%E6%94%B6%E5%B7%A5%E3%80%8B%E5%88%98%E6%9D%9C%E5%BB%BA-%E7%8E%B0%E4%BB%A3%E6%8A%95%E8%B5%84.jpg\n" +
//					"sjyx/activity/votePic/phone/《撒花》王立荣-华菱钢铁.jpg,http://sjyximage.oss-cn-shenzhen.aliyuncs.com/sjyx/activity/votePic/phone/%E3%80%8A%E6%92%92%E8%8A%B1%E3%80%8B%E7%8E%8B%E7%AB%8B%E8%8D%A3-%E5%8D%8E%E8%8F%B1%E9%92%A2%E9%93%81.jpg\n" +
//					"sjyx/activity/votePic/phone/《整洁》刘利-湖南投资.jpg,http://sjyximage.oss-cn-shenzhen.aliyuncs.com/sjyx/activity/votePic/phone/%E3%80%8A%E6%95%B4%E6%B4%81%E3%80%8B%E5%88%98%E5%88%A9-%E6%B9%96%E5%8D%97%E6%8A%95%E8%B5%84.jpg\n" +
//					"sjyx/activity/votePic/phone/《文件打印中》王希媛-湖南投资.jpg,http://sjyximage.oss-cn-shenzhen.aliyuncs.com/sjyx/activity/votePic/phone/%E3%80%8A%E6%96%87%E4%BB%B6%E6%89%93%E5%8D%B0%E4%B8%AD%E3%80%8B%E7%8E%8B%E5%B8%8C%E5%AA%9B-%E6%B9%96%E5%8D%97%E6%8A%95%E8%B5%84.jpg\n" +
//					"sjyx/activity/votePic/phone/《无极》 印文-湖南投资.jpg,http://sjyximage.oss-cn-shenzhen.aliyuncs.com/sjyx/activity/votePic/phone/%E3%80%8A%E6%97%A0%E6%9E%81%E3%80%8B%20%E5%8D%B0%E6%96%87-%E6%B9%96%E5%8D%97%E6%8A%95%E8%B5%84.jpg\n" +
//					"sjyx/activity/votePic/phone/《无限风光》王六合-凯美特气.jpg,http://sjyximage.oss-cn-shenzhen.aliyuncs.com/sjyx/activity/votePic/phone/%E3%80%8A%E6%97%A0%E9%99%90%E9%A3%8E%E5%85%89%E3%80%8B%E7%8E%8B%E5%85%AD%E5%90%88-%E5%87%AF%E7%BE%8E%E7%89%B9%E6%B0%94.jpg\n" +
//					"sjyx/activity/votePic/phone/《日出东方—大上海》张平-湖南投资.jpg,http://sjyximage.oss-cn-shenzhen.aliyuncs.com/sjyx/activity/votePic/phone/%E3%80%8A%E6%97%A5%E5%87%BA%E4%B8%9C%E6%96%B9%E2%80%94%E5%A4%A7%E4%B8%8A%E6%B5%B7%E3%80%8B%E5%BC%A0%E5%B9%B3-%E6%B9%96%E5%8D%97%E6%8A%95%E8%B5%84.jpg\n" +
//					"sjyx/activity/votePic/phone/《日落时分》黄田-永清环保.JPG,http://sjyximage.oss-cn-shenzhen.aliyuncs.com/sjyx/activity/votePic/phone/%E3%80%8A%E6%97%A5%E8%90%BD%E6%97%B6%E5%88%86%E3%80%8B%E9%BB%84%E7%94%B0-%E6%B0%B8%E6%B8%85%E7%8E%AF%E4%BF%9D.JPG\n" +
//					"sjyx/activity/votePic/phone/《时光隧道》谢丽容-湖南投资.jpg,http://sjyximage.oss-cn-shenzhen.aliyuncs.com/sjyx/activity/votePic/phone/%E3%80%8A%E6%97%B6%E5%85%89%E9%9A%A7%E9%81%93%E3%80%8B%E8%B0%A2%E4%B8%BD%E5%AE%B9-%E6%B9%96%E5%8D%97%E6%8A%95%E8%B5%84.jpg\n" +
//					"sjyx/activity/votePic/phone/《明明如月》王端-长沙银行.JPG,http://sjyximage.oss-cn-shenzhen.aliyuncs.com/sjyx/activity/votePic/phone/%E3%80%8A%E6%98%8E%E6%98%8E%E5%A6%82%E6%9C%88%E3%80%8B%E7%8E%8B%E7%AB%AF-%E9%95%BF%E6%B2%99%E9%93%B6%E8%A1%8C.JPG\n" +
//					"sjyx/activity/votePic/phone/《春江水暖鸭先知》陈湘敏-中南传媒.jpg,http://sjyximage.oss-cn-shenzhen.aliyuncs.com/sjyx/activity/votePic/phone/%E3%80%8A%E6%98%A5%E6%B1%9F%E6%B0%B4%E6%9A%96%E9%B8%AD%E5%85%88%E7%9F%A5%E3%80%8B%E9%99%88%E6%B9%98%E6%95%8F-%E4%B8%AD%E5%8D%97%E4%BC%A0%E5%AA%92.jpg\n" +
//					"sjyx/activity/votePic/phone/《晨归》宋邦-泰嘉新材.jpg,http://sjyximage.oss-cn-shenzhen.aliyuncs.com/sjyx/activity/votePic/phone/%E3%80%8A%E6%99%A8%E5%BD%92%E3%80%8B%E5%AE%8B%E9%82%A6-%E6%B3%B0%E5%98%89%E6%96%B0%E6%9D%90.jpg\n" +
//					"sjyx/activity/votePic/phone/《晨曦》胡召辉-湖南投资.jpg,http://sjyximage.oss-cn-shenzhen.aliyuncs.com/sjyx/activity/votePic/phone/%E3%80%8A%E6%99%A8%E6%9B%A6%E3%80%8B%E8%83%A1%E5%8F%AC%E8%BE%89-%E6%B9%96%E5%8D%97%E6%8A%95%E8%B5%84.jpg\n" +
//					"sjyx/activity/votePic/phone/《晨练》裴建科-湖南投资.jpg,http://sjyximage.oss-cn-shenzhen.aliyuncs.com/sjyx/activity/votePic/phone/%E3%80%8A%E6%99%A8%E7%BB%83%E3%80%8B%E8%A3%B4%E5%BB%BA%E7%A7%91-%E6%B9%96%E5%8D%97%E6%8A%95%E8%B5%84.jpg\n" +
//					"sjyx/activity/votePic/phone/《晶莹世界》胡延-楚天科技.jpg,http://sjyximage.oss-cn-shenzhen.aliyuncs.com/sjyx/activity/votePic/phone/%E3%80%8A%E6%99%B6%E8%8E%B9%E4%B8%96%E7%95%8C%E3%80%8B%E8%83%A1%E5%BB%B6-%E6%A5%9A%E5%A4%A9%E7%A7%91%E6%8A%80.jpg\n" +
//					"sjyx/activity/votePic/phone/《最好的我们》王思雨-方正证券.JPG,http://sjyximage.oss-cn-shenzhen.aliyuncs.com/sjyx/activity/votePic/phone/%E3%80%8A%E6%9C%80%E5%A5%BD%E7%9A%84%E6%88%91%E4%BB%AC%E3%80%8B%E7%8E%8B%E6%80%9D%E9%9B%A8-%E6%96%B9%E6%AD%A3%E8%AF%81%E5%88%B8.JPG\n" +
//					"sjyx/activity/votePic/phone/《未来的仰望》王佐-长沙银行.JPG,http://sjyximage.oss-cn-shenzhen.aliyuncs.com/sjyx/activity/votePic/phone/%E3%80%8A%E6%9C%AA%E6%9D%A5%E7%9A%84%E4%BB%B0%E6%9C%9B%E3%80%8B%E7%8E%8B%E4%BD%90-%E9%95%BF%E6%B2%99%E9%93%B6%E8%A1%8C.JPG\n" +
//					"sjyx/activity/votePic/phone/《构成》王劲松-长沙银行.jpg,http://sjyximage.oss-cn-shenzhen.aliyuncs.com/sjyx/activity/votePic/phone/%E3%80%8A%E6%9E%84%E6%88%90%E3%80%8B%E7%8E%8B%E5%8A%B2%E6%9D%BE-%E9%95%BF%E6%B2%99%E9%93%B6%E8%A1%8C.jpg\n" +
//					"sjyx/activity/votePic/phone/《梳妆镜》王立荣-华菱钢铁.jpg,http://sjyximage.oss-cn-shenzhen.aliyuncs.com/sjyx/activity/votePic/phone/%E3%80%8A%E6%A2%B3%E5%A6%86%E9%95%9C%E3%80%8B%E7%8E%8B%E7%AB%8B%E8%8D%A3-%E5%8D%8E%E8%8F%B1%E9%92%A2%E9%93%81.jpg\n" +
//					"sjyx/activity/votePic/phone/《橙色云朵普漫天》黄亮-华菱钢铁.jpg,http://sjyximage.oss-cn-shenzhen.aliyuncs.com/sjyx/activity/votePic/phone/%E3%80%8A%E6%A9%99%E8%89%B2%E4%BA%91%E6%9C%B5%E6%99%AE%E6%BC%AB%E5%A4%A9%E3%80%8B%E9%BB%84%E4%BA%AE-%E5%8D%8E%E8%8F%B1%E9%92%A2%E9%93%81.jpg\n" +
//					"sjyx/activity/votePic/phone/《武功山的日出》余锦华-现代投资.jpg,http://sjyximage.oss-cn-shenzhen.aliyuncs.com/sjyx/activity/votePic/phone/%E3%80%8A%E6%AD%A6%E5%8A%9F%E5%B1%B1%E7%9A%84%E6%97%A5%E5%87%BA%E3%80%8B%E4%BD%99%E9%94%A6%E5%8D%8E-%E7%8E%B0%E4%BB%A3%E6%8A%95%E8%B5%84.jpg\n" +
//					"sjyx/activity/votePic/phone/《残荷》杨春丽-中南传媒.JPG,http://sjyximage.oss-cn-shenzhen.aliyuncs.com/sjyx/activity/votePic/phone/%E3%80%8A%E6%AE%8B%E8%8D%B7%E3%80%8B%E6%9D%A8%E6%98%A5%E4%B8%BD-%E4%B8%AD%E5%8D%97%E4%BC%A0%E5%AA%92.JPG\n" +
//					"sjyx/activity/votePic/phone/《水乡诗画》明健飞-中南传媒.jpg,http://sjyximage.oss-cn-shenzhen.aliyuncs.com/sjyx/activity/votePic/phone/%E3%80%8A%E6%B0%B4%E4%B9%A1%E8%AF%97%E7%94%BB%E3%80%8B%E6%98%8E%E5%81%A5%E9%A3%9E-%E4%B8%AD%E5%8D%97%E4%BC%A0%E5%AA%92.jpg\n" +
//					"sjyx/activity/votePic/phone/《水墨丹清》朱敏-湖南投资.jpg,http://sjyximage.oss-cn-shenzhen.aliyuncs.com/sjyx/activity/votePic/phone/%E3%80%8A%E6%B0%B4%E5%A2%A8%E4%B8%B9%E6%B8%85%E3%80%8B%E6%9C%B1%E6%95%8F-%E6%B9%96%E5%8D%97%E6%8A%95%E8%B5%84.jpg\n" +
//					"sjyx/activity/votePic/phone/《清凉一夏》欧艳芳-湖南投资.jpg,http://sjyximage.oss-cn-shenzhen.aliyuncs.com/sjyx/activity/votePic/phone/%E3%80%8A%E6%B8%85%E5%87%89%E4%B8%80%E5%A4%8F%E3%80%8B%E6%AC%A7%E8%89%B3%E8%8A%B3-%E6%B9%96%E5%8D%97%E6%8A%95%E8%B5%84.jpg\n" +
//					"sjyx/activity/votePic/phone/《渔民生活》李卓宇-湖南投资.jpg,http://sjyximage.oss-cn-shenzhen.aliyuncs.com/sjyx/activity/votePic/phone/%E3%80%8A%E6%B8%94%E6%B0%91%E7%94%9F%E6%B4%BB%E3%80%8B%E6%9D%8E%E5%8D%93%E5%AE%87-%E6%B9%96%E5%8D%97%E6%8A%95%E8%B5%84.jpg\n" +
//					"sjyx/activity/votePic/phone/《满城尽带黄金甲村》王铂茜-长沙银行.jpg,http://sjyximage.oss-cn-shenzhen.aliyuncs.com/sjyx/activity/votePic/phone/%E3%80%8A%E6%BB%A1%E5%9F%8E%E5%B0%BD%E5%B8%A6%E9%BB%84%E9%87%91%E7%94%B2%E6%9D%91%E3%80%8B%E7%8E%8B%E9%93%82%E8%8C%9C-%E9%95%BF%E6%B2%99%E9%93%B6%E8%A1%8C.jpg\n" +
//					"sjyx/activity/votePic/phone/《湛蓝》胡英-开元仪器.jpg,http://sjyximage.oss-cn-shenzhen.aliyuncs.com/sjyx/activity/votePic/phone/%E3%80%8A%E6%B9%9B%E8%93%9D%E3%80%8B%E8%83%A1%E8%8B%B1-%E5%BC%80%E5%85%83%E4%BB%AA%E5%99%A8.jpg\n" +
//					"sjyx/activity/votePic/phone/《滩涂》阳冬华-华菱钢铁.jpg,http://sjyximage.oss-cn-shenzhen.aliyuncs.com/sjyx/activity/votePic/phone/%E3%80%8A%E6%BB%A9%E6%B6%82%E3%80%8B%E9%98%B3%E5%86%AC%E5%8D%8E-%E5%8D%8E%E8%8F%B1%E9%92%A2%E9%93%81.jpg\n" +
//					"sjyx/activity/votePic/phone/《独立天地间》胡岚-长沙银行.jpg,http://sjyximage.oss-cn-shenzhen.aliyuncs.com/sjyx/activity/votePic/phone/%E3%80%8A%E7%8B%AC%E7%AB%8B%E5%A4%A9%E5%9C%B0%E9%97%B4%E3%80%8B%E8%83%A1%E5%B2%9A-%E9%95%BF%E6%B2%99%E9%93%B6%E8%A1%8C.jpeg\n" +
//					"sjyx/activity/votePic/phone/《生意》肖莉波-中南传媒.jpg,http://sjyximage.oss-cn-shenzhen.aliyuncs.com/sjyx/activity/votePic/phone/%E3%80%8A%E7%94%9F%E6%84%8F%E3%80%8B%E8%82%96%E8%8E%89%E6%B3%A2-%E4%B8%AD%E5%8D%97%E4%BC%A0%E5%AA%92.jpg\n" +
//					"sjyx/activity/votePic/phone/《瘦影风中立，秋来独自芬》黄亮-华菱钢铁.jpg,http://sjyximage.oss-cn-shenzhen.aliyuncs.com/sjyx/activity/votePic/phone/%E3%80%8A%E7%98%A6%E5%BD%B1%E9%A3%8E%E4%B8%AD%E7%AB%8B%EF%BC%8C%E7%A7%8B%E6%9D%A5%E7%8B%AC%E8%87%AA%E8%8A%AC%E3%80%8B%E9%BB%84%E4%BA%AE-%E5%8D%8E%E8%8F%B1%E9%92%A2%E9%93%81.jpg\n" +
//					"sjyx/activity/votePic/phone/《画中人》曾时雨-中南传媒.jpg,http://sjyximage.oss-cn-shenzhen.aliyuncs.com/sjyx/activity/votePic/phone/%E3%80%8A%E7%94%BB%E4%B8%AD%E4%BA%BA%E3%80%8B%E6%9B%BE%E6%97%B6%E9%9B%A8-%E4%B8%AD%E5%8D%97%E4%BC%A0%E5%AA%92.jpg\n" +
//					"sjyx/activity/votePic/phone/《看火岗位》刘时庄-华菱钢铁.jpg,http://sjyximage.oss-cn-shenzhen.aliyuncs.com/sjyx/activity/votePic/phone/%E3%80%8A%E7%9C%8B%E7%81%AB%E5%B2%97%E4%BD%8D%20%E3%80%8B%E5%88%98%E6%97%B6%E5%BA%84-%E5%8D%8E%E8%8F%B1%E9%92%A2%E9%93%81.jpg\n" +
//					"sjyx/activity/votePic/phone/《百年工程》肖戈-现代投资.jpg,http://sjyximage.oss-cn-shenzhen.aliyuncs.com/sjyx/activity/votePic/phone/%E3%80%8A%E7%99%BE%E5%B9%B4%E5%B7%A5%E7%A8%8B%E3%80%8B%E8%82%96%E6%88%88-%E7%8E%B0%E4%BB%A3%E6%8A%95%E8%B5%84.jpg\n" +
//					"sjyx/activity/votePic/phone/《秋千》王瑞-长沙银行.JPG,http://sjyximage.oss-cn-shenzhen.aliyuncs.com/sjyx/activity/votePic/phone/%E3%80%8A%E7%A7%8B%E5%8D%83%E3%80%8B%E7%8E%8B%E7%91%9E-%E9%95%BF%E6%B2%99%E9%93%B6%E8%A1%8C.JPG\n" +
//					"sjyx/activity/votePic/phone/《矮寨大桥》张建中-华菱钢铁.jpg,http://sjyximage.oss-cn-shenzhen.aliyuncs.com/sjyx/activity/votePic/phone/%E3%80%8A%E7%9F%AE%E5%AF%A8%E5%A4%A7%E6%A1%A5%E3%80%8B%E5%BC%A0%E5%BB%BA%E4%B8%AD-%E5%8D%8E%E8%8F%B1%E9%92%A2%E9%93%81.jpeg\n" +
//					"sjyx/activity/votePic/phone/《秋收》瞿西-中广天择.jpg,http://sjyximage.oss-cn-shenzhen.aliyuncs.com/sjyx/activity/votePic/phone/%E3%80%8A%E7%A7%8B%E6%94%B6%E3%80%8B%E7%9E%BF%E8%A5%BF-%E4%B8%AD%E5%B9%BF%E5%A4%A9%E6%8B%A9.jpg\n" +
//					"sjyx/activity/votePic/phone/《空中舞台》王新跃-华菱钢铁.jpg,http://sjyximage.oss-cn-shenzhen.aliyuncs.com/sjyx/activity/votePic/phone/%E3%80%8A%E7%A9%BA%E4%B8%AD%E8%88%9E%E5%8F%B0%E3%80%8B%E7%8E%8B%E6%96%B0%E8%B7%83-%E5%8D%8E%E8%8F%B1%E9%92%A2%E9%93%81.jpg\n" +
//					"sjyx/activity/votePic/phone/《秋日》蔡劲锋-爱尔眼科.jpg,http://sjyximage.oss-cn-shenzhen.aliyuncs.com/sjyx/activity/votePic/phone/%E3%80%8A%E7%A7%8B%E6%97%A5%E3%80%8B%E8%94%A1%E5%8A%B2%E9%94%8B-%E7%88%B1%E5%B0%94%E7%9C%BC%E7%A7%91.jpg\n" +
//					"sjyx/activity/votePic/phone/《等候》雷楷铭-现代投资.jpg,http://sjyximage.oss-cn-shenzhen.aliyuncs.com/sjyx/activity/votePic/phone/%E3%80%8A%E7%AD%89%E5%80%99%E3%80%8B%E9%9B%B7%E6%A5%B7%E9%93%AD-%E7%8E%B0%E4%BB%A3%E6%8A%95%E8%B5%84.jpg\n" +
//					"sjyx/activity/votePic/phone/《童年的记忆》于广生-永清环保.jpg,http://sjyximage.oss-cn-shenzhen.aliyuncs.com/sjyx/activity/votePic/phone/%E3%80%8A%E7%AB%A5%E5%B9%B4%E7%9A%84%E8%AE%B0%E5%BF%86%E3%80%8B%E4%BA%8E%E5%B9%BF%E7%94%9F-%E6%B0%B8%E6%B8%85%E7%8E%AF%E4%BF%9D.jpg\n" +
//					"sjyx/activity/votePic/phone/《策马奔驰》江艺玲-长高集团.jpg,http://sjyximage.oss-cn-shenzhen.aliyuncs.com/sjyx/activity/votePic/phone/%E3%80%8A%E7%AD%96%E9%A9%AC%E5%A5%94%E9%A9%B0%E3%80%8B%E6%B1%9F%E8%89%BA%E7%8E%B2-%E9%95%BF%E9%AB%98%E9%9B%86%E5%9B%A2.jpg\n" +
//					"sjyx/activity/votePic/phone/《绽放》王徉-华菱钢铁.jpg,http://sjyximage.oss-cn-shenzhen.aliyuncs.com/sjyx/activity/votePic/phone/%E3%80%8A%E7%BB%BD%E6%94%BE%E3%80%8B%E7%8E%8B%E5%BE%89-%E5%8D%8E%E8%8F%B1%E9%92%A2%E9%93%81.jpg\n" +
//					"sjyx/activity/votePic/phone/《纽约落日》易佳丽-飞鹿股份.jpg,http://sjyximage.oss-cn-shenzhen.aliyuncs.com/sjyx/activity/votePic/phone/%E3%80%8A%E7%BA%BD%E7%BA%A6%E8%90%BD%E6%97%A5%E3%80%8B%E6%98%93%E4%BD%B3%E4%B8%BD-%E9%A3%9E%E9%B9%BF%E8%82%A1%E4%BB%BD.jpg\n" +
//					"sjyx/activity/votePic/phone/《羞涩的舞者》王虹-凯美特气.jpg,http://sjyximage.oss-cn-shenzhen.aliyuncs.com/sjyx/activity/votePic/phone/%E3%80%8A%E7%BE%9E%E6%B6%A9%E7%9A%84%E8%88%9E%E8%80%85%E3%80%8B%E7%8E%8B%E8%99%B9-%E5%87%AF%E7%BE%8E%E7%89%B9%E6%B0%94.jpg\n" +
//					"sjyx/activity/votePic/phone/《老镇时光》明健飞-中南传媒.jpg,http://sjyximage.oss-cn-shenzhen.aliyuncs.com/sjyx/activity/votePic/phone/%E3%80%8A%E8%80%81%E9%95%87%E6%97%B6%E5%85%89%E3%80%8B%E6%98%8E%E5%81%A5%E9%A3%9E-%E4%B8%AD%E5%8D%97%E4%BC%A0%E5%AA%92.jpg\n" +
//					"sjyx/activity/votePic/phone/《老腔》肖莉波-中南传媒.jpg,http://sjyximage.oss-cn-shenzhen.aliyuncs.com/sjyx/activity/votePic/phone/%E3%80%8A%E8%80%81%E8%85%94%E3%80%8B%E8%82%96%E8%8E%89%E6%B3%A2-%E4%B8%AD%E5%8D%97%E4%BC%A0%E5%AA%92.jpg\n" +
//					"sjyx/activity/votePic/phone/《苗家赶场》李稼贝-现代投资.jpg,http://sjyximage.oss-cn-shenzhen.aliyuncs.com/sjyx/activity/votePic/phone/%E3%80%8A%E8%8B%97%E5%AE%B6%E8%B5%B6%E5%9C%BA%E3%80%8B%E6%9D%8E%E7%A8%BC%E8%B4%9D-%E7%8E%B0%E4%BB%A3%E6%8A%95%E8%B5%84.jpg\n" +
//					"sjyx/activity/votePic/phone/《虹》周彧-现代投资.jpg,http://sjyximage.oss-cn-shenzhen.aliyuncs.com/sjyx/activity/votePic/phone/%E3%80%8A%E8%99%B9%E3%80%8B%E5%91%A8%E5%BD%A7-%E7%8E%B0%E4%BB%A3%E6%8A%95%E8%B5%84.jpg\n" +
//					"sjyx/activity/votePic/phone/《西藏风光》杨君-九芝堂.jpg,http://sjyximage.oss-cn-shenzhen.aliyuncs.com/sjyx/activity/votePic/phone/%E3%80%8A%E8%A5%BF%E8%97%8F%E9%A3%8E%E5%85%89%E3%80%8B%E6%9D%A8%E5%90%9B-%E4%B9%9D%E8%8A%9D%E5%A0%82.jpg\n" +
//					"sjyx/activity/votePic/phone/《请扫码支付》雷楷铭-现代投资.jpg,http://sjyximage.oss-cn-shenzhen.aliyuncs.com/sjyx/activity/votePic/phone/%E3%80%8A%E8%AF%B7%E6%89%AB%E7%A0%81%E6%94%AF%E4%BB%98%E3%80%8B%E9%9B%B7%E6%A5%B7%E9%93%AD-%E7%8E%B0%E4%BB%A3%E6%8A%95%E8%B5%84.jpg\n" +
//					"sjyx/activity/votePic/phone/《读书会》余波-湖南投资.jpg,http://sjyximage.oss-cn-shenzhen.aliyuncs.com/sjyx/activity/votePic/phone/%E3%80%8A%E8%AF%BB%E4%B9%A6%E4%BC%9A%E3%80%8B%E4%BD%99%E6%B3%A2-%E6%B9%96%E5%8D%97%E6%8A%95%E8%B5%84.jpg\n" +
//					"sjyx/activity/votePic/phone/《资江靓韵》雷振梁-南岭民爆.jpg,http://sjyximage.oss-cn-shenzhen.aliyuncs.com/sjyx/activity/votePic/phone/%E3%80%8A%E8%B5%84%E6%B1%9F%E9%9D%93%E9%9F%B5%E3%80%8B%E9%9B%B7%E6%8C%AF%E6%A2%81-%E5%8D%97%E5%B2%AD%E6%B0%91%E7%88%86.jpg\n" +
//					"sjyx/activity/votePic/phone/《赶海》印文 湖南投资.jpg,http://sjyximage.oss-cn-shenzhen.aliyuncs.com/sjyx/activity/votePic/phone/%E3%80%8A%E8%B5%B6%E6%B5%B7%E3%80%8B%E5%8D%B0%E6%96%87%20%E6%B9%96%E5%8D%97%E6%8A%95%E8%B5%84.jpg\n" +
//					"sjyx/activity/votePic/phone/《超人》李珍妮-湖南投资.jpg,http://sjyximage.oss-cn-shenzhen.aliyuncs.com/sjyx/activity/votePic/phone/%E3%80%8A%E8%B6%85%E4%BA%BA%E3%80%8B%E6%9D%8E%E7%8F%8D%E5%A6%AE-%E6%B9%96%E5%8D%97%E6%8A%95%E8%B5%84.jpg\n" +
//					"sjyx/activity/votePic/phone/《迷雾东江湖》高洁-方正证券.jpg,http://sjyximage.oss-cn-shenzhen.aliyuncs.com/sjyx/activity/votePic/phone/%E3%80%8A%E8%BF%B7%E9%9B%BE%E4%B8%9C%E6%B1%9F%E6%B9%96%E3%80%8B%E9%AB%98%E6%B4%81-%E6%96%B9%E6%AD%A3%E8%AF%81%E5%88%B8.jpg\n" +
//					"sjyx/activity/votePic/phone/《透》安清萍-长沙银行.jpg,http://sjyximage.oss-cn-shenzhen.aliyuncs.com/sjyx/activity/votePic/phone/%E3%80%8A%E9%80%8F%E3%80%8B%E5%AE%89%E6%B8%85%E8%90%8D-%E9%95%BF%E6%B2%99%E9%93%B6%E8%A1%8C.jpg\n" +
//					"sjyx/activity/votePic/phone/《那年的星空》王慧敏-凯美特气.jpg,http://sjyximage.oss-cn-shenzhen.aliyuncs.com/sjyx/activity/votePic/phone/%E3%80%8A%E9%82%A3%E5%B9%B4%E7%9A%84%E6%98%9F%E7%A9%BA%E3%80%8B%E7%8E%8B%E6%85%A7%E6%95%8F-%E5%87%AF%E7%BE%8E%E7%89%B9%E6%B0%94.jpg\n" +
//					"sjyx/activity/votePic/phone/《邵阳火车站魏源广场》雷振梁-南岭民爆.jpg,http://sjyximage.oss-cn-shenzhen.aliyuncs.com/sjyx/activity/votePic/phone/%E3%80%8A%E9%82%B5%E9%98%B3%E7%81%AB%E8%BD%A6%E7%AB%99%E9%AD%8F%E6%BA%90%E5%B9%BF%E5%9C%BA%E3%80%8B%E9%9B%B7%E6%8C%AF%E6%A2%81-%E5%8D%97%E5%B2%AD%E6%B0%91%E7%88%86.jpg\n" +
//					"sjyx/activity/votePic/phone/《金洒贡嘎》尹海波-岳阳兴长.jpg,http://sjyximage.oss-cn-shenzhen.aliyuncs.com/sjyx/activity/votePic/phone/%E3%80%8A%E9%87%91%E6%B4%92%E8%B4%A1%E5%98%8E%E3%80%8B%E5%B0%B9%E6%B5%B7%E6%B3%A2-%E5%B2%B3%E9%98%B3%E5%85%B4%E9%95%BF.jpg\n" +
//					"sjyx/activity/votePic/phone/《钢城基石》易幼清-华菱钢铁.jpg,http://sjyximage.oss-cn-shenzhen.aliyuncs.com/sjyx/activity/votePic/phone/%E3%80%8A%E9%92%A2%E5%9F%8E%E5%9F%BA%E7%9F%B3%E3%80%8B%E6%98%93%E5%B9%BC%E6%B8%85-%E5%8D%8E%E8%8F%B1%E9%92%A2%E9%93%81.jpg\n" +
//					"sjyx/activity/votePic/phone/《锦绣前程》苏仲武-湖南投资.jpg,http://sjyximage.oss-cn-shenzhen.aliyuncs.com/sjyx/activity/votePic/phone/%E3%80%8A%E9%94%A6%E7%BB%A3%E5%89%8D%E7%A8%8B%E3%80%8B%E8%8B%8F%E4%BB%B2%E6%AD%A6-%E6%B9%96%E5%8D%97%E6%8A%95%E8%B5%84.jpg\n" +
//					"sjyx/activity/votePic/phone/《长沙大剧院》胡延-楚天科技.jpg,http://sjyximage.oss-cn-shenzhen.aliyuncs.com/sjyx/activity/votePic/phone/%E3%80%8A%E9%95%BF%E6%B2%99%E5%A4%A7%E5%89%A7%E9%99%A2%E3%80%8B%E8%83%A1%E5%BB%B6-%E6%A5%9A%E5%A4%A9%E7%A7%91%E6%8A%80.jpg\n" +
//					"sjyx/activity/votePic/phone/《阅台》印文-湖南投资.jpg,http://sjyximage.oss-cn-shenzhen.aliyuncs.com/sjyx/activity/votePic/phone/%E3%80%8A%E9%98%85%E5%8F%B0%E3%80%8B%E5%8D%B0%E6%96%87-%E6%B9%96%E5%8D%97%E6%8A%95%E8%B5%84.jpg\n" +
//					"sjyx/activity/votePic/phone/《雨中博弈》李建华-旗滨集团.jpg,http://sjyximage.oss-cn-shenzhen.aliyuncs.com/sjyx/activity/votePic/phone/%E3%80%8A%E9%9B%A8%E4%B8%AD%E5%8D%9A%E5%BC%88%E3%80%8B%E6%9D%8E%E5%BB%BA%E5%8D%8E-%E6%97%97%E6%BB%A8%E9%9B%86%E5%9B%A2.jpg\n" +
//					"sjyx/activity/votePic/phone/《雪中人》佟小俊-湖南投资.jpg,http://sjyximage.oss-cn-shenzhen.aliyuncs.com/sjyx/activity/votePic/phone/%E3%80%8A%E9%9B%AA%E4%B8%AD%E4%BA%BA%E3%80%8B%E4%BD%9F%E5%B0%8F%E4%BF%8A-%E6%B9%96%E5%8D%97%E6%8A%95%E8%B5%84.jpg\n" +
//					"sjyx/activity/votePic/phone/《风云》沈龙-长沙银行.jpg,http://sjyximage.oss-cn-shenzhen.aliyuncs.com/sjyx/activity/votePic/phone/%E3%80%8A%E9%A3%8E%E4%BA%91%E3%80%8B%E6%B2%88%E9%BE%99-%E9%95%BF%E6%B2%99%E9%93%B6%E8%A1%8C.jpg\n" +
//					"sjyx/activity/votePic/phone/《风采》阳冬华-华菱钢铁.jpg,http://sjyximage.oss-cn-shenzhen.aliyuncs.com/sjyx/activity/votePic/phone/%E3%80%8A%E9%A3%8E%E9%87%87%E3%80%8B%E9%98%B3%E5%86%AC%E5%8D%8E-%E5%8D%8E%E8%8F%B1%E9%92%A2%E9%93%81.jpg\n" +
//					"sjyx/activity/votePic/phone/《香格里拉的天空》崔钰镅-湖南投资.jpg,http://sjyximage.oss-cn-shenzhen.aliyuncs.com/sjyx/activity/votePic/phone/%E3%80%8A%E9%A6%99%E6%A0%BC%E9%87%8C%E6%8B%89%E7%9A%84%E5%A4%A9%E7%A9%BA%E3%80%8B%E5%B4%94%E9%92%B0%E9%95%85-%E6%B9%96%E5%8D%97%E6%8A%95%E8%B5%84.jpg\n" +
//					"sjyx/activity/votePic/phone/《岳阳坦度镇，高铁从艾草地边穿过》陈正-中南传媒.jpg,http://sjyximage.oss-cn-shenzhen.aliyuncs.com/sjyx/activity/votePic/phone/%E3%80%8A%E9%AB%98%E9%93%81%E4%BB%8E%E8%89%BE%E8%8D%89%E5%9C%B0%E8%BE%B9%E7%A9%BF%E8%BF%87%E3%80%8B%E9%99%88%E6%AD%A3-%E4%B8%AD%E5%8D%97%E4%BC%A0%E5%AA%92.jpg\n" +
//					"sjyx/activity/votePic/phone/《高铁时代》雷振梁-南岭民爆.jpg,http://sjyximage.oss-cn-shenzhen.aliyuncs.com/sjyx/activity/votePic/phone/%E3%80%8A%E9%AB%98%E9%93%81%E6%97%B6%E4%BB%A3%E3%80%8B%E9%9B%B7%E6%8C%AF%E6%A2%81-%E5%8D%97%E5%B2%AD%E6%B0%91%E7%88%86.jpg\n" +
//					"sjyx/activity/votePic/phone/《高铁进我家》徐善庭-时代新材.jpg,http://sjyximage.oss-cn-shenzhen.aliyuncs.com/sjyx/activity/votePic/phone/%E3%80%8A%E9%AB%98%E9%93%81%E8%BF%9B%E6%88%91%E5%AE%B6%E3%80%8B%E5%BE%90%E5%96%84%E5%BA%AD-%E6%97%B6%E4%BB%A3%E6%96%B0%E6%9D%90.jpg\n" +
//					"sjyx/activity/votePic/phone/《麻布.天鹅岛》欧红-华升股份.jpg,http://sjyximage.oss-cn-shenzhen.aliyuncs.com/sjyx/activity/votePic/phone/%E3%80%8A%E9%BA%BB%E5%B8%83.%E5%A4%A9%E9%B9%85%E5%B2%9B%E3%80%8B%E6%AC%A7%E7%BA%A2-%E5%8D%8E%E5%8D%87%E8%82%A1%E4%BB%BD.jpg\n" +
//					"sjyx/activity/votePic/phone/《黄昏》李梅-湖南投资.jpg,http://sjyximage.oss-cn-shenzhen.aliyuncs.com/sjyx/activity/votePic/phone/%E3%80%8A%E9%BB%84%E6%98%8F%E3%80%8B%E6%9D%8E%E6%A2%85-%E6%B9%96%E5%8D%97%E6%8A%95%E8%B5%84.jpg\n" +
//					"sjyx/activity/votePic/phone/《齐心协力》尹海波-岳阳兴长.jpg,http://sjyximage.oss-cn-shenzhen.aliyuncs.com/sjyx/activity/votePic/phone/%E3%80%8A%E9%BD%90%E5%BF%83%E5%8D%8F%E5%8A%9B%E3%80%8B%E5%B0%B9%E6%B5%B7%E6%B3%A2-%E5%B2%B3%E9%98%B3%E5%85%B4%E9%95%BF.jpg\n";
//			String linePhone = null;
//            String lineCamera = null;
////            System.out.println("2");
//            int count=0;
//            for (Row row : sheet) {
//                for (Cell cell : row) {
//                    if (cell.getCellType()==1) {//字符串
//                        if(cell.getRichStringCellValue().getString().equals("手机类") ||cell.getRichStringCellValue().getString().equals("相机类") || cell.getRichStringCellValue().getString().equals("序号")){
//                                continue;
//                        }
////                        System.out.print(cell.getRichStringCellValue().getString());
//                        String item[] = line.split(",");//CSV格式文件为逗号分隔符文件，这里根据逗号切分
////                        System.out.println("sssstr="+item.length);
////                        System.out.println("i");
//                        for(int i=0;i<item.length;i++){
//
//                            if(item[i].indexOf("《") > -1) {
////                                System.out.println("j");
////                                System.out.println("str1"+cell.getRichStringCellValue().getString()+"////"+item[i].substring(item[i].indexOf("《"), item[i].lastIndexOf(".")).replaceAll(" ",""));
//                                String zmm=cell.getRichStringCellValue().getString();
//                                zmm=zmm.substring((zmm.indexOf("《")+1),zmm.indexOf("》"));
////                                System.out.println("dsda"+item[i].substring(item[i].indexOf("《"), item[i].lastIndexOf(".")).replaceAll(" ","")+"zmm"+zmm);
//                                if(item[i].substring((item[i].indexOf("《")+1),item[i].indexOf("》")).equals(zmm)){
//                                    String url="";
//                                    if(item[i+1].indexOf(".jpg") != -1){
//                                        url=item[i+1].substring(0,item[i+1].indexOf(".jpg")+4);
//                                    }else if(item[i+1].indexOf(".JPG") != -1){
//                                        url=item[i+1].substring(0,item[i+1].indexOf(".JPG")+4);
//                                    }
//                                    tbClubPic.setPicurl(url);
//                                    String[] str=cell.getRichStringCellValue().getString().replaceAll("《",",").replaceAll("》",",").split(",");
//                                    tbClubPic.setAuthorname(str[2]);
//                                    tbClubPic.setPicname(str[1]+"(手机类)");
////									tbClubPic.setPicname(str[1]+"(相机类)");
////                                    tbClubPic.setDevicetype(1);	//相机类
//									tbClubPic.setDevicetype(2);	//手机类
////                                    System.out.println("dsda"+item[i].substring((item[i].indexOf("《")+1),item[i].indexOf("》")).replaceAll(" ","")+"zmm"+zmm);
////                                    System.out.println("setPicurl="+url);
////                                    System.out.println("setAuthorname="+str[2]);
////                                    System.out.println("setPicname="+str[1]+"(手机类)");
////                                    System.out.println("count="+count);
//                                    count++;
//                                    iTb_club_picService.save(tbClubPic);
//                                }
////                                System.out.println(item[i].substring(item[i].indexOf("《"), item[i].lastIndexOf(".")).replaceAll(" ",""));
//                            }
//                        }
//
//                    }
//                }
//            }
//            System.out.println("count="+count);
////			jsonResult.setData(tbClubPic.getPicurl());
//			jsonResult.setCode(Constants.SUCCESS_CODE);
//			jsonResult.setMessage(Constants.SUCCESS_DATA);
//		} catch (Exception e)
//		{
//			jsonResult.setCode(Constants.SERVICE_EXCEPTION_CODE);
//			jsonResult.setMessage(Constants.SERVICE_EXCEPTION_DATA);
//			log.debug("异常:"+e);
//		}
//		return ajaxJson(JSONObject.toJSONString(jsonResult), response);
//	}

	/**
	 * 搜索
	 * @param content
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/app/searchVotePic")
	public String searchVotePic(String content, HttpServletRequest request, HttpServletResponse response)
	{
		JsonResult jsonResult = new JsonResult();
		try
		{
			String openId = getOpenId(request, response);
//			String openId = "oZO5n5KPJpwLWrJ2HRIe7pQWqhVc";
			List<Object[]> result = iTb_club_picService.getVoteInfo(Constants.VOTEID);
			Object[] obj = result.get(0);
			Date startTime = (Date)obj[0];	//起始时间
			Date endTime = (Date)obj[1];	//结束时间
			if (null != content || "".equals(content)) {
				StringBuffer sb = new StringBuffer("SELECT p.*,s.VoteNum AS num FROM tb_club_pic AS p LEFT JOIN (SELECT * FROM tb_club_stat WHERE DateDiff(dd,UpdateTime,getdate())=0 AND OpenId = ?)s ON p.id =s.PicId  WHERE ");
				if (VoteTime.isNumeric(content)){
					sb.append(" p.id = ").append(content);
				}else {
					sb.append(" p.authorname LIKE '%").append(content).append("%'");
				}
				sb.append(" order by p.id asc");
				List<Object[]> list = iTb_club_picService.findBySql(sb.toString(),openId);

				List back = new ArrayList();
				if (list.size() > 0){
					back = PicList.picListUtilObj(list,startTime,endTime);
				}
				jsonResult.setData(back);
			}
			jsonResult.setCode(Constants.SUCCESS_CODE);
			jsonResult.setMessage(Constants.SUCCESS_DATA);
		}
		catch (Exception e)
		{
			jsonResult.setCode(Constants.SERVICE_EXCEPTION_CODE);
			jsonResult.setMessage(Constants.SERVICE_EXCEPTION_DATA);
			log.debug("异常:"+e);
		}
		return ajaxJson(JSONObject.toJSONString(jsonResult), response);
	}

	/**
	 * 图片详情
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/voteSwitch")
	public String voteSwitch(HttpServletRequest request, HttpServletResponse response)
	{
		JsonResult jsonResult = new JsonResult();
		try
		{
			List<Object[]> result = iTb_club_picService.getVoteInfo(Constants.VOTEID);
			Object[] obj = result.get(0);
			Date time = (Date)obj[2];	//打开时间
			jsonResult.setData(VoteTime.AbigBCalendar(DateUtil.getDate(),time));
            jsonResult.setCode(Constants.SUCCESS_CODE);
            jsonResult.setMessage(Constants.SUCCESS_DATA);
		}
		catch (Exception e)
		{
			jsonResult.setCode(Constants.SERVICE_EXCEPTION_CODE);
			jsonResult.setMessage(Constants.SERVICE_EXCEPTION_DATA);
			log.debug("异常:"+e);
		}
		return ajaxJson(JSONObject.toJSONString(jsonResult), response);
	}

//    /**
//     * 图片详情
//     * @param request
//     * @param response
//     * @return
//     */
//    @RequestMapping(value = "/test")
//    public String test(HttpServletRequest request, HttpServletResponse response)
//    {
//        JsonResult jsonResult = new JsonResult();
//        try
//        {
//            List<Tb_club_stat> temp = iTb_club_statService.findByHQL("from Tb_club_stat o where DateDiff(dd,o.updatetime,getdate())=0 and o.picid = ? and o.openid = ?",69,"oZO5n5K9mMlrGFhXSP1z_tVMDFIY");
//            System.out.println("---->>>>>"+temp.size());
//            jsonResult.setCode(Constants.SUCCESS_CODE);
//            jsonResult.setMessage(Constants.SUCCESS_DATA);
//        }
//        catch (Exception e)
//        {
//            jsonResult.setCode(Constants.SERVICE_EXCEPTION_CODE);
//            jsonResult.setMessage(Constants.SERVICE_EXCEPTION_DATA);
//            log.debug("异常:"+e);
//        }
//        return ajaxJson(JSONObject.toJSONString(jsonResult), response);
//    }
}
