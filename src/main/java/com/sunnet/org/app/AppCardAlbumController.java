package com.sunnet.org.app;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.alibaba.fastjson.JSONObject;
import com.sunnet.framework.controller.BaseController;
import com.sunnet.framework.pagenation.PageBean;
import com.sunnet.framework.pagenation.QueryResult;
import com.sunnet.framework.util.JsonResult;
import com.sunnet.org.albumcard.model.Tb_albumcard;
import com.sunnet.org.albumcard.model.Tb_albumcardrecord;
import com.sunnet.org.albumcard.service.ITb_albumcardService;
import com.sunnet.org.albumcard.service.ITb_albumcardrecordService;
import com.sunnet.org.albumcard.vo.Tb_albumcardUtil;
import com.sunnet.org.albumcard.vo.Tb_albumcardrecordUtil;
import com.sunnet.org.information.model.Tb_doc;
import com.sunnet.org.information.vo.Tb_docUtil;
import com.sunnet.org.member.model.Tb_member;
import com.sunnet.org.member.model.Tre_docfilelabel;
import com.sunnet.org.member.service.ITb_memberService;
import com.sunnet.org.member.vo.Tre_docfilelabelUtil;
import com.sunnet.org.util.StringUtils;

@Controller
@RequestMapping("/app")
public class AppCardAlbumController extends BaseController {

	@Autowired
	private ITb_memberService tb_memberService;

	@Autowired
	private ITb_albumcardService tb_albumcardService;
	@Autowired
	private ITb_albumcardrecordService tb_albumcardrecordService;

	private static String basePath = "http://39.108.216.108:8080/sunnet_flb/upload";
	private static String basePath2 = "http://39.108.216.108//E://webjava//apache-tomcat-8.0.36//webapps//sunnet_flb//upload//card1";
	private static int flag = 0;
	private static String hight = "?x-oss-process=image/resize,h_800";

