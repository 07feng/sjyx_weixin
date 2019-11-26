package com.sunnet.code.util;

import com.sunnet.code.common.CodeManyUtil;
import com.sunnet.code.model.EntityModule;

/**
 * 字符串拼接
 * 
 * @author 强强
 *
 *         时间: 2017年2月20日
 */
public class StringBuModel {

	/**
	 * 赛选字段 拼接字符串
	 * 
	 * @return
	 */
	public static String getServceImpl() {
		StringBuffer str = new StringBuffer();
		for (String screen : ModelUtil.screen) {
			str.append("\n if (StringUtils.isStringNull(request.getParameter(\"" + screen + "\")))");
			str.append("\n{");
			str.append("\n str.append(\" and o." + screen + " like '%\" + request.getParameter(\"" + screen
					+ "\") + \"%'\");");
			str.append("\n}");
		}
		return str.toString();
	}

	/**
	 * Vo 反馈
	 * 
	 * @return
	 */
	public static String getVoUtil() {
		StringBuffer str = new StringBuffer();
		for (String voutil : ModelUtil.voutil) {
			if (voutil.contains("&")) {
				String[] st = voutil.split("&");
				str.append("\n if (obj.get" + TestUtil.captureName(st[0]) + "() != null) {");
				str.append("\n map.put(\"" + st[0] + "_" + st[1] + "\", obj.get" + TestUtil.captureName(st[0])
						+ "().get" + TestUtil.captureName(st[1]) + "());");
				str.append("\n } else {");
				str.append("\n map.put(\"" + st[0] + "_" + st[1] + "\", \"\");");
				str.append("\n }");
			} else	if (voutil.contains("_")) {
				String[] st = voutil.split("_");
				if(st[1].trim().equals("id")){
					str.append("\n if (obj.get" + TestUtil.captureName(voutil) + "() != null) {");
					str.append("\n map.put(\"" + st[0] + "_fdId\", obj.get" + TestUtil.captureName(voutil)
							+ "().getFdId());");
					str.append("\n } else {");
					str.append("\n map.put(\"" + st[0] + "_fdId\", \"\");");
					str.append("\n }");
				}if(!(st[1].trim().equals("id"))){
					str.append("\n if (obj.get" + TestUtil.captureName(voutil) + "() != null) {");
					str.append("\n map.put(\"" + st[0] + "_" + st[1] + "\", obj.get" + TestUtil.captureName(voutil)
							+ "().get" + TestUtil.captureName(st[1]) + "());");
					str.append("\n } else {");
					str.append("\n map.put(\"" + st[0] + "_" + st[1] + "\", \"\");");
					str.append("\n }");
				}else{
					str.append("\n map.put(\"" + voutil + "\", obj.get" + TestUtil.captureName(voutil) + "());");
				}
			} else {
				str.append("\n map.put(\"" + voutil + "\", obj.get" + TestUtil.captureName(voutil) + "());");
			}
		}
		return str.toString();
	}

	/**
	 * [页面]赛选生成
	 * 
	 * @return
	 */
	public static String jsScren() {
		StringBuffer str = new StringBuffer();
		for (String screen : ModelUtil.screen) {
			str.append("\n" + ModelUtil.total.get(screen)
					+ "： <input type='text' class='input-text' style='width: 250px' placeholder='' id='' name='"
					+ screen + "'>");
		}
		return str.toString();
	}

	/**
	 * [页面]th说明
	 * 
	 * @return
	 */
	public static String jsListTh() {
		StringBuffer str = new StringBuffer();
		for (String par : ModelUtil.parameter) {
			str.append("\n <th width='150'>" + ModelUtil.total.get(par) + "</th>");
		}
		return str.toString();
	}

	/**
	 * [页面]th说明
	 * 
	 * @return
	 */
	public static String jsListLook() {
		StringBuffer str = new StringBuffer();
		if (CodeManyUtil._select) {
			str.append(
					"<a title='预览' href='javascript:;' onclick=\"com_look('查看','{{result.resultList[i].fdId}}')\" class='ml-5' style='text-decoration:none'><i class='Hui-iconfont'>&#xe695;</i></a>");
		}
		return str.toString();
	}

