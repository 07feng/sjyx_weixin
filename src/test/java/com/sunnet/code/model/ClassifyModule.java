package com.sunnet.code.model;

import java.lang.reflect.Method;

import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.sunnet.code.common.CodeManyUtil;
import com.sunnet.code.util.ModelUtil;
import com.sunnet.code.util.TestUtil;

/**
 * 分类模块
 * 
 * @author 强强
 *
 *         时间: 2017年2月20日
 */
public class ClassifyModule {

	private static String type = "";

	private static String className = "";

	private static String com = "com/sunnet/org/";

	public ClassifyModule() {
		ModelUtil.clear();
	}

	public ClassifyModule(String model, String types) {
		ModelUtil.clear();
		type = types;
		className = model;
		if (CodeManyUtil.standard) {
			getStandard();
		}

	}

	private void getStandard() {
		try {
			/* 去除外键 */
			ModelUtil.parameter = ModelUtil.toClass(getUrl(), false);
			// add页面
			ModelUtil.add = ModelUtil.toClass(getUrl(), true);
			// 查看页面
			ModelUtil.select = ModelUtil.toClass(getUrl(), false);
			/* 反馈 */
			ModelUtil.voutil = ModelUtil.toClass(getUrl(), false);
			ModelUtil.toClassTool(getUrl(), false);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


	/**
	 * 分类详情
	 */
	public void getClassify() {
		ModelUtil.parameter.add("fdId");
		ModelUtil.parameter.add("aboutsName");
		ModelUtil.parameter.add("aboutContent");
		ModelUtil.screen.add("aboutsName");
		ModelUtil.add.add("aboutsName");
		ModelUtil.add.add("aboutContent");
		ModelUtil.voutil.add("fdId");
		ModelUtil.voutil.add("aboutsName");
		ModelUtil.voutil.add("aboutContent");
		ModelUtil.total.put("fdId", "ID");
		ModelUtil.total.put("aboutsName", "标题");
		ModelUtil.total.put("aboutContent", "内容");
		/* 原版指定生成 */
		// 列表参数
		// ModelUtil.parameter.add("fdId");
		// ModelUtil.parameter.add("classify_title");
		// ModelUtil.parameter.add("classify_url");
		// ModelUtil.foreignparameter.add("classify_fdId");
		// //赛选字段
		// ModelUtil.screen.add("classify_title");
		// //添加字段
		// ModelUtil.add.add("classify_title");
		// ModelUtil.add.add("classify_url");
		// //vo反馈
		// ModelUtil.voutil.add("fdId");
		// ModelUtil.voutil.add("classify_title");
		// ModelUtil.voutil.add("classify_url");
		// ModelUtil.voutil.add("classify&classify_url");//类-》属性
		// ModelUtil.voutil.add("classify&classify_title");//类-》属性

		/* 自动指定生成 */
		// ModelUtil.screen.add("fdId");
		// ModelUtil.foreignparameter.add("classify_fdId");
		// ModelUtil.parameter = ModelUtil.toClass(Classify.class, false);
		// ModelUtil.voutil = ModelUtil.toClass(Classify.class, false);
		// ModelUtil.add= ModelUtil.toClass(Classify.class, true);
		// ModelUtil.total.put("fdId", "ID");
		// ModelUtil.total.put("classify_title", "内容");
		// ModelUtil.total.put("classify_url", "链接路径");
		// ModelUtil.total.put("classify_fdId", "外键ID");

		// 外键
		// ModelUtil.foreignparameter.add("duties");
		// ModelUtil.foreignparameter.add("jobs");
		// ModelUtil.foreignparameter.add("region");
		// /* 去除外键 */
		// ModelUtil.parameter = ModelUtil.toClass(Applyfor.class, false);
		// ModelUtil.parameter.add("duties_fdTitle");
		// ModelUtil.parameter.add("region_city");
		// ModelUtil.parameter.add("jobs_jobName");
		// ModelUtil.parameter.remove("fdContent"); //去除单个list属性
		// ModelUtil.parameter.remove("fdMemImg");
		//
		// /* 赛选 */
		// ModelUtil.screen.add("fdName");
		//
		// //add页面
		// ModelUtil.add= ModelUtil.toClass(Applyfor.class, true);
		// /* 外键select */
		// ModelUtil.add.add("duties&fdId");
		// ModelUtil.add.add("jobs&fdId");
		// ModelUtil.add.add("region&fdId");
		//
		// //查看页面
		// ModelUtil.select= ModelUtil.toClass(Applyfor.class, true);
		//
		// /* 反馈 */
		// ModelUtil.voutil= ModelUtil.toClass(Applyfor.class, false);
		// ModelUtil.voutil.add("duties&fdTitle");
		// ModelUtil.voutil.add("jobs&jobName");
		// ModelUtil.voutil.add("region&city");
		//
		// /* 自动外键 */
		// ModelUtil.voutilSelecyt.add("work&region&city");
		// ModelUtil.voutilSelecyt.add("work&jobs&jobName");
		// ModelUtil.voutilSelecyt.add("work&duties&fdTitle");
		//
		// ModelUtil.total.put("fdId", "ID");
		// ModelUtil.total.put("fdName", "姓名");
		// ModelUtil.total.put("major", "专业");
		// ModelUtil.total.put("fdPhone", "手机");
		// ModelUtil.total.put("academy", "院校");
		// ModelUtil.total.put("workType", "工作经验");
		// ModelUtil.total.put("fdContent", "爱好");
		// ModelUtil.total.put("fdMemImg", "附件");
		// ModelUtil.total.put("fdStatus", "查看状态");
		//
		// ModelUtil.total.put("duties_fdTitle", "职位");
		// ModelUtil.total.put("jobs_jobName", "岗位");
		// ModelUtil.total.put("region_city", "地区");
		//
		// ModelUtil.total.put("duties&fdId", "职位");
		// ModelUtil.total.put("jobs&fdId", "岗位");
		// ModelUtil.total.put("region&fdId", "地区");
		//
		// ModelUtil.total.put("duties&fdTitle", "职位");
		// ModelUtil.total.put("jobs&jobName", "岗位");
		// ModelUtil.total.put("region&city", "地区");
	}

	public static String getUrl() {
		return TestUtil.getDelStr(com + type + "/model/" + className);
	}

}