	/**
	 * 相册修改
	 * @param filePath
	 * @param imgUrl
	 * @param textStr
	 * @param resultPath
	 * @param type
	 * @param docName
	 * @throws IOException
	 */
	public static void renaHTMLAndChange(String filePath, String[] imgUrl, String textStr, String resultPath, Integer type,
			String docName) throws IOException {

	//	filePath = "C:\\Users\\Administrator.PC-20170708WVKW\\Desktop\\聚会\\index.html";
		//resultPath = "C:\\Users\\Administrator.PC-20170708WVKW\\Desktop\\聚会\\test.html";
		File f = new File(filePath);
		Element name = null;
//		String text = "";
//		if (!("".equals(textStr)) && textStr != null) {
//			text = textStr;
//		}
		try {
			
			Document doc = Jsoup.parse(f, "utf-8");
			Element docTitle = doc.select("title").first();// 编辑文字
			if (!("".equals(docName)) && docName != null) {
//				text = textStr;
				docTitle.html(docName);
			} 
			if (type == 2) {
				// 影展
				name = doc.select(".picture-title").first();
				name.html(textStr);
			} else if (type == 0) {
				// 贺卡
				if(!("".equals(textStr)) && textStr != null){
					name = doc.select("p").first();// 编辑文字
					name.html(textStr);
				}
//				if (name == null) {
//				
//					name.html(text);
//				} else {
//					name.html(userName);
//				}
				for (int i = 0; i < imgUrl.length; i++) {
					Element img = doc.select("img[name='" + (i + 1) + "']").first();// 替换图片
					img.attr("src", imgUrl[i]+hight);
				}
				FileOutputStream fos = new FileOutputStream(resultPath, true);
				OutputStreamWriter osw = new OutputStreamWriter(fos, "utf-8");
				osw.write(doc.html());
				osw.close();
			} else {
				// 相册
				if(!("".equals(textStr)) && textStr != null){
					Element pStrElement = doc.select("p").first();// 编辑文字
					pStrElement.html(textStr);
				}
				
				Document doc2 = rePlayAlbum(doc, imgUrl);
				FileOutputStream fos = new FileOutputStream(resultPath, true);
				OutputStreamWriter osw = new OutputStreamWriter(fos, "utf-8");
				osw.write(doc2.html());
				osw.close();
//				File f2 = new File(resultPath);
//				if(f2.exists()){
//					return  true;
//				}else{
//					return  false;
//				}
			}
		 
		} catch (Exception e) {
			e.printStackTrace();
//			return false;
//			writeEror_to_txt(path,"ddddd");
		}
	}
	@RequestMapping(value = "/CardAlbum/Listli")
	public String CardAlbumListli(Integer type,String menberId,String isPay,PageBean pageBean,
			HttpServletRequest request, HttpServletResponse response) {
		JsonResult jsonResult = new JsonResult();
		try {
			List<Tb_albumcardrecord> list = null;
			StringBuffer str = new StringBuffer();
			str.append(" where fd_member_id='"+menberId+"'"); 
			if (StringUtils.isStringNull(menberId)) {
				str.append(" and o.fd_member_id.id like '%" + menberId + "%'");
			}
			if (StringUtils.isStringNull(isPay)) {
				str.append(" and o.fd_ispay like '%" + isPay + "%'");
			}
			if (null!=type && type>=0) {
				str.append(" and o.fd_albumvard_id.fd_type = " + type );
			}
			
			QueryResult<Tb_albumcardrecord> result = tb_albumcardrecordService.list(pageBean,request);
			jsonResult.setResult(result);
			jsonResult.setCode("200");
			jsonResult.setMessage("执行成功！");
		} catch (Exception e) {
			e.printStackTrace();
			jsonResult.setCode("500");
			jsonResult.setMessage("执行错误！");
			log.debug("异常:" + e);
		}
		return ajaxJson(JSONObject.toJSONString(jsonResult), response);
//	}

}

	
	/** 
	 * 
	 * @param type
	 * @param menberId
	 * @param isPay
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/CardAlbum/List")
	public String CardAlbumList(Integer type,String menberId,String isPay,PageBean pageBean,
			HttpServletRequest request, HttpServletResponse response) {
		JsonResult jsonResult = new JsonResult();
		try {
			String hql = " from Tb_albumcardrecord where fd_member_id='"+menberId+"'";
			if(isPay != null){
				hql += " and fd_ispay="+isPay+"";
			}
			if(type != null){
				hql += " and fd_albumvard_id.fd_type="+type+"";
			}
			
			List<Tb_albumcardrecord> resultList = tb_albumcardrecordService.findByHQL(hql,pageBean);
			int totalRecord = tb_albumcardrecordService.findByHQLCount(hql);
			List<Map<String, Object>> item = new ArrayList<Map<String, Object>>();
			Map<String, Object> map = new HashMap<String, Object>();
			Map<String, Object> map1 = new HashMap<String, Object>();
			map1.put("startPage", pageBean.getStartPage());// 当前页
			map1.put("pageSize", pageBean.getPageSize());// 一页多少条
			map1.put("totalRecord", totalRecord);// 总记录数
			map1.put("totalPage", ((totalRecord + pageBean.getPageSize() - 1) / pageBean.getPageSize()));// 一共有多少页
			item.add(map1);
			for (int i = 0; i < resultList.size(); i++) {
				map = getMap(resultList.get(i));
				item.add(map);
			}
			
			jsonResult.setResult(item);
			jsonResult.setCode("200");
			jsonResult.setMessage("执行成功");
		} catch (Exception e) {
			e.printStackTrace();
			jsonResult.setCode("500");
			jsonResult.setMessage("执行错误！");
			log.debug("异常:" + e);
		}
		return ajaxJson(JSONObject.toJSONString(jsonResult), response);
//	}

}


	/**
	 *
	 * @param imgurls
	 * @param albumcard
	 * @param text
	 * @param type
	 * @param menberId
	 * @param docName
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/CardAlbum/DIY")
public String CardAlbumDIY(String imgurls, Tb_albumcard albumcard, String text, Integer type, String menberId,String docName,
		HttpServletRequest request, HttpServletResponse response) {
	JsonResult jsonResult = new JsonResult();
	String[] imgurl = imgurls.split(",");
	String sourcePath = request.getSession().getServletContext().getRealPath("/upload/"+ albumcard.getFd_name() + "/");
//	String sourcePath = "C:\\Users\\Administrator.PC-20170708WVKW\\Desktop\\xj\\1111111111\\heka4";
//	String resultPath = "C:\\Users\\Administrator.PC-20170708WVKW\\Desktop\\xj\\1111111111\\test.html";
	
	try {
		// 结果路径
		if (imgurl != null && imgurl.length > 0) {
			long date = new Date().getTime();

			List<Tb_member> list = tb_memberService.findByHQL("from Tb_member where Id =? ", menberId);

			String resultPath = sourcePath + "\\"+menberId.replaceAll("\"", "") + date + ".html";
			
//			renaHTMLAndChange(sourcePath+"\\index.html", imgurl, text, resultPath, type, list.get(0).getUsersname());
			renaHTMLAndChange(sourcePath+"\\index.html", imgurl, text, resultPath, type, docName);
			File f = new File(resultPath);
			while(!f.exists()){
				renaHTMLAndChange(sourcePath+"\\index.html", imgurl, text, resultPath, type, docName);
			}
			
			Tb_albumcardrecord tb_albumcardrecord = new Tb_albumcardrecord();
			tb_albumcardrecord.setFd_albumvard_id(albumcard);
			tb_albumcardrecord.setFd_url(menberId.replaceAll("\"", "") + date);
			tb_albumcardrecord.setFd_ispay(0);
			tb_albumcardrecord.setFdmemberid(list.get(0));
			tb_albumcardrecord.setFd_docpath(imgurls);
			tb_albumcardrecord.setFd_mname(docName);
			tb_albumcardrecord.setFd_filmfirstpic(imgurl[0]);
			tb_albumcardrecord.setFd_docdec(text);
			tb_albumcardrecordService.save(tb_albumcardrecord);
			
			
			jsonResult.setResult(Tb_albumcardrecordUtil.getControllerMap(tb_albumcardrecord));
			jsonResult.setCode("200");
			jsonResult.setMessage("执行成功");
		 
		} else {
			// jsonResult.setResult(resultPath);
			jsonResult.setCode("100");
			jsonResult.setMessage("请选择图片");
		}
		
	} catch (Exception e) {
		e.printStackTrace();
		jsonResult.setCode("500");
		jsonResult.setMessage("执行错误！");
		log.debug("异常:" + e);
	}
	return ajaxJson(JSONObject.toJSONString(jsonResult), response);
}


	/**
	 * 编辑相册贺卡
	 * @param imgurls
	 * @param fd_name
	 * @param tb_albumcardrecord
	 * @param type
	 * @param text
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/CardAlbum/updataDIY")
public String updataDIY(String imgurls,String fd_name, Tb_albumcardrecord tb_albumcardrecord,int type,String text,
		HttpServletRequest request, HttpServletResponse response) {
	JsonResult jsonResult = new JsonResult();
	
	String[] imgurl = imgurls.split(",");
	String sourcePath = request.getSession().getServletContext().getRealPath("/upload/"+ fd_name + "/");
	try {
		// 结果路径
		if (imgurl != null && imgurl.length > 0) {
			String resultPath = sourcePath + "\\"+tb_albumcardrecord.getFd_url() + ".html";
			File f = new File(resultPath);
			if(f.exists()){
				f.delete();
			}
			renaHTMLAndChange(sourcePath+"\\index.html", imgurl, text, resultPath, type, tb_albumcardrecord.getFd_mname());
 
			File f2= new File(resultPath);
			while(!f2.exists()){
				renaHTMLAndChange(sourcePath+"\\index.html", imgurl, text, resultPath, type, tb_albumcardrecord.getFd_mname());
			}
			if(StringUtils.isStringNull(request.getParameter("docName"))){
				tb_albumcardrecord.setFd_mname(request.getParameter("docName"));
			}
			tb_albumcardrecord.setFd_filmfirstpic(imgurl[0]);
			tb_albumcardrecord.setFd_docpath(imgurls);
			tb_albumcardrecord.setFd_docdec(text);
			tb_albumcardrecordService.update(tb_albumcardrecord);
			jsonResult.setResult(Tb_albumcardrecordUtil.getControllerMap(tb_albumcardrecord,type,fd_name));
			jsonResult.setCode("200");
			jsonResult.setMessage("执行成功");
			 
		} else {
			// jsonResult.setResult(resultPath);
			jsonResult.setCode("100");
			jsonResult.setMessage("请选择图片");
		}
		
	} catch (Exception e) {
		e.printStackTrace();
		jsonResult.setCode("500");
		jsonResult.setMessage("执行错误！");
		log.debug("异常:" + e);
	}
	return ajaxJson(JSONObject.toJSONString(jsonResult), response);
}


@RequestMapping(value = "/CardAlbum/updataDocName")
public String updataDocName(Tb_albumcardrecord tb_albumcardrecord,String fd_name, 
		HttpServletRequest request, HttpServletResponse response) {
			JsonResult jsonResult = new JsonResult();
	try{
		if(!(fd_name.equals(""))&&fd_name!=null){
			//修改文件名
			List<Tb_albumcardrecord> recordList = tb_albumcardrecordService.findByHQL("from Tb_albumcardrecord where fd_id =? ", tb_albumcardrecord.getFd_id());
			String sourcePath = request.getSession().getServletContext().getRealPath("/upload/"+ fd_name + "/");
			String resultPath = sourcePath + "\\"+recordList.get(0).getFd_url() + ".html";
			
			File f = new File(resultPath);
			if(f.exists()){
				f.delete();
			}
			String[] imgurl = recordList.get(0).getFd_docpath().split(",");
			renaHTMLAndChange(sourcePath+"\\index.html", imgurl, recordList.get(0).getFd_docdec(), resultPath,Integer.parseInt(recordList.get(0).getFd_albumvard_id().getFd_type()), tb_albumcardrecord.getFd_mname());
//			renaHTMLAndChange("", imgurl, recordList.get(0).getFd_docdec(), "",Integer.parseInt(recordList.get(0).getFd_albumvard_id().getFd_type()), tb_albumcardrecord.getFd_mname());
			File f2= new File(resultPath);
			while(!f2.exists()){
				renaHTMLAndChange(sourcePath+"\\index.html", imgurl, recordList.get(0).getFd_docdec(), resultPath,Integer.parseInt(recordList.get(0).getFd_albumvard_id().getFd_type()), tb_albumcardrecord.getFd_mname());
			}
		}
//			Tb_albumcardrecord tb_albumcardrecord = new Tb_albumcardrecord();
//			tb_albumcardrecord.setFd_albumvard_id(albumcard);
//			tb_albumcardrecord.setFd_url(menberId.replaceAll("\"", "") + date);
//			tb_albumcardrecord.setFd_ispay(0);
//			tb_albumcardrecord.setFdmemberid(list.get(0));
//			if(type == 2){
//				tb_albumcardrecord.setFd_filmfirstpic(imgurl[0]);
//			}
//			tb_albumcardrecordService.save(tb_albumcardrecord);
//			tb_albumcardrecord.setFd_docpath(imgurls);
		tb_albumcardrecordService.update(tb_albumcardrecord);
//			jsonResult.setResult(Tb_albumcardrecordUtil.getControllerMap(tb_albumcardrecord));
		jsonResult.setCode("200");
		jsonResult.setMessage("执行成功！");
			
	} catch (Exception e) {
		e.printStackTrace();
		jsonResult.setCode("500");
		jsonResult.setMessage("执行错误！");
		log.debug("异常:" + e);
	}
	return ajaxJson(JSONObject.toJSONString(jsonResult), response);
}

	/**
	 * 查询相册或贺卡
	 * @param response
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/findModelByType/list")
public String findModelByType(HttpServletResponse response, HttpServletRequest request) {
	JsonResult jsonResult = new JsonResult();
	List<Tb_albumcard> list = null;
	try {
		list = tb_albumcardService.findByHQL("from Tb_albumcard where fd_type=?", request.getParameter("fd_type"));
		jsonResult.setResult(Tb_albumcardUtil.getControllerList(list));
		jsonResult.setMessage("执行成功！");
		jsonResult.setCode("200");
		
	} catch (Exception e) {
		jsonResult.setCode("500");
		jsonResult.setMessage("执行错误！");
		log.debug("异常:" + e);
	}
	return ajaxJson(JSONObject.toJSONString(jsonResult), response);
}


	/**
	 * 根据id查询定制的相册或贺卡
	 * @param id
	 * @param response
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/recordefindDiyById/obj")
public String findDiyById(String id,HttpServletResponse response, HttpServletRequest request) {
	JsonResult jsonResult = new JsonResult();
	List<Tb_albumcardrecord> list = null;
	try {
		list = tb_albumcardrecordService.findByHQL("from TB_AlbumCardRecord where fd_id=?", id);
		jsonResult.setResult(Tb_albumcardrecordUtil.getControllerMap(list.get(0)));
		jsonResult.setMessage("执行成功！");
		jsonResult.setCode("200");
		
	} catch (Exception e) {
		jsonResult.setCode("500");
		jsonResult.setMessage("执行错误！");
		log.debug("异常:" + e);
	}
	return ajaxJson(JSONObject.toJSONString(jsonResult), response);
}

	/**
	 * 删除
	 * 
	 * @param ids
	 *            数组
	 * @return json
	 */
	@RequestMapping(value = "/tb_albumcardRecord/delete")
	public String delete(String[] ids, String menberId, HttpServletResponse response, HttpServletRequest request) {
		JsonResult jsonResult = new JsonResult();
		Tb_albumcardrecord albumcardrecord = new Tb_albumcardrecord();
		Tb_albumcard albumcard = new Tb_albumcard();
		String sourcePath = "";
		String resultPath = "";
//		String path = request.getSession().getServletContext().getRealPath("/");
//		String path = "C:\\Users\\Administrator.PC-20170708WVKW\\Desktop";
//		System.out.println(path);
//		PrintWriter prw = null ;
//		System.out.println(path+"//base.txt");
//		try {
//			prw = new PrintWriter(path+"//base.txt");
//		} catch (FileNotFoundException e1) {
//			// TODO Auto-generated catch block
//			e1.printStackTrace();
//		}
		try {
			if (ids != null && ids.length > 0) {
				for (int i = 0; i < ids.length; i++) {
//					prw.println("0000"+ids[i]);
					albumcardrecord = tb_albumcardrecordService
							.findByHQL("from Tb_albumcardrecord where fd_id =? ", ids[i]).get(0);
//					prw.println("11111"+albumcardrecord.getFd_id());
//					prw.println("222"+albumcardrecord.getFd_id());
					sourcePath = request.getSession().getServletContext().getRealPath("/upload/" + albumcardrecord.getFd_albumvard_id().getFd_name() + "/");
					
					
					resultPath = sourcePath + "\\" + albumcardrecord.getFd_url() + ".html";
//					prw.println("3333"+resultPath);
 
					File file = new File(resultPath);
					if(file.exists()){
						file.delete();
					}
//					prw.println("444444"+resultPath);
//					prw.println("成功");
					tb_albumcardrecordService.delete(ids, "");
				}
				jsonResult.setMessage("执行成功！");
				jsonResult.setCode("200");
			} else {
				jsonResult.setMessage("请选择删除选项!");
				jsonResult.setCode("300");
			}
		} catch (Exception e) {
			jsonResult.setCode("400");
			jsonResult.setMessage(e.toString());
			log.debug("异常:" + e);
		}
		return ajaxJson(JSONObject.toJSONString(jsonResult), response);
	}