	/**
	 * [页面]td说明
	 * 
	 * @return
	 */
	public static String jsListTd() {
		StringBuffer str = new StringBuffer();
		for (String par : ModelUtil.parameter) {
			if (par.contains("&")) {
				String[] st = par.split("&");
				str.append("\n <td>{{result.resultList[i]." + st[0] + "_" + st[1] + "}}</td>");
			} else {
				if (par.contains("img")) {
					str.append("\n <td><img src='<%=request.getContextPath() %>/{{result.resultList[i]." + par
							+ "}}' style='width: 100px;  height: 100px;' /></td>");
				} else {
					str.append("\n <td>{{result.resultList[i]." + par + "}}</td>");
				}
			}
		}
		return str.toString();
	}

	/**
	 * [页面]add说明
	 * 
	 * @param MinName
	 *            类名
	 * @return
	 */
	public static String jsAdd(String minName) {
		StringBuffer str = new StringBuffer();
		int cen = 0;
		int i=0;
		for (String add : ModelUtil.add) {
			str.append("\n <div class='row cl'>");
			str.append("\n <label class='form-label col-xs-4 col-sm-2'>" + ModelUtil.total.get(add) + "：</label>");
			str.append("\n <div class='formControls col-xs-8 col-sm-9'>");
			// 类型 包含品种：1.什么都不包含 2.包含& 即有外键 3.包含#有类型
			if (add.contains("&")) {
				String[] st = add.split("&");
				str.append("\n <select name='" + st[0] + "." + st[1] + "' id='" + st[0] + "_" + st[1]
						+ "' class='select radius input-text' >");
				str.append("\n </select>");
			} else if (add.contains("#")) {
				String[] st = add.split("#");
				if (st[1].equals("select")) {
					str.append("\n <select name='" + st[0] + "' id='" + st[0] + "' class='select radius input-text' >");
					str.append("\n </select>");
				} else if (st[1].equals("input")) {
					str.append("\n <input type='text' class='input-text' value='${" + minName + "." + st[0]
							+ " }' placeholder='' id='' name='" + st[0] + "'>");
				} else if (st[1].equals("textarea")) {
					str.append("\n <textarea name='" + st[0] + "' id='" + st[0]
							+ "' class='text' style='width: 500px; height: 100px;'>${" + minName + "." + st[0]
							+ " }</textarea>");
				}
			} else if (add.contains("img")) {
				str.append("\n <div class='yuYulanImg' >");
				str.append("\n <img src='' id='" + add + "_yulan' style='width: 100px;  height: 100px;' /> ");
				str.append("\n </div>");

				str.append("\n <div class='loadUpImg'>");
				str.append("\n <input type='file' name='" + add + "_file' id='" + add + "' />");
				str.append("\n <button type='button' class='btn btn-primary'>选择图片</button>");
				str.append("\n </div>");
				ModelUtil.img.add(add);
			} else if (add.contains("Content")) {
				str.append("\n <textarea name='" + add + "' id='editor" + cen
						+ "' class='text'	style='width: 500px; height: 100px;'>${" + minName + "." + add
						+ " }</textarea>");
				str.append("\n <script type='text/javascript'>");
				str.append("\n var editor = UE.getEditor('editor" + cen + "',{");
				str.append("\n initialFrameWidth : 700,");
				str.append("\n initialFrameHeight: 150");
				str.append("\n });");
				str.append("\n </script>");
				cen++;
			} else if (add.contains("Recommended")) {
				str.append("\n <div>");
				str.append("\n <input class='tgl tgl-flip' id='cb"+i+"' type='checkbox' onchange=\"xuanpai(this,'"+add+"')\">");
				str.append("\n <label class='tgl-btn' data-tg-off='推荐' data-tg-on='否' for='cb"+i+"'></label>");
				str.append("\n </div>");
				i++;
			} else {
				str.append("\n <input type='text' class='input-text' value='${" + minName + "." + add
						+ " }' placeholder='' id='' name='" + add + "'>");
			}
			str.append("\n </div>");
			str.append("\n </div>");
		}
		return str.toString();
	}

