package com.sunnet.code.common;

import java.sql.SQLException;

import com.sunnet.code.factory.CodeManyGenerateFactory;

/**
 * 我爱生成   生成 dao  biz  controller  页面
 * 
 * @author  
 *
 */
public class CodeManyUtil
{

	/* 生成规则 -》 对应于单个实体生成 -》 true 则生成 */
	public static boolean controller =false; //控制器
	public static boolean service = false; //业务接口
	public static boolean serviceImpl =false; //业务接口实现类
	public static boolean dao = false; //db接口
	public static boolean daoImpl = false;
	public static boolean vo = false; //返回的json信息
	public static boolean entity = false; //实体类[一般不用]
	public static boolean txtContent = false; //接口说明
	public static boolean _list = true; //list页面
	public static boolean _add = false; //add页面
	public static boolean _select = false; //查看页面

	/* 权限管理 */
	public static boolean permission = true; // true是否需要新增权限 ,false 不用
	public static boolean is_permission = true; // 1个 true 3个false
	/* 初始化权限 */
	public static boolean is_ = true;
	/* 标准生成 */
	public static boolean standard=true;

	public static void main(String[] args) throws SQLException
	{
		//首先理解
		//*第一个参数是你的类名称 比如：Dome
		//*第二个参数是你的org下的模块包名称比如：dome
		//*第三个参数是你的类的说明
		//第四个是作者
		
		//我们把页面也放出来
		
		/* 自媒体 */
		//CodeManyGenerateFactory.codeGenerate("Dome", "dome", "测试", "jing");
		//CodeManyGenerateFactory.codeGenerate("Tb_filetype", "information", "分类管理", "jing");
//		CodeManyGenerateFactory.codeGenerate("Tb_link", "information", "友情链接", "jing");
//		CodeManyGenerateFactory.codeGenerate("Tb_shuffling", "information", "轮播图管理", "jing"); 
		//CodeManyGenerateFactory.codeGenerate("Tb_doc", "information", "影像管理", "jing"); 
//		CodeManyGenerateFactory.codeGenerate("Tb_member", "member", "会员管理", "jing");
 	// CodeManyGenerateFactory.codeGenerate("Vie_searchDoc", "view", "app模糊搜索", "jing");
 	 //CodeManyGenerateFactory.codeGenerate("Tre_contestaward", "competition", "赛事奖项", "jing");
//		CodeManyGenerateFactory.codeGenerate("Tb_memberconsumption", "member", "会员资金明细", "jing");
		CodeManyGenerateFactory.codeGenerate("Tb_groupdocgood", "doc", "专家推荐文件", "jing");
		/*CodeManyGenerateFactory.codeGenerate("Filmfestivalvip_pay", "doc", "打赏影展记录", "jing");*/
		/* syste */
		System.out.println("生成结束!");

		/* 系统初始化 */
		//CodeManyGenerateFactory.initAdmin();
	}

}