	/**
	 * 删除
	 * @param response
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/openClose")
	public String openClose(HttpServletResponse response, HttpServletRequest request) {
		JsonResult jsonResult = new JsonResult();
		try {
				jsonResult.setResult(0);
				jsonResult.setMessage("执行成功！");
				jsonResult.setCode("200");
			 
		} catch (Exception e) {
			jsonResult.setResult(0);
			jsonResult.setCode("400");
			jsonResult.setMessage(e.toString());
			log.debug("异常:" + e);
		}
		return ajaxJson(JSONObject.toJSONString(jsonResult), response);
	}
	
	/**
	 * 重新拼接相册
	 * 
	 * @param doc
	 * @param imgUrl
	 * @return
	 */
	private static Document rePlayAlbum(Document doc, String[] imgUrl) {
		int length = imgUrl.length;

		Elements divs = null;
		divs = doc.select(".main");
		if (divs == null || divs.size() <= 0) {
			// class为swiper的修改
			divs = doc.select(".swiper-slide");
			String[] swiperArray = new String[divs.size()];
			Integer[] imgLength = new Integer[divs.size()];
			Elements imgs = null;
			int imgNum = 0;// 一轮可替换的图片个数
			for (int i = 0; i < divs.size(); i++) {
				swiperArray[i] = divs.get(i).toString();// 获取每页的swiper div
				imgs = divs.get(i).select("[data=\"change\"]");
				imgLength[i] = divs.get(i).select("[data=\"change\"]").size();// 每个div中可替换的图片个数
				imgNum = imgNum + imgs.size();
			}
			// int count = 0;
			int changeImg = 0;
			String str = "";
			if (imgUrl.length <= imgNum) {
				// 图片个数小于模板图片个数
					for (int i = 0; i < divs.size(); i++) {
						changeImg = changeImg + imgLength[i];
						if (imgUrl.length <= changeImg) {
							for (int j = 0; j < imgUrl.length; j++) {
								Element img = doc.select("img[name='" + (j + 1) + "']").first();// 替换图片
								img.attr("src", imgUrl[j]+hight);
							}
							if (imgUrl.length < changeImg) {
								int a = changeImg - imgUrl.length;
								// 当传递图片少于已有图片时删除多余图片
								for (int j = imgUrl.length; j < changeImg; j++) {
									doc.select("img[name='" + (j + 1) + "']").remove();
								}
							}
//							for (int k = i + 1; k < divs.size(); k++) {
//								divs.get(k).remove();
//							}
							break;
						}
					}
				} else {
					// 图片个数大于模板图片个数
					int count = 0;
					// 先替换所有模板中图片
					for (int j = 0; j < imgNum; j++) {
						Element img = doc.select("img[name='" + (j + 1) + "']").first();// 替换图片
						img.attr("src", imgUrl[j]+hight);
						count++;
					}
					// 循环拼接多余图片
					int num = imgUrl.length - imgNum;// 多余的图片
					int temp = 0; 
					int temp2 = 0;
//					changeImg = imgLength[temp];
					do{
						str = swiperArray[temp];
						Element mainDiv = doc.select(".swiper-wrapper").first();// 最外层div
						mainDiv.append(str);// 拼接div
						Elements imgs2 = doc.select(".swiper-slide").get(divs.size()+temp2).select("img");
						for(int j=0;j<imgs2.size();j++){
							if(count>=imgUrl.length){
								imgs2.get(j).remove();
							}else{
								imgs2.get(j).attr("src", imgUrl[count]+hight);
							}
							count++;
							changeImg ++;
						}
//						changeImg += imgLength[temp];
						temp++;
						temp2++;
						if(temp >= divs.size()){
							temp = 0;
						}
					} while(changeImg <= num);
			}
		 }else{
		 //class为main的修改
//			Integer[] imgLength = new Integer[divs.size()];
			Elements imgs = null;
			Elements divs2 = divs.select("div");
			int imgNum = divs2.size()-2;// 一轮可替换的图片个数
			String[] pageArray = new String[imgNum];
			for (int i = 2; i < divs2.size(); i++) {
				pageArray[i-2] = divs2.get(i).toString();// 获取每页的 div
			}
			String str = "";
			int delecTemp = 0;
			
			if (imgUrl.length <= imgNum) {
				for (int j = 0; j < imgUrl.length; j++) {
					Element img = doc.select("img[name='" + (j + 1) + "']").first();// 替换图片
					img.attr("src", imgUrl[j]+hight);
					delecTemp ++;
				}
//				for (int k = delecTemp; k < imgNum; k++) {
//					divs2.get(k+2).remove();
//				}
			}else{
				int count = 0;
				for (int j = 0; j < imgNum; j++) {
					Element img = doc.select("img[name='" + (j + 1) + "']").first();// 替换图片
					img.attr("src", imgUrl[count]+hight);
					count++;
				}
				int num = imgUrl.length - imgNum;// 多余的图片
				int startTemp = 0;
				for(int i = 0; i < num; i++){//循环拼接可替换div
					str = pageArray[startTemp];
					Element mainDiv = doc.select(".main").first();// 最外层div
					mainDiv.append(str);// 拼接div
					//替换图片
					Element img = doc.select(".main").select("div").get(divs2.size()+i).select("img").first();
					img.attr("src",imgUrl[count]+hight);
					count++;
					startTemp ++ ;
					if(startTemp==imgNum){
						startTemp = 0;
					}
				}
			}
		}
		return doc;
	}
	