	/**
	 * 图片
	 * 
	 * @param minName
	 * @return
	 */
	public static String getImg(String minName) {
		StringBuffer str = new StringBuffer();
		for (String add : ModelUtil.img) {
			str.append("\n if('${" + minName + "." + add + " }' != ''){");
			str.append("\n    $('#" + add + "_yulan').attr('src', '<%=request.getContextPath() %>/${" + minName + "."
					+ add + " }');");
			str.append("\n }else{");
			str.append("\n    $('#" + add
					+ "_yulan').attr('src', '<%=request.getContextPath() %>/resources/img/user.png');");
			str.append("\n }");
			str.append("\n $('#" + add + "').bind('change', function() {");
			str.append("\n    clickupLoadclass('" + add + "','" + add + "_yulan');");
			str.append("\n });");
		}
		return str.toString();
	}

	/**
	 * 图片
	 * 
	 * @param minName
	 * @return
	 */
	public static String getImgUrl(String minName) {
		StringBuffer str = new StringBuffer();
		if (ModelUtil.img.size() > 0) {
			str.append("imgUpLoad(url,bag,service,'form-article-add');");
		} else {
			str.append("addUpdate(url,bag,service);");
		}
		return str.toString();
	}

	/**
	 * 图片 Control
	 * 
	 * @param minName
	 * @return
	 */
	public static String ConllerImg(String minName) {
		StringBuffer str = new StringBuffer();
		for (String add : ModelUtil.img) {
			str.append(",MultipartFile " + add + "_file");
		}
		return str.toString();
	}

	/**
	 * 图片 Control
	 * 
	 * @param minName
	 * @return
	 */
	public static String ConllerImgD(String minName) {
		StringBuffer str = new StringBuffer();
		for (String add : ModelUtil.img) {
			str.append("\n if (" + add + "_file != null) {");
			str.append("\n String img = FileUploadUtil.upload(" + add + "_file, request, \"" + minName + "\");");
			str.append("\n " + minName + ".set" + TestUtil.captureName(add) + "(img);");
			str.append("\n }");
		}
		return str.toString();
	}

	/**
	 * [页面]add说明 [外键]
	 * 
	 * @return
	 */
	public static String getVoutilSelecyt(String minName) {
		StringBuffer str = new StringBuffer();
		for (String select : ModelUtil.voutilSelecyt) {
			// 第一个参数 那个包/那个请求/什么参数
			String[] sp = select.split("&");
			// soty&category&fdTitle
			str.append("\n $.ajax({  ");
			str.append("\n        type : 'POST',  //提交方式    ");
			if(sp[1].contains("_") && sp[1].contains("id")){
				String [] p = sp[1].split("_");
				str.append("\n        url : '<%=request.getContextPath() %>/" + sp[0] + "/" + p[0] + "/listAll',   ");
			}else{
				str.append("\n        url : '<%=request.getContextPath() %>/" + sp[0] + "/" + sp[1] + "/listAll',   ");
			}
			str.append("\n        dataType:'json', ");
			str.append("\n        success : function(result) { ");
			str.append("\n           var ht='';  ");
			str.append("\n           for(var i = 0 ; i<result.result.length ; i++ ){ ");
			str.append("\n                 if(result.result[i].fdId == '${" + minName + "." + sp[1] + ".fdId }' ){  ");
			str.append(
					"\n                     ht+=\"<option value='\"+result.result[i].fdId+\"' selected='selected' >\"+result.result[i]."
							+ sp[2] + "+\"</option>\";  ");
			str.append("\n           }else{	");
			str.append("\n               ht+=\"<option value='\"+result.result[i].fdId+\"'>\"+result.result[i]." + sp[2]
					+ "+\"</option>\";  ");
			str.append("\n           } ");
			str.append("\n        } ");
			str.append("\n           $('#" + sp[1] + "_fdId').html(ht); ");
			str.append("\n      }   ");
			str.append("\n    });  ");
		}
		return str.toString();
	}

	/**
	 * [页面]select说明
	 * 
	 * @param MinName
	 *            类名
	 * @return
	 */
	public static String jsSelect(String minName) {
		StringBuffer str = new StringBuffer();
		for (String select : ModelUtil.select) {
			str.append("\n <div class='row cl'>");
			str.append("\n <label class='form-label col-xs-4 col-sm-2'></label>");
			str.append("\n  <div class='formControls col-xs-8 col-sm-8'>");
			str.append("\n " + ModelUtil.total.get(select) + "：${" + minName + "." + select + " }");
			str.append("\n </div>");
			str.append("\n </div>");
		}
		return str.toString();
	}