	public Map<String, Object> getMap(Tb_albumcardrecord obj) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("fd_fdId", obj.getFd_id());
		map.put("usersname", obj.getFdmemberid().getUsersname());
		map.put("fd_name", obj.getFd_albumvard_id().getFd_name());
		map.put("fd_url", obj.getFd_url());
		map.put("fd_albumvard", obj.getFd_albumvard_id().getFd_id());
		map.put("fd_ispay", obj.getFd_ispay());
		map.put("memberid", obj.getFdmemberid().getId());
		map.put("fd_money", obj.getFd_albumvard_id().getPay_money());
		if(obj.getFd_filmfirstpic()!=null){
			map.put("fd_filmFirstPic", obj.getFd_filmfirstpic()); 
		}else{
			map.put("fd_filmFirstPic", ""); 
		}
		if(obj.getFd_mname()!=null){
			map.put("docName", obj.getFd_mname()); 
		}else{
			map.put("docName", ""); 
		}
		if(obj.getFd_docdec()!=null){
			map.put("docDec", obj.getFd_docdec()); 
		}else{
			map.put("docDec", ""); 
		}
		if(obj.getFd_docpath()!=null){
			map.put("docPath", obj.getFd_docpath()); 
		}else{
			map.put("docPath", ""); 
		}
		// }
		return map;
	}
	
}