	/**
	 * 权限管理
	 * 
	 * @return
	 */
	public static String permissionAdd(String code) {
		StringBuffer str = new StringBuffer();
		str.append("@RequiresPermissions(\"" + code + "\")");
		return str.toString();
	}

	/**
	 * 返回的 entityVlue
	 * 
	 * @return
	 */
	public static String getStringCode() {
		StringBuffer getset = new StringBuffer();
		// 手动写的
		for (EntityModule entityModule : ModelUtil.list) {
			String cloumnName = entityModule.getModelVlue();
			if (entityModule.getModelVlue().contains("_")) {
				cloumnName = "";
				String[] sp = entityModule.getModelVlue().split("_");
				for (int i = 0; i < sp.length; i++) {
					if (i == 0) {
						cloumnName += sp[i];
					} else {
						cloumnName += TestUtil.getToClassName(sp[i]);
					}
				}
			}
			// 大写
			String BigCloumnName = TestUtil.getToClassName(cloumnName);

			getset.append("\t private " + entityModule.getModelType() + " " + cloumnName + ";  ");
			if (ModelUtil.total.get(entityModule.getModelVlue()) != null) {
				getset.append("//" + ModelUtil.total.get(entityModule.getModelVlue()));
			}
			getset.append("  \r\r");
			getset.append("\t public " + entityModule.getModelType() + " get" + BigCloumnName + "() \r");
			getset.append("\t {                       \r");
			getset.append("\t return " + entityModule.getModelVlue() + ";        	   \r");
			getset.append("\t }                       \r\r");
			getset.append("\t public void set" + BigCloumnName + "(" + entityModule.getModelType() + " "
					+ entityModule.getModelVlue() + ") \r");
			getset.append("\t {                       \r");
			getset.append(
					"\t this." + entityModule.getModelVlue() + " = " + entityModule.getModelVlue() + ";       \r\r");
			getset.append("\t }				 \r\r");
		}
		return getset.toString();
	}

	public static Object ContentJS() {
		StringBuffer getset = new StringBuffer();
		for (String add : ModelUtil.add) {
			if (add.contains("Content")) {
				getset.append(
						"\n <script type='text/javascript' charset='utf-8' src='<%=request.getContextPath()%>/ueditor/ueditor.config.js'></script>");
				getset.append(
						"\n <script type='text/javascript' charset='utf-8' src='<%=request.getContextPath()%>/ueditor/ueditor.all.min.js'></script>");
			}
		}
		return getset.toString();
	}

	public static Object ecommendJs(String minName) {
		StringBuffer getset = new StringBuffer();
		int i = 0;
		for (String add : ModelUtil.add) {
			if (add.contains("Recommended")) {
				getset.append("xuanpaitrue('${" + minName + "." + add + " }','cb" + i + "');");
				i++;
			}
		}
		return getset.toString();
	}
	
	public static Object ecommendADD(String minName) {
		StringBuffer getset = new StringBuffer();
		for (String add : ModelUtil.add) {
			if (add.contains("Recommended")) {
				getset.append("\n <input type='hidden' class='input-text' value='${"+minName+"."+add+" }' placeholder='' id='"+add+"' name='"+add+"'>");
			}
		}
		return getset.toString();
	}

	public static String getStringDel(String minName) {
		StringBuffer getset = new StringBuffer();
		for (String add : ModelUtil.add) {
			if(add.contains("fdStatus")){
				getset.append("\n @Override");
				getset.append("\n public void delete(Object[] entityids) {");
				getset.append("\n for (int i = 0; i < entityids.length; i++) {");
				getset.append("\n "+TestUtil.getStr(minName)+"Dao.update(\"update "+TestUtil.getToClassName(minName)+" set fdStatus = 0  where fdId ='\"+entityids[i]+\"'\");");
				getset.append("\n }");
				getset.append("\n }");
				return getset.toString();
			}
		}
		return getset.toString();
	}
}